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

import com.javainsights.exceptions.RS232Exception;
import com.javainsights.utils.Sender;
import com.javainsights.utils.params.Constants;

public class Send implements Callable<Boolean> {
	
	private byte[] request = null;
	private Sender sender = null;
	
	public Send(byte[] request,
				Sender sender){
		this.request = request;
		this.sender = sender;
	}

	@Override
	public Boolean call() throws RS232Exception{
		try{
			return this.sender.send(request, null, 0, (Byte[])null);
		}catch(RS232Exception e){
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.SEND_ERROR_CODE_6, Constants.SEND_ERR_MSG, e);
		}
	}

}
