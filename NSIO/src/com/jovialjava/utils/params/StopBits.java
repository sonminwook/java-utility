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

public enum StopBits {
	NONE(0, "NO StopBits"),	
	ONE(SerialPort.STOPBITS_1, 	"1-StopBit"),
	ONEHALF(SerialPort.STOPBITS_1_5, "1.5-StopBits"),
	TWO(SerialPort.STOPBITS_2, 	"2-StopBits");
	
	private final int	nbits ;
	private final String name ;
	
	private StopBits( int nbits, final String name )
	{ this.nbits = nbits ; this.name = name ; }
	
	public final int getValue()
	{	return nbits ;	}

	public final String toString()
	{	return name ;	}

	public static StopBits parse( final String value ){
		if( "0".equals(value) || "zero".equals(value) || "none".equals(value) ) 
			return NONE ;
		else if( "1".equals(value) || "one".equalsIgnoreCase(value) ) 
			return ONE ;
		else if( "1/2".equals(value) || "1.5".equalsIgnoreCase(value) ) 
			return ONEHALF ;
		else if( "2".equals(value) || "two".equalsIgnoreCase(value) ) 
			return TWO ;
		else
			return NONE;

	}
}