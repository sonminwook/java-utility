package com.javainsights.Junit.utils;

public class PrepareHeaders {
	
	private static ConfigDTO DTO = PrepareLoginMessage.getConfig();
	private static Integer sessCounter = 0;
	private static Integer acCounter = 0;
	
	public static String getLoginHeader(){
		String header = "1.0,E,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
							+DTO.getActivationCode()+",0,"+ DTO.getSessionCounter();		
		return header;
	}
	
	public static synchronized String getLoginHeader(boolean isMultiple){
		String ac = ""+ acCounter++;
		ac = Integer.parseInt(ac) <= 9 ? ("0" +ac + DTO.getActivationCode()) : (ac + DTO.getActivationCode());
		return "1.0,E,Q,"+DTO.getDeviceID()+","+DTO.getTID()+","+DTO.getMID()+","
							+ac+",0,"+ (sessCounter++);			
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
	
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(getLoginHeader(true));
		}
	}
	
	public static synchronized String getLoginHeader(String ac, String deviceID, String sessionCounter){		
		return "1.0,E,Q,"+deviceID+","+DTO.getTID()+","+DTO.getMID()+","
							+ac+",0,"+ sessionCounter;		
			
	}
	
	public static synchronized String getGetTableHeader(String ac, String deviceID, String sessionCounter){
		String header = "1.0,T,Q,"+deviceID+","+DTO.getTID()+","+DTO.getMID()+","
						+ac+",0,"+ sessionCounter;		
		return header;
	}
	public static synchronized String getEditTableHeader(String ac, String deviceID, String sessionCounter){
		String header = "1.0,M,Q,"+deviceID+","+DTO.getTID()+","+DTO.getMID()+","
						+ac+",0,"+ sessionCounter;		
		return header;
	}
	
	public static synchronized String getDisconnectHeader(String ac, String deviceID, String sessionCounter){
		String header = "1.0,D,Q,"+deviceID+","+DTO.getTID()+","+DTO.getMID()+","
						+ac+",0,"+ sessionCounter;
		//DTO.increaseCounter();
		return header;
	}

}
