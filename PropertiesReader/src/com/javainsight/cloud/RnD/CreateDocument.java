package com.javainsight.cloud.RnD;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentEntry;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.docs.PresentationEntry;
import com.google.gdata.data.docs.SpreadsheetEntry;
import com.google.gdata.util.ServiceException;

public class CreateDocument {
	private DocsService client = new DocsService("~Javainsights~");
	
	public static void main(String[] args) {
		try{
			CreateDocument createDoc = new CreateDocument();
			createDoc.client.setUserToken(Constants.AUTH_TOKEN);
			//createDoc.createDoc();
			createDoc.uploadDocument();
			//createDoc.uploadDocInFolder(Constants.GOOGLE_DOC_URL+Constants.FOLDER, Constants.AUTH_TOKEN, "config");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void createDoc() throws IOException, ServiceException{
		DocumentListEntry createdEntry = null;

		// Create an empty word processor document
		createdEntry = createNewDocument("NewDocTitle", "document");
		System.out.println("Document now online @ :" + createdEntry.getDocumentLink().getHref());

		// Create an empty presentation
		createdEntry = createNewDocument("NewPresentationTitle", "presentation");
		System.out.println("Presentation now online @ :" + createdEntry.getDocumentLink().getHref());

		// Create an empty spreadsheet
		createdEntry = createNewDocument("NewSpreadsheetTitle", "spreadsheet");
		System.out.println("Spreadsheet now online @ :" + createdEntry.getDocumentLink().getHref());

		
	}
	
	private DocumentListEntry createNewDocument(String title, String type)  throws IOException, ServiceException {
		  DocumentListEntry newEntry = null;
		  if (type.equals("document")) {
		    newEntry = new DocumentEntry();
		  } else if (type.equals("presentation")) {
		    newEntry = new PresentationEntry();
		  } else if (type.equals("spreadsheet")) {
		    newEntry = new SpreadsheetEntry();
		  }
		  newEntry.setTitle(new PlainTextConstruct(title));
		
		  // Prevent collaborators from sharing the document with others?
		  // newEntry.setWritersCanInvite(false);
		
		  // You can also hide the document on creation
		  // newEntry.setHidden(true);
		
		  return client.insert(new URL("https://docs.google.com/feeds/default/private/full/"), newEntry);
	}

	private void uploadDocument() throws IOException, ServiceException{
		DocumentListEntry uploadedEntry = uploadFile("TestData/result.csv", "DailyTxn.csv");
		System.out.println("Document now online @ :" + uploadedEntry.getDocumentLink().getHref());

	}

	public DocumentListEntry uploadFile(String filepath, String title)   throws IOException, ServiceException  {
	File file = new File(filepath);
	String mimeType = DocumentListEntry.MediaType.fromFileName(file.getName()).getMimeType();

	DocumentListEntry newDocument = new DocumentListEntry();
	newDocument.setFile(file, mimeType);
	newDocument.setTitle(new PlainTextConstruct(title));

	// Prevent collaborators from sharing the document with others?
	// newDocument.setWritersCanInvite(false);

		//return client.insert(new URL("https://docs.google.com/feeds/default/private/full/"), newDocument);
		return client.update(new URL("https://docs.google.com/feeds/default/private/full/"), newDocument);
	}

	private void uploadDocInFolder(String URL, String _authToken, String folderName) throws IOException, ServiceException{
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
			  if(entry.getTitle().getPlainText().equalsIgnoreCase(folderName)){
				  String destFolderUrl = ((MediaContent) entry.getContent()).getUri();
				  DocumentListEntry createdEntry = createNewDocument("NewDocument", "document", new URL(destFolderUrl));
				  System.out.println("Document now online in folder '" +
				      entry.getTitle().getPlainText() + "' @ :" + createdEntry.getDocumentLink().getHref());

			  }
			  
		  }
	}
	
	public DocumentListEntry createNewDocument(String title, String type, URL uri)  throws IOException, ServiceException {
		  DocumentListEntry newEntry = null;
		  if (type.equals("document")) {
			  newEntry = new DocumentEntry();
		  } else if (type.equals("presentation")) {
			  newEntry = new PresentationEntry();
		  } else if (type.equals("spreadsheet")) {
			  newEntry = new SpreadsheetEntry();
		  }
		  	newEntry.setTitle(new PlainTextConstruct(title));
		
		  return client.insert(uri, newEntry);
	}

	}
