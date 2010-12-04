package com.javainsight.utils.params;

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
