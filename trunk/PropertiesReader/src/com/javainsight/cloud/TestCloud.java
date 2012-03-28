package com.javainsight.cloud;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.json.JsonCParser;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.javainsight.enums.events.FolderEvent;

import com.google.api.services.docs.DocsClient;


//import com.google.api.client.googleapis.GoogleHeaders;
//import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.HttpTransport;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.jackson.JacksonFactory;
//import com.google.gdata.client.authn.oauth.GoogleOAuthParameters;
//import com.google.gdata.client.spreadsheet.SpreadsheetService;
//import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
//import com.javainsight.enums.events.FolderEvent;




public class TestCloud {
	
	private final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(1);
	private static Logger logger = Logger.getLogger(TestCloud.class);
	private Stack<SpreadsheetEntry> updateQueue = new Stack<SpreadsheetEntry>();
	private Stack<String> deleteQueue = new Stack<String>();
	private List<FolderEvent> folderEventList = new ArrayList<FolderEvent>();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private static final JsonFactory jsonFactory = new JacksonFactory();

	private static final HttpTransport transport = new NetHttpTransport();

	private static HttpRequestFactory requestFactory = null;


	private static final String EMAIL = "873813560123@developer.gserviceaccount.com";
	private static final String SCOPE = "https://spreadsheets.google.com/feeds";
	private CloudReader reader = null;
	
	//private String directory = null;
	private int pollingTime = 15;
	
	//--------LOCK BETWEEN CONTROLLER AND FOLDER MONITOR THREAD----
	Lock proceed = new ReentrantLock();
	Condition isProceed = proceed.newCondition();
	

