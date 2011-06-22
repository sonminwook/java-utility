/**
 * 
 */
package com.javainsights.Junit.RnD;

import junit.framework.TestCase;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareHeaders;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareResponse;

/**
 * @author sjain
 *
 */
public class TestLogIn extends TestCase {
	
	private String message = null;
	private Connection connection = null;

	/* (non-Java doc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {		
		message = PrepareHeaders.getLoginHeader() + "," + PrepareLoginMessage.getEmployeeNumber();
		message = LengthUtils.getLength(","+ message) + "," + message;
		connection = new Connection();
		System.out.println("Trying connection");
		ConfigDTO dto = PrepareLoginMessage.getConfig();
		connection.initialize(dto.getHostname(),
							  dto.getPort(),
							  dto.getTimeOut(),
							  dto.getCertName(),
							  dto.getPassword());
		super.setUp();
	}
/*
	public void testLogInRequest() throws Exception{
		System.out.println( "Running the test case here with message \r\n" + message);
		String response = connection.send(message);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "01");
		connection.disconnect();
	}	
	*/
	public void testLogInRequest() throws Exception{
		System.out.println( "Running the test case here with message \r\n" + message);
		String response = connection.send(message);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "02");
		connection.disconnect();
		Thread.sleep(2000);
	}	

	@Override
	protected void runTest() throws Throwable {
		System.out.println("Inside run test");
		super.runTest();
	}

	@Override
	public void setName(String name) {
		System.out.println("==============================================");
		System.out.println("Starting Test Case [" + name + "]");
		System.out.println("==============================================");
		super.setName(name);
	}


	@Override
	protected void tearDown() throws Exception {
		System.out.println("Inside tear down");
		super.tearDown();
	}

	
}
