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
package com.javainsight.exceptions;

public class RS232Exception extends Exception{

	private String errorCode = null;
		
	public static final long serialVersionUID = 34230892308523L;
	
	public RS232Exception(String errorCode, String errorMessage){
		super(errorMessage);
		this.errorCode = errorCode;
		
	}
	
	public RS232Exception(String errorCode, Exception e){
		super(e.getMessage());
		this.errorCode = errorCode;
		this.setStackTrace(e.getStackTrace());
	}
	
	public RS232Exception(String errorCode, String message, Exception e){
		super(message + "\r\n" + e.getMessage());
		this.errorCode = errorCode;
		this.setStackTrace(e.getStackTrace());
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}

	
	

	
}
