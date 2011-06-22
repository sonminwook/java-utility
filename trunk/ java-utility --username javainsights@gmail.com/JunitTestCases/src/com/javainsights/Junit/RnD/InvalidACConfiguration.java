package com.javainsights.Junit.RnD;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareResponse;

import junit.framework.TestCase;

public class InvalidACConfiguration extends TestCase {

	private String message = null;
	private Connection connection = null;
	
	protected void setUp() throws Exception {
		message = PrepareLoginMessage.getLoginHeaderWithInvalidConfiguration() + "," + PrepareLoginMessage.getEmployeeNumber();
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

	public void testInvalidConfig() throws Exception{
		System.out.println( "Running the test case here with message \r\n" + message);
		String response = connection.send(message);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "53");
		connection.disconnect();
	}	

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
