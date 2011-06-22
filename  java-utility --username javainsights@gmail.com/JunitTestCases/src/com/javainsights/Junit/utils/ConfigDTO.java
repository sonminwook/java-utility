package com.javainsights.Junit.utils;

public class ConfigDTO {
	private String ActivationCode = null;
	private String DeviceID = null;
	private String TID = null;
	private String MID = null;
	private String sessionCounter = null;
	private String employeeNumber = null;
	private String hostname = null;
	private String password = null;
	private int port = 9002;
	private String certName = null;
	private int timeOut = 180000;
	private String invalidACConfig = null;
	private String validTableNumber = null;
	
	
	public String getInvalidACConfig() {
		return invalidACConfig;
	}
	public void setInvalidACConfig(String invalidACConfig) {
		this.invalidACConfig = invalidACConfig;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getActivationCode() {
		return ActivationCode;
	}
	public String getDeviceID() {
		return DeviceID;
	}
	public String getTID() {
		return TID;
	}
	public String getMID() {
		return MID;
	}
	public String getSessionCounter() {
		return sessionCounter;
	}
	public void setActivationCode(String activationCode) {
		ActivationCode = activationCode;
	}
	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}
	public void setTID(String tid) {
		TID = tid;
	}
	public void setMID(String mid) {
		MID = mid;
	}
	public void setSessionCounter(String sessionCounter) {
		this.sessionCounter = sessionCounter;
	}
	
	public void increaseCounter(){
		try{
			int count = Integer.parseInt(this.sessionCounter);
			this.setSessionCounter(Integer.toString(++count));			
		}catch(Exception e){
			
		}
	}
	public String getHostname() {
		return hostname;
	}
	public String getPassword() {
		return password;
	}
	public int getPort() {
		return port;
	}
	public String getCertName() {
		return certName;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setCertName(String certName) {
		this.certName = certName;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	public String getValidTableNumber() {
		return validTableNumber;
	}
	public void setValidTableNumber(String validTableNumber) {
		this.validTableNumber = validTableNumber;
	}
}
