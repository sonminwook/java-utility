package com.javainsight.cloud;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import com.google.api.services.oauth2.model.Userinfo;

import com.google.api.services.samples.shared.cmdline.oauth2.LocalServerReceiver;
import com.google.api.services.samples.shared.cmdline.oauth2.OAuth2Native;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;


/**
 * Sample application using OAuth in the Google Data Java Client.  See the
 * comments below to learn about the details.
 *
 * 
 */
class OAuthExample {

	 /** Global instance of the HTTP transport. */
	  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

	  /** Global instance of the JSON factory. */
	  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

	  /** OAuth 2.0 scopes. */
	  private static final List<String> SCOPES = Arrays.asList(
	      "https://www.googleapis.com/auth/userinfo.profile",
	      "https://www.googleapis.com/auth/userinfo.email");

	  private static Oauth2 oauth2;

	  public static void main(String[] args) {
	    try {
	      try {
	        // authorization
	        Credential credential = OAuth2Native.authorize(HTTP_TRANSPORT, JSON_FACTORY, new LocalServerReceiver(), SCOPES);
	        // set up global Oauth2 instance
	        oauth2 = Oauth2.builder(HTTP_TRANSPORT, JSON_FACTORY)
	            .setApplicationName("Google-OAuth2Sample/1.0").setHttpRequestInitializer(credential)
	            .build();
	        // run commands
	        tokenInfo(credential.getAccessToken());
	        userInfo();
	        // success!
	        return;
	      } catch (IOException e) {
	        System.err.println(e.getMessage());
	      }
	    } catch (Throwable t) {
	      t.printStackTrace();
	    }
	    System.exit(1);
	  }

	  private static void tokenInfo(String accessToken) throws IOException {
	    header("Validating a token");
	    Tokeninfo tokeninfo = oauth2.tokeninfo().setAccessToken(accessToken).execute();
	    System.out.println(tokeninfo.toPrettyString());
	    if (!tokeninfo.getAudience()
	        .equals(OAuth2Native.getClientSecrets().getDetails().getClientId())) {
	      System.err.println("ERROR: audience does not match our client ID!");
	    }
	  }

	  private static void userInfo() throws IOException {
	    header("Obtaining User Profile Information");
	    Userinfo userinfo = oauth2.userinfo().get().execute();
	    System.out.println(userinfo.toPrettyString());
	  }

	  static void header(String name) {
	    System.out.println();
	    System.out.println("================== " + name + " ==================");
	    System.out.println();
	  }

}
