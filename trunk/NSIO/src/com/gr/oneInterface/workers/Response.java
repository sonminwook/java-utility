package com.gr.oneInterface.workers;

import java.util.concurrent.Callable;

import com.gr.oneInterface.utils.Sender;
import com.gr.oneInterface.utils.params.Constants;

public class Response implements Callable<Boolean> {
	
	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private Sender sender = null;
	
	public Response(byte[] request,
					Sender send,
					String waitString,
					int waitTime){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
	}

	@Override
	public Boolean call() throws Exception {
		if(request == null){
				return this.sender.send(waitString, waitTime , Constants.ETX);
		}else {
				return this.sender.send(request, waitString, waitTime, Constants.ETX);
		}		
	}

}
