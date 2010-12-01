package com.gr.oneInterface.workers;

import java.util.concurrent.Callable;

import com.gr.oneInterface.DataResult;
import com.gr.oneInterface.utils.Sender;

public class SendNRecieve implements Callable<DataResult> {
	
	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private Sender sender = null;
	
	public SendNRecieve(byte[] request,
						Sender send,
						String waitString,
						int waitTime){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
		}

	@Override
	public DataResult call() {
		boolean status = this.sender.sendNRecieve(this.request, this.waitString, this.waitTime);
		if(status){
			return DataResult.RESPONSE;
		}else{
			return DataResult.NO_DATA;
		}		
	}

}
