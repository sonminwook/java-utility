package com.javainsight.cloud;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
import com.javainsight.enums.events.FolderEvent;
import com.javainsight.reader.PropReader;

public class CloudReader {
	
	//---------------------Queue Variables BETWEEN CONTROLLER AND PROP READER--------------------
	private Stack<SpreadsheetEntry> updateQueue = new Stack<SpreadsheetEntry>();
	private Stack<String> deleteQueue = new Stack<String>();
	private List<FolderEvent> eventQueue = new ArrayList<FolderEvent>();
	
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
		 detectiveOO7 = new CloudController(updateQueue, deleteQueue, eventQueue, location, this, timePeriod);
		 new Thread(detectiveOO7).start();	 
		 logger.debug("Cloud CONTROLLER thread has been started");
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
					List<String> lines = new ReadFile().readWorkSheet(entry.getTitle().getPlainText(), null, false);
					logger.info("Cloud Update <SUCCESS> for <"+entry.getTitle().getPlainText()+">");
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
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+entry.getTitle().getPlainText()+"<<", e);
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
			new PropReader("config", 15).returnMapValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
