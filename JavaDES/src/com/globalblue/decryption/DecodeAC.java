package com.globalblue.decryption;

import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import com.globalblue.DTO.Authentication;
import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.exception.OneIPlusSecurityException;
import com.globalblue.utils.Dates;
import com.globalblue.utils.MotherBoardUtil;

public class DecodeAC {
	
	private static boolean isSystemIdentifierRequired = true;
	private static ResourceBundle OneIplusResource = null;
	static{
		try{
			OneIplusResource = ResourceBundle.getBundle("OneInterfacePlus");
			if(OneIplusResource.getString(Constant.SYS_IDENTITY_FLAG).equalsIgnoreCase(Constant.TRUE_VALUE)){
				isSystemIdentifierRequired = true;
			}else{
				isSystemIdentifierRequired = false;
			}
		}catch(Exception e){}
	}
	 
	
	public static Authentication decode(String activationCode) throws OneIPlusSecurityException{
			try {
				/*
				 * First 2 character represents Data DATE-MONTH-YEAR
				 */
				String month = activationCode.substring(0,1);
				String year = activationCode.substring(1,2);
				String uniqueNumber = activationCode.substring(2);
				/*
				 * Create an expiry Date
				 */
				Calendar cal = Calendar.getInstance();			
				cal.set(Dates.getValue(year)+Constant.EXPIRY_CENTURY, 
						Dates.getValue(month),
						Dates.getLastDayofMonth(Dates.getValue(month),Dates.getValue(year)+Constant.EXPIRY_CENTURY));			
				
				
				Authentication newAuthenitication = new Authentication();
				
				newAuthenitication.setUniqueOneIPlusNumber(uniqueNumber);
				
				if(isSystemIdentifierRequired){
					newAuthenitication.setUniqueSystemNumber(MotherBoardUtil.getMotherboardSN());
				}else{
					newAuthenitication.setUniqueSystemNumber(Constant.FAKE_SYSTEM_IDENTIFIER);
				}						
				newAuthenitication.setOneIPlusExpiryDate(cal.getTime());
				newAuthenitication.setLastECRInitDate(new Date());
				return newAuthenitication;
			} catch (RuntimeException e) {
				throw new OneIPlusSecurityException(ExceptionConstants.DECODE_ERR_MSG, e);				
			}		
	}
}
