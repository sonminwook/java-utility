package com.javainsight.monitor.utils;

import java.util.Map;
import java.io.File;

public class CollectData {
	
	private Map<String, Long> lastModifiedList;
	
	
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
