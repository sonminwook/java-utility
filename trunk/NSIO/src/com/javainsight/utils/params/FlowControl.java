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
package com.javainsight.utils.params;

import gnu.io.SerialPort;

public enum FlowControl {
	NONE(SerialPort.FLOWCONTROL_NONE, "NoFlowControl")
,	SW(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT, "SoftwareFlowControl")
,	HW(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT, "HardwareFlowControl")
;
	
	private final int	value ;
	private final String name ;
	
	private FlowControl( int value, final String name )
	{	
		this.value = value; 
		this.name = name ;
	}
	
	public final int getValue()
	{	return value ;	}

	public final String toString()
	{	return name ;	}

	public static FlowControl	parse( final String value )		
	{
		if( "0".equals(value) || "zero".equalsIgnoreCase(value) || "none".equalsIgnoreCase(value) ) 
			return NONE ;
		else if( "1".equals(value) || "software".equalsIgnoreCase(value) || "sw".equalsIgnoreCase(value) ) 
			return SW ;
		else if( "2".equalsIgnoreCase(value) || "hardware".equalsIgnoreCase(value) || "hw".equalsIgnoreCase(value) || "rtscts".equalsIgnoreCase(value) ) 
			return HW ;
		else
			return NONE;
	
		
	}
}