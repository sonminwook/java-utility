/*-------------------------------------------------------------------------
Copyright [2010] [Sunny Jain (email:xesunny@gmail.com)]
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--------------------------------------------------------------------------*/
package com.javainsights.workers;

import java.util.concurrent.Callable;

import com.javainsights.DataResult;
import com.javainsights.exceptions.RS232Exception;
import com.javainsights.utils.Sender;
import com.javainsights.utils.params.Common;
import com.javainsights.utils.params.Constants;



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
		/*
		 * This response is in HEX. converting to ASCII
		 */
		response = response.length() == 1? "0"+response : response;
		response = Common.convertHexToString(response);
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
		}
		catch(RS232Exception e){
			throw e;
		}
		catch(Exception e){			
			throw new RS232Exception(Constants.ACK_NAK_ERROR_CODE_1, Constants.ACK_NACK_ERR_MSG, e);
		}
		
	}
}
