package com.globalblue.decryption;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

import com.globalblue.constants.Constant;
import com.globalblue.constants.ExceptionConstants;
import com.globalblue.exception.OneIPlusSecurityException;
import com.globalblue.utils.HexToBytes;

public class ActivationCode {

	private static Logger logger = Logger.getLogger(ActivationCode.class);
	private static final String UNICODE_FORMAT = "UTF8";	
	private KeySpec myKeySpec;
	private SecretKeyFactory mySecretKeyFactory;
	private Cipher cipher;			
	SecretKey key;
	
	public ActivationCode(String passKey) throws OneIPlusSecurityException{		
			
		try {
			
			myKeySpec = new DESKeySpec(passKey.getBytes(UNICODE_FORMAT));
			mySecretKeyFactory = SecretKeyFactory.getInstance(Constant.DES_ENCRYPTION_SCHEME);
			cipher = Cipher.getInstance(Constant.DES_ENCRYPTION_SCHEME);
			key = mySecretKeyFactory.generateSecret(myKeySpec);
			
		} catch (InvalidKeyException e) {
			logger.error("!!!ERROR!!! - INVALID KEY "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_1, e);			
		} catch (UnsupportedEncodingException e) {
			logger.error("!!!ERROR - UNSUPPORTED ENCODING "+e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_2, e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("!!!ERROR - UNSUPPORTED ALGORITHM "+e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_3, e);
		} catch (NoSuchPaddingException e) {
			logger.error("!!!ERROR - UNSUPPORTED PADDING "+e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_4, e);
		} catch (InvalidKeySpecException e) {
			logger.error("!!!ERROR - INVALID KEY SPECS "+e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_5, e);
		}			
	}
	
	/**
	 * Method To Decrypt An Ecrypted String
	 */
	public String decrypt(String encryptedString) throws OneIPlusSecurityException {
		String decryptedText=null;
		try {
			
			encryptedString = encryptedString.substring(4);
			cipher.init(Cipher.DECRYPT_MODE, key);		
			byte[] encryptedText = HexToBytes.fromHexString(encryptedString);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText= bytes2String(plainText);
			
		} catch (InvalidKeyException e) {
			logger.error("!!!ERROR!!! - INVALID KEY "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_1, e);			
		} catch (IllegalBlockSizeException e) {
			logger.error("!!!ERROR!!! - WRONG BLOCK SIZE "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_6, e);			
		} catch (BadPaddingException e) {
			logger.error("!!!ERROR!!! - BAD PADDING "+ e.getMessage());
			throw new OneIPlusSecurityException(ExceptionConstants.DES_KEY_ERR_MSG_7, e);			
		}
		
		return decryptedText;
	}
	
	/**
	 * Returns String From An Array Of Bytes
	 */
	private static String bytes2String(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			stringBuffer.append((char) bytes[i]);
		}
		return stringBuffer.toString();
	}
}
