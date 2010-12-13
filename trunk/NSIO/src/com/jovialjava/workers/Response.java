/*-------------------------------------------------------------------------
RS232Java - This program has been build to simplfy the serial communication by providing 
the implementation of common flow of serial communication world.
Copyright (C) 2010  Sunny Jain [email: xesunny@gmail.com ]

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--------------------------------------------------------------------------*/
package com.jovialjava.workers;

import java.util.concurrent.Callable;

import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.Sender;
import com.jovialjava.utils.params.Constants;

public class Response implements Callable<Boolean> {
	
	private byte[] request = null;
	private String waitString = null;
	private int waitTime = 500;
	private Sender sender = null;
	Byte[] notifyingBytes = null;
	
	public Response(byte[] request,
					Sender send,
					String waitString,
					int waitTime,
					Byte[] notifyingBytes){
		this.request = request;
		this.sender = send;
		this.waitString = waitString;
		this.waitTime = waitTime;
		this.notifyingBytes = notifyingBytes;
	}

	@Override
	public Boolean call() throws Exception {
		try{
			if(this.notifyingBytes == null){
				if(request == null){
						return this.sender.send(waitString, waitTime , Constants.ETX);
				}else {
						return this.sender.send(request, waitString, waitTime, Constants.ETX);
				}
			}else{
				if(request == null){
					return this.sender.send(waitString, waitTime , notifyingBytes);
				}else {
					return this.sender.send(request, waitString, waitTime, notifyingBytes);
				}
			}
		}catch(RS232Exception e){
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.RESPONSE_ERROR_CODE_5, Constants.RESPONSE_ERR_MSG, e);
		}
	}

}
