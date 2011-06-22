package com.javainsights.Junit.RnD;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareHeaders;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareResponse;

import junit.framework.TestCase;

public class InvalidLoginRequest extends TestCase {

	private String message = null;
	private Connection connection = null;
	
	protected void setUp() throws Exception {
		message = PrepareHeaders.getLoginHeader();
		message = LengthUtils.getLength(","+ message) + "," + message;
		connection = new Connection();
		ConfigDTO dto = PrepareLoginMessage.getConfig();
		connection.initialize(dto.getHostname(),
							  dto.getPort(),
							  dto.getTimeOut(),
							  dto.getCertName(),
							  dto.getPassword());
		super.setUp();
	}
	
	public void testInvalidRequest_1() throws Exception{
		System.out.println( "INCOMPLETE REQUEST \r\n" + message);
		String response = connection.send(message);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "54");
		connection.disconnect();
	}
	
	public void testInvalidRequest_2() throws Exception{		
		String msg = PrepareHeaders.getLoginHeader()+ "," + "!@$@#%";
		msg = LengthUtils.getLength(","+ msg) + "," + msg;		
		System.out.println( "INVALID REQUEST \r\n" + message);
		String response = connection.send(msg);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "54");
		connection.disconnect();
	}
	
	public void testRequestWithoutLength() throws Exception{
		
		try {
			String msg = PrepareHeaders.getLoginHeader()+ "," + "!@$@#%";			
			System.out.println( "INVALID REQUEST \r\n" + message);
			String response = connection.send(msg);	
			System.out.println("RESPONSE - "+ response);
			PrepareResponse.check(message, response, "54");
			connection.disconnect();
		} catch (NullPointerException e) {		
			//Its expected so its success
		}
	}
	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
