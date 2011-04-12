package com.globalblue.constants;

public class ExceptionConstants {

	private static final String EXCEPTION = Constant.lineSeparator+"!!!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!!!!!"+Constant.lineSeparator;
	private static final String ERRORCODE = Constant.lineSeparator+"!!!!!!!!!!!!!!!!!";
	private static final String ERRORTAIL = "!!!!!!!!!!!!!!!!!";
	
	/*
	 * ====================ERROR CODES===============================
	 */
	private static final String KEY_GENERATOR_ERROR_CODE   = "OneI+001";
	
	private static final String DECODE_ERROR_CODE   = "OneI+002";
	private static final String AUTHENTICATE_FILE_GENERATION_ERROR_CODE   = "OneI+003";
	private static final String FIRST_TIME_ERROR_CODE   = "OneI+004";
	
	private static final String VALIDATION_ERROR_CODE   = "OneI+005";
	
	private static final String PASS_KEY_ERROR_CODE_1 = "OneI+006";
	private static final String PASS_KEY_ERROR_CODE_2 = "OneI+007";
	private static final String PASS_KEY_ERROR_CODE_3 = "OneI+008";
	private static final String PASS_KEY_ERROR_CODE_4 = "OneI+009";
	private static final String PASS_KEY_ERROR_CODE_5 = "OneI+010";
	private static final String PASS_KEY_ERROR_CODE_6 = "OneI+011";
	
	private static final String DES_KEY_ERROR_CODE_1 = "OneI+012";
	private static final String DES_KEY_ERROR_CODE_2 = "OneI+013";
	private static final String DES_KEY_ERROR_CODE_3 = "OneI+014";
	private static final String DES_KEY_ERROR_CODE_4 = "OneI+015";
	private static final String DES_KEY_ERROR_CODE_5 = "OneI+016";
	private static final String DES_KEY_ERROR_CODE_6 = "OneI+017";
	private static final String DES_KEY_ERROR_CODE_7 = "OneI+018";
	
	
	private static final String MATCH_ERROR_CODE_1 = "OneI+019";
	private static final String MATCH_ERROR_CODE_2 = "OneI+020";
	private static final String MATCH_ERROR_CODE_3 = "OneI+021";
	private static final String MATCH_ERROR_CODE_4 = "OneI+022";
	
	
	
	public static final String KEYGENERATOR_ERR_MSG = EXCEPTION + 
												"   WHILE UPLOADING VELOCITY ENGINE" +
												ERRORCODE + KEY_GENERATOR_ERROR_CODE +ERRORTAIL;
	
	//===============PASS KEY GENERATION==========================//
	public static final String PASS_KEY_ERR_MSG_1 = EXCEPTION + 
												"   INVALID PUBLIC KEY " +
												ERRORCODE + PASS_KEY_ERROR_CODE_1 +ERRORTAIL;
	public static final String PASS_KEY_ERR_MSG_2 = EXCEPTION + 
											"   FILE NOT FOUND " +
											ERRORCODE + PASS_KEY_ERROR_CODE_2 +ERRORTAIL;
	
	public static final String PASS_KEY_ERR_MSG_3 = EXCEPTION + 
											"   INVALID ALGORITHM TO GENERATE THE KEY " +
											ERRORCODE + PASS_KEY_ERROR_CODE_3 +ERRORTAIL;
	
	public static final String PASS_KEY_ERR_MSG_4 = EXCEPTION + 
											"   INVALID PADDING IN THE ENCRYPTED PASS KEY " +
												ERRORCODE + PASS_KEY_ERROR_CODE_4 +ERRORTAIL;
	
	public static final String PASS_KEY_ERR_MSG_5 = EXCEPTION + 
												"   IO EXCEPTION " +
													ERRORCODE + PASS_KEY_ERROR_CODE_5 +ERRORTAIL;
	
