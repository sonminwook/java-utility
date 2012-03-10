package com.javainsight.cloud.RnD;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.media.MediaService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.ServiceException;
import com.google.gdata.util.common.io.CharStreams;

public class RetrieveFilterDoc {
	
	private SpreadsheetService  client = new SpreadsheetService("~Javainsights~");
	
	public static void main(String[] args) {
		try {
			System.err.println("== Testing Google Docs ==");
			RetrieveFilterDoc filter = new RetrieveFilterDoc();
			//filter.filterAllDocs(Constants.GOOGLE_DOC_URL+Constants.FOLDER, Constants.AUTH_TOKEN, "test", false);
			filter.listFiles(Constants.SPREADSHEET_URL, Constants.AUTH_TOKEN);
		}  catch (Exception e) {			
			e.printStackTrace();
		}
	}

	public void filterAllDocs(String URL, String _authToken, String _documentName, boolean isExact) throws IOException, ServiceException {
		  /*
		   * Set Client login credentials
		   */
		   this.client.setUserToken(_authToken);
		   /*
		    * Set Data Fetching URL
		    */
		  URL feedUri = new URL(URL);
		   /*
		   * Preparing the Filter
		   */		  
		  DocumentQuery query = new DocumentQuery(feedUri);
		  query.setTitleQuery(_documentName);		  
		  query.setTitleExact(isExact);		  
          /*
           * Fetch the documents
           */		  
		  DocumentListFeed feed = this.client.getFeed(query, DocumentListFeed.class);

		  for (DocumentListEntry entry : feed.getEntries()) {
			  System.err.println(entry.getTitle().getPlainText());
		  }
		}


	public void listFiles(String URL, String _authToken) throws IOException, ServiceException{
		/*
		   * Set Client login credentials
		   */
		//  this.client.setUserToken(_authToken);
		this.client.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		   /*
		    * Set Data Fetching URL
		    */
		  URL feedUri = new URL(URL);
		  /*
		   * Find the document
		   */
		  DocumentQuery query = new DocumentQuery(feedUri);
		 // query.setTitleQuery(_documentName);		  
		 // query.setTitleExact(isExact);	
		  //query.
		 
		  DocumentListFeed feeds = this.client.getFeed(feedUri, DocumentListFeed.class);
		  /*
		   * Print the data
		   */
		  for(DocumentListEntry entry : feeds.getEntries()){			 
			  System.err.println(entry.getTitle().getPlainText());
			 if (!entry.getParentLinks().isEmpty()){ 
			  //if(entry.getTitle().getPlainText().contains("Test")){				 
				  //System.err.println(entry.getContent().getType());
				  //System.err.println(IContent.Type.HTML + " "+ IContent.Type.MEDIA+ " "+ IContent.Type.OTHER_BINARY+ " "+ IContent.Type.OTHER_TEXT);
				  //System.err.println(IContent.Type.OTHER_XML + " "+ IContent.Type.TEXT+ " "+ IContent.Type.XHTML+ " "+ IContent.Type.OTHER_TEXT);
				  //MediaContent content = (MediaContent)entry.getContent();
				  //MediaService service = new MediaService("doc", "Sunny.test");				  
				  MediaSource src =  entry.getMediaSource();//service.getMedia(content); //content.getMediaSource();//client.getMedia(content);				  				 
				  String data = CharStreams.toString(new InputStreamReader(src.getInputStream()));

				  System.err.println(data);
				  //System.err.println(entry.getTextContent());
			  }
//			  String foldersFeedUri =  ((MediaContent)entry.getContent()).getUri();			  
//			  DocumentListFeed filesInFolder = this.client.getFeed(new URL(foldersFeedUri), DocumentListFeed.class);
//			  for(DocumentListEntry files : filesInFolder.getEntries()){
//				  System.err.println("    " + files.getTitle().getPlainText());
//				  if(files.getTitle().getPlainText().contains("Test")){
//					  	System.err.println(files.getContent());
//				  		//System.err.println(files.getMediaSource().toString());
//					  	
//				  		System.err.println(files.getPlainTextContent()+"/content");
//				  		MediaContent content = (MediaContent)files.getContent(); 
//				  		String url = content.getUri();
//				  		
//				  		System.err.println(url);
//				  		System.err.println(content.getLang());
//
//				  }
//			  }
		  }
	}

}
