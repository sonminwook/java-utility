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

import com.jovialjava.DataResult;
import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.Sender;
import com.jovialjava.utils.params.Constants;

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
			boolean status = this.sender.sendNWait1Byte(request, waitString, timeOut, Constants.ENQ);
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
				throw new RS232Exception(Constants.ENQ_ERROR_CODE_3, Constants.ENQ_ERROR_MSG, e);
			}
			
	}

}
