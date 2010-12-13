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

public enum Baud {
	BAUD_9600(9600, "9600-baud")
	,	BAUD_19200(19200, "19200-baud")
	,	BAUD_38400(38400, "38400-baud")
	,	BAUD_57600(57600, "57600-baud")
	,	BAUD_115200(115200, "115200-baud")
	;
		
		private final int		baud	;
		private final String	name	;
		
		private Baud( int baud, final String name )
		{ this.baud = baud ; this.name = name ; }
		
		public final int getValue()
		{	return baud ;	}

		@Override
		public final String toString()
		{	return name ;	}
		
		/** Factory method 
		 * @param speed String representation of the speed.
		 * @return A valid Baud instance.
		 * @throws UnknownCodeException if the passed speed is unknown.
		 */
		public static Baud parse( final String speed ) 
		{
			if( "9600".equals(speed) ) 
				return BAUD_9600 ;
			else if( "19200".equals(speed) ) 
				return BAUD_19200 ;
			else if( "38400".equals(speed) ) 
				return BAUD_38400;
			else if( "57600".equals(speed) ) 
				return BAUD_57600 ;
			else if( "115200".equals(speed) ) 
				return BAUD_115200 ;
			else
				return BAUD_9600;
			



		}
	
}
