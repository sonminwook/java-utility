package com.javainsight.tweet;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.javainsight.tweet.utils.TwitterEvents;

public class TwitterController implements Runnable{
	
	private static Logger logger = Logger.getLogger(TwitterController.class);
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	private Stack<String> updateQueue = null;
	private Stack<String> deleteQueue = null; 
	private List<TwitterEvents> folderEventList = null; 
	
	private TwitterReader reader = null;
	
	//private String directory = null;
	private int pollingTime = 15;
	private String directory2Monitor;
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	
	public TwitterController(Stack<String> load,
			 				Stack<String> unload,
			 				List<TwitterEvents> eventQueue,
			 				String directory2Monitor,
			 				TwitterReader prop,
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
		TwitterMonitorThread folderMonitor = new TwitterMonitorThread(this.directory2Monitor,
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
					for(TwitterEvents event : folderEventList){
						switch(event){
						case REVERT:{
							logger.debug("Twitter Revert Command");
							for(String file : this.updateQueue){
								logger.debug("File Name is "+ file);							
							}
							break;
						}
						case DELETE:{
							logger.debug("Twitter Delete Command");
							for(String file : this.deleteQueue){
								logger.debug("File Name is "+ file);							
							}
							break;							
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
