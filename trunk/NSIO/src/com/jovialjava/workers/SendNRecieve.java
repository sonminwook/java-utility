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
package com.jovialjava.workers;

import java.util.concurrent.Callable;

import com.jovialjava.DataResult;
import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.Sender;
import com.jovialjava.utils.params.Constants;

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
		}catch(RS232Exception e){
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.SEND_N_RECEIVE_ERROR_CODE_7, Constants.SEND_N_RECEIVE_ERR_MSG, e);
		}
	}

}
