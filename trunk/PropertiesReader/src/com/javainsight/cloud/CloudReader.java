package com.javainsight.cloud;

import java.io.File;
import java.net.NoRouteToHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.cloud.utils.Constants;
import com.javainsight.cloud.utils.ServiceFactory;
import com.javainsight.cloud.utils.WorkSheetReader;
import com.javainsight.commons.io.FileUtils;
import com.javainsight.enums.events.FolderEvent;

public class CloudReader {
	
	//---------------------Queue Variables BETWEEN CONTROLLER AND PROP READER--------------------
	private Stack<SpreadsheetEntry> updateQueue = new Stack<SpreadsheetEntry>();
	private Stack<String> deleteQueue = new Stack<String>();
	private List<FolderEvent> eventQueue = new ArrayList<FolderEvent>();
	private List<String>	stopUpdateList = new ArrayList<String>();
	
	//-----------------==LOCK and CONIDITON BETWEEN 
	private final Lock updateLock = new ReentrantLock();
	private Condition loadCondition = updateLock.newCondition();
	
	//-------- SCHEDULOR---------------------
	private CloudController detectiveOO7 = null;	
	private String location = null;
	private static Logger logger = Logger.getLogger(CloudReader.class);
	private boolean isReady = false;
	private boolean closeCloudAfterFirstTime = false;
	
	
	
	public CloudReader(String location, int timePeriod, boolean isClosedAfterFirstTime) {		
		 this.location = location;
		 this.closeCloudAfterFirstTime = isClosedAfterFirstTime;
		 detectiveOO7 = new CloudController(updateQueue, deleteQueue, eventQueue, location, this, timePeriod, stopUpdateList);
		 new Thread(detectiveOO7).start();	 
		 logger.info("Cloud module has been started");
		 /*
		  * Adding a shutdown hook, In case of user perform System.exit(..).
		  * A grace full shutdown will be initiated.
		  */
		 Runtime.getRuntime().addShutdownHook(
				new Thread() {
			    @Override
			    public void run() {
			    	detectiveOO7.graceFullShutDown();
			        logger.info("~~~~~~JCache CLOUD READER - SHUT DOWN COMPLETED~~~~~~");
			    }});
		}
	
	public void start(boolean isReady){		
		try {
			updateLock.lock();
			for(SpreadsheetEntry entry : updateQueue){
				try{
					logger.info("Downloading <"+entry.getTitle().getPlainText()+">");
					
					List<String> lines = null;
					WorkSheetReader reader = new WorkSheetReader(entry.getTitle().getPlainText());
					synchronized (reader) {
						FutureTask<List<String>> callTask = new FutureTask<List<String>>(reader);
						callTask.run();
						for (int i = 0; i < Constants.WAITING_TIME_IN_SECONDS ; i++) {
							reader.wait(1000);
							if(callTask.isDone()){
								lines = callTask.get();
								break;
							}
						}
					if(lines == null){
							throw new NoRouteToHostException("Unable to download file from Cloud");
						}
					}
					logger.info("/Cloud Update Command/Status {VALID}/Result {SUCCESS}/File {"+entry.getTitle().getPlainText()+"}");					
					if(entry.getTitle().getPlainText().contains(".")){
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								entry.getTitle().getPlainText().substring(0, entry.getTitle().getPlainText().indexOf("."))
								+ Constants.SUFFIX));
						FileUtils.writeLines(new File(this.location + File.separator + entry.getTitle().getPlainText()), lines);
					}else{
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								entry.getTitle().getPlainText()
								+ Constants.XML_SUFFIX));												
						FileUtils.writeLines(new File(this.location + File.separator + entry.getTitle().getPlainText()+ Constants.SUFFIX), lines);
					}
				}catch(NoRouteToHostException nrthe){
					logger.error("Unable to download file from cloud, either Internet not available or firewall blocking the route to google.com [" + 
							nrthe.getMessage()+"]");
						break;
				}
				catch(ExecutionException uhe){
						logger.error("Unable to download file from cloud, either Internet not available or firewall blocking the route to google.com [" + 
									uhe.getMessage()+"]");
						break;
				}
				catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+entry.getTitle().getPlainText()+"<<", e);
				}
			}
			
			for(String file : deleteQueue){
				try{					
					if(file.contains(".")){
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								file));
						logger.info("/Cloud Delete Command/Status {VALID}/Result {SUCCESS}/File {"+file+"}");
					}else{
						FileUtils.deleteQuietly(new File(this.location +
								File.separator +
								file + 
								Constants.SUFFIX));
						logger.info("/Cloud Delete Command/Status {VALID}/Result {SUCCESS}/File {"+file+"}");
					}					
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+file+"<<", e);
					e.printStackTrace();
				}
			}
			this.updateQueue.clear();
			this.deleteQueue.clear();
			this.eventQueue.clear();
			this.isReady = isReady;
		} catch (Exception e) {			
			logger.error(" Exception while updating cache", e);
		}finally{
			ServiceFactory.reset();
			if(this.closeCloudAfterFirstTime){
				this.eventQueue.add(FolderEvent.EXIT);
			}
			loadCondition.signal();
			updateLock.unlock();
			
		}		
	}
	
	public void isDownloaded(){
		updateLock.lock();
		try{
			if(!isReady){
				try {
					//logger.debug("Checking Cloud...");					
					loadCondition.await();
					//logger.info("Checking completed");
				} catch (InterruptedException e) { 
			          logger.error("Exception", e);		        	 	 
				}
			}					
		}finally{			
			updateLock.unlock();			
		}
		//return Collections.unmodifiableMap(propMap);
	}
	
	public void update(String name){
		this.stopUpdateList.add(name);
	}
	
	/**
	 * This method will trigger a grace full shutdown 
	 * sequence of all threads.
	 */
	public void shutDown(){
		logger.info("SHUTTING DOWN....");
		detectiveOO7.graceFullShutDown();
	}

}
