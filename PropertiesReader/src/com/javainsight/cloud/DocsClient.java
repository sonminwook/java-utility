package com.javainsight.cloud;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
import com.google.gdata.client.authn.oauth.OAuthException;
import com.google.gdata.client.authn.oauth.OAuthHmacSha1Signer;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.util.ServiceException;
/**
 * @author Yaniv Inbar
 */
public class DocsClient {
	
	
	public static void main(String[] args) {
		
		try {
			String CONSUMER_KEY = "773845619631.apps.googleusercontent.com";
			String CONSUMER_SECRET = "sM52Mts_d7snVIomnJaQkjkA";

			GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
			oauthParameters.setOAuthConsumerKey(CONSUMER_KEY);
			oauthParameters.setOAuthConsumerSecret(CONSUMER_SECRET);

			DocsService client = new DocsService("testing");
			client.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());

			// Retrieve user's list of Google Docs
			String user = "xesunny@gmail.com";
			URL feedUrl = new URL("https://docs.google.com/feeds/default/private/full" +
			                      "?xoauth_requestor_id=" + user);

			DocumentListFeed resultFeed = client.getFeed(feedUrl, DocumentListFeed.class);
			for (DocumentListEntry entry : resultFeed.getEntries()) {
			  System.out.println(entry.getTitle().getPlainText());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}	

	


}

