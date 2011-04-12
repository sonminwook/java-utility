package com.globalblue.decryption;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.exception.OneIPlusSecurityException;

public class PassKey extends KeyRep{
	
	private static Logger logger = Logger.getLogger(PassKey.class);
			
	public static final long serialVersionUID = 34330892308525L;
	
	public PassKey(){
		super(KeyRep.Type.PUBLIC, Constant.RSA_ALGORITHM, Constant.PUBLIC_KEY_FORMAT, Constant.getPUBLIC_KEY());	
	}

	/**
	 * This will read the public key and encrypted pass phrase and
	 * return the string representation of that STRING.
	 */
	public String getPassKey( ) throws OneIPlusSecurityException{
		String passKey = null;
		try {
			/*
			 * First 4 Bytes represents Length
			 */
			int length = 0;		
			/*
			 * Lets try from the store encrypted data. 
			 */
			byte[] encryptedData = Constant.getENCRYPTED_KEY();//new BASE64Decoder().decodeBuffer(Constant.ENCRYPTED_KEY);			
			int disposeKeyLength = 0;
			try{
				length = Integer.parseInt(new String(encryptedData,0, Constant.FIRST_TRY_LENGTH));
				disposeKeyLength = Constant.FIRST_TRY_LENGTH;
			}catch(NumberFormatException nfe){				
				length = Integer.parseInt(new String(encryptedData,0, Constant.SECOND_TRY_LENGTH));
				disposeKeyLength = Constant.SECOND_TRY_LENGTH;
			}		
			
			byte[] wrappedKey = new byte[length];			
			for(int i=0; i<wrappedKey.length; i++){
				wrappedKey[i] = encryptedData[disposeKeyLength+i];							
			}		
						
			byte[] originalEncryptData = new byte[encryptedData.length - wrappedKey.length - disposeKeyLength];
			int myCounter = 0;
			for(int len = wrappedKey.length + disposeKeyLength; len < encryptedData.length; len++){
				originalEncryptData[myCounter] = encryptedData[len];
				myCounter++;
			}
			/*
			 * This is the public key
			 */		     
			Key publicKey = (Key)this.readResolve();			
			/*
			 * Unwrapping the public Key first with the help of private key
			 */
			Cipher cipher = Cipher.getInstance(Constant.RSA_ALGORITHM);
			cipher.init(Cipher.UNWRAP_MODE, publicKey);
			Key key = cipher.unwrap(wrappedKey, Constant.KEY_GENERATION_ALGO, Cipher.SECRET_KEY);
			
			cipher = Cipher.getInstance(Constant.KEY_GENERATION_ALGO);
			cipher.init(Cipher.DECRYPT_MODE, key);
			passKey = decrypt(cipher, originalEncryptData);		
		} catch (InvalidKeyException e) {
			logger.error("!!!ERROR!!! INVALID KEY "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_1, e);
		} catch (FileNotFoundException e) {
			logger.error("!!!ERROR!!! FILE NOT FOUND "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_2,e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("!!!ERROR!!! NO SUCH ALGORITHM "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_3,e);
		} catch (NoSuchPaddingException e) {
			logger.error("!!!ERROR!!! INVALID PADDING "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_4,e);
		} catch (IOException e) {
			logger.error("!!!ERROR!!! IO EXCEPTION "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_5,e);
		} catch (GeneralSecurityException e) {
			logger.error("!!!ERROR!!! ONEI PLUS SECURITY ISSUE "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.PASS_KEY_ERR_MSG_6,e);
		}
		return passKey;
	}
	
	private String decrypt(Cipher cipher, byte[] encryptedData ) throws 
										IOException,
										GeneralSecurityException
	   {	
		  String tmp = "";
	      int blockSize = cipher.getBlockSize();
	      int outputSize = cipher.getOutputSize(blockSize);
	      byte[] inBytes = new byte[blockSize];
	      byte[] outBytes = new byte[outputSize];
	      /*
	       * By Sunny
	       */
	      int inSize = encryptedData.length;
	      int i = 0;
	      int j = 0;	      
	      while(inSize >= blockSize){
	    	  int counter = 0;
	    	  for(; i < j + blockSize ; i++){
	    		  inBytes[counter] = encryptedData[i];
	    		  counter++;
	    	  }    	  
	    	  int outLength = cipher.update(inBytes, 0, blockSize, outBytes);	            			      
	          tmp += new String(outBytes,0, outLength);	          
	          /*
	           * Reduce the length
	           */
	          inSize = inSize - blockSize;
	          j = j + blockSize;
	      }
	      
	      int inLength = 0;;
	      if (inSize > 0){	 
	    	  int counter = 0;
	    	  inBytes = new byte[blockSize];
	    		  for( int remLength = blockSize - inSize; remLength < blockSize; remLength++){
	    			  inBytes[counter] = encryptedData[remLength];
	    		  }
	    	  inLength = blockSize - inSize;
	         outBytes = cipher.doFinal(inBytes, 0, inLength);
	      } else
	         outBytes = cipher.doFinal();	      	 
	      	 tmp += new String(outBytes);      	
	      	return tmp;
	   }
	
}
