package com.globalblue.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.globalblue.DTO.Authentication;
import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.exception.OneIPlusSecurityException;

public class ValidateOneI {
	
	private static Logger logger = Logger.getLogger(ValidateOneI.class);
	
	public static Authentication readSerializedFile() throws OneIPlusSecurityException{
		File f = new File(Constant.AUTHENTICATION_FILE_NAME);
		Authentication authentication = null;
	try{	   
		   ObjectInputStream obj = new ObjectInputStream(new FileInputStream(f));
		   authentication = (Authentication)obj.readObject();		   
		   obj.close();		   
		   return authentication;
	   }catch(ClassNotFoundException e){		 
		   logger.error("!!!ERROR!!! CLASS NOT FOUND "+ e.getMessage());
		   throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_1, e);
	   }catch(FileNotFoundException e){		 
		   logger.error("!!!ERROR!!! FILE NOT FOUND "+ e.getMessage());
		   throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_2, e);
	   }catch(IOException e){
		   logger.error("!!!ERROR!!! IO EXCEPTION "+ e.getMessage());
		   throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_3, e);
		}
		
	}
	
	/*
	 * This method only MATCHES OLD STORED INFORMATION WITH NEW INFORMATION RETRIEVED FROM ACTIVATION CODE
	 */
	public static boolean match(Authentication newAuthentication) throws OneIPlusSecurityException{
		Authentication oldAuthentication = readSerializedFile();
		/*
		 * Match the System Uniqueness
		 */
		if(oldAuthentication.getUniqueSystemNumber().equalsIgnoreCase(
				newAuthentication.getUniqueSystemNumber())){
			logger.debug(" ====POS SYSTEM IDENTIFIED==== ");
		}else{
			throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_4, new IllegalArgumentException("POS MACHINE NOT RECOGNIZED"));			
		}
		
		/*
		 * Match OneI plus Uniquness
		 */
		if(oldAuthentication.getUniqueOneIPlusNumber().equalsIgnoreCase(
				newAuthentication.getUniqueOneIPlusNumber())){
			logger.debug(" ====ONE INTERFACE INSTANCE IDENTIFIED==== ");
		}else{
			throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_4, 
								new IllegalArgumentException("OneInterfacePlus Sequence No IS NOT RECOGNIZED"));
		}
		
		
		/*
		 * If we reach here, means - Someone is not trying to cheat oneInterface. We may have recieved a new activation code
		 * with new expiry date, in this case we may have to update the authentication file.
		 * Match the expiry Date
		 */
		Date exipryDate = oldAuthentication.getOneIPlusExpiryDate();
		Date newExpiryDate = newAuthentication.getOneIPlusExpiryDate();
		Calendar oldCal = Calendar.getInstance();		
		oldCal.setTime(exipryDate);
		Calendar newCal = Calendar.getInstance();
		newCal.setTime(newExpiryDate);
		
		logger.debug("EXPIRY DATE IS "+ new java.text.SimpleDateFormat(Constant.EXPIRY_DATE_FORMAT).format(oldCal.getTime()));		
		logger.debug("Calculated DATE is "+ new java.text.SimpleDateFormat(Constant.EXPIRY_DATE_FORMAT).format(newCal.getTime()));
		//======= If it is a new ACTIVATION CODE, UPDATE THE STORED FILE=====
		/*
		 * Compare the year first
		 */
		if(oldCal.get(Calendar.YEAR) < newCal.get(Calendar.YEAR)){
			return new AuthenticationFile().update(newAuthentication);
		}
		/*
		 * compare the month
		 */
		if(oldCal.get(Calendar.MONTH) < newCal.get(Calendar.MONTH)){
			return new AuthenticationFile().update(newAuthentication);
		}
		/*
		 * Compare the date
		 */
		if(oldCal.get(Calendar.DAY_OF_YEAR) < newCal.get(Calendar.DAY_OF_YEAR)){
			return new AuthenticationFile().update(newAuthentication);
		}
		//==========================END HERE=========================
		
		/*
		 * Compare if it is the same expiry Date
		 */		
		if(oldCal.get(Calendar.YEAR) == newCal.get(Calendar.YEAR)){
			if(oldCal.get(Calendar.MONTH) == newCal.get(Calendar.MONTH)){
				if(oldCal.get(Calendar.DAY_OF_MONTH) == newCal.get(Calendar.DAY_OF_MONTH)){					
					return true;
				}
			}
		}
		/*
		 * Compare the year first
		 */
		if(oldCal.get(Calendar.YEAR) > newCal.get(Calendar.YEAR)){
			throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_4, 
					new IllegalArgumentException("ACTIVATION CODE IS OUTDATED"));
		}
		/*
		 * compare the month
		 */
		if(oldCal.get(Calendar.MONTH) > newCal.get(Calendar.MONTH)){
			throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_4, 
					new IllegalArgumentException("ACTIVATION CODE IS OUTDATED"));
		}
		/*
		 * Compare the date
		 */
		if(oldCal.get(Calendar.DAY_OF_MONTH) > newCal.get(Calendar.DAY_OF_MONTH)){
			throw new OneIPlusSecurityException(ExceptionConstants.MATCH_ERR_MSG_4, 
					new IllegalArgumentException("ACTIVATION CODE IS OUTDATED"));
		}
		
		return true;
 }
}
