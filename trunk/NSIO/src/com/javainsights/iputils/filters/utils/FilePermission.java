package com.javainsights.iputils.filters.utils;

import java.io.File;

import org.apache.log4j.Logger;

public class FilePermission {
	
	private static Logger logger = Logger.getLogger(FilePermission.class);
	
	/**
	 * This method will check Read permission on the file name passed.<br>
	 * Following 5 tests will be performed:<br>
	 * Null or Blank<br>
	 * Check if file exists<br>
	 * Check if file has valid name, must end with extensionName, this test wont be executed if extension name is null<br>
	 * Check if it is a directory<br>
	 * Check for read permission<br>
	 * @param pathOfFile : name of the file with full path
	 * @throws IllegalArgumentException :In case of any error.
	 */
	public static void checkReadFilePermission(String pathOfFile, String extensionName) throws IllegalArgumentException{
		File templateFile = null;		
		try{
			templateFile = new File(pathOfFile);
			
			/*
			 * Check for null and blank
			 */
			if(pathOfFile == null || "".equalsIgnoreCase(pathOfFile)){
				logger.error("file ["+ pathOfFile+"] is null or blank");
				throw new IllegalArgumentException("Permission checking Error on File ["+pathOfFile+"]");
			}
			
			/*
			 * Check if file exists
			 */
			if(!templateFile.exists()){
				logger.error("File ["+ templateFile.getAbsolutePath()+"] doesn't exists");
				throw new IllegalArgumentException("Permission checking Error on File ["+pathOfFile+"]");
			}
			
			/*
			 * Check if file has valid name
			 */
			if(extensionName != null){
			String name = templateFile.getName();
			if(!(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase(extensionName))){ 
				logger.error("File ["+ templateFile.getAbsolutePath()+"] doesn't have valid file name, expected to end with ["+ extensionName+"]");
				throw new IllegalArgumentException("Permission checking Error on File ["+pathOfFile+"]");
				
			}
			}
			
			/*
			 * Check if it is directory
			 */
			if(templateFile.isDirectory()){
				logger.error("File ["+ templateFile.getAbsolutePath()+"] is a directory, OneI expects file");
				throw new IllegalArgumentException("Permission checking Error on File ["+pathOfFile+"]");
			}
			
			/*
			 * Check if read permission
			 */
			if(!templateFile.canRead()){
				logger.error("File ["+ templateFile.getAbsolutePath()+"] doesn't have read persmission," +
						" attempting to change persmisson");
				if(!templateFile.setReadable(true)){
					logger.error("Failed to set permission");
					throw new IllegalArgumentException("Permission checking Error on File ["+pathOfFile+"]");
				}
			}			
		}catch(Exception e){
			logger.error("!!ERROR!! - While checking the permission on File ["+pathOfFile+"]");
			logger.error("Exception Information", e);
		}
	}
	
	
	

}
