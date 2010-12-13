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
package com.jovialjava.exceptions;

public class RS232Exception extends Exception{

		
	public static final long serialVersionUID = 34230892308523L;
	
	public RS232Exception(String errorCode, String errorMessage){
		super(errorMessage);		
	}
	
	public RS232Exception(String errorCode, Exception e){
		super(e.getMessage());		
		this.setStackTrace(e.getStackTrace());
	}
	
	public RS232Exception(String errorCode, String message, Exception e){
		super(message + "\r\n" + e.getMessage());
		this.setStackTrace(e.getStackTrace());
	}
	
}
