package com.GR.beans;

import com.GR.interfaces.Bean;

public class DBBean implements Bean{
	
	private String user = null;
	private String password = null;
	private String defaultFramework = null;
	private String defaultDriver = null;
	private String defaultProtocol = null;
	private String dbName = null;
	private boolean isCreate = true;
	private boolean isAutoCommit = false;
	private boolean isShutDown = true;
	private String subprotocol = null;
	
	public String getSubprotocol() {
		return subprotocol;
	}
	public void setSubprotocol(String subprotocol) {
		this.subprotocol = subprotocol;
	}
	public boolean isShutDown() {
		return isShutDown;
	}
	public void setShutDown(boolean isShutDown) {
		this.isShutDown = isShutDown;
	}
	public boolean isAutoCommit() {
		return isAutoCommit;
	}
	public void setAutoCommit(boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDefaultFramework() {
		return defaultFramework;
	}
	public void setDefaultFramework(String defaultFramework) {
		this.defaultFramework = defaultFramework;
	}
	public String getDefaultDriver() {
		return defaultDriver;
	}
	public void setDefaultDriver(String defaultDriver) {
		this.defaultDriver = defaultDriver;
	}
	public String getDefaultProtocol() {
		return defaultProtocol;
	}
	public void setDefaultProtocol(String defaultProtocol) {
		this.defaultProtocol = defaultProtocol;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public boolean isCreate() {
		return isCreate;
	}
	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	
}
