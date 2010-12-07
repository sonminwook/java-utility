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

import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class Response implements Callable<Boolean> {
	
	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private Sender sender = null;
	
	public Response(byte[] request,
					Sender send,
					String waitString,
					int waitTime){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
	}

	@Override
	public Boolean call() throws Exception {
		try{
			if(request == null){
					return this.sender.send(waitString, waitTime , Constants.ETX);
			}else {
					return this.sender.send(request, waitString, waitTime, Constants.ETX);
			}
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_5, Constants.RESPONSE_ERR_MSG, e);
		}
	}

}
