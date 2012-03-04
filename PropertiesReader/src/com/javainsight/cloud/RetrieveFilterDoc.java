package com.javainsight.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.DocumentListLink;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.ServiceException;

public class RetrieveFilterDoc {
	
	private DocsService client = new DocsService("~Javainsights~");
	
	public static void main(String[] args) {
		try {
			System.err.println("== Testing Google Docs ==");
			RetrieveFilterDoc filter = new RetrieveFilterDoc();
			//filter.filterAllDocs(Constants.GOOGLE_DOC_URL+Constants.FOLDER, Constants.AUTH_TOKEN, "test", false);
			filter.listFiles(Constants.GOOGLE_DOC_URL+Constants.FOLDER, Constants.AUTH_TOKEN);
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
		   this.client.setUserToken(_authToken);
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
			  String foldersFeedUri =  ((MediaContent)entry.getContent()).getUri();			  
			  DocumentListFeed filesInFolder = this.client.getFeed(new URL(foldersFeedUri), DocumentListFeed.class);
			  for(DocumentListEntry files : filesInFolder.getEntries()){
				  System.err.println("    " + files.getTitle().getPlainText());
				  if(!files.getTitle().getPlainText().contains("Table")){
					  	System.err.println(files.getContent());
				  		//System.err.println(files.getMediaSource().toString());
				  		
				  		MediaContent content = (MediaContent)files.getContent(); 
				  		String url = content.getUri();
				  		//System.err.println(content.get);
				  		System.err.println(content.getLang());

				  }
			  }
		  }
	}

}
