package com.javainsight.reader;

import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.javainsight.cloud.CloudReader;
import com.javainsight.enums.events.FolderEvent;
import com.javainsight.monitor.FolderMonitorThread;

public class Controller implements Runnable{

	private Stack<String> load = null;
	private Stack<String> unload = null;
	private List<FolderEvent> folderEventList = null;
	private String directory2Monitor = null;
	private PropReader prop = null;
	private int timePeriod = 5;
	private CloudReader cloud = null;
	
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	
	private static Logger logger = Logger.getLogger(Controller.class);
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	
	public Controller(Stack<String> load,
					 Stack<String> unload,
					 List<FolderEvent> eventQueue,
					 String directory2Monitor,
					 PropReader prop,
					 int timePeriod,
					 CloudReader cloud){
		this.load = load;
		this.unload = unload;
		this.folderEventList = eventQueue;
		this.directory2Monitor = directory2Monitor;		
		this.prop = prop;
		this.timePeriod = timePeriod;
		this.cloud = cloud;
	}
	
	//----------------STARTING TO MONITOR THE THREAD---------------------
	public void run() {
		//-----------------FOR FIRST TIME UPLOAD-----------------------
		/*
		 * It is guarnted that all the 3 queues will be empty after this.
		 * folderEventList, load, and unload.
		 */
		firstTime();
		
		/*
		 * Here we will start the FolderMonitorThread, which will go and check
		 * after every 5 seconds for modifications.
		 */
		try{
			startMonitoring(directory2Monitor);
			}catch(Exception e){
				e.printStackTrace();
		}
		/*
		 * We will go in an do an Event based programming here,
		 * Whenever there is a fileEvent notified by FolderMonitor
		 * thread, this loop will resume function otherwise it will stay
		 * in hold status and will leave all the blocked resources of 
		 * system. 
		 */
INFINITE_LOOP:while(true){
				this.proceed.lock();
				try{
					if(this.folderEventList.isEmpty()){
						this.isProceed.await();						
					}
					for(FolderEvent event : folderEventList){
						switch(event){
						case LOAD:{
							logger.debug("Following files have been Added");
							for(String fileName : this.load){
								logger.debug("File Name is "+ fileName);							
							}
							break;
						}
						case UNLOAD:{
							logger.debug("Following files have been deleted");
							for(String fileName : this.unload){
								logger.debug("File Name is "+ fileName);							
							}
							break;
						}
						case EXIT:{
							logger.debug("Doing a gracefull shutdown");
							break INFINITE_LOOP;
						}
						}					
					}
					this.prop.loadPropertiesFile();					
				}catch(Exception e){
						logger.error("Exception while controller thread interacting with folder monitor thread");
						logger.error("Exception", e);					
				}finally{
					this.proceed.unlock();
				}
			}		

	}
	
	private void firstTime(){
		if(this.cloud != null){
			cloud.isDownloaded();
		}else{
			logger.error("Cloud monitoring has been turned off");
		}
		
		logger.debug(":::::::::::::::::::::::::::::::::::");
		logger.debug("Monitor Thread has been started");
		logger.debug("Directory under monitor is :"+ directory2Monitor);
		
		File f = new File(directory2Monitor);
		if(f.isDirectory()){
			for(File file : f.listFiles()){
				if(!(file.isDirectory())){
					logger.debug(file);
					this.load.push(file.getName());
				}
			}			
			if(this.load.size() > 0){
				this.folderEventList.add(FolderEvent.LOAD);
			}
			logger.debug(":::::::::::::::::::::::::::::::::::");
		}else{
			logger.error(directory2Monitor + " is not a direcotry\n" );			
		}
	}
	
	private void startMonitoring(String directory) throws Exception{
		FolderMonitorThread folderMonitor = new FolderMonitorThread(directory,
																	this.load,
																	this.unload,
																	this.folderEventList,
																	this.proceed,
																	this.isProceed);
		executorPool.scheduleWithFixedDelay(folderMonitor, 0, timePeriod, TimeUnit.SECONDS);
	}
	
	void graceFullShutDown(){
		logger.info("SHUTTING DOWN Executor");
		executorPool.shutdown();
		this.folderEventList.add(FolderEvent.EXIT);
		this.proceed.lock();
		try{
		this.isProceed.signal();
		}finally{
			this.proceed.unlock();
		}
		//executorPool.shutdown();
	}
	
	void refresh(){
		this.proceed.lock();
		this.firstTime();		
	}
	
	void unlock(){
		this.proceed.unlock();
	}
}
