package com.sunz.Executors;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.GR.enums.events.FolderEvent;
import com.sunz.monitor.FolderMonitorThread;

public class Monitor {

	private static final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	
	private static Logger logger = Logger.getLogger(Monitor.class);
	
	//-------MESSAGE QUEUE BETWEEN CONTROLLER AND FOLDER MONITOR THREAD-----
	private Stack<String> load = new Stack<String>();
	private Stack<String> unload = new Stack<String>();
	private List<FolderEvent> folderEventList = new ArrayList<FolderEvent>();
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	
	
	public static void main(String[] args) throws Exception{
		Monitor montior = new Monitor();
		montior.startMonitoring("bin/properties");
	}
	
	public void startMonitoring(String directory) throws Exception{
		FolderMonitorThread folderMonitor = new FolderMonitorThread(directory,
																	this.load,
																	this.unload,
																	this.folderEventList,
																	this.proceed,
																	this.isProceed);
			
		ScheduledFuture<?> results = executorPool.scheduleWithFixedDelay(folderMonitor, 0, 5, TimeUnit.SECONDS);
		while(true){
			this.proceed.lock();
			try{
				if(this.folderEventList.isEmpty()){
					System.out.println("In the loop waiting for Event");
					this.isProceed.await();
				}
				for(FolderEvent event : folderEventList){
					switch(event){
					case LOAD:{
						System.out.println("Following files have been Added");
						for(String fileName : this.load){
							System.out.printf("File Name is %s\n", fileName);							
						}
						break;
					}
					case UNLOAD:{
						System.out.println("Following files have been deleted");
						for(String fileName : this.unload){
							System.out.printf("File Name is %s\n", fileName);							
						}
						break;
					}
					}					
				}
				folderEventList.clear();
				load.clear();
				unload.clear();
			}finally{
				this.proceed.unlock();
			}
		}
		

	}
}
