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
package com.jovialjava.utils.params;

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