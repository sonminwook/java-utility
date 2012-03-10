package com.javainsight.cloud.utils;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.io.FileUtils;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.enums.events.FolderEvent;



public class CheckCloudModification {
	
	private Map<SpreadsheetEntry, Long> fileModificationMap = null;
	//private File dir = null;
//	private Collection<File> fileList = null;
	private List<SpreadsheetEntry> fileList = null;
	private Stack<SpreadsheetEntry> updateQueue = null;
	private Stack<SpreadsheetEntry> deleteQueue = null;
	private List<FolderEvent> folderEventList = null;
	
	public CheckCloudModification(Map<SpreadsheetEntry, Long> fileModificationMap,
							 				//File dir,
							 				Stack<SpreadsheetEntry> updateQueue,
							 				Stack<SpreadsheetEntry> deleteQueue,
							 				List<FolderEvent> folderEventList){
		this.fileModificationMap = fileModificationMap;
		//this.dir = dir;		
		this.updateQueue = updateQueue;
		this.deleteQueue = deleteQueue;
		this.folderEventList = folderEventList;
	}
	
	public boolean check(){
		/*
		 * Step 1: Take the latest file listing
		 */
		
		fileList = new ExcelCloud().getFileList();
		/*
		 * Step 2: Update the Queues
		 */
		this.updateQueues( );
		
		if(this.updateQueue.size() > 0 || this.deleteQueue.size() > 0){
			return true;
		}else{
			return false;
		}
		
		
	}
	
	private void updateQueues( ){
		/*
		 * Check for deletion first
		 */
		Set<SpreadsheetEntry> existingFileSet = this.fileModificationMap.keySet();
		for(SpreadsheetEntry file : existingFileSet){
			if(!this.fileList.contains(file)){				
				/*
				 * This file has been deleted
				 */
				this.deleteQueue.push(file);
				/*
				 * Remove it from File Database
				 */
				this.fileModificationMap.remove(file);
				/*
				 * Add an Event
				 */
				this.folderEventList.add(FolderEvent.UNLOAD);
			}
		}
		/*
		 * Check for new addition
		 */
		//for(File file : fileList){
		for (int i = 0; i < fileList.size(); i++) {
			SpreadsheetEntry entry = fileList.get(i);
			if(!this.fileModificationMap.containsKey(entry)){				
				/*
				 * This is a new File
				 */
				this.updateQueue.push(entry);
				/*
				 * Add an Event
				 */
				this.folderEventList.add(FolderEvent.LOAD);
			}
		}
		/*
		 * Check for modification
		 */
		for(SpreadsheetEntry file : fileList){
			Long lastModifiedTime = file.getUpdated().getValue();			
			Long previousModifiedTime = this.fileModificationMap.get(file) == null ? -1 : this.fileModificationMap.get(file);
			
			if(previousModifiedTime < 0){
				/*
				 * It was new addition, File Object has been already pushed onto Queue
				 */	
				this.fileModificationMap.put(file, lastModifiedTime);
			}else{
				if(lastModifiedTime > previousModifiedTime){
					/*
					 * File has been modified				
					 */
					this.fileModificationMap.put(file, lastModifiedTime);
					this.updateQueue.push(file);					
					/*
					 * Add an Event
					 */
					this.folderEventList.add(FolderEvent.LOAD);
				}
			}
			
		}
		/*
		 * Self stopping logic
		 */
		for(SpreadsheetEntry file : fileList){
			if(file.getTitle().getPlainText().equalsIgnoreCase(Constants.EXIT)){
				this.updateQueue.push(file);
				this.folderEventList.add(FolderEvent.EXIT);
			}
		}
		
	}
	

}
