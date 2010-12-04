package com.javainsight.utils.params;

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