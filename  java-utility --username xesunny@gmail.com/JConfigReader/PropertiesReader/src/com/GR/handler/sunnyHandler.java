package com.GR.handler;

import java.io.IOException;
import java.util.Map;

import com.GR.enums.ConfigurationHelper;
import com.GR.enums.Delimiter;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropHandler;
import com.GR.beans.sunnyBean;

public class sunnyHandler implements PropHandler {

	private ConfigurationHelper helper = new ConfigurationHelper();
	private String keyName = null;
	private sunnyBean myBean = new sunnyBean();
	
	@Override
	public void addToSession(Map<String, Bean> map) {
		map.put(keyName, myBean);

	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		//myBean.setName(helper.getStringValue(key, prefix, defaultValue));
		myBean.setName(helper.getStringValue("name", null, "xxx"));
		myBean.setLastName(helper.getStringArrayValue("lastName", null, "", Delimiter.RATEOF));

	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {		
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);	
	}

}
