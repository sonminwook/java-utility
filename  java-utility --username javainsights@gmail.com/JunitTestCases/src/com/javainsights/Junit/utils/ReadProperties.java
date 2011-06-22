package com.javainsights.Junit.utils;

import java.io.*;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * @author Sunny Jain
 * @Email sjain@global-blue.com
 * @Version 1.0
 * @Date 02-Mar-2010
 * @about This program is to read properties file where ISO message is stored.</br>
 */

public class ReadProperties{
    
  /**
   * @author Sunny Jain
   * @Email sjain@global-blue.com
   * @Version 1.0
   * @Date 02-Mar-2010
   * @param fileName : Relative path of the file along with file Name.<br>
   * 		<b>pro</b>: This the properties Object where the properties read from the file will be stored<br>
   * @about This program is to read properties file where ISO message is stored.</br>
   */
  
  private static Logger logger = Logger.getLogger(ReadProperties.class);
  public static boolean readProperties(String fileName, Properties pro) throws IOException{
    try{
       File f = new File(fileName);       
       if(f.exists() && f.canRead() && f.isFile()){
    	  FileInputStream in = new FileInputStream(f);
    	  logger.debug(f.getAbsolutePath());
          pro.load(in);          
          return true;
       }
        else{
        	System.out.println(f.getAbsolutePath());
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
    	logger.error("Exception while reading the properties file :"+ e.getMessage());
    throw e;
    }
    }    
  }

