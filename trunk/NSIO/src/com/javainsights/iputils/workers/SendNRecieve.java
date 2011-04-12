package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

import com.javainsights.DataResult;
import com.javainsights.exceptions.IPException;
import com.javainsights.iputils.IPSender;
import com.javainsights.utils.params.Constants;

public class SendNRecieve implements Callable<DataResult> {

	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private IPSender sender = null;
	
	public SendNRecieve(byte[] request,
						IPSender send,
						String waitString,
						int waitTime){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
		}

	@Override
	public DataResult call() throws IPException{
		try{
			boolean status = this.sender.sendNRecieve(this.request, this.waitString, this.waitTime);
			if(status){
				return DataResult.RESPONSE;
			}else{
				return DataResult.NO_DATA;
			}
		}catch(IPException e){
			throw e;
		}catch(Exception e){
			throw new IPException(Constants.SEND_N_RECEIVE_ERROR_CODE_7, Constants.SEND_N_RECEIVE_ERR_MSG, e);
		}
	}

}
