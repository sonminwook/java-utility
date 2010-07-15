package com.GR.handler;

import java.io.IOException;
import java.util.Map;

import com.GR.enums.ConfigurationHelper;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropHandler;
import com.GR.beans.versionBean;

public class versionHandler implements PropHandler {

	private ConfigurationHelper helper = new ConfigurationHelper();
	private String keyName = null;
	private versionBean myBean = new versionBean();
	@Override
	public void addToSession(Map<String, Bean> map) {
		map.put(keyName, myBean);

	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		myBean.setProject(helper.getStringValue("project", null, "JConfigReader"));
		myBean.setVersion(helper.getFloatValue("version", null, "0.0"));

	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);

	}

}
