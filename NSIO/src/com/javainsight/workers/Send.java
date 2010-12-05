package com.javainsight.workers;


import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;

import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class Send implements Callable<Boolean> {
	
	private byte[] request = null;
	private Sender sender = null;
	
	public Send(byte[] request,
				Sender sender){
		this.request = request;
		this.sender = sender;
	}

	@Override
	public Boolean call() throws RS232Exception{
		try{
			return this.sender.send(request, null, 0, (Byte[])null);
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_6, Constants.SEND_ERR_MSG, e);
		}
	}

}
