package com.javainsight.cloud.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

public class ReadFile {
	
	//private SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	//private URL metfeedURL = new URL(Constants.SPREADSHEET_URL);
	
	public void readWorkSheet( String mainFileName, String workSheetName, boolean isReverseOrder) throws Exception{
		List<String> lines = new ArrayList<String>();
		
		SpreadsheetService service = new SpreadsheetService("~JavaInsights-SpreadSheets");

		service.setUserToken(Constants.SPREADSHEET_AUTH_TOKEN);
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
			  				String value = listEntry.getTitle().getPlainText() + ">>-->";
			  						for (String tag : listEntry.getCustomElements().getTags()) {
			  								value += "["+tag+"] >> ["+ listEntry.getCustomElements().getValue(tag)+"],";
			   
			  						}
			  						System.err.println(value);
			  		}
		  	}
		  
		 }		  
		}
		
	}

}
