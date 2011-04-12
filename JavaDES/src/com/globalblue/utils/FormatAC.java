package com.globalblue.utils;

import com.globalblue.constants.Constant;

public class FormatAC {

	public static String removeDashes(String activationCode){
		if(activationCode.contains(Constant.DASH_CHAR)){
			String[] parts = activationCode.split(Constant.DASH_CHAR);
			String newAC = "";
			for(String str : parts){
				newAC += str;
			}
			return newAC;
		}else{
			return activationCode;
		}
	}
}
