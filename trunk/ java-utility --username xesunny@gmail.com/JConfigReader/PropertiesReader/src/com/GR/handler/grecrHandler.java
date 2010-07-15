package com.GR.handler;

import java.io.IOException;
import java.util.Map;
import com.GR.enums.ConfigurationHelper;
import com.GR.interfaces.*;
import com.GR.beans.*;


public class grecrHandler implements PropHandler {

	private ConfigurationHelper helper = new ConfigurationHelper();
	private grecrBean myBean = new grecrBean();
	private String keyName = null;
	@Override
	public void addToSession(Map<String, Bean> map) {
		map.put(keyName, myBean);		
	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		
		
		myBean.setGrtxBindPort(helper.getIntValue("GrtxBindPort", "grecr.", "9123"));
		myBean.setDefaultTimeout(helper.getIntValue("defaultTimeout", "grecr.", "6000"));
		myBean.setHttpsBindPort(helper.getIntValue("httpsBindPort", "grecr.", "8080"));
		myBean.setDefaultPortNum(helper.getIntValue("defaultPortNum", "grecr.", "9123"));
		
		myBean.setGRTxHostname(helper.getStringValue("GRTxHostname", "grecr.", "*"));
		myBean.setActivationCode(helper.getStringValue("activationCode", "grecr.", "0000-0000-0000-0000"));
		myBean.setDefaultCommMode(helper.getStringValue("defaultCommMode", "grecr.", "SOCKET"));
		myBean.setDefaultTargetComponent(helper.getStringValue("defaultTargetComponent", "grecr.", "HYPERCOM_APAC_T2100"));
		myBean.setDefaultTerminalType(helper.getStringValue("defaultTerminalType", "grecr.", "TERMINAL"));
		myBean.setDefaultIPAddress(helper.getStringValue("defaultIPAddress", "grecr.", "127.0.0.1"));
		myBean.setPosId(helper.getStringValue("posId", "grecr.", "POSID9001"));
		
		myBean.setGrtxLoggingEnabled(helper.getBooleanValue("GrtxLoggingEnabled", "grecr.", "true"));
		myBean.setGrtxValidateXml(helper.getBooleanValue("GrtxValidateXml", "grecr.", "false"));
		myBean.setTerminalCheck(helper.getBooleanValue("terminalCheck", "grecr.", "true"));
		myBean.setTerminalAttached(helper.getBooleanValue("isTerminalAttached", "grecr.", "true"));
		myBean.setTfsCheck(helper.getBooleanValue("tfsCheck", "grecr.", "false"));
		myBean.setPropertiesDeleted(helper.getBooleanValue("isPropertiesDeleted", "grecr.", "false"));
		myBean.setDownload(helper.getBooleanValue("isDownload", "grecr.", "true"));
		myBean.setIpMapperDownload(helper.getBooleanValue("isIpMapperDownload", "grecr.", "true"));
		myBean.setVersionUpdate(helper.getBooleanValue("versionUpdate", "grecr.", "false"));	
		
	}

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);		
	}
	
	

}
