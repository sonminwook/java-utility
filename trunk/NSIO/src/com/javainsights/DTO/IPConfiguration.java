package com.javainsights.DTO;

public class IPConfiguration {
	
	private String IPAddress = null;
	private int port = 0;
	private int inputBufferSize = 1024;
	private int outputBufferSize = 1024;
	private String sslCertificate = null;
	private String password = null;
	private boolean SSL = false;
	private int connectionTimeOut = 10000;
	
	public IPConfiguration(){
		this.IPAddress = "localhost";
		this.port = 443;
	}

	public String getIPAddress() {
		return IPAddress;
	}

	public int getPort() {
		return port;
	}

	public int getInputBufferSize() {
		return inputBufferSize;
	}

	public int getOutputBufferSize() {
		return outputBufferSize;
	}

	public void setIPAddress(String address) {
		IPAddress = address;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setInputBufferSize(String inputBufferSize) {
		try{
			this.inputBufferSize = Integer.parseInt(inputBufferSize);
		}catch(Exception e){
			this.inputBufferSize = 1024;
		}
	}

	public void setOutputBufferSize(String outputBufferSize) {
		try{
			this.outputBufferSize = Integer.parseInt(outputBufferSize);
		}catch(Exception e){
			this.outputBufferSize = 1024;
		}
	}

	public String getSslCertificate() {
		return sslCertificate;
	}

	public String getPassword() {
		return password;
	}

	public boolean isSSL() {
		return SSL;
	}

	public void setSslCertificate(String sslCertificate) {
		this.sslCertificate = sslCertificate;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSSL(boolean ssl) {
		SSL = ssl;
	}

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

}
