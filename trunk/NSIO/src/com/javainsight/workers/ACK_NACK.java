package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.DataResult;
import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class ACK_NACK implements Callable<DataResult> {

	private byte[] request = null;
	private Sender sender = null;
	private String waitString = null;
	private int timeOut = 200;
		
	public ACK_NACK(byte[] request,
					Sender sender,
					String waitString,
					int timeOut){
		this.request = request;
		this.sender = sender;
		this.waitString = waitString;
		this.timeOut = timeOut;
	}
	
	@Override
	public DataResult call() throws RS232Exception{
		try{
		boolean status = this.sender.sendNWait1Byte(request, waitString, timeOut, Constants.NACK, Constants.ACK);
		if(!status){
			return DataResult.NO_DATA;
		}
		String response = this.sender.getResponse();
		byte[] bytes = response.getBytes();
		for( int i = bytes.length -1; i>=0; i++){
			byte b = bytes[i];
			if(b == Constants.ACK){	
				return DataResult.ACK;
			}
			if(b == Constants.NACK){
				return DataResult.NACK;
			}			
		}
		 return DataResult.NO_DATA;
		}catch(Exception e){			
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_1, Constants.ACK_NACK_ERR_MSG, e);
		}
		
	}

}
