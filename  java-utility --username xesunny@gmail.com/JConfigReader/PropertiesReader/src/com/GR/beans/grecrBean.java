package com.GR.beans;

import com.GR.interfaces.Bean;

public class grecrBean implements Bean {
	
	
	//-------------- INT PROPERTIES DECLARATION----------------
	private int defaultTimeout;
	private int grtxBindPort;
	private int httpsBindPort;
	private int defaultPortNum;
	
	//-------------- STRING PROPERTIES DECLARATION----------------
	private String activationCode;
	private String defaultCommMode;
	private String defaultTargetComponent;
	private String defaultTerminalType;
	private String gRTxHostname;
	private String defaultIPAddress;
	private String posId;
	
	//-------------- BOOLEAN PROPERTIES DECLARATION----------------
	private boolean grtxLoggingEnabled;
	private boolean grtxValidateXml;
	private boolean terminalCheck;
	private boolean isTerminalAttached;
	private boolean tfsCheck;
	private boolean isPropertiesDeleted;
	private boolean isDownload;
	private boolean isIpMapperDownload;
	private boolean versionUpdate;
	
	
	
	public boolean isGrtxValidateXml() {
		return grtxValidateXml;
	}
	public void setGrtxValidateXml(boolean grtxValidateXml) {
		this.grtxValidateXml = grtxValidateXml;
	}
	public String getGRTxHostname() {
		return gRTxHostname;
	}
	public void setGRTxHostname(String txHostname) {
		gRTxHostname = txHostname;
	}
	public int getGrtxBindPort() {
		return grtxBindPort;
	}
	public void setGrtxBindPort(int grtxBindPort) {
		this.grtxBindPort = grtxBindPort;
	}
	public int getDefaultTimeout() {
		return defaultTimeout;
	}
	public void setDefaultTimeout(int defaultTimeout) {
		this.defaultTimeout = defaultTimeout;
	}
	public int getHttpsBindPort() {
		return httpsBindPort;
	}
	public void setHttpsBindPort(int httpsBindPort) {
		this.httpsBindPort = httpsBindPort;
	}
	public int getDefaultPortNum() {
		return defaultPortNum;
	}
	public void setDefaultPortNum(int defaultPortNum) {
		this.defaultPortNum = defaultPortNum;
	}
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getDefaultCommMode() {
		return defaultCommMode;
	}
	public void setDefaultCommMode(String defaultCommMode) {
		this.defaultCommMode = defaultCommMode;
	}
	public String getDefaultTargetComponent() {
		return defaultTargetComponent;
	}
	public void setDefaultTargetComponent(String defaultTargetComponent) {
		this.defaultTargetComponent = defaultTargetComponent;
	}
	public String getDefaultTerminalType() {
		return defaultTerminalType;
	}
	public void setDefaultTerminalType(String defaultTerminalType) {
		this.defaultTerminalType = defaultTerminalType;
	}
	public String getDefaultIPAddress() {
		return defaultIPAddress;
	}
	public void setDefaultIPAddress(String defaultIPAddress) {
		this.defaultIPAddress = defaultIPAddress;
	}
	public String getPosId() {
		return posId;
	}
	public void setPosId(String posId) {
		this.posId = posId;
	}
	public boolean isGrtxLoggingEnabled() {
		return grtxLoggingEnabled;
	}
	public void setGrtxLoggingEnabled(boolean grtxLoggingEnabled) {
		this.grtxLoggingEnabled = grtxLoggingEnabled;
	}
	public boolean isTerminalCheck() {
		return terminalCheck;
	}
	public void setTerminalCheck(boolean terminalCheck) {
		this.terminalCheck = terminalCheck;
	}
	public boolean isTerminalAttached() {
		return isTerminalAttached;
	}
	public void setTerminalAttached(boolean isTerminalAttached) {
		this.isTerminalAttached = isTerminalAttached;
	}
	public boolean isTfsCheck() {
		return tfsCheck;
	}
	public void setTfsCheck(boolean tfsCheck) {
		this.tfsCheck = tfsCheck;
	}
	public boolean isPropertiesDeleted() {
		return isPropertiesDeleted;
	}
	public void setPropertiesDeleted(boolean isPropertiesDeleted) {
		this.isPropertiesDeleted = isPropertiesDeleted;
	}
	public boolean isDownload() {
		return isDownload;
	}
	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}
	public boolean isIpMapperDownload() {
		return isIpMapperDownload;
	}
	public void setIpMapperDownload(boolean isIpMapperDownload) {
		this.isIpMapperDownload = isIpMapperDownload;
	}
	public boolean isVersionUpdate() {
		return versionUpdate;
	}
	public void setVersionUpdate(boolean versionUpdate) {
		this.versionUpdate = versionUpdate;
	}

}
