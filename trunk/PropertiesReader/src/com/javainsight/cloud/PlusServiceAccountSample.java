package com.javainsight.cloud;

/*
 * Copyright (c) 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusScopes;
import com.google.api.services.plus.model.Activity;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * @author Yaniv Inbar
 */
public class PlusServiceAccountSample {

  /** E-mail address of the service account. */
  private static final String SERVICE_ACCOUNT_EMAIL =  "873813560123@developer.gserviceaccount.com";

  /** Global instance of the HTTP transport. */
  private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = new JacksonFactory();

  private static Plus plus;

  public static void main(String[] args) {
    try {
      try {
        // check for valid setup
        Preconditions.checkArgument(!SERVICE_ACCOUNT_EMAIL.startsWith("[["),
            "Please enter your service account e-mail from the Google APIs Console to the "
            + "SERVICE_ACCOUNT_EMAIL constant in %s", PlusServiceAccountSample.class.getName());
        String p12Content = Files.readFirstLine(new File("key.p12"), Charset.defaultCharset());
        Preconditions.checkArgument(!p12Content.startsWith("Please"), p12Content);
        // service account credential (uncomment setServiceAccountUser for domain-wide delegation)
        GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
            														.setJsonFactory(JSON_FACTORY)
            														.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
            														.setServiceAccountScopes(new String[]{""}/*PlusScopes.PLUS_ME*/)
            														.setServiceAccountPrivateKeyFromP12File(new File("key.p12"))
             .setServiceAccountUser("xesunny@gmail.com")
            .build();
       // new BasicAuthentication().
        HttpRequestFactory rf = HTTP_TRANSPORT.createRequestFactory(credential);
        GenericUrl shortenEndpoint = new GenericUrl("https://www.googleapis.com/urlshortener/v1/url");
	    String requestBody = "{\"longUrl\":\"http://farm6.static.flickr.com/5281/5686001474_e06f1587ff_o.jpg\"}";
	    HttpRequest request = rf.buildPostRequest(shortenEndpoint, new ByteArrayContent("UTF-8", requestBody.getBytes()));
	    HttpHeaders header = new HttpHeaders();
	    header.setContentType("application/json");
	    request.setHeaders(header);
	    //request.headers.contentType = ;
	    HttpResponse shortUrl = request.execute();
	    BufferedReader output = new BufferedReader(new InputStreamReader(shortUrl.getContent()));
	    System.out.println("Shorten Response: ");
	    for (String line = output.readLine(); line != null; line = output.readLine()) {
	      System.out.println(line);
	    }
//        // set up global Plus instance
//        plus = Plus.builder(HTTP_TRANSPORT, JSON_FACTORY)
//            .setApplicationName("Google-PlusServiceAccountSample/1.0")
//            .setHttpRequestInitializer(credential).build();
//        // run commands
//        getActivity();
//        // success!
        return;
      } catch (IOException e) {
        System.err.println(e.getMessage());
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
    System.exit(1);
  }

  /** Get an activity for which we already know the ID. */
  private static void getActivity() throws IOException {
    // A known public activity ID
    String activityId = "z12gtjhq3qn2xxl2o224exwiqruvtda0i";
    // We do not need to be authenticated to fetch this activity
    View.header1("Get an explicit public activity by ID");
    Activity activity = plus.activities().get(activityId).execute();
    View.show(activity);
  }
}
