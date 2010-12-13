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

public enum Parity {
		NONE(SerialPort.PARITY_NONE, "NoParity")
	,	ODD(SerialPort.PARITY_ODD,	"OddParity")
	,	EVEN(SerialPort.PARITY_EVEN, "EvenParity")
	,	MARK(SerialPort.PARITY_MARK, "MarkParity")
	,	SPACE(SerialPort.PARITY_SPACE, "SpaceParity")
	;
		
		private final int	nbits ;
		private final String name ;
		
		Parity( int nbits, final String name )
		{ this.nbits = nbits ; this.name = name ;  }
		
		public final int getValue()
		{	return nbits ;	}

		public final String toString()
		{	return name ;	}

		public static Parity	parse( final String value ){
			if( "0".equals(value) || "zero".equalsIgnoreCase(value) || "none".equalsIgnoreCase(value) ) 
				return NONE ;
			else if( "1".equals(value) || "odd".equalsIgnoreCase(value) ) 
				return ODD ;
			else if( "2".equals(value) || "even".equalsIgnoreCase(value) ) 
				return EVEN ;
			else if( "3".equals(value) || "mark".equalsIgnoreCase(value) ) 
				return MARK ;
			else if( "4".equals(value) || "space".equalsIgnoreCase(value) ) 
				return SPACE ;
			else
				return NONE;
		}

}
