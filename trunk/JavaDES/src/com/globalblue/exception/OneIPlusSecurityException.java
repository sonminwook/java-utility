package com.globalblue.exception;

import com.globalblue.constants.Constant;

public class OneIPlusSecurityException extends Exception{

	public static final long serialVersionUID = 34230892308523L;
		
		public OneIPlusSecurityException(String message, Exception e){
			super(message + Constant.lineSeparator+ e.getMessage());		
			this.setStackTrace(e.getStackTrace());
		}	
	}