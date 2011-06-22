package com.javainsights.Junit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.javainsights.Junit.constants.Constant;

public class PrepareResponse {
	
	private static Logger logger = Logger.getLogger(PrepareResponse.class);
	
	public static void check(String request, String response , String expectedErrorCode){
		response = response.substring(1);
		request = request.substring(3);	
		
		checkHeader(request, response);
		
		Pattern pattern = Pattern.compile(Constant.ERROR_REGEX);
		Matcher match  = pattern.matcher(response);
		if(match.find()){
			logger.debug("==RESPONSE MESSAGE DETECTED TO BE - ERROR RESPONSE");
			String errorCode = getErrorCode(response);
			if(errorCode.equals(expectedErrorCode)){
				logger.debug("Test CASE Successful");
			}else{
				throw new IllegalArgumentException("INVALID RESPONSE");
			}
		}		
	}
	
	
	private static String getErrorCode(String response){		
		Pattern pattern = Pattern.compile(Constant.ERROR_CODE_REGEX);
		Matcher match  = pattern.matcher(response);
		
		while(match.find()){			
			String newStr = response.substring(match.group().length());
			return newStr.split(",")[0];
		}
		return null;
	}

	private static void checkHeader(String request, String response){		
		Pattern pattern = Pattern.compile(Constant.ACTIVATION_CODE_REGEX);
		Matcher match  = pattern.matcher(request);
		
		
		if(match.find()){
			String activationCode_1 = match.group();
			Matcher responseMatch = pattern.matcher(response);
			if(responseMatch.find()){
				String activationCode_2 = responseMatch.group();				
				if(activationCode_1.equals(activationCode_2)){
					logger.debug(" BOTH AC ARE SAME");
				}else{
					logger.debug("REQUEST AC " + activationCode_1);
					logger.debug("RESPONSE AC "+ activationCode_2);
					throw new IllegalArgumentException("HEADER MISMATCH");
				}
				
			}else{
				throw new IllegalArgumentException("RESPONSE DOESN'T CONTAIN HEADER");
			}
			
		}
		
	}
}
