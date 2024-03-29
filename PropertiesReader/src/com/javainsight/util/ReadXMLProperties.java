package com.javainsight.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * @author thegoodcode engineer
 * @Email contact@thegoodcode.com
 * @Version 1.0
 * @Date 02-Mar-2010
 * @about This program is to read properties file where ISO message is stored.</br>
 */
public class ReadXMLProperties {
		 
	private static Logger logger = Logger.getLogger(ReadXMLProperties.class);
	 /**
	   * @author thegoodcode engineer
	   * @Email contact@thegoodcode.com
	   * @Version 1.0
	   * @Date 02-Mar-2010
	   * @param fileName : Relative path of the file along with file Name.<br>
	   * 		<b>pro</b>: This the properties Object where the properties read from the file will be stored<br>
	   * @about This program is to read properties file where ISO message is stored.</br>
	   */	  
	    public static boolean readProperties(String fileName, Properties pro) 
	    														throws IOException {        
	    	try{
	            File f = new File(fileName);       
	            if(f.exists() && f.canRead() && f.isFile()){
	         	  FileInputStream in = new FileInputStream(f);
	         	   logger.debug(f.getAbsolutePath());
	               pro.loadFromXML(in);          
	               return true;
	            }
	             else{
	             	//System.out.println(f.getAbsolutePath());
	             	if(!f.exists())
	                logger.error("File not found!");
	             	else if(!f.canRead())
	             	logger.error("Can not read the file !");
	             	else if(!f.isFile())
	             	logger.error("Not a file !");
	                return false;
	             }      
	           }
	         catch(IOException e){
	           	 StackTraceElement stackTrace = new StackTraceElement("ReadXMLProperties","readProperties", fileName, -1);
	        	 e.setStackTrace(new StackTraceElement[]{stackTrace});
	        	 throw e;	 
	    }}
}