	public static final String PASS_KEY_ERR_MSG_6 = EXCEPTION + 
													"   GENERAL SECURITY EXCEPTION " +
													ERRORCODE + PASS_KEY_ERROR_CODE_6 +ERRORTAIL;
	//============DES ALGO EXCEPTIONS=================================//
	public static final String DES_KEY_ERR_MSG_1 = EXCEPTION + 
													"   INVALID KEY " +
														ERRORCODE + DES_KEY_ERROR_CODE_1 +ERRORTAIL;
	public static final String DES_KEY_ERR_MSG_2 = EXCEPTION + 
													"   ENCODING NOT SUPPORTED " +
													ERRORCODE + DES_KEY_ERROR_CODE_2 +ERRORTAIL;

	public static final String DES_KEY_ERR_MSG_3 = EXCEPTION + 
													"   INVALID ALGORITHM " +
													ERRORCODE + DES_KEY_ERROR_CODE_3 +ERRORTAIL;

	public static final String DES_KEY_ERR_MSG_4 = EXCEPTION + 
													"   INVALID PADDING " +
													ERRORCODE + DES_KEY_ERROR_CODE_4 +ERRORTAIL;

	public static final String DES_KEY_ERR_MSG_5 = EXCEPTION + 
													"   INVALID KEY SPECS " +
													ERRORCODE + DES_KEY_ERROR_CODE_5 +ERRORTAIL;

	public static final String DES_KEY_ERR_MSG_6 = EXCEPTION + 
													"   WRONG BLOCK SIZE " +
													ERRORCODE + DES_KEY_ERROR_CODE_6 +ERRORTAIL;
	public static final String DES_KEY_ERR_MSG_7 = EXCEPTION + 
													"   BAD PADDING " +
														ERRORCODE + DES_KEY_ERROR_CODE_7 +ERRORTAIL;
	
	//==========DECODING OF ACTIVATION CODE====================
	public static final String DECODE_ERR_MSG = EXCEPTION + 
													"   UNABLE TO DECODE ACTIVATION CODE " +
													ERRORCODE + DECODE_ERROR_CODE +ERRORTAIL;
	//==========GENERATION OF AUTHENICATION FILE========
	public static final String AUTHENTICATE_FILE_GENERATION_ERR_MSG = EXCEPTION + 
													"   UNABLE TO GENERATE AUTHENTICATION FILE " +
													ERRORCODE + AUTHENTICATE_FILE_GENERATION_ERROR_CODE +ERRORTAIL;
	//========= FIRST TIME VALIDATION ================
	public static final String FIRST_TIME_ERR_MSG = EXCEPTION + 
													"   UNABLE TO DO FIRST VALIDATION " +
													ERRORCODE + FIRST_TIME_ERROR_CODE +ERRORTAIL;
	//=========GENERAL VALIDATION ERROR CODE ========
	public static final String VALIDATE_ERR_MSG = EXCEPTION + 
													"   INVALID ACTIVATION CODE " +
														ERRORCODE + VALIDATION_ERROR_CODE +ERRORTAIL;
	//========MATCHING THE AUTHENTICATION=============
	public static final String MATCH_ERR_MSG_1 = EXCEPTION + 
													"   AUTHENTICATION CLASS NOT FOUND " +
													ERRORCODE + MATCH_ERROR_CODE_1 +ERRORTAIL;
	public static final String MATCH_ERR_MSG_2 = EXCEPTION + 
													"   AUTHENTICATION FILE NOT FOUND " +
														ERRORCODE + MATCH_ERROR_CODE_2 +ERRORTAIL;
	public static final String MATCH_ERR_MSG_3 = EXCEPTION + 
													"   EXCEPTION WHILE READING AUTHENTICATION FILE " +
													ERRORCODE + MATCH_ERROR_CODE_3 +ERRORTAIL;
	public static final String MATCH_ERR_MSG_4 = EXCEPTION + 
													"   INVALID ACTIVATION CODE " +
													ERRORCODE + MATCH_ERROR_CODE_4 +ERRORTAIL;
}
