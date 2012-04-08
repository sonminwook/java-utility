package com.javainsight.tweet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.cloud.utils.Constants;
import com.javainsight.cloud.utils.ReadFile;
import com.javainsight.cloud.utils.ServiceFactory;
import com.javainsight.reader.PropReader;
import com.javainsight.tweet.utils.TwitterEvents;

public class TwitterReader {
	
	//---------------------Queue Variables BETWEEN CONTROLLER AND PROP READER--------------------
	private Stack<String> revertQueue = new Stack<String>();
	private Stack<String> deleteQueue = new Stack<String>();
	private List<TwitterEvents> eventQueue = new ArrayList<TwitterEvents>();
	
	//-----------------==LOCK and CONIDITON BETWEEN 
	private final Lock updateLock = new ReentrantLock();
	private Condition loadCondition = updateLock.newCondition();
	
	//-------- SCHEDULOR---------------------
	private TwitterController detectiveOO7 = null;	
	private String location = null;
	private static Logger logger = Logger.getLogger(TwitterReader.class);
	private boolean isReady = false;
	
	
	public TwitterReader(String location, int timePeriod) {		
		 this.location = location;
		 detectiveOO7 = new TwitterController(revertQueue, deleteQueue, eventQueue, location, this, timePeriod);
		 new Thread(detectiveOO7).start();	 
		 logger.debug("Twitter CONTROLLER thread has been started");
		 /*
		  * Adding a shutdown hook, In case of user perform System.exit(..).
		  * A grace full shutdown will be initiated.
		  */
		 Runtime.getRuntime().addShutdownHook(
				new Thread() {
			    @Override
			    public void run() {
			    	detectiveOO7.graceFullShutDown();
			        logger.info("~~~~~~JCache Twitter READER - SHUT DOWN COMPLETED~~~~~~");
			    }});
		}
	
	public void start(boolean isReady){		
		try {
			updateLock.lock();
			for(String file : revertQueue){
				try{					
					if(file.contains(".")){
						
						logger.info("Cloud Revert Update <SUCCESS> for <"+file+">");
					}else{
						
						logger.info("Cloud Revert Update <SUCCESS> for <"+file+">");
					}					
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+file+"<<", e);
					e.printStackTrace();
				}
			}
			
			for(String file : deleteQueue){
				try{					
					if(file.contains(".")){
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								file));
						logger.info("Cloud Deletion Update <SUCCESS> for <"+file+">");
					}else{
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								file + 
								Constants.SUFFIX));
						logger.info("Cloud Deletion Update <SUCCESS> for <"+file+">");
					}					
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+file+"<<", e);
					e.printStackTrace();
				}
			}
			this.revertQueue.clear();
			this.deleteQueue.clear();
			this.eventQueue.clear();
			this.isReady = isReady;
		} catch (Exception e) {			
			logger.error(" Exception while updating cache", e);
		}finally{
			ServiceFactory.reset();
			loadCondition.signal();
			updateLock.unlock();			
		}		
	}
	
	public void isDownloaded(){
		updateLock.lock();
		try{
			if(!isReady){
				try {
					logger.debug("Checking Cloud...");					
					loadCondition.await();
					logger.info("Checking completed");
				} catch (InterruptedException e) { 
			          logger.error("Exception", e);		        	 	 
				}
			}					
		}finally{			
			updateLock.unlock();			
		}
		//return Collections.unmodifiableMap(propMap);
	}
	
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("config/log4j.properties");
			new TwitterReader("config", 15).isDownloaded();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
