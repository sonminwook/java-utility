package com.javainsight.cloud;

import java.io.File;
import java.io.Reader;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.enums.events.FolderEvent;

public class TestCloud {
	
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	private static Logger logger = Logger.getLogger(TestCloud.class);
	private Stack<SpreadsheetEntry> updateQueue = new Stack<SpreadsheetEntry>();
	private Stack<SpreadsheetEntry> deleteQueue = new Stack<SpreadsheetEntry>();
	private List<FolderEvent> folderEventList = null;
	
	private Reader reader = null;
	
	//private String directory = null;
	private int pollingTime = 0;
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	

	public static void main(String[] args) {
		
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
		executorPool.scheduleWithFixedDelay(folderMonitor, 0, this.pollingTime, TimeUnit.SECONDS);
		/*
		 * DANGEROUS --> Infinite Loop "Handle Carefully"		
		 */
INFINITE_LOOP:while(true){
				//this.proceed.lock();
				try{
					if(this.folderEventList.isEmpty()){
						this.isProceed.await();						
					}
					for(FolderEvent event : folderEventList){
						switch(event){
						case LOAD:{
							logger.debug("Cache Updation Alert");
							for(File file : this.updateQueue){
								//logger.debug("File Name is "+ file.getName());							
							}
							break;
						}
						case UNLOAD:{
							logger.debug("Cache Deletion Alert");
							for(File file : this.deleteQueue){
								logger.debug("File Name is "+ file.getName());							
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
					//this.reader.start();
			}catch(Exception e){						
						logger.error("Error" + e.getMessage(), e);					
				}finally{
					this.proceed.unlock();
				}			
			}		
	}
}

