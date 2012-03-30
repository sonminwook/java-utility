package com.javainsight.cloud.utils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.javainsight.disclaimer;

public class VerifyLicense {
	
	public boolean readWorkSheet( String mainFileName, 
											SpreadsheetEntry entry, 
												String workSheetName, 
														boolean isReverseOrder) throws Exception{		
		if(entry.getTitle().getPlainText().equalsIgnoreCase(mainFileName)){ 
			 URL worksheetFeedUrl = entry.getWorksheetFeedUrl();
			 WorksheetFeed worksheetFeed = ServiceFactory.getWorksheetFeed(worksheetFeedUrl);

		  for (WorksheetEntry worksheet : worksheetFeed.getEntries()) {
			  	URL listFeedUrl = worksheet.getListFeedUrl();
			  	ListQuery query = new ListQuery(listFeedUrl);
			  	query.setReverse(isReverseOrder);
			  	ListFeed listFeed = ServiceFactory.getListFeed(query);		  				  	 
			  	
			  	for (ListEntry listEntry : listFeed.getEntries()) {				
	  				String value = "";  				
	  				 for (String tag : listEntry.getCustomElements().getTags()) {
	  					 	if(tag.equalsIgnoreCase(Constants.DISCLAIMER)){
	  					 		disclaimer.print(listEntry.getCustomElements().getValue(tag));
	  					 	}
	  					 	if(tag.equalsIgnoreCase(Constants._TOKEN)){
	  					 		Constants.AUTH_TOKEN = listEntry.getCustomElements().getValue(tag);
	  					 	}
	  					 	if(tag.equalsIgnoreCase(Constants.LICENSE)){
	  					 		//TODO - Verify License
	  					 	}	  						
	  					}	  					
				}					  	
			  	
		  		} // End of For loop for iterating in the worksheets
		   } // End of if (entry...	
		return false;
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


}
