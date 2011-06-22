package com.javainsights.Junit.Squirrel;

import java.security.SecureRandom;

import junit.framework.TestCase;

import com.globalblue.oneInterface.communicator.squirrel.FIFOQueue;
import com.globalblue.oneInterface.communicator.squirrel.Utils.QueueRequest;

public class TestFIFO extends TestCase {	

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testPush() throws Exception{
		for(int i=0; i < 5; i++){
			new Thread(new dummyFormatter()).start();
		}
		
		Thread.sleep(15000);
	}



	private static class dummyFormatter implements Runnable {
		public void run(){
			try {
				System.out.println("Formatter started");
				String request = Integer.toString(new SecureRandom().nextInt());
				QueueRequest req = new QueueRequest();
				req.setHost("10.44.10.13");
				req.setPort(4998);
				req.setRequest(request);
				req.setTimeout(180000);
				req.setSession(null);
				
				FIFOQueue.initialize();
				Integer myNumber = FIFOQueue.push(req);
				synchronized (myNumber) {
					myNumber.wait(10000);
					String response = FIFOQueue.getMyResponse(myNumber);
					System.out.println(myNumber + "  " + response);
				}
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
	}
}