	public static void main(String[] args) {
		
		try{
//			PropertyConfigurator.configure("config/log4j.properties");
//			new TestCloud().run();
			 GoogleCredential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT).setJsonFactory(JSON_FACTORY)
	            														.setServiceAccountId(EMAIL)
	            														.setServiceAccountScopes(SCOPE)
	            														.setServiceAccountPrivateKeyFromP12File(new File("lib/key.p12")).build();
		
			 System.err.println(credential.getTransport());
			 
			 DocsClient client = new DocsClient(HTTP_TRANSPORT.createRequestFactory(credential));
		     client.setApplicationName("Google-DocsSample/1.0");

			 

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void test() throws Exception{
		
//		 HttpTransport transport = HTTP_TRANSPORT;		 
//		 GoogleHeaders headers = new GoogleHeaders();//(GoogleHeaders) transport.defaultHeaders;
//		 headers.setApplicationName("test-OAuth");
//		 headers.gdataVersion = "2";
//		 
//		 
//
//		 ClientLogin authenticator = new ClientLogin();		 
//		 authenticator.authTokenType = "youtube";
//		 authenticator.username = "javainsights";
//		 authenticator.password = "1qaz1234";
//		 authenticator.transport = transport;
		 
		 //authenticator.
		 
		 final JsonCParser parser = new JsonCParser(jsonFactory);
		requestFactory = transport.createRequestFactory(new HttpRequestInitializer() {

		      @Override
		      public void initialize(HttpRequest request) {
		        // headers
		        GoogleHeaders headers = new GoogleHeaders();
		        headers.setApplicationName("Google-YouTubeSample/1.0");
		        headers.gdataVersion = "3.0";
		        request.setHeaders(headers);
		        request.addParser(parser);		        
		      }
		    });
		    
		   // URL url = new URL("");
		    //HttpRequest request = requestFactory.buildGetRequest(url);

		    YouTubeUrl url = YouTubeUrl.forVideosFeed();
		    url.author = "xesunny";
		    
		    HttpRequest request = requestFactory.buildGetRequest(url);
		    SpreadsheetFeed feed = request.execute().parseAs(SpreadsheetFeed.class);
		    
		    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
			for (int i = 0; i < spreadsheets.size(); i++) {
			  SpreadsheetEntry entry = spreadsheets.get(i);
			  System.out.println("\t" + entry.getTitle().getPlainText());			 
			}
		    // execute GData request for the feed
		   // VideoFeed feed = client.executeGetVideoFeed(url);


		 
		 
		 
//		String scope = "https://spreadsheets.google.com/feeds/";
//	    //AppTokenJdo appToken 	= AppTokenStore.getToken(scope);
//
//	    String consumerKey 		= sp.getOAuthConsumerKey();
//	    String consumerSecret 	= sp.getOAuthConsumerSecret();
//
//	    String token 			= appToken.getAccessTokenKey();
//	    String tokenSecret 		= appToken.getAccessTokenSecret();
//
//	    GoogleOAuthParameters oauthParameters = new GoogleOAuthParameters();
//	    oauthParameters.setOAuthConsumerKey(consumerKey);
//	    oauthParameters.setOAuthConsumerSecret(consumerSecret);
//	    oauthParameters.setOAuthToken(token);
//	    oauthParameters.setOAuthTokenSecret(tokenSecret);
//
//	    //log.info("Token " + token + " TokenSecret: " + tokenSecret + " ConsumerKey: " + consumerKey + " ConsumerSecret: " + consumerSecret);
//
//	    SpreadsheetService service = new SpreadsheetService("Gone_Vertical_LLC-CoreSystem_v1");
//	    try {
//	      service.setOAuthCredentials(oauthParameters, new OAuthHmacSha1Signer());
//	    } catch (OAuthException e) {
//	      e.printStackTrace();
//	    }
//
//	    URL url = null;
//	    try {                    
//	      url = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
//	    } catch (MalformedURLException e1) {
//	      e1.printStackTrace();
//	    }
//	    SpreadsheetFeed feed = null;
//	    try {
//	      feed = service.getFeed(url, SpreadsheetFeed.class);
//	    } catch (IOException e) {
//	      e.printStackTrace();
//	    } catch (ServiceException e) {
//	      e.printStackTrace();
//	    }
//	    List<SpreadsheetEntry> spreadsheets = feed.getEntries();
//	    for (int i = 0; i < spreadsheets.size(); i++) {
//	      SpreadsheetEntry entry = spreadsheets.get(i);
//	      //System.out.println("\t" + entry.getTitle().getPlainText());
//	    }

	}
	public void run(){
		/*
		 * Start the folder monitoring immediately
		 */
		CloudMonitorThread folderMonitor = new CloudMonitorThread( //this.directory,
																	this.updateQueue,
																	this.deleteQueue,
																	this.folderEventList,
																	this.proceed,
																	this.isProceed);
		executorPool.scheduleWithFixedDelay(folderMonitor, 1, this.pollingTime, TimeUnit.SECONDS);
		/*
		 * DANGEROUS --> Infinite Loop "Handle Carefully"		
		 */
INFINITE_LOOP:while(true){
				this.proceed.lock();
				try{
					if(this.folderEventList.isEmpty()){
						System.err.println("on hold");
						this.isProceed.await();						
					}
					for(FolderEvent event : folderEventList){
						switch(event){
						case LOAD:{
							logger.debug("Cache Updation Alert");
							for(SpreadsheetEntry file : this.updateQueue){
								logger.debug("File Name is "+ file.getTitle().getPlainText());							
							}
							while(this.updateQueue.size() > 0){
								this.updateQueue.pop();
							}
							break;
						}
						case UNLOAD:{
							logger.debug("Cache Deletion Alert");
							for(String file : this.deleteQueue){
								logger.debug("File Name is "+ file);							
							}
							while(this.deleteQueue.size() > 0){
								this.deleteQueue.pop();
							}
							break;
							
						}
						case EXIT:{
							logger.debug("Shutdown Alert");
							executorPool.shutdownNow();							
							break INFINITE_LOOP;
						}
						}					
				}
					folderEventList.remove(0);
				//	this.reader.start();
			}catch(Exception e){						
						logger.error("Error" + e.getMessage(), e);					
				}finally{
					this.proceed.unlock();
				}			
			}		
	}
}

