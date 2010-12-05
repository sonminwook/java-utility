package com.javainsight.exceptions;

public class RS232Exception extends Exception{

	private String errorCode = null;
		
	public static final long serialVersionUID = 34230892308523L;
	
	public RS232Exception(String errorCode, String errorMessage){
		super(errorMessage);
		this.errorCode = errorCode;
		
	}
	
	public RS232Exception(String errorCode, Exception e){
		super(e.getMessage());
		this.errorCode = errorCode;
		this.setStackTrace(e.getStackTrace());
	}
	
	public RS232Exception(String errorCode, String message, Exception e){
		super(message + "\r\n" + e.getMessage());
		this.errorCode = errorCode;
		this.setStackTrace(e.getStackTrace());
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}

	
	

	
}
