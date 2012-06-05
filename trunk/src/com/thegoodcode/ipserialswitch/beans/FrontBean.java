package com.thegoodcode.ipserialswitch.beans;

public class FrontBean {
	
	private int port = 4123;
	private int readBufferSize = 2048;
	private int sessionWriteIdleTime= 180;
	
	public int getReadBufferSize() {
		return readBufferSize;
	}
	public int getSessionWriteIdleTime() {
		return sessionWriteIdleTime;
	}
	public void setReadBufferSize(int readBufferSize) {
		this.readBufferSize = readBufferSize;
	}
	public void setSessionWriteIdleTime(int sessionWriteIdleTime) {
		this.sessionWriteIdleTime = sessionWriteIdleTime;
	}
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	

}
