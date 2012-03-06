package com.javainsight.cloud;

import java.io.File;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.DocumentQuery;
import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.docs.DocumentListEntry;
import com.google.gdata.data.docs.DocumentListFeed;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

public class UpdateExcel {
	
	private DocsService client = new DocsService("~Javainsights~");
	
	public static void main(String[] args) {
		try{
			UpdateExcel update = new UpdateExcel();
			/*
			 * Insert a worksheet, Get List
			 */
			//update.addWorkSheet("DailyTxn.csv", "TestWorkSheet");
			//update.getAllExcelList();
			/*
			 * Readign all the rows
			 */
			//update.readWorkSheet("ACMapper", null, true);
			update.readStructuredQuery("ACMapper", null, "storeid = 123456");
			
			//DocumentListEntry uploadedEntry = update.updateExisting(Constants.AUTH_TOKEN, Constants.GOOGLE_DOC_URL, "TestData/result.csv", "DailyTxn.csv", true);
			//System.out.println("Document now online @ :" + uploadedEntry.getDocumentLink().getHref());
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void getAllExcelList() throws Exception	{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		  List<WorksheetEntry> worksheets = entry.getWorksheets();
		  for (int j = 0; j < worksheets.size(); j++) {
		    WorksheetEntry worksheet = worksheets.get(j);
		    String title = worksheet.getTitle().getPlainText();
		    int rowCount = worksheet.getRowCount();
		    int colCount = worksheet.getColCount();
		    System.out.println("\t" + title + "- rows:" + rowCount + " cols: " + colCount);
		  }
		}
	}
	
	private void addWorkSheet(String mainFileName, String workSheetName) throws Exception{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		  if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){
			  WorksheetEntry worksheet = new WorksheetEntry();
				worksheet.setTitle(new PlainTextConstruct(workSheetName));
				worksheet.setRowCount(200);
				worksheet.setColCount(30);
				URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
				service.insert(worksheetFeedUrl, worksheet);
		  }
		}
	}
	
	private void modifyWorkSheet(String mainFileName, String oldName, String newTitle, int newRows, int newColumns) throws Exception{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);

		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
		    String currTitle = worksheet.getTitle().getPlainText();
		    if (currTitle.equals(oldName)) {
		    	worksheet.setTitle(new PlainTextConstruct(newTitle));
		    	worksheet.setRowCount(newRows);
		    	worksheet.setColCount(newColumns);
		    	worksheet.update();
		    	return;
		    }
		  }
		 
		}
	}
	
	private void deleteWorkSheet(String mainFileName, String workSheetName) throws Exception{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);

		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
		    String currTitle = worksheet.getTitle().getPlainText();
		    if (currTitle.equals(workSheetName)) {
		    	worksheet.delete();
		    	
		    }
		  }
		 
		}
	}
	
	private void readWorkSheet(String mainFileName, String workSheetName, boolean isReverseOrder) throws Exception{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		 if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){ 
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);

		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
		  URL listFeedUrl = worksheet.getListFeedUrl();
		  	ListQuery query = new ListQuery(listFeedUrl);
		  	query.setReverse(isReverseOrder);
			ListFeed listFeed = service.query(query, ListFeed.class);
			for (ListEntry listEntry : listFeed.getEntries()) {
			  System.out.println(listEntry.getTitle().getPlainText());
			  for (String tag : listEntry.getCustomElements().getTags()) {
			    System.out.println("  <gsx:" + tag + ">" +
			    listEntry.getCustomElements().getValue(tag) + "</gsx:" + tag + ">");
			  }
			}
		  }
		 }		  
		}
		
	}
	
	private void readStructuredQuery(String mainFileName, String workSheetName, String conditionStatment) throws Exception{
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");
		service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
		URL metafeedUrl = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		 if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){ 
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = service.getFeed(worksheetFeedUrl, WorksheetFeed.class);

		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
		  URL listFeedUrl = worksheet.getListFeedUrl();
		  	ListQuery query = new ListQuery(listFeedUrl);
		  	query.setSpreadsheetQuery(conditionStatment);
			ListFeed listFeed = service.query(query, ListFeed.class);
			for (ListEntry listEntry : listFeed.getEntries()) {
			  System.out.println(listEntry.getTitle().getPlainText());
			  for (String tag : listEntry.getCustomElements().getTags()) {
			    System.out.println("  <gsx:" + tag + ">" +
			    listEntry.getCustomElements().getValue(tag) + "</gsx:" + tag + ">");
			  }
			}
		  }
		 }		  
		}
		
	}
	
	private DocumentListEntry uploadNew(String _authToken, String URL,String filepath, String title) throws Exception{
		File file = new File(filepath);
		String mimeType = DocumentListEntry.MediaType.fromFileName(file.getName()).getMimeType();

		DocumentListEntry newDocument = new DocumentListEntry();
		newDocument.setFile(file, mimeType);
		newDocument.setTitle(new PlainTextConstruct(title));		
		return client.insert(new URL(URL), newDocument);		
	}
	
	
	private DocumentListEntry updateExisting(String _authToken, String URL, String filepath, String _documentName, boolean isExact) throws Exception{
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
		  
		  if(feed.getEntries().size() > 0){
			  for (DocumentListEntry entry : feed.getEntries()) {
				  System.err.println("Found <" + entry.getTitle().getPlainText() + ">, trying to update");
				  /*
				   * Update code
				   */
				  File file = new File(filepath);
				  String mimeType = DocumentListEntry.MediaType.fromFileName(file.getName()).getMimeType();
				  entry.setMediaSource(new MediaFileSource(file, mimeType));
				 // entry.
				  //entry.setTitle(new PlainTextConstruct("Newer Title"));
				  entry.setWritersCanInvite(false);
				  
				  DocumentListEntry updatedEntry = entry.update();
				  return updatedEntry;
			  }
			  return null;
		  }else{
			  return uploadNew(_authToken, URL,	  filepath, _documentName);
		  }
		
	}

}
