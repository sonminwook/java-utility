package com.thegoodcode.ipserialswitch.back.serial.utils;

import gnu.io.SerialPort;


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