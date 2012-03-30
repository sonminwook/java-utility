package com.javainsight.cloud;

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

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.enums.events.FolderEvent;

public class CloudController implements Runnable{
	
	private static Logger logger = Logger.getLogger(CloudController.class);
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	private Stack<SpreadsheetEntry> updateQueue = null;
	private Stack<String> deleteQueue = null; 
	private List<FolderEvent> folderEventList = null; 
	
	private CloudReader reader = null;
	
	//private String directory = null;
	private int pollingTime = 15;
	private String directory2Monitor;
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	
	public CloudController(Stack<SpreadsheetEntry> load,
			 				Stack<String> unload,
			 				List<FolderEvent> eventQueue,
			 				String directory2Monitor,
			 				CloudReader prop,
			 				int timePeriod){
			this.updateQueue = load;
			this.deleteQueue = unload;
			this.folderEventList = eventQueue;
			this.directory2Monitor = directory2Monitor;		
			this.reader = prop;
			this.pollingTime = timePeriod;
}
	
	void graceFullShutDown(){
		
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
							logger.debug("Cloud Update Available");
							for(SpreadsheetEntry file : this.updateQueue){
								logger.debug("File Name is "+ file.getTitle().getPlainText());
							}

							break;
						}
						case UNLOAD:{
							logger.debug("Cache Deletion notification");
							for(String file : this.deleteQueue){
								logger.debug("File Name is "+ file);							
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
				this.reader.start(true);
			}catch(Exception e){						
					logger.error("Error" + e.getMessage(), e);					
				}finally{
					this.proceed.unlock();
				}			
			}		
	}


}
