package com.javainsight.cloud.RnD;


import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleAuthTokenFactory.UserToken;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.Link;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.extensions.LastModifiedBy;
import com.google.gdata.util.ServiceException;


public class TestGoogleDocs {
	
	static DocsService client = new DocsService("yourCo-yourAppName-v1");
	private static String URL_SPECIAL = "https://docs.google.com/feeds/default/private/full/";
	private static String EXCEL 	  = "-/spreadsheet";
	private static String DOC 		  = "-/document";
	private static String PPT 		  = "-/presentation/mine";
	private static String FOLDER 	  = "-/folder";
	
	
	public static void main(String[] args) {
		try {
			System.err.println("== Testing Google Docs ==");
			/*
			 * By default, the client libraries set an account-type parameter to HOSTED_OR_GOOGLE.
			 * That means ClientLogin will first try to authenticate the user's credentials as a 
			 * Google Apps hosted account. If that fails, it will try to authenticate as a Google Account.
			 * This becomes tricky if user@example.com is both a Google Account and a Google Apps account.
			 * In that special case, set the account type to GOOGLE if the user wishes to use the Google 
			 * Accounts version of user@example.com.
			 */
			client.setUserCredentials("javainsights@gmail.com", "1qaz1234", ClientLoginAccountType.GOOGLE);
			/*
			 * Use token to avoid CAPTCHA  CHALLENGE
			 */
			//client.setUserToken("DQAAAKAAAABsRsSvYr5DC_a0K9mfde2R0bMe2BoKWB--sQ3yJxAjzp6IL" +
							//	"4NxUkQoN58cSfCVVnCWm7QH-KNs7ElXLqgAaCwu8mLyBs3yXOGOO7vArQw5Xu3N1X5WycB5" +
							//	"qwx1eeobZkQRntqNGiFOhTNq0amrqbYJBaSpKugPCC0nq4g55UgCBf75m0uMlpPES68-r4hsDAq" +
							//	"ZBJjvc-ilxWmNMwVhtYWf");
			
			UserToken auth_token = (UserToken) client.getAuthTokenFactory().getAuthToken();
			String token = auth_token.getValue(); // token is '12345abcde'
//			
			System.err.println("Token is "+ token);
			
		//	showAllDocs();
			
			

		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void showAllDocs() throws IOException, ServiceException {
		  URL feedUri = new URL(URL_SPECIAL);
		  DocumentListFeed feed = client.getFeed(feedUri, DocumentListFeed.class);

		  for (DocumentListEntry entry : feed.getEntries()) {
		    printEntry(entry);
		  }
		}

		public static void printEntry(DocumentListEntry entry) {
		  String resourceId = entry.getResourceId();
		  String docType = entry.getType();

		  System.err.println("=============================================================");
		  System.out.println("'" + entry.getTitle().getPlainText() + "' (" + docType + ")");
		  System.out.println("  link to Google Docs: " + entry.getDocumentLink().getHref());
		  System.out.println("  resource id: " + resourceId);
		  System.out.println("  doc id: " + entry.getDocId());

		  // print the parent folder the document is in
		  if (!entry.getParentLinks().isEmpty()) {
		    System.out.println("  Parent folders: ");
		    for (Link link : entry.getParentLinks()) {
		      System.out.println("    --" + link.getTitle() + " - " + link.getHref());
		    }
		  }

		  // print the timestamp the document was last viewed
		  DateTime lastViewed = entry.getLastViewed();
		  if (lastViewed != null) {
		    System.out.println("  last viewed: " + lastViewed.toUiString());
		  }

		  // print who made the last modification
		  LastModifiedBy lastModifiedBy = entry.getLastModifiedBy();
		  if (lastModifiedBy != null) {
		    System.out.println("  updated by: " +
		        lastModifiedBy.getName() + " - " + lastModifiedBy.getEmail());
		  }

		  // Files such as PDFs take up quota
		  if (entry.getQuotaBytesUsed() > 0) {
		    System.out.println("Quota used: " + entry.getQuotaBytesUsed() + " bytes");
		  }

		  // print other useful metadata
		  System.out.println("  last updated: " + entry.getUpdated().toUiString());
		  System.out.println("  viewed by user? " + entry.isViewed());
		  System.out.println("  writersCanInvite? " + entry.isWritersCanInvite().toString());
		  System.out.println("  hidden? " + entry.isHidden());
		  System.out.println("  starred? " + entry.isStarred());
		  System.out.println("  trashed? " + entry.isTrashed());
		  System.out.println();
		  
		  System.err.println("=============================================================");
		}



}
