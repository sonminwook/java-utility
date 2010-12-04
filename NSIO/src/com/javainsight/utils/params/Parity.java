package com.javainsight.utils.params;

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
