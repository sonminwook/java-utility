package com.GR.monitor.utils;

import java.io.IOException;
import java.util.Map;
import java.io.File;

import org.apache.log4j.Logger;

public class CollectData {
	
	private Map<String, Long> lastModifiedList;
	
	private static Logger logger = Logger.getLogger(CollectData.class);
	
	public CollectData(Map<String, Long> lastModifiedList){
		this.lastModifiedList = lastModifiedList;
		this.lastModifiedList.clear();
	}
	
	public void collect(String folderPath){
		File directory = new File(folderPath);
		if(directory.isDirectory()){
				String[] fileNames = directory.list();
					for(String name : fileNames){
						File tempFile = new File(folderPath + File.separator + name);
						lastModifiedList.put(name, tempFile.lastModified());
					}
			}else{
				IllegalArgumentException nfe = new IllegalArgumentException("Folder ["+folderPath+"] is not a directory");
				StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"collect", this.getClass().getPackage().toString(), -1);
				nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
	       	 	throw nfe;			
			}
	}
}