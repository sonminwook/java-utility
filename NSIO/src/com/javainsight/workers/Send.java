package com.javainsight.workers;


import java.util.concurrent.Callable;

import com.javainsight.utils.Sender;

public class Send implements Callable<Boolean> {
	
	private byte[] request = null;
	private Sender sender = null;
	
	public Send(byte[] request,
				Sender sender){
		this.request = request;
		this.sender = sender;
	}

	@Override
	public Boolean call() {		
		return this.sender.send(request, null, 0, (Byte[])null);		
	}

}
