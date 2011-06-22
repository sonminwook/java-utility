package com.javainsights.Junit.utils;

public class PrepareHeaders {
	
	private static ConfigDTO DTO = PrepareLoginMessage.getConfig();
	
	public static String getLoginHeader(){
		String header = "1.0,E,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
							+DTO.getActivationCode()+",0,"+ DTO.getSessionCounter();		
		return header;
	}
	
	public static String getGetTableHeader(){
		String header = "1.0,T,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
						+DTO.getActivationCode()+",0,"+ DTO.getSessionCounter();		
		return header;
	}
	
	public static String getEditTableHeader(){
		String header = "1.0,M,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
						+DTO.getActivationCode()+",0,"+ DTO.getSessionCounter();		
		return header;
	}
	
	public static String getDisconnectHeader(){
		String header = "1.0,D,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
						+DTO.getActivationCode()+",0,"+ DTO.getSessionCounter();
		DTO.increaseCounter();
		return header;
	}

}
