package com.javainsights.Junit.communication;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;



public class SSLContextGenerator {
	
	public SSLContext getClientContext(String certName, String password) throws Exception{
		/*
		 * Get the SSL Certificate Key Name
		 */
		String certificateKey = certName;	
		String PASSWORD = password;
		String FILE_PATH = certificateKey;
		/*
		 * STEP 1: Create a FILE OBJECT
		 */			
		File clientJKS = new File(FILE_PATH);
		/*
		 * STEP2: Upload file in KeyStoreFactory
		 */
		final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
		keyStoreFactory.setDataFile(clientJKS);
		keyStoreFactory.setPassword(PASSWORD);
		/*
		 * STEP3:GET JKS OBJECT and upload into SSLCONTEXT_FACTORY
		 */
		final KeyStore keyStore = keyStoreFactory.newInstance();
		final SslContextFactory contextFactory = new SslContextFactory();
		contextFactory.setTrustManagerFactoryKeyStore(keyStore);
		contextFactory.setKeyManagerFactoryKeyStorePassword(PASSWORD);
		/*
		 * STEP 4:RETURN SSLCONTEXT
		 */
		return contextFactory.newInstance();		
	}	
		
	

}
