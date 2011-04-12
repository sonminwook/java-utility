package com.globalblue.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import com.globalblue.DTO.Authentication;
import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.exception.OneIPlusSecurityException;

public class AuthenticationFile {
	
	public boolean generate(Authentication authentication) throws OneIPlusSecurityException{
	try {
	    // Serialize to a file
	    ObjectOutput out = new ObjectOutputStream(
	    						new FileOutputStream(Constant.AUTHENTICATION_FILE_NAME));
	    out.writeObject(authentication);
	    out.close();
	    return true;
	} catch (IOException e) {		
		throw new OneIPlusSecurityException(ExceptionConstants.AUTHENTICATE_FILE_GENERATION_ERR_MSG, e);
		}
	}
	
	public boolean update(Authentication authentication) throws OneIPlusSecurityException {
		    File auth_file = new File(Constant.AUTHENTICATION_FILE_NAME);
		    if(auth_file.exists()){
		    	if(auth_file.delete()){
		    		return this.generate(authentication);
		    	}
		    }
		    return false;		
	}

}
