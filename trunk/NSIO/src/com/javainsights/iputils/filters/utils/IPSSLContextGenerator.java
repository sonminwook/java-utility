package com.javainsights.iputils.filters.utils;

import java.io.File;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;

package com.javainsights.DTO.IPConfiguration;

public class IPSSLContextGenerator {
	
	private static Logger log = Logger.getLogger(IPSSLContextGenerator.class);
	
	public SSLContext getClientContext(IPConfiguration ipConfig) throws Exception{
		/*
		 * Get the SSL Certificate Key Name
		 */
		String PASSWORD = ipConfig.getPassword();
		String FILE_PATH = ipConfig.getSslCertificate();
		boolean needSSL = ipConfig.isSSL();
		if(needSSL){
			/*
			 * STEP 1: Create a FILE OBJECT
			 */
			FilePermission.checkReadFilePermission(FILE_PATH, "JKS");
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
		}else{
			return null;
		}
		
	}
	

}
