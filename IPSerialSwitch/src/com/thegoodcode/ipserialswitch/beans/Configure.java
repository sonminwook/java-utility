package com.thegoodcode.ipserialswitch.beans;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;

import com.thegoodcode.ipserialswitch.back.serial.SerialConfig;
import com.thegoodcode.ipserialswitch.back.serial.utils.Baud;

public class Configure {
	private static Properties prop = null;
	
	private static void configureEverything() throws Exception{
		prop = new Properties();
		prop.load(new FileReader(new File("IPSerialSwitch.properties")));
		
	}
	
	public static boolean isLoggingEnabled() {
		
		return Boolean.parseBoolean(prop.getProperty("system.enableLogging","false"));
	}
	
	
	public static FrontBean getFront() throws Exception{
		if(prop == null){
			configureEverything();
		}
		FrontBean front = new FrontBean();
		front.setPort(Integer.parseInt(prop.getProperty("front.port", "4123")));
		front.setReadBufferSize(Integer.parseInt(prop.getProperty("front.buffer","2048")));
		front.setSessionWriteIdleTime(Integer.parseInt(prop.getProperty("front.cleanTime", "600")));		
		return front;
	}
	
	public static SerialConfig getSerialConfig() throws Exception{
		if(prop == null){
			configureEverything();
		}
		
		SerialConfig serial = new SerialConfig();
		
		serial.setPort(prop.getProperty("back.port"));
		serial.setBufferSize(Integer.parseInt(prop.getProperty("back.buffer","2048")));
		serial.setSessionIdleTime(Integer.parseInt(prop.getProperty("back.cleanTime", "600")));	
		
		serial.setBauds(Baud.parse(prop.getProperty("back.bauds", "9600")));
		
		switch (com.thegoodcode.ipserialswitch.back.serial.utils.DataBits.parse(prop.getProperty("back.databits", "8"))) {
		case FIVE:
			serial.setDatabits(org.apache.mina.transport.serial.SerialAddress.DataBits.DATABITS_5);
			break;
		case SIX:
			serial.setDatabits(org.apache.mina.transport.serial.SerialAddress.DataBits.DATABITS_6);
			break;
		case SEVEN:
			serial.setDatabits(org.apache.mina.transport.serial.SerialAddress.DataBits.DATABITS_7);
			break;
		case EIGHT:
			serial.setDatabits(org.apache.mina.transport.serial.SerialAddress.DataBits.DATABITS_8);
			break;
		default:
			serial.setDatabits(org.apache.mina.transport.serial.SerialAddress.DataBits.DATABITS_8);
			break;
		}
		
		switch(com.thegoodcode.ipserialswitch.back.serial.utils.Parity.parse(prop.getProperty("back.parity", "none"))){
		case EVEN:
			serial.setParity(Parity.EVEN);
			break;
		case MARK:
			serial.setParity(Parity.MARK);
			break;
		case NONE:
			serial.setParity(Parity.NONE);
			break;
		case ODD:
			serial.setParity(Parity.ODD);
			break;
		case SPACE:
			serial.setParity(Parity.SPACE);
			break;
		}
		
		switch(com.thegoodcode.ipserialswitch.back.serial.utils.StopBits.parse(prop.getProperty("back.stopbits", "1"))){
		case NONE:
			serial.setStopBits(StopBits.BITS_1);
			break;
		case ONE:
			serial.setStopBits(StopBits.BITS_1);
			break;
		case ONEHALF:
			serial.setStopBits(StopBits.BITS_1_5);
			break;
		case TWO:
			serial.setStopBits(StopBits.BITS_2);
			break;
		}
		
		switch(com.thegoodcode.ipserialswitch.back.serial.utils.FlowControl.parse(prop.getProperty("back.flowcontrol", "none"))){
		case HW:
			serial.setFlowControl(FlowControl.RTSCTS_IN_OUT);
			break;
		case NONE:
			serial.setFlowControl(FlowControl.NONE);
			break;
		case SW:
			serial.setFlowControl(FlowControl.XONXOFF_IN_OUT);
			break;
		}		
		
		return serial;
		
	}

}
