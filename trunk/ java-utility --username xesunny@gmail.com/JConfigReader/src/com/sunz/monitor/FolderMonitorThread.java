package com.GR.monitor;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.GR.enums.events.FolderEvent;
import com.GR.monitor.utils.CheckModification;
import com.GR.monitor.utils.CollectData;
import com.GR.monitor.utils.FileEvents;

public class FolderMonitorThread implements Runnable {
	
	private String folderPath = null;
	private Map<String, Long> lastModifiedList = new HashMap<String, Long>();
	private int count = 0;
		
	//-------MESSAGE QUEUES BETWEEN FOLDER MONITOR THREAD and its SUBCLASSES---------
	private Stack<String> fileAddedQueue = new Stack<String>();
	private Stack<String> fileModifiedQueue = new Stack<String>();
	private Stack<String> fileDeletedQueue = new Stack<String>();
	private LinkedList<FileEvents> fileEventQueue = new LinkedList<FileEvents>();
	
	//-------MESSAGE QUEUE BETWEEN CONTROLLER AND FOLDER MONITOR THREAD-----
	private Stack<String> load = null;
	private Stack<String> unload = null;
	private List<FolderEvent> folderEventList = null;
	
	//-----LOCKS and CONDITIONS BETWEEN CONTROLLER AND FOLDER MONITOR THREAD ----
	private Lock proceed = null;
	private Condition isProceed = null;
		
	private static Logger logger = Logger.getLogger(FolderMonitorThread.class);
	
	public FolderMonitorThread(String folderPath,
							Stack<String> load,
							Stack<String> unload,
							List<FolderEvent> folderEventList,
							Lock proceed,
							Condition isProceed){
		this.folderPath = folderPath;
		this.load = load;
		this.unload = unload;
		this.folderEventList = folderEventList;
		this.proceed = proceed;
		this.isProceed = isProceed;
	}
	
	public void run(){
		logger.debug("Count no "+count++);
		if(lastModifiedList.size() == 0){
				new CollectData(lastModifiedList).collect(this.folderPath);
				//return FolderEvent.NO_CHANGE;
			}else{
				/*
				 * This will clear the data in the queue object passed.
				 */
				CheckModification checker = new CheckModification(this.fileAddedQueue,
																  this.fileModifiedQueue,
																  this.fileDeletedQueue,
																  this.fileEventQueue);
				boolean result = checker.check(this.folderPath, this.lastModifiedList);
				if(result){
					for(int eventNum = 0; eventNum < fileEventQueue.size(); eventNum++){
						FileEvents event = fileEventQueue.pop();
						switch(event){
							case ADDED:{
								logger.debug("Following File(s) has/have been ADDED");
								for(int i=0; i< fileAddedQueue.size(); i++){
									String fileName = fileAddedQueue.pop();
									logger.debug(fileName);
									this.load.push(fileName);
									if(!this.folderEventList.contains(FolderEvent.LOAD)){
										this.folderEventList.add(FolderEvent.LOAD);
									}
								}
								break;
							}
							case DELETED:{
								logger.debug("Following File(s) has/have been DELETED");
								for(int i=0; i< fileDeletedQueue.size(); i++){
									String fileName = fileDeletedQueue.pop();
									logger.debug(fileName);
									this.unload.push(fileName);
									if(!this.folderEventList.contains(FolderEvent.UNLOAD)){
										this.folderEventList.add(FolderEvent.UNLOAD);
									}
								}
								break;
							}
							case MODIFIED:{
								logger.debug("Following File(s) has/have been MODIFIED");
								for(int i=0; i< fileModifiedQueue.size(); i++){
									String fileName = fileModifiedQueue.pop();
									logger.debug(fileName);
									this.load.push(fileName);
									if(!this.folderEventList.contains(FolderEvent.LOAD)){
										this.folderEventList.add(FolderEvent.LOAD);
									}
								}
								break;
							}
						}
						//fileEventQueue.remove(0);
					}
					/*
					 * Replacing the previous timestamp with new one.
					 */
					new CollectData(lastModifiedList).collect(this.folderPath);
					
				}
				//return FolderEvent.NO_CHANGE;	
				
			}
			logger.debug("Loop completed");
			if(this.folderEventList.size() != 0){
				this.proceed.lock();
				try{
					logger.debug("Notifying Controller thread that Data is ready");
					this.isProceed.signal();
				}finally{
					this.proceed.unlock();
				}
			}
				
				
	}
	

	
}
