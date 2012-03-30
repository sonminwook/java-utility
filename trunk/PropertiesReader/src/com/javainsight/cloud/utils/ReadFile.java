package com.javainsight.cloud.utils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

//1561659027
public class ReadFile {
	
	public List<String> readWorkSheet( String mainFileName, String workSheetName, boolean isReverseOrder) throws Exception{
		List<String> lines = new ArrayList<String>();
		List<SpreadsheetEntry> spreadsheets = ServiceFactory.getSpreadSheets();
		
		for (int i = 0; i < spreadsheets.size(); i++) {
		  SpreadsheetEntry entry = spreadsheets.get(i);		  
		 if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){ 
		  URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
		  WorksheetFeed worksheetFeed = ServiceFactory.getWorksheetFeed(worksheetFeedUrl);


		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
			  	URL listFeedUrl = worksheet.getListFeedUrl();
			  	ListQuery query = new ListQuery(listFeedUrl);
			  	query.setReverse(isReverseOrder);
			  	ListFeed listFeed = ServiceFactory.getListFeed(query);		  				  	 
			  	
			  	lines.add(this.prepareComment(mainFileName, worksheet));			  	
			  	lines.addAll(this.prepareString(mainFileName, listFeed, worksheet.getTitle().getPlainText()));
		  		} // End of For loop for iterating in the worksheets
		   } // End of if (entry... 		  
		}// End of for (int i...	
		this.prepareHeader(mainFileName, lines);
		return lines;
	}

	private String prepareComment(String mainFileName, WorksheetEntry worksheet){
		String comment = "Entries from "+ worksheet.getTitle().getPlainText() +
							" ["+ "Last Modified on " + 
								new SimpleDateFormat(Constants.COMMENT_DATE_FORMAT).format(new Date(worksheet.getUpdated().getValue()))+"]";
		
		if(mainFileName.contains(".XML") || mainFileName.contains(".xml")){
			comment = "<!-- " + comment + "-->";
		}else{
			comment = "# " + comment;
		}		
		return comment;		
	}
	
	private List<String> prepareString(String mainFileName, ListFeed listFeed, String worksheetSheetName){
		List<String> lines = new ArrayList<String>();		
		if(mainFileName.contains(".XML") || mainFileName.contains(".xml")){			
			for (ListEntry listEntry : listFeed.getEntries()) {				
  				String value = "";  				
  					for (String tag : listEntry.getCustomElements().getTags()) {
  						String val = listEntry.getCustomElements().getValue(tag)==null?"":listEntry.getCustomElements().getValue(tag);
  						value = value + "<" + tag + ">" + val + "</" + tag + ">";			
  					}
  					lines.add(value);	
  					System.err.println(value);
			}						
		}else{
			for (ListEntry listEntry : listFeed.getEntries()) {
  				String replacer = "";
  				String value = ""; 
  				boolean isRequired = false;  				
  				
  				if(listEntry.getCustomElements().getTags().contains(Constants.SEPARATOR)){
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
			}// End of reading every row of the worksheet
  				else{
  					for (String tag : listEntry.getCustomElements().getTags()) {
							String val = listEntry.getCustomElements().getValue(tag)==null?"":listEntry.getCustomElements().getValue(tag);
							value =  tag + "=" + val;
							lines.add(value);							
						}					
						
  				}			 
			}			
		}		
		return lines;
	}
	
	private void prepareHeader(String mainFileName, List<String> lines){
		if(mainFileName.contains(".XML") || mainFileName.contains(".xml")){
			lines.add(0, Constants.XML_HEADER);
			lines.add(1, "<" + mainFileName + ">");
			lines.add("</"+ mainFileName+">");			
		}
	}
}
