package com.javainsight.cloud;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.enums.events.FolderEvent;

public class TestCloud {
	
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	private static Logger logger = Logger.getLogger(TestCloud.class);
	private Stack<SpreadsheetEntry> updateQueue = new Stack<SpreadsheetEntry>();
	private Stack<String> deleteQueue = new Stack<String>();
	private List<FolderEvent> folderEventList = new ArrayList<FolderEvent>();
	
	private Reader reader = null;
	
	//private String directory = null;
	private int pollingTime = 15;
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	

	public static void main(String[] args) {
		try{
			PropertyConfigurator.configure("config/log4j.properties");
			new TestCloud().run();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void run(){
		/*
		 * Start the folder monitoring immediately
		 */
		CloudMonitorThread folderMonitor = new CloudMonitorThread( //this.directory,
																	this.updateQueue,
																	this.deleteQueue,
																	this.folderEventList,
																	this.proceed,
																	this.isProceed);
		executorPool.scheduleWithFixedDelay(folderMonitor, 1, this.pollingTime, TimeUnit.SECONDS);
		/*
		 * DANGEROUS --> Infinite Loop "Handle Carefully"		
		 */
INFINITE_LOOP:while(true){
				this.proceed.lock();
				try{
					if(this.folderEventList.isEmpty()){
						System.err.println("on hold");
						this.isProceed.await();						
					}
					for(FolderEvent event : folderEventList){
						switch(event){
						case LOAD:{
							logger.debug("Cache Updation Alert");
							for(SpreadsheetEntry file : this.updateQueue){
								logger.debug("File Name is "+ file.getTitle().getPlainText());							
							}
							while(this.updateQueue.size() > 0){
								this.updateQueue.pop();
							}
							break;
						}
						case UNLOAD:{
							logger.debug("Cache Deletion Alert");
							for(String file : this.deleteQueue){
								logger.debug("File Name is "+ file);							
							}
							while(this.deleteQueue.size() > 0){
								this.deleteQueue.pop();
							}
							break;
							
						}
						case EXIT:{
							logger.debug("Shutdown Alert");
							executorPool.shutdownNow();							
							break INFINITE_LOOP;
						}
						}					
				}
					folderEventList.remove(0);
					//this.reader.start();
			}catch(Exception e){						
						logger.error("Error" + e.getMessage(), e);					
				}finally{
					this.proceed.unlock();
				}			
			}		
	}
}

