package com.javainsight.workers;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.javainsight.DataResult;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class ENQ implements Callable<DataResult> {
	
	private byte[] request = null;
	private Sender sender = null;
	private String waitString = null;
	private int timeOut = 200;
	private static Logger logger = Logger.getLogger(ENQ.class);
	
	public ENQ(byte[] request,
					Sender sender,
					String waitString,
					int timeOut){
		this.request = request;
		this.sender = sender;
		this.waitString = waitString;
		this.timeOut = timeOut;
	}
	
	@Override
	public DataResult call() {
		try{
			boolean status = this.sender.sendNWait1Byte(request, waitString, timeOut, Constants.ENQ, Constants.ETX);
			if(!status){
				return DataResult.NO_DATA;
			}
			String response = this.sender.getResponse();			
			byte[] bytes = response.getBytes();
			for(byte b : bytes){
				if(b == Constants.ENQ){
					return DataResult.ENQ;
				}				
				if(b == Constants.ETX){
					return DataResult.RESPONSE;
				}
			}
			 return DataResult.NO_DATA;
			}catch(Exception e){	
				logger.error("!!!ERROR!!! "+ e.getMessage());
				return DataResult.FAILED;
			}
			
	}

}
