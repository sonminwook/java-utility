package com.javainsight.reader;

import java.io.File;
import java.io.IOException;
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

import com.javainsight.enums.events.FolderEvent;
import com.javainsight.handler.ConfigurationConstants;
import com.javainsight.interfaces.Bean;
import com.javainsight.interfaces.PropHandler;
import com.javainsight.tweet.TwitterHandler;
import com.javainsight.tweet.TwitterReader;

/**
 * This is the main class of program which will be used by User.
 * User has only two visible methods : 
 * <br><b>returnMapValue(....)<br>
 * shutDown(....)</b><br>
 * Application will start when user creates an Object of PropReader class.
 * User has to pass the directory path( Relative or direct path) where properties files are stored<br>
 * Calling the constructor will initiates a chain of threads, so
 * In order to have efficient Memory and CPU utilization, User is recommended to call
 * either System.exit(...) or shutDown method of this class.
 * @author  abc
 * @company Java~Insight
 * @version 1.0
 */

public class PropReader {

	//---------------------Queue Variables BETWEEN CONTROLLER AND PROP READER--------------------
	private Stack<String> load = new Stack<String>();
	private Stack<String> unload = new Stack<String>();
	private List<FolderEvent> eventQueue = new ArrayList<FolderEvent>();
	
	//-----------------==LOCK and CONIDITON BETWEEN 
	private final Lock updateLock = new ReentrantLock();
	private Condition loadCondition = updateLock.newCondition();
	
	//-------- SCHEDULOR---------------------
	private Controller detectiveOO7 = null;
	private TwitterReader twitterReader = null;
	
	private final Map<String, Bean> propMap = new ConcurrentHashMap<String, Bean>();
	private ConcurrentHashMap<Bean, PropHandler> beanPropHandlerMap = new ConcurrentHashMap<Bean, PropHandler>();
	private String location = null;
	private static Logger logger = Logger.getLogger(PropReader.class);
	private boolean priorityFlag = false;
	
	private static boolean needCloud = false;
	private static boolean needTwitter = false;
	private static boolean closeCloudAfterFirstTime = false;
	
	/*
	 * Static block to check whether Cloud and Twitter are required or not
	 */
	static{
		try{
			needCloud = ConfigurationConstants.needCloud; 
			needTwitter = ConfigurationConstants.needTwitter;
			closeCloudAfterFirstTime = ConfigurationConstants.closeCloudAfterFirstTime;
		}catch(Exception e){
			logger.error("Problem while reading class \"ConfigurationConstants.class\", Please make sure it is in classpath");
			logger.error(e.getMessage());
		}
	}
		
