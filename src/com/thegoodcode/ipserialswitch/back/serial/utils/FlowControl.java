package com.thegoodcode.ipserialswitch.back.serial.utils;

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