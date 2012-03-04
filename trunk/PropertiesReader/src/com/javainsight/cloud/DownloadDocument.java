package com.javainsight.cloud;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.util.ServiceException;

public class DownloadDocument {
	
private DocsService client = new DocsService("~Javainsights~");
	
	public static void main(String[] args) {
		try {
			DownloadDocument download = new DownloadDocument();
			//update.updateDocumentName(Constants.GOOGLE_DOC_URL, Constants.AUTH_TOKEN, "coreNew_1", "coreNew_1");
			download.downloadFile(Constants.GOOGLE_DOC_URL+Constants.FOLDER, "testData/core.doc", Constants.AUTH_TOKEN);
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}
	
	
	public void downloadFile(String URL, String filepath, String _authToken) throws IOException, MalformedURLException, ServiceException {
		  System.out.println("Exporting document from: " + URL);

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
		//  DocumentQuery query = new DocumentQuery(feedUri);
		  DocumentListFeed feeds = this.client.getFeed(feedUri, DocumentListFeed.class);
		  /*
		   * Print the data
		   */
		  for(DocumentListEntry entry : feeds.getEntries()){
			  System.err.println(entry.getTitle().getPlainText());
			  System.err.println(entry.getTitle().getPlainText());
			  String foldersFeedUri =  ((MediaContent)entry.getContent()).getUri();			  
			  DocumentListFeed filesInFolder = this.client.getFeed(new URL(foldersFeedUri), DocumentListFeed.class);
			  for(DocumentListEntry files : filesInFolder.getEntries()){
				  System.err.println("    " + files.getTitle().getPlainText());
				  if(files.getTitle().getPlainText().contains("coreNew_1")){
					  MediaContent mc = new MediaContent();
					  mc.setUri(files.getDocumentLink().getHref());
					  System.out.println("Exporting document from: " + files.getDocumentLink().getHref());
					  MediaSource ms = client.getMedia(mc);
					
					  InputStream inStream = null;
					  FileOutputStream outStream = null;
					
					  try {
					    inStream = ms.getInputStream();
					    outStream = new FileOutputStream(filepath);
					
					    int c;
					    while ((c = inStream.read()) != -1) {
					      outStream.write(c);
					    }
					  } finally {
					    if (inStream != null) {
					      inStream.close();
					    }
					    if (outStream != null) {
					      outStream.flush();
					      outStream.close();
					    }
					  } 
				  }
			  }
		  }
		  
}


}