	/**
	 * This is the heart of application. In order to start, all it needs is location of properties
	 * file either relative or direct path.<br>
	 * This program will start a Folder monitor Thread - which will keep track of changes at folder level.<br>
	 * This program will also initiate a thread which will upload the properties file into cache and bind the cache to POJO.
	 * @param location : Directory where properties files are stored. Either direct or relative path.
	 */	
	public PropReader(String location, int timePeriod) {
	 this.location = location;
	 detectiveOO7 = new Controller(load, unload, eventQueue, location, this, timePeriod, needCloud, closeCloudAfterFirstTime);
	 new Thread(detectiveOO7).start();
	 if(needTwitter){
		 twitterReader = new TwitterReader(this.location, timePeriod*4);
		 twitterReader.isDownloaded();
	 }
	 
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
		        logger.info("~~~~~~JCache READER - SHUT DOWN COMPLETED~~~~~~");
		    }});
	}


	/**
	 * This is the method which will upload the properties file into Cache and will bind
	 * cache to POJO. This method will share a lock and condition object with ReturnMapValue 
	 * method to avoid concurrent access to HashMap.
	 * Following precautions has been taken to avoid concurrent HashMap access problem:
	 * ConcurrentHashMap has been choosen.
	 * UnmodifiableHashMap has been returned to caller so that other party doesn't modify it. 
	 */
	void loadPropertiesFile() throws IOException, InstantiationException,
											IllegalAccessException,
											ClassNotFoundException
											{
		this.priorityFlag = true;
		updateLock.lock();
		logger.debug("UPDATE HAS THE LOCK");
		// Added for tweeting capabilities
		List<String> errTweetLines = new ArrayList<String>();
		try{
			logger.debug("Loading the properties file");
			for(FolderEvent event : eventQueue){
					logger.debug("Event size "+eventQueue.size());
					switch(event){
					case LOAD:{
						for(String name : load){							
							try {
								String className = name.substring(0, name.indexOf(".")).concat("Handler");
								PropHandler handler = null;
								if(propMap.get(name)== null){
									//----MEANS This is the first time.
									try {
										handler=(PropHandler)Class.forName("com.javainsight.handler."+className).newInstance();
										handler.loadPropertiesFile(location + File.separator+ name);
										handler.initialize();
										handler.addToSession(propMap);
										beanPropHandlerMap.put(propMap.get(name), handler);
									} catch (ClassNotFoundException e) {
										logger.info("No Handler/Bean has been defined for the ["+name+"] properties file");
										logger.error("IGNORE -->"+ e.getMessage());									
									}								
								}else{
									handler = beanPropHandlerMap.get(propMap.get(name));
									handler.loadPropertiesFile(location + File.separator+ name);
									handler.initialize();
									handler.addToSession(propMap);
									// -- Twitter update
									if(needTwitter){
										twitterReader.update(name);
									}
								}
							} catch (StringIndexOutOfBoundsException e) {
								logger.info(name + " doesn't seem to be a valid property file name");
								logger.error("IGNORE -->"+ e.getMessage());	
								errTweetLines.add("Invalid Config File Name ["+name+"], It won't be uploaded into cache");
							} catch(Exception e){
								logger.info(name + " can not be uploaded into cache");
								logger.error("IGNORE -->"+ e.getMessage()+ "|"+ e.getClass());
								errTweetLines.add("Err for ["+name+"]"+e.getMessage()+ "|"+ e.getClass());
							}
														
						} //End of For Loop for Load Class
							break;
					}
					case UNLOAD :{
						for(String name : unload){
								propMap.remove(name);
								//-- Twitter Reader
								if(needTwitter){
									twitterReader.delete(name);
								}
							}
							break;
					}
				}
			}			
			//-------------------CLEAR THE QUEUES--------------
			eventQueue.clear();
			load.clear();
			unload.clear();
		}finally{
			//-----------------NOTIFY the returnMapValue-----------
			this.priorityFlag=false;
			loadCondition.signal();
			updateLock.unlock();
			logger.info("Files uploaded into Cache");
			logger.debug("UPDATE RELEASED THE LOCK");
			new TwitterHandler().handle(errTweetLines);
		}		
	}
	
	/**
	 * This method will return an UNMODIFIABLE HASH MAP.
	 * <b><h1>~~PLEASE DO NOT MODIFY ANYTHING IN THE MAP~~</h1></b><br>
	 * One can get the DTO/POJO object of each properties file from this map.
	 * Key of the DTO/POJO object will be the name of properties file.
	 */
	public Map<String, Bean> returnMapValue(){
		updateLock.lock();
		try{
			if(propMap.size() == 0){
				try {
					logger.debug("Waiting for properties file to upload...");
					logger.info("Reader on HOLD");
					loadCondition.await();
					logger.info("Reader UNHOLD");
				} catch (InterruptedException e) {
					 StackTraceElement stackTrace = new StackTraceElement("PropReader","returnMapValue", "com.javainsight.reader", -1);
		        	 e.setStackTrace(new StackTraceElement[]{stackTrace});
		        	 logger.debug("!!!!!!!!!!!!!!!Exception!!!!!!!!!!!!!!!!");
			           logger.debug("Message "+ e.getLocalizedMessage());
			           for(int i=1; i<=e.getStackTrace().length; i++){
			            	System.out.format("Trace %s: "+e.getStackTrace()[i-1]+"\n", i++);
			            }
			           logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
		        	 	 
				}
			}
			//----------WILL WAIT UNTILL PROPERTIES FILE ARE UPLOADED----------
			if(priorityFlag){
				loadCondition.await();
			}			
		}catch(InterruptedException e){
			 StackTraceElement stackTrace = new StackTraceElement("PropReader","returnMapValue", "com.javainsight.reader", -1);
        	 e.setStackTrace(new StackTraceElement[]{stackTrace});
        	 logger.debug("!!!!!!!!!!!!!!!Exception!!!!!!!!!!!!!!!!");
	           logger.debug("Message "+ e.getLocalizedMessage());
	           for(int i=1; i<=e.getStackTrace().length; i++){
	            	System.out.format("Trace %s: "+e.getStackTrace()[i-1]+"\n", i++);
	            }
	           logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"); 
		}finally{			
			updateLock.unlock();			
		}
		return Collections.unmodifiableMap(propMap);
	}

	//----------------------RUNNING THE GARBAGE COLLECTOR-----------------
	protected void finalize(){
		logger.info("Inside finalize method, killing the child threads");
		detectiveOO7.graceFullShutDown();
		
	}
	
	/**
	 * This method will trigger a grace full shutdown 
	 * sequence of all threads.
	 */
	public void shutDown(){
		logger.info("SHUTTING DOWN....");
		detectiveOO7.graceFullShutDown();
	}
	
	/**
	 * this method is to do Force upload.
	 */
	public void refresh(){
		updateLock.lock();
		try{
			detectiveOO7.refresh();
			updateLock.unlock();
			this.loadPropertiesFile();
		}catch(Exception iae){
			logger.error("Refresh action can not be performed");
			logger.error("EXCEPTION", iae);
		}
		finally{
			detectiveOO7.unlock();
		}
	}

}
