package com.javainsights.Junit.constants;

public class Constant {
	public static final String HEADER_REGEX = "((\\p{Graph}){1,}(,){1}){6}" + 
	  									"((\\p{Alnum}){5}(-){0,1}){4}"+
	  									"((,)(\\p{Digit}){1,}(,)(\\p{Digit}){1,}(,)){1}";
	public static final String ERROR_REGEX = "((\\p{Graph}){1,}(,){1}){6}" + 
		 								"((\\p{Alnum}){5}(-){0,1}){4}"+
		 								"((,)(\\p{Digit}){1,2}(,)(\\p{Digit}){1,}(,)" +
		 								"((\\p{Graph}){0,}(,)){2})";
	public static final String ERROR_CODE_REGEX = "((\\p{Graph}){1,}(,){1}){6}" + 
	  									"((\\p{Alnum}){5}(-){0,1}){4}(,)";
	public static final String ACTIVATION_CODE_REGEX = "((\\p{Alnum}){5}(-){0,1}){4}";
	
	public static final String TENDER_REGEX = "[{]{1}" +
										"(\\p{Digit}){1,10}[#]{1}" +
										"[YN]{1}[#]{1}" +
										"(\\p{Upper}){4,6}[#]{1}" +
										"(\\p{Digit}){2}[#]{1}" +
										"(\\p{Digit}){0,10}[#]{1}" +
										"([0-9Xx*]{6}[Xx*]{5,9}[0-9Xx*]{4}){0,}[#]{1}"+
										"([0-9xx*]){0,4}[#]{1}" +
										"(\\p{Alnum}){0,10}[#]{1}" +
										"(\\p{Alnum}){3}[#]{1}" +
										"(\\p{Digit}){1,32}[#]{1}" +
										"(\\p{Digit}){1,32}[#]{1}" +
										"(\\p{Digit}){0,10}[#]{1}" +
										"(\\p{Alnum}){0,2}[#]{1}" +
										"(\\p{Alnum}){0,10}[#]{1}" +
										"(\\p{Digit}){0,2}[#]{1}" +
										"(\\p{Alnum}){0,10}[#]{1}" +
										"(\\p{Digit}){2}[#]{1}" +
										"([YN]){1}[#]{1}" +
										"(\\p{Digit}){0,32}[#]{1}" +
										"(\\p{Alnum}){0,3}[#]{1}" +
										"(\\p{Digit}){0,32}[#]{1}" +
										"(\\p{Alnum}){0,3}[#]{1}" +
										"(\\p{Digit}){0,10}[#]{1}" +
										"(\\p{Digit}){0,1}[#]{1}" +
										"(\\p{Graph}){0,32}[#]{1}" +
										"(\\p{Graph}){0,32}[#]{1}" +
										"(\\p{Graph}){0,32}[#]{1}" +
										"(\\p{Graph}){0,512}[#]{1}" +
										"(\\p{Digit}){6}[#]{1}" +
										"(\\p{Digit}){6}" +
										"[}]{1}";
	public static final String TENDER_LIST =  "("+TENDER_REGEX+"){0,}"; 
	
	public static final String CHECK_REGEX = "[{]{1}(\\p{Digit}){1,6}[#]{1}(\\p{Alnum}){0,32}[#]{1}(\\p{Digit}){2}[#]{1}(\\p{Digit}){1,32}[#]{1}" +
	 											TENDER_LIST+
	 											"[#]{1}" +
	 											"(\\p{Graph}){0,2048}"+
	 											"[}]{1}";
	public static final String CHECK_LIST = "("+ CHECK_REGEX + "){0,}";
	
	public static final String TABLE_REGEX =  "[{]{1}(\\p{Digit}){1,6}[#]{1}" +
													"(\\p{Alnum}){0,32}[#]{1}" +
													"(\\p{Upper}){4,9}[#]{1}" +
													"(\\p{Digit}){1,32}[#]{1}" +
													  CHECK_LIST+
													  "[}]{1}";
	public static final String SUCCESS_LOGIN_WITHOUT_TABLE = "((\\p{Graph}){1,}(,){1}){6}" + 
													 "((\\p{Alnum}){5}(-){0,1}){4}"+
													 "((,)(\\p{Digit}){1,}(,)(\\p{Digit}){1,}){1}"+
													 "((,){1}(\\p{Alnum}){1,32}){0,}";
	public static final String SUCCESS_LOGIN_WITH_TABLE = SUCCESS_LOGIN_WITHOUT_TABLE +
												  "(,){1}"+
												  "("+TABLE_REGEX+"){0,}";
	
	public static final String TABLE_NUMBER_SPLITTER = "[#]{1}" +
														"(\\p{Alnum}){0,32}[#]{1}" +
														"(\\p{Upper}){4,9}[#]{1}" +
														"(\\p{Digit}){1,32}[#]{1}" +
														CHECK_LIST+
														"[}]";
	public static final String SUCCESS_GET_TABLE = HEADER_REGEX +
												   "(\\p{Digit}{1,32}(,)){1}"+
												   TABLE_REGEX;
	public static final String SUCCESS_EDIT_TABLE = HEADER_REGEX +													
													TABLE_REGEX;
	
}
