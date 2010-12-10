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
package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.DataResult;
import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class ENQ implements Callable<DataResult> {
	
	private byte[] request = null;
	private Sender sender = null;
	private String waitString = null;
	private int timeOut = 200;
		
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
	public DataResult call() throws RS232Exception{
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
			}catch(RS232Exception e){
				throw e;
			}catch(Exception e){	
				throw new RS232Exception(Constants.NSIO_ERROR_CODE_3, Constants.ENQ_ERROR_MSG, e);
			}
			
	}

}
