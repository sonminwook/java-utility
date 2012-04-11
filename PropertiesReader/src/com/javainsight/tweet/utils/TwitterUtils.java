package com.javainsight.tweet.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.javainsight.enums.events.FolderEvent;



public class TwitterUtils {
	
	private Map<String, Long> fileModificationMap = null;
	//private File dir = null;
//	private Collection<File> fileList = null;
	private List<SpreadsheetEntry> fileList = null;
	private Stack<SpreadsheetEntry> updateQueue = null;
	private Stack<String> deleteQueue = null;
	private List<FolderEvent> folderEventList = null;
	
	public TwitterUtils(Map<String, Long> fileModificationMap,
							 				//File dir,
							 				Stack<SpreadsheetEntry> updateQueue,
							 				Stack<String> deleteQueue,
							 				List<FolderEvent> folderEventList){
		this.fileModificationMap = fileModificationMap;
		//this.dir = dir;		
		this.updateQueue = updateQueue;
		this.deleteQueue = deleteQueue;
		this.folderEventList = folderEventList;
	}
	
	
	
	private void updateQueues( ){
		/*
		 * Check for deletion first
		 */
		//System.err.println("Inside update Queues");
		//System.err.println("File Map" + this.fileModificationMap);
		Set<String> existingFileSet = this.fileModificationMap.keySet();
		
		Map<String, SpreadsheetEntry> tempFileNameURLMap = new HashMap<String, SpreadsheetEntry>();
		
		for(SpreadsheetEntry entry : fileList){
			String fileName = entry.getTitle().getPlainText();
			tempFileNameURLMap.put(fileName, entry);
		}
		
		for(String fileName : existingFileSet){
			if(!tempFileNameURLMap.containsKey(fileName)){
				/*
				 * This file has been deleted
				 */
				this.deleteQueue.push(fileName);
				/*
				 * Remove it from File Database
				 */
				this.fileModificationMap.remove(fileName);
				/*
				 * Add an Event
				 */
				this.folderEventList.add(FolderEvent.UNLOAD);
			}
		}
		/*
		 * Check for new addition
		 */
		for (int i = 0; i < fileList.size(); i++) {
			SpreadsheetEntry entry = fileList.get(i);
			if(!this.fileModificationMap.containsKey(entry.getTitle().getPlainText())){				
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
			Long previousModifiedTime = this.fileModificationMap.get(file.getTitle().getPlainText()) == null ? -1 : 
																					this.fileModificationMap.get(file.getTitle().getPlainText());
			
			if(previousModifiedTime < 0){
				/*
				 * It was new addition, File Object has been already pushed onto Queue
				 */	
				this.fileModificationMap.put(file.getTitle().getPlainText(), lastModifiedTime);
			}else{
				if(lastModifiedTime > previousModifiedTime){
					/*
					 * File has been modified				
					 */
					this.fileModificationMap.put(file.getTitle().getPlainText(), lastModifiedTime);
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
