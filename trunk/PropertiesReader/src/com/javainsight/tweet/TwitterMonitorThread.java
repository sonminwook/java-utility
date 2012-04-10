package com.javainsight.tweet;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.javainsight.cloud.utils.ServiceFactory;
import com.javainsight.tweet.utils.CheckTwitterStatus;
import com.javainsight.tweet.utils.TwitterCloud;
import com.javainsight.tweet.utils.TwitterEvents;

public class TwitterMonitorThread implements Runnable {

	//private String directory;	
	private Map<String, Long> fileModificationMap = new ConcurrentHashMap<String, Long>();
	private boolean firstTime = true;
	private Stack<String> revertQueue = null;
	private Stack<String> deleteQueue = null;
	private CheckTwitterStatus checker = null;
	private List<TwitterEvents> folderEventList = null;
	
	//-----LOCKS and CONDITIONS BETWEEN CONTROLLER AND FOLDER MONITOR THREAD ----
	private Lock proceed = null;
	private Condition isProceed = null;
	
	String directory = null;
	
		
	private static Logger logger = Logger.getLogger(TwitterMonitorThread.class);
	
	private Map<String, List<String>> fileDataMap = null;
	
	public TwitterMonitorThread(String directory,
								Stack<String> updateQueue,
								Stack<String> deleteQueue,
								List<TwitterEvents> folderEventList,
								Lock proceed,
								Condition isProceed){
					this.directory = directory;
					this.revertQueue = updateQueue;
					this.deleteQueue = deleteQueue;
					this.folderEventList = folderEventList;
					this.proceed = proceed;
					this.isProceed = isProceed;
					
					Runtime.getRuntime().addShutdownHook(
							new Thread() {
								@Override
								public void run() {
									logger.debug("CloudMonitorThread Deamon stopped <SUCCESS>");
								}});
		}	
	
	public void run(){		
		try {
			boolean status = false;
			/*
			 * Step 1: Check if it is first time
			 */
			if(firstTime){
				//this.firstTime();
				firstTime = false;
				checker = new CheckTwitterStatus(fileModificationMap, /*new File(directory),*/ revertQueue, deleteQueue, this.folderEventList);
				//this.folderEventList.add(FolderEvent.LOAD);
				status = false;
			}else{
			
			/*
			 * Step 2: Check if any file is being modified/added/deleted
			 */	
			 status = checker.check();		 
			}

			if(status){
				 if(this.folderEventList.size() > 0){
						this.proceed.lock();
						try{
							logger.debug("Notifying Controller Deamon to update cache !");
							this.isProceed.signal();					
						}finally{
							this.proceed.unlock();
						}
					}
			 }else{
				 ServiceFactory.reset();
			 }
		} catch (Exception e) {
			logger.error("Error" ,  e);
		}
		
	}	
	
	
	/*
	 * First Time twitter reading will be performed
	 */
	private void firstTime() throws Exception{
		/*
		 * Read data of all properties file
		 */
		this.fileDataMap = new TwitterCloud().getFileList(this.directory);
		
	}
	
}