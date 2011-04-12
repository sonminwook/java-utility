package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

import com.javainsights.exceptions.IPException;
import com.javainsights.iputils.IPSender;
import com.javainsights.utils.params.Constants;

public class Response implements Callable<Boolean> {

	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private IPSender sender = null;
	Byte[] notifyingBytes = null;
	private boolean isLRC = false;
	
	public Response(byte[] request,
					IPSender send,
					String waitString,
					int waitTime,
					Byte[] notifyingBytes,
					boolean isLRC){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
		this.notifyingBytes = notifyingBytes;
		this.isLRC = isLRC;
	}
	
	@Override
	public Boolean call() throws IPException {
		try{			
			if(this.notifyingBytes == null){
				if(request == null){
						if(isLRC){
							return this.sender.sendnWaitForLRC(null, waitString, waitTime, Constants.ETX);
						}else{
							return this.sender.send(null, waitTime , Constants.ETX);
						}
				}else {
						if(isLRC){														
							return this.sender.sendnWaitForLRC(request, waitString, waitTime, Constants.ETX);
						}else{
							return this.sender.send(new String(request),  waitTime, Constants.ETX);
						}
				}
			}else{
				if(request == null){
					return this.sender.send(null, waitTime , notifyingBytes);
				}else {
					if(isLRC){
						return this.sender.send(new String(request),  waitTime, Constants.ETX);
					}else{
						return this.sender.send(new String(request),  waitTime, this.notifyingBytes);
					}
				}
			}
		}catch(IPException e){
			throw e;
		}catch(Exception e){
			throw new IPException(Constants.RESPONSE_ERROR_CODE_5, Constants.RESPONSE_ERR_MSG, e);
		}
	}

}
