package com.javainsight.monitor.utils;

import java.util.*;
import java.io.*;


public class CheckModification {
	
	private Stack<String> fileAddedQueue = null;
	private Stack<String> fileModifiedQueue = null;
	private Stack<String> fileDeletedQueue = null;
	private LinkedList<FileEvents> fileEventQueue = null;	
	
	
	public CheckModification(Stack<String> fileAddedQueue, 
							 Stack<String> fileModifiedQueue,
							 Stack<String> fileDeleteQueue,
							LinkedList<FileEvents> fileEventQueue){
		this.fileAddedQueue = fileAddedQueue;
		this.fileModifiedQueue = fileModifiedQueue;
		this.fileDeletedQueue = fileDeleteQueue;
		this.fileEventQueue = fileEventQueue;
		
		//-----------CLEARING EVERYTHING FIRST---------
		this.fileAddedQueue.clear();
		this.fileDeletedQueue.clear();
		this.fileModifiedQueue.clear();
		this.fileEventQueue.clear();
	}
	
	public boolean check(String folderPath, Map<String, Long> lastLoopResults){
		File directory = new File(folderPath);
		if(directory.isDirectory()){
			    String[] fileNames = directory.list();				
				//----------SIZE CHECK--------------
				if(fileNames.length == lastLoopResults.size()){
					//logger.debug("No File(s) has/have been added or Deleted, Checking for modification");
					alertModification(folderPath, fileNames, lastLoopResults);
				}else if(fileNames.length > lastLoopResults.size()){
					//logger.debug("New File(s) has/have been added");
					alertAdd(fileNames, lastLoopResults);
				}else{
					//logger.debug("File(s) has/have been deleted");
					alertDeleted(fileNames, lastLoopResults);
				}
			}else{
				IllegalArgumentException e = new IllegalArgumentException(directory +" is not a direcotry ");
				StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"check", this.getClass().getPackage().toString(), -1);
				e.setStackTrace(new StackTraceElement[]{stackTrace});			
	       	 	throw e;			
			}
		
		//--------------ALERTING THE CALLING METHOD----------
		if(this.fileEventQueue.size() != 0){
			return true;
		}else{
			return false;
		}
	}
	
	private void alertAdd(String[] fileNames, Map<String, Long> lastLoopResults){
		Set<String> lastLoopFileNames = lastLoopResults.keySet();
		for(String fileName : fileNames){
			if(!lastLoopFileNames.contains(fileName)){
				fileAddedQueue.push(fileName);
			}
		}
		fileEventQueue.add(FileEvents.ADDED);
	}

	private void alertDeleted(String[] fileNames, Map<String, Long> lastLoopResults){
		Set<String> lastLoopFileNames = lastLoopResults.keySet();
		boolean isDeleted = false;
		for(String str : lastLoopFileNames){
			isDeleted = true;
			for(String newFile : fileNames){
				if(str.equalsIgnoreCase(newFile)){
					isDeleted = false;
					break;
				}
			}
			if(isDeleted){
				this.fileDeletedQueue.push(str);
			}
		}
		this.fileEventQueue.add(FileEvents.DELETED);
	}

	private void alertModification(String folderPath,String[] fileNames, Map<String, Long> lastLoopResults){
		for(String name : fileNames){
			File file = new File(folderPath + File.separator + name);
			long modifiedTime = file.lastModified();
			if(!(modifiedTime == (long)lastLoopResults.get(name))){
				this.fileModifiedQueue.push(name);
			}
		}
		//logger.debug("no of files modified "+ fileModifiedQueue.size());
		if(this.fileModifiedQueue.size()!= 0){
		this.fileEventQueue.add(FileEvents.MODIFIED);
		}
	}
}
