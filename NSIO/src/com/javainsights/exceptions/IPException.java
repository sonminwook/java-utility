package com.javainsights.exceptions;

public class IPException extends Exception {

public static final long serialVersionUID = 34030892308523L;
	
	public IPException(String errorCode, String errorMessage){
		super(errorMessage);		
	}
	
	public IPException(String errorCode, Exception e){
		super(e.getMessage());		
		this.setStackTrace(e.getStackTrace());
	}
	
	public IPException(String errorCode, String message, Exception e){
		super(message + "\r\n" + e.getMessage());		
		this.setStackTrace(e.getStackTrace());
	}		
}
