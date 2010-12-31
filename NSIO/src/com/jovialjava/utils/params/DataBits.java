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