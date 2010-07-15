package com.GR.handler;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Level;

import com.GR.beans.serverBean;
import com.GR.enums.ConfigurationHelper;
import com.GR.enums.Delimiter;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropHandler;

public class serverHandler implements PropHandler {
	private ConfigurationHelper helper = new ConfigurationHelper();
	private String keyName = null;
	private serverBean myBean = new serverBean();	
	

	@Override
	public void addToSession(Map<String, Bean> map) {
		// TODO Auto-generated method stub
		map.put(keyName, myBean);
	}

	@Override
	public void initialize() throws IllegalArgumentException,
									NumberFormatException {
		// TODO Auto-generated method stub
		myBean.setHostAddress(helper.getStringValue("HOSTADDRESS", null, "localhost"));
		String[] ports = helper.getStringArrayValue("PORT", null, null, Delimiter.COMMA);
		int[] int_ports = new int[ports.length];
		for(int i=0; i<ports.length; i++){
			int_ports[i] = Integer.parseInt(ports[i]);
		}
		myBean.setPorts(int_ports);
		String log_level = helper.getStringValue("LOGGING_LEVEL", null, "INFO");
		Level level = Level.INFO;
		if(log_level.equalsIgnoreCase("DEBUG")){
			level = Level.DEBUG;
		}else if(log_level.equalsIgnoreCase("ERROR")){
			level = Level.ERROR;
		}else if(log_level.equalsIgnoreCase("ALL")){
			level = Level.ALL;
		}
		myBean.setLoggingLevel(level);
		myBean.setREAD_SIZE(helper.getIntValue("READ_BUFFER_SIZE", null, "4000"));
		myBean.setWRITE_SIZE(helper.getIntValue("WRITE_BUFFER_SIZE", null, "4000"));
		myBean.setREQ_POOL_SIZE(helper.getIntValue("REQUEST_POOL_SIZE", null, "500"));
		myBean.setRES_POOL_SIZE(helper.getIntValue("RESPONSE_POOL_SIZE", null, "500"));
	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		// TODO Auto-generated method stub
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);

	}

}
