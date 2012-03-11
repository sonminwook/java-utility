package com.javainsight.cloud;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.cloud.utils.CheckCloudModification;
import com.javainsight.cloud.utils.ExcelCloud;
import com.javainsight.enums.events.FolderEvent;

public class CloudMonitorThread implements Runnable {

	//private String directory;	
	private Map<String, Long> fileModificationMap = new ConcurrentHashMap<String, Long>();
	private List<SpreadsheetEntry> fileList = null;
	private boolean firstTime = true;
	private Stack<SpreadsheetEntry> updateQueue = null;
	private Stack<String> deleteQueue = null;
	private CheckCloudModification checker = null;
	private List<FolderEvent> folderEventList = null;
	
	//-----LOCKS and CONDITIONS BETWEEN CONTROLLER AND FOLDER MONITOR THREAD ----
	private Lock proceed = null;
	private Condition isProceed = null;
	
		
	private static Logger logger = Logger.getLogger(CloudMonitorThread.class);
	
	public CloudMonitorThread(//String directory,
								Stack<SpreadsheetEntry> updateQueue,
								Stack<String> deleteQueue,
								List<FolderEvent> folderEventList,
								Lock proceed,
								Condition isProceed){
					//this.directory = directory;
					this.updateQueue = updateQueue;
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
				this.firstTime();
				firstTime = false;
				checker = new CheckCloudModification(fileModificationMap, /*new File(directory),*/ updateQueue, deleteQueue, this.folderEventList);
				this.folderEventList.add(FolderEvent.LOAD);
				status = true;
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
			 }
		} catch (Exception e) {
			logger.error("Error" ,  e);
		}
		
	}	
	
	
	
	private void firstTime(){
		/*
		 * Step 2 : Find out all the files present in the directory
		 */
		fileList = new ExcelCloud().getFileList();
		/*
		 * Step 3: Fill up modification time
		 */
		this.fillUpModificationTime(fileList);
		/*
		 * Step 4: Fill up the update Queue
		 */
		for(SpreadsheetEntry file : fileList){
			this.updateQueue.push(file);
		}
		
	}
	
	
	private void fillUpModificationTime(List<SpreadsheetEntry> fileList){
		
		for(SpreadsheetEntry file : fileList){
			Long lastModifiedTime = file.getUpdated().getValue();
			Long previousModifiedTime = this.fileModificationMap.get(file.getTitle().getPlainText()) == null? 0 : this.fileModificationMap.get(file.getTitle().getPlainText());
			
			if(lastModifiedTime > previousModifiedTime){
				this.fileModificationMap.put(file.getTitle().getPlainText(), lastModifiedTime);
			}else{
				System.err.println("<NOT MODIFIED> "+ file.getTitle().getPlainText());
			}
		}
		
	}

	
}