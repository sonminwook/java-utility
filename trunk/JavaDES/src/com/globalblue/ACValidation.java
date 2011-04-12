package com.globalblue;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.globalblue.DTO.Authentication;
import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.decryption.ActivationCode;
import com.globalblue.decryption.DecodeAC;
import com.globalblue.decryption.PassKey;
import com.globalblue.exception.OneIPlusSecurityException;
import com.globalblue.utils.AuthenticationFile;
import com.globalblue.utils.DeleteECRSession;
import com.globalblue.utils.FormatAC;
import com.globalblue.utils.ValidateOneI;

public class ACValidation {
	
	private static Logger logger = Logger.getLogger(ACValidation.class);
	
	/**
	 * This method will be called after first successful ECRInit.
	 * This method will create a successful Authentic Initialization File.
	 */
	public static boolean firstTimeValidation(String activationCode) throws OneIPlusSecurityException{
		try {
			/*
			 * STEP-1: Remove all dashes from ACTIVATION CODE
			 */
			activationCode = FormatAC.removeDashes(activationCode);
			/*
			 * STEP-2 : Get the encrypting key of DES Algo
			 */
			String passKey = new PassKey().getPassKey();
			/*
			 * STEP-3: Pass the encryption Key and activation Code to find SECRET activation Code
			 */
			String secretActivationCode = new ActivationCode(passKey).decrypt(activationCode);
			/*
			 * STEP-4: Create the Authentication Object
			 */
			Authentication clientAuthentication = DecodeAC.decode(secretActivationCode);
			/*
			 * STEP-5: Store the Object
			 */
			return new AuthenticationFile().generate(clientAuthentication);
		} catch (OneIPlusSecurityException e) {
			throw e;
		} catch( Exception e){
			throw new OneIPlusSecurityException(ExceptionConstants.FIRST_TIME_ERR_MSG,e);
		}
	}
	
	/**
	 * This method needs to be called after every SUCCESSFUL ECR-Init request
	 * @param activationCode
	 * @return
	 * @throws OneIPlusSecurityException
	 */
	public static boolean validateAC(String activationCode)throws OneIPlusSecurityException{
		try {
			/*
			 * STEP-1: Remove all dashes from ACTIVATION CODE
			 */
			activationCode = FormatAC.removeDashes(activationCode);
			/*
			 * STEP-2 : Get the encrypting key of DES Algo
			 */
			String passKey = new PassKey().getPassKey();
			/*
			 * STEP-3: Pass the encryption Key and activation Code to find SECRET activation Code
			 */
			String secretActivationCode = new ActivationCode(passKey).decrypt(activationCode);
			/*
			 * STEP-4: Create the Authentication Object
			 */
			Authentication clientAuthentication = DecodeAC.decode(secretActivationCode);
			/*
			 * STEP-5 : Validate against the stored value
			 */
			if(ValidateOneI.match(clientAuthentication)){
				/*
				 * Check if AC has expired
				 */
				if(checkExpiryDate()){
					/*
					 * Check if someone Changed the system date
					 */
					if(checkSystemDate()){
						return true;
					}else{
						new DeleteECRSession().delete(Constant.ECRSerializedObject);
						return false;
					}					
				}else{
					new DeleteECRSession().delete(Constant.ECRSerializedObject);
					return false;
				}
			}else{
				new DeleteECRSession().delete(Constant.ECRSerializedObject);
				return false;
			}
		} catch (OneIPlusSecurityException e) {
			new DeleteECRSession().delete(Constant.ECRSerializedObject);
			throw e;
		} catch(Exception e){
			 new DeleteECRSession().delete(Constant.ECRSerializedObject);
			throw new OneIPlusSecurityException(ExceptionConstants.VALIDATE_ERR_MSG, e);
		}
	}
	
	private static boolean checkExpiryDate( ) throws OneIPlusSecurityException{
		/*
		 * Current DATE
		 */
		Calendar current = Calendar.getInstance();		
		current.setTime(new Date());		
		/*
		 * Stored Authentication
		 */
		Authentication clientInfo = ValidateOneI.readSerializedFile();
		Date expiryDate = clientInfo.getOneIPlusExpiryDate();
		Calendar expiry = Calendar.getInstance();
		expiry.setTime(expiryDate);
		/*
		 * Compare DATES
		 * > Check YEARS
		 */
		if((expiry.get(Calendar.YEAR)  - current.get(Calendar.YEAR)) < 0){
			if((expiry.get(Calendar.MONTH) == 11) && (current.get(Calendar.MONTH)== 0)){
				/*
				 * Relaxation of One Day
				 */				
				if(Math.abs(current.get(Calendar.DAY_OF_YEAR) - expiry.get(Calendar.DAY_OF_YEAR) + 365) > 2 ){
					logger.error("!!!### W A R N I N G ###!!! Activation code has EXPIRED");
					return false;
				}else{
					logger.error("!!!### W A R N I N G ###!!! Activation code is about to expire, Please contact Global Blue");
					return true;
				}
			}else{
				return false;
			}
			/*
			 * SAME YEAR
			 */
		}else if((expiry.get(Calendar.YEAR)  - current.get(Calendar.YEAR)) == 0){
			if(expiry.get(Calendar.MONTH) < current.get(Calendar.MONTH)){
				return false;
			}else if(expiry.get(Calendar.MONTH) == current.get(Calendar.MONTH)){
				/*
				 * Relaxation of One Day
				 */
				if(current.get(Calendar.DAY_OF_YEAR) - expiry.get(Calendar.DAY_OF_YEAR) > 2 ){
					logger.error("!!!### W A R N I N G ###!!! Activation code has EXPIRED");
					return false;
				}else if(current.get(Calendar.DAY_OF_YEAR) - expiry.get(Calendar.DAY_OF_YEAR) == 0){
					logger.error("!!!### W A R N I N G ###!!! Activation code is about to expire, Please contact Global Blue");
					return true;
				}else{
					return true;
				}
			}else{
				return true;
			}
		}else{
				/*
				 * Year is greater
				 */
			return true;
		}		
		
	}
	
	private static boolean checkSystemDate() throws OneIPlusSecurityException{
		/*
		 * Current DATE
		 */
		Calendar current = Calendar.getInstance();		
		current.setTime(new Date());		
		/*
		 * Stored Authentication
		 */
		Authentication clientInfo = ValidateOneI.readSerializedFile();
		Date lastDate = clientInfo.getLastECRInitDate();
		Calendar yesterday = Calendar.getInstance();
		yesterday.setTime(lastDate);
		/*
		 * Compare DATES
		 * > if no system date has been changed
		 */
		if(yesterday.before(current)){
			/*
			 * Everything is ok, we need to update the stored file.
			 */
			clientInfo.setLastECRInitDate(current.getTime());
			new AuthenticationFile().update(clientInfo);
			return true;
		}
		/*
		 * > if Last day is same as TODAY
		 */
		if(yesterday.equals(current)){
			/*
			 * Everything is ok, we need to update the stored file.
			 */
			clientInfo.setLastECRInitDate(current.getTime());
			new AuthenticationFile().update(clientInfo);
			return true;
		}
		/*
		 * > if some one changed the system date to previous day
		 */
		if(yesterday.after(current)){
			logger.error("!!!### W A R N I N G ###!!! INVALID SYSTEM DATE");
			return false;
		}		
		return false;
	}

}
