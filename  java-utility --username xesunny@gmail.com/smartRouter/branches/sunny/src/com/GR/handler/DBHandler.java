package com.GR.handler;

import java.io.IOException;
import java.util.Map;

import com.GR.beans.DBBean;
import com.GR.enums.ConfigurationHelper;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropHandler;

public class DBHandler implements PropHandler {
	
	private ConfigurationHelper helper = new ConfigurationHelper();
	private String keyName = null;
	private DBBean myBean = new DBBean();

	@Override
	public void addToSession(Map<String, Bean> map) {
		// TODO Auto-generated method stub
		map.put(keyName, myBean);
	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		// TODO Auto-generated method stub
		myBean.setCreate(helper.getBooleanValue("create", null, "true"));
		myBean.setDbName(helper.getStringValue("dbName", null, "derbyDB" ));
		myBean.setDefaultDriver(helper.getStringValue("defaultDriver", null, "org.apache.derby.jdbc.EmbeddedDriver"));
		myBean.setDefaultFramework(helper.getStringValue("defaultFramework", null, "embedded"));
		myBean.setDefaultProtocol(helper.getStringValue("defaultProtocol", null, "jdbc:derby:"));
		myBean.setUser(helper.getStringValue("user", null, "user1"));
		myBean.setPassword(helper.getStringValue("password", null, "user1"));
		myBean.setAutoCommit(helper.getBooleanValue("autocommit", null, "false"));
		myBean.setShutDown(helper.getBooleanValue("shutdown", null, "true"));
		myBean.setSubprotocol(helper.getStringValue("subprotocol", null, "memory:"));
	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		// TODO Auto-generated method stub
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);

	}

}
