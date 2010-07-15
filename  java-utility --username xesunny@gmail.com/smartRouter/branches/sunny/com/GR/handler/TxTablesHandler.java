package com.GR.handler;

import java.io.IOException;
import java.util.Map;

import com.GR.enums.ConfigurationHelper;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropHandler;
import com.GR.beans.TxTablesBean;

public class TxTablesHandler implements PropHandler {
	
	private ConfigurationHelper helper = new ConfigurationHelper();
	private String keyName = null;
	private TxTablesBean myBean = new TxTablesBean();

	@Override
	public void addToSession(Map<String, Bean> map) {
		// TODO Auto-generated method stub
		map.put(keyName, myBean);

	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		// TODO Auto-generated method stub
		myBean.setTB_ECRINIT_REQ(helper.getStringValue("TB_ECRINIT_REQ", null, null));
	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		// TODO Auto-generated method stub
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);
	}

}
