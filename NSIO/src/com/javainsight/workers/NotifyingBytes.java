package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.DataResult;
import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class NotifyingBytes implements Callable<DataResult> {
	
	private byte[] request = null;
	private Sender sender = null;
	private String waitString = null;
	private int timeOut = 200;
	Byte[] notifyingBytes = null;
	
	public NotifyingBytes(byte[] request,
					Sender sender,
					String waitString,
					int timeOut,
					Byte[] notifyingBytes){
		this.request = request;
		this.sender = sender;
		this.waitString = waitString;
		this.timeOut = timeOut;
		this.notifyingBytes = notifyingBytes;
	}

	@Override
	public DataResult call() throws Exception {
		
		try{
			boolean status = this.sender.sendNWait1Byte(request, waitString, timeOut, notifyingBytes);
			if(!status){
				return DataResult.NO_DATA;
			}
			String response = this.sender.getResponse();
			byte[] bytes = response.getBytes();
			for( int i = bytes.length -1; i>=0; i++){
				byte b = bytes[i];
				for(Byte notify : notifyingBytes){
					if(b == notify){
						return DataResult.BYTE_RECEIVED;
					}
				}
			}
			 return DataResult.NO_DATA;
			}catch(Exception e){	
				throw new RS232Exception(Constants.RS232_NOTIFYING_BYTES_EXCEPTION,
						Constants.NOTIFY_ERROR_MSG,
						e);
			}
	}

}
