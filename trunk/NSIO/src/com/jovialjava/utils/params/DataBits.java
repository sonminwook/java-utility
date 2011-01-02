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

//import com.gr.one1nterface.grecr.common.component.errorcodes.SerialErrorConstants;

public enum DataBits {
	FIVE(SerialPort.DATABITS_5,		"5-bits")
,	SIX(SerialPort.DATABITS_6,		"6-bits")
,	SEVEN(SerialPort.DATABITS_7,	"7-bits")
,	EIGHT(SerialPort.DATABITS_8,	"8-bits");
	
	private final int	nbits ;
	private final String name ;
	
	private DataBits(int nbits, final String name )
	{ this.nbits = nbits ; this.name = name ; }
	
	public final int getValue()
	{	return nbits ;	}

	public final String toString()
	{	return name ;	}

	public static DataBits parse( final String value ){
		if( "5".equals(value) ) 
			return FIVE ;
		else if( "6".equals(value) ) 
			return SIX ;
		else if( "7".equals(value) ) 
			return SEVEN ;
		else if( "8".equals(value) ) 
			return EIGHT ;
		else 
			return EIGHT;	
	}
}