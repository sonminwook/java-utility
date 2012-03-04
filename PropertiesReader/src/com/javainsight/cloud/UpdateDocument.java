package com.javainsight.cloud;

import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaByteArraySource;
import com.google.gdata.util.ServiceException;

public class UpdateDocument {
	
	//entry.setTitle(new PlainTextConstruct("Updated Title"));
	//DocumentListEntry updatedEntry = entry.update();

	// Alternatively, you can use the DocsService object's update():
	// DocumentListEntry updatedEntry = client.update(new URL(entry.getEditLink().getHref()), entry, entry.getEtag());

	//System.out.println(updatedEntry.getTitle().getPlainText());
	
	private DocsService client = new DocsService("~Javainsights~");
	
	public static void main(String[] args) {
		try {
			UpdateDocument update = new UpdateDocument();
			update.updateDocumentName(Constants.GOOGLE_DOC_URL, Constants.AUTH_TOKEN, "coreNew_1", "coreNew_1");
		} catch (Exception e) {			
			e.printStackTrace();
		} 
	}
	
	private void updateDocumentName(String URL, String _authToken, String oldName, String newName) throws IOException, ServiceException {
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
			  if(entry.getTitle().getPlainText().equalsIgnoreCase(oldName)){
				 // entry.setTitle(new PlainTextConstruct(newName));
				  //DocumentListEntry updatedEntry = entry.update();

				  // Alternatively, you can use the DocsService object's update():
				  // DocumentListEntry updatedEntry = client.update(new URL(entry.getEditLink().getHref()), entry, entry.getEtag());

				  System.out.println(entry.getTitle().getPlainText());
				  entry.setMediaSource(new MediaByteArraySource("updated content".getBytes(), "text/plain"));
				  DocumentListEntry newEntry = entry.updateMedia(true);

			  }
			  
		  }
	}

}
