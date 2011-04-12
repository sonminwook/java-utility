package com.javainsights.iputils.contants;


public class ErrorCodeConstant {
	
	private static final String COMMUNICATION_ERR = "04";
	private static final String HOST_ERR = "05";
	private static final String TIME_OUT_ERR = "10";
	private static final String UNKNOWN_ERR_FROM_TERMINAL = "12";
	
	public static final String lineSeparator = System.getProperty ( "line.separator" );
	
	private static final String EXCEPTION = lineSeparator+"!!!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!!!!!!!"+lineSeparator;
	private static final String ERRORCODE = lineSeparator+"!!!!!!!!!!!!!!!!!";
	private static final String ERRORTAIL = "!!!!!!!!!!!!!!!!!";
	
	public static final String COMMUNICATION_ERROR = EXCEPTION + "   COMMUNICATION ERROR" +  ERRORCODE + COMMUNICATION_ERR +ERRORTAIL;
	public static final String HOST_ERROR = EXCEPTION + "   HOST ERROR" +  ERRORCODE + HOST_ERR +ERRORTAIL;
	public static final String TIME_OUT_ERROR = EXCEPTION + "   TIME OUT ERROR" +  ERRORCODE + TIME_OUT_ERR +ERRORTAIL;
	public static final String UNKNOWN_ERROR_FROM_TERMINAL = EXCEPTION + "   UNKNOWN ERROR FROM TERMINAL" +  ERRORCODE + UNKNOWN_ERR_FROM_TERMINAL +ERRORTAIL;
}

