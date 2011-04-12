package com.globalblue.utils;

import java.io.File;

import org.apache.log4j.Logger;

public class DeleteECRSession {

	private static Logger logger = Logger.getLogger(DeleteECRSession.class);
	
	public boolean delete(String file){
		try{
			File delFile = new File(file);
			
			if(delFile.exists()){
				if(delFile.isFile()){
					return delFile.delete();
				}else{
					logger.debug("No such File<"+file+"> found");
					return false;
				}
			}else{
				logger.debug("No such File<"+file+"> exists");
				return false;
			}
		}catch(Exception e){
			logger.debug("Unable to complete the ECRSession deletion operation ");
			logger.error(e.getMessage() + "||"+ e.getClass());
			return false;
		}
	}
}
