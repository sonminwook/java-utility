package com.javainsights.Junit.constants;

public class SessionConstant {
	
	/*
	 * These two values will be same in all session related to connected 
	 * clients. For example - If two clients are connected then values of these 
	 * two keys will same in session of both the clients.
	 */
	public static final String ONEI_CACHE = "JConfigReader_PropReader";
	public static final String CLIENT_SESSION_DATABASE = "ClientSessionDataBase";
	
	/*
	 * These values will be different for each client.
	 */
	public static final String DECODED_LENGTH_KEY = "DECODED_LENGTH_KEY";
	public static final String DECODED_REQUEST_KEY = "DECODED_REQUEST_KEY";
	public static final String REQUEST = "ONEINTERFACE_PAY_@TABLE_REQUEST";
	public static final String RESPONSE = "ONEINTERFACE_PAY_@TABLE_REPONSE";
	public static final String ONEI_EXCEPTION = "EXCEPTION IN ONEINTERFACE"; 
	public static final String ERROR_CODE = "ERROR CODE";
	public static final String REQUEST_HEADER = "REQUEST HEADER";	
	public static final String CLOSE_SESSION = "CLOSE SESSION WITH CLIENT";
	public static final String UNIQUE_KEY = "CLIENTS UNIQUE KEY FOR SESSION";
	
	/*
	 * Added ERROR Text for new ERROR RESPONSE
	 */
	public static final String ERROR_TEXT = "ERROR TEXT";

}
