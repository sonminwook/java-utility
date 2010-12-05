package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.DataResult;
import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

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
	public DataResult call() throws RS232Exception{
		try{
			boolean status = this.sender.sendNRecieve(this.request, this.waitString, this.waitTime);
			if(status){
				return DataResult.RESPONSE;
			}else{
				return DataResult.NO_DATA;
			}
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_7, Constants.SEND_N_RECEIVE_ERR_MSG, e);
		}
	}

}
