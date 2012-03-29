package com.javainsight.cloud.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

//1561659027
public class ReadFile {
	
	//private SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	//private URL metfeedURL = new URL(Constants.SPREADSHEET_URL);
	
	public void readWorkSheet( String mainFileName, String workSheetName, boolean isReverseOrder) throws Exception{
		List<String> lines = new ArrayList<String>();
		List<SpreadsheetEntry> spreadsheets = ServiceFactory.getSpreadSheets();
		
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);
		  System.out.println("\t" + entry.getTitle().getPlainText());
		  
		 if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){ 
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = ServiceFactory.getWorksheetFeed(worksheetFeedUrl);


		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
			  	URL listFeedUrl = worksheet.getListFeedUrl();
			  	ListQuery query = new ListQuery(listFeedUrl);
			  	query.setReverse(isReverseOrder);
			  	ListFeed listFeed = ServiceFactory.getListFeed(query);

			  	
			  		for (ListEntry listEntry : listFeed.getEntries()) {
			  				String replacer = "";
			  				String value = ""; 
			  				boolean isRequired = false;
			  						for (String tag : listEntry.getCustomElements().getTags()) {
			  							String val = listEntry.getCustomElements().getValue(tag)==null?"":listEntry.getCustomElements().getValue(tag);
			  							if(isRequired){
			  								if(tag.equalsIgnoreCase(Constants.SEPARATOR)){
			  									replacer = listEntry.getCustomElements().getValue(tag);
			  								}else{
			  									value +=  val + Constants.SEPARATOR_CHAR;
			  								}
			  							}else{
			  								value =  val + "=";
			  								isRequired = true;
			  							}
			   
			  						}
			  						value = value.replaceAll(Constants.SEPARATOR_CHAR, replacer);
			  						if(value.endsWith(replacer)){
			  							value = value.substring(0, value.lastIndexOf(replacer));
			  						}
			  						lines.add(value);
			  						System.err.println(value);
			  		}
		  	}
		  
		 }		  
		}
		
	}

}
