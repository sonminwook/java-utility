package com.thegoodcode.ipserialswitch.back.serial;

import org.apache.mina.transport.serial.SerialAddress.DataBits;
import org.apache.mina.transport.serial.SerialAddress.FlowControl;
import org.apache.mina.transport.serial.SerialAddress.Parity;
import org.apache.mina.transport.serial.SerialAddress.StopBits;

import com.thegoodcode.ipserialswitch.back.serial.utils.Baud;
import com.thegoodcode.ipserialswitch.beans.Config;

public class SerialConfig implements Config {
	
	@Override
	public String toString() {
		return "SerialConfig [port=" + port + ", bufferSize=" + bufferSize
				+ ", sessionIdleTime=" + sessionIdleTime + ", bauds=" + bauds
				+ ", databits=" + databits + ", parity=" + parity
				+ ", stopBits=" + stopBits + ", flowControl=" + flowControl
				+ "]";
	}
	private String port = null;
	private int bufferSize = 2048;
	private int sessionIdleTime= 600;
	private Baud bauds = null;
	private DataBits databits = null;
	private Parity parity = null;
	private StopBits stopBits = null;
	private FlowControl flowControl = null;
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public int getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	public int getSessionIdleTime() {
		return sessionIdleTime;
	}
	public void setSessionIdleTime(int sessionIdleTime) {
		this.sessionIdleTime = sessionIdleTime;
	}
	public Baud getBauds() {
		return bauds;
	}
	public void setBauds(Baud bauds) {
		this.bauds = bauds;
	}
	public DataBits getDatabits() {
		return databits;
	}
	public void setDatabits(DataBits databits) {
		this.databits = databits;
	}
	public Parity getParity() {
		return parity;
	}
	public void setParity(Parity parity) {
		this.parity = parity;
	}
	public StopBits getStopBits() {
		return stopBits;
	}
	public void setStopBits(StopBits stopBits) {
		this.stopBits = stopBits;
	}
	public FlowControl getFlowControl() {
		return flowControl;
	}
	public void setFlowControl(FlowControl flowControl) {
		this.flowControl = flowControl;
	}
	

}
