package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

import com.javainsights.exceptions.IPException;
import com.javainsights.exceptions.RS232Exception;
import com.javainsights.iputils.IPSender;
import com.javainsights.utils.params.Constants;

public class Send implements Callable<Boolean> {
	
	private byte[] request = null;
	private IPSender sender = null;
	
	public Send(byte[] request, IPSender sender){
		this.request = request;
		this.sender  = sender;
	}
	

	@Override
	public Boolean call() throws IPException {
		try{
			this.sender.send(new String(request), 0);
			return true;
		}catch(IPException e){
			throw e;
		}catch(Exception e){
			throw new IPException(Constants.SEND_ERROR_CODE_6, Constants.SEND_ERR_MSG, e);
		}
	}

}
