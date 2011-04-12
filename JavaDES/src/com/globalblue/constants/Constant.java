package com.globalblue.constants;

public class Constant {

	public static final int KEYSIZE = 1024;
	public static final String RSA_ALGORITHM = "RSA";
	public static final String KEY_GENERATION_ALGO = "AES";
	public static final String DES_ENCRYPTION_SCHEME = "DES";
	
	public static final String PUBLIC_KEY_FORMAT = "X.509";
	public static final String PRIVATE_KEY_FORMAT = "PKCS#8";
	
	public static final String lineSeparator = System.getProperty ( "line.separator" );
	
	private final static String PUBLIC_KEY_BYTES = "48,-127,-97,48,13,6,9,42,-122,72,-122,-9,13,1,1,1,5,0,3,-127,-115,0,48,-127,-119,2," +
													"-127,-127,0,-94,-6,-69,114,-41,111,-82,-12,70,19,-81,103,66,34,60,113," +
													"125,78,1,-126,56,-124,56,113,-49,31,-6,0,-2,122,-84,90,-13,-12,-3,24,-2," +
													"44,43,-89,76,54,-123,-112,-83,-39,1,-18,116,-17,-51,90,112,45,40,56,30,-33," +
													"-21,-35,29,75,-116,99,-76,-120,43,-36,96,-26,45,-46,-104,-48,-111,48,108,80," +
													"-93,-96,-115,93,-34,-69,91,-104,-110,8,104,122,116,-83,-102,-98,-78,-71,125," +
													"-47,95,50,76,3,22,23,56,42,-117,-122,-35,-58,-70,-81,-79,87,39,22,-3,85,-26,-49," +
													"40,76,102,-58,-18,-23,125,25,2,3,1,0,1,";
	public final static String ENCRYPTED_KEY_BYTES = "49,50,56,106,-94,-127,-35,96,5,114,-102,117,-55,77,-126,-82,44,-22,-7,92," +
														"-88,-43,-52,28,-99,28,116,-35,-111,-38,116,71,-96,-99,49,22,20,-119,61,-79,-71," +
														"127,11,-9,96,24,54,-60,36,12,-25,48,79,101,21,67,-63,51,-3,-58,-11,22,86,115,-77," +
														"-16,-3,-56,-1,3,-118,-27,113,-108,-24,121,-124,76,-2,45,-42,58,105,12,-22,83,62,-2," +
														"-67,-31,-92,-46,102,-54,15,121,89,47,99,94,70,-120,70,-53,-98,78,-55,-39,117,7,55,3,6,60," +
														"-8,-73,-97,106,-45,-6,-90,-6,71,126,8,-58,-103,49,103,110,16," +
														"119,118,56,84,-64,30,3,33,10,-26,-45,-16,56,85,45,11,";
	public final static int FIRST_TRY_LENGTH = 4;
	public final static int SECOND_TRY_LENGTH = 3;
	
	public final static String DASH_CHAR = "-"; 
	
	/*
	 * Authentication STUFF
	 */
	public final static String EXPIRY_DATE_FORMAT = "dd-MMM-yyyy";
	public final static Integer EXPIRY_CENTURY = 2000;
	public final static String AUTHENTICATION_FILE_NAME = "OneInterfacePlus.ser";
	public final static String SYS_IDENTITY_FLAG = "SystemIdentifier";
	public final static String TRUE_VALUE = "true";
	public final static String FAKE_SYSTEM_IDENTIFIER = "XXXXXXX";
	
	public final static String ECRSerializedObject = "ecrSettings.ser";
	
	
	public static byte[] getPUBLIC_KEY(){
		String[] byteArray = PUBLIC_KEY_BYTES.split(",");
		byte[] byts = new byte[byteArray.length];
		int i=0;
		for(String str : byteArray){
			byts[i] = Byte.parseByte(str);
			i++;
		}
		return byts;
	}
	
	public static byte[] getENCRYPTED_KEY(){
		String[] byteArray = ENCRYPTED_KEY_BYTES.split(",");
		byte[] byts = new byte[byteArray.length];
		int i=0;
		for(String str : byteArray){			
			byts[i] = Byte.parseByte(str);			
			i++;
		}	
		return byts;
	}
}
