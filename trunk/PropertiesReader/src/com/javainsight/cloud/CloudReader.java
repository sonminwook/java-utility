package com.javainsight.cloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.disclaimer;
import com.javainsight.cloud.utils.ReadFile;
import com.javainsight.enums.events.FolderEvent;
import com.javainsight.interfaces.Bean;
import com.javainsight.interfaces.PropHandler;

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
	
	private final Map<String, Bean> propMap = new ConcurrentHashMap<String, Bean>();
	private ConcurrentHashMap<Bean, PropHandler> beanPropHandlerMap = new ConcurrentHashMap<Bean, PropHandler>();
	private String location = null;
	private static Logger logger = Logger.getLogger(CloudReader.class);
	private boolean priorityFlag = false;
	
	public CloudReader(String location, int timePeriod) {
		 disclaimer.print();
		 this.location = location;
		 detectiveOO7 = new CloudController(updateQueue, deleteQueue, eventQueue, location, this, timePeriod);
		 new Thread(detectiveOO7).start();	 
		 logger.debug("CONTROLLER thread has been started");
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
	
	public void start() throws Exception{		
		try {
			updateLock.lock();
			for(SpreadsheetEntry entry : updateQueue){
				try{
					new ReadFile().readWorkSheet(entry.getTitle().getPlainText(), null, false);
					logger.info("Cloud Update <SUCCESS> for <"+entry.getTitle().getPlainText()+">");
					
				}catch(Exception e){
					logger.error("IGNORING EXCEPTION FOR >>"+entry.getTitle().getPlainText()+"<<", e);
					e.printStackTrace();
				}
			}
			
			for(String file : deleteQueue){
				try{
					if(this.propMap.containsKey(file)){
						this.propMap.remove(file);
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
		} catch (Exception e) {			
			logger.error(" Exception while updating cache", e);
		}finally{
			loadCondition.signal();
			updateLock.unlock();
			
		}
		
	}
	
	public Map<String, Bean> getFileMap( ){
		try{
			updateLock.lock();
			if(propMap.size() == 0){
				logger.info("Sys on HOLD, Waiting Cache Update");
				loadCondition.await();			
			}
			
		}catch(InterruptedException e){
			logger.error("Exception while updating cache", e);    	
		}finally{			
			updateLock.unlock();			
		}
		return Collections.unmodifiableMap(propMap);
	}
	
	public static void main(String[] args) {
		try {
			PropertyConfigurator.configure("config/log4j.properties");
			new CloudReader("Trying", 15).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
