package com.javainsights.Junit.RnD;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareHeaders;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareResponse;

public class TestNLoging extends TestCase implements Callable<Object>{

	private static ExecutorService executorPool= Executors.newFixedThreadPool(100);

	@Override
	public Object call() throws Exception {
		message = PrepareHeaders.getLoginHeader(true) + "," + PrepareLoginMessage.getEmployeeNumber();
		message = LengthUtils.getLength(","+ message) + "," + message;		
		System.out.println("Trying connection");	
		connection = new Connection();
		ConfigDTO dto = PrepareLoginMessage.getConfig();
		connection.initialize(dto.getHostname(),
							  dto.getPort(),
							  dto.getTimeOut(),
							  dto.getCertName(),
							  dto.getPassword());
		System.out.println( "Running the test case here with message \r\n" + message);
		String response = connection.send(message);	
		System.out.println("RESPONSE - "+ response);
		PrepareResponse.check(message, response, "02");
		connection.disconnect();
		return null;
	}


	private String message = null;
	private Connection connection = null;

	/* (non-Java doc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {			
		super.setUp();
	}

	public void testLogInRequest() throws Exception{		
		for (int i = 0; i < 100; i++) {
			Callable<Object> thread = new TestNLoging();
			executorPool.submit(thread);
		}
		Thread.sleep(10000);
		executorPool.shutdown();
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
