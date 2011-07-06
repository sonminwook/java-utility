package com.javainsights.Junit.utils;

import java.util.*;

public class PrepareLoginMessage {
	
	private static ConfigDTO DTO = null;
	
	static{
		try{
			Properties JUnitConfig = new Properties();
			ReadProperties.readProperties("config/JUnitConfig.properties", JUnitConfig);			
			DTO = new ConfigDTO();			
			DTO.setActivationCode(JUnitConfig.getProperty("ACTIVATION-CODE"));
			DTO.setDeviceID(JUnitConfig.getProperty("DEVICEID"));
			DTO.setMID(JUnitConfig.getProperty("MID"));
			DTO.setTID(JUnitConfig.getProperty("TID"));
			DTO.setSessionCounter(JUnitConfig.getProperty("SESSION_COUNTER_START"));
			DTO.setEmployeeNumber(JUnitConfig.getProperty("EMPLOYEE-NUMBER"));
			DTO.setCertName(JUnitConfig.getProperty("SSL"));
			DTO.setPassword(JUnitConfig.getProperty("PASSWORD"));
			DTO.setHostname(JUnitConfig.getProperty("CLIENT_IP"));
			DTO.setPort(Integer.parseInt(JUnitConfig.getProperty("CLIENT_PORT")));
			DTO.setTimeOut(Integer.parseInt(JUnitConfig.getProperty("TIMEOUT")));
			DTO.setInvalidACConfig(JUnitConfig.getProperty("INVALID-AC-CONFIG"));
			DTO.setValidTableNumber(JUnitConfig.getProperty("VALIDTABLE"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getEmployeeNumber(){
		return DTO.getEmployeeNumber();
	}	
	
	public static String getLoginHeaderWithInvalidAC(){
		String header = "1.0,E,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
							+"!@#$%SASDBASETE"+",0,"+ DTO.getSessionCounter();
		DTO.increaseCounter();
		return header;
	}
	
	public static String getLoginHeaderWithInvalidConfiguration(){
		String header = "1.0,E,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
							+DTO.getInvalidACConfig()+",0,"+ DTO.getSessionCounter();
		DTO.increaseCounter();
		return header;
	}
	
	public static String getPerfectLoginRequest(){
		//DTO.increaseCounter();
		String header = PrepareHeaders.getLoginHeader();
		String message = header + "," + getEmployeeNumber();		
		return message;
	}
	
	public static ConfigDTO getConfig(){
		return DTO;
	}
}
