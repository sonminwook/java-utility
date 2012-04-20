package com.javainsight.tweet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.javainsight.cloud.utils.Constants;
import com.javainsight.commons.io.FileUtils;
import com.javainsight.reader.PropReader;
import com.javainsight.tweet.utils.TwitterCloud;
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
	private Map<String, List<String>> fileDataMap = null;
	
	
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
						File newFile = new File(this.location +File.separator +file);
						if(newFile.exists()){
							FileUtils.writeLines(newFile, this.fileDataMap.get(file));
							logger.info("/Twitter Revert Command/Status {VALID}/Result {SUCCESS}/File {"+file+"}");
						}else{
							logger.info("/Twitter Revert Command/Status {INVALID}");
						}
					}else{
						File newFile = new File(this.location +File.separator +file + Constants.SUFFIX);
						if(newFile.exists()){
							FileUtils.writeLines(newFile, this.fileDataMap.get(file + Constants.SUFFIX));
							logger.info("/Twitter Revert Command/Status {VALID}/Result {SUCCESS}/File {"+file+"}");
						}else{
							logger.info("/Twitter Revert Command/Status {INVALID}");
						}
					}					
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+file+"<<", e);
				}
			}
			
			for(String file : deleteQueue){
				try{					
						File newFile = new File(this.location + File.separator +file);
						if(newFile.exists()){
							FileUtils.deleteQuietly(newFile);
							logger.info("/Twitter Delete Command/Status {VALID}/Result {SUCCESS}/File {"+file+"}");
						}else{
							logger.info("/Twitter Delete Command/Status {INVALID}");
						}				
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+file+"<<", e);
				}
			}
			this.revertQueue.clear();
			this.deleteQueue.clear();
			this.eventQueue.clear();
			this.isReady = isReady;
		} catch (Exception e) {			
			logger.error(" Exception while updating cache", e);
		}finally{
			//ServiceFactory.reset();
			loadCondition.signal();
			updateLock.unlock();			
		}		
	}
	
	public void isDownloaded(){
		updateLock.lock();
		try{
			if(!isReady){
				try {
					this.update();
					logger.debug("Checking Cloud...");					
					loadCondition.await();
					logger.info("Checking completed");
				} catch (Exception e) { 
			          logger.error("Exception", e);		        	 	 
				}
			}					
		}finally{			
			updateLock.unlock();			
		}
		//return Collections.unmodifiableMap(propMap);
	}
	
	/*
	 * First Time twitter reading will be performed
	 */
	private void update() throws Exception{
		/*
		 * Read data of all properties file
		 */
		this.fileDataMap = new TwitterCloud().getFileList(this.location);
	}
	
	public void update(String fileName){
		try{
			List<String> fileData = FileUtils.readLines(new File(this.location + File.separator + fileName));
			System.err.println("updated File >"+ fileName+" with "+ fileData.size() + " lines");
			
			this.fileDataMap.put(fileName, fileData);
		}catch(IOException ioe){
			logger.error("Unable to update file data", ioe);
		}
	}
	
	public void delete(String fileName){
		this.fileDataMap.remove(fileName);
	}
	
	
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("config/log4j.properties");
			new PropReader("config", 15).returnMapValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
