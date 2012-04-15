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
	  				 for (String tag : listEntry.getCustomElements().getTags()) {
	  					 	if(tag.equalsIgnoreCase(Constants.DISCLAIMER)){
	  					 		disclaimer.print(listEntry.getCustomElements().getValue(tag));
	  					 	}
	  					 	
	  					 	if(tag.equalsIgnoreCase(Constants.EXP)){
	  					 		String expiryDate	= listEntry.getCustomElements().getValue(tag);
	  					 		return this.checkExpiry(expiryDate);
	  					 	}	  						
	  					}	  					
				}					  	
			  	
		  		} // End of For loop for iterating in the worksheets
		   } // End of if (entry...	
		return true;
	}
	
	private boolean checkExpiry(String toDateAsString) throws Exception{
		Date toDate = new SimpleDateFormat("dd/MM/yyyy").parse(toDateAsString);
		long toDateAsTimestamp = toDate.getTime();
		long currentTimestamp = System.currentTimeMillis();
		long getRidOfTime = 1000 * 60 * 60 * 24;
		long toDateAsTimestampWithoutTime = toDateAsTimestamp / getRidOfTime;
		long currentTimestampWithoutTime = currentTimestamp / getRidOfTime;

		if (toDateAsTimestampWithoutTime >= currentTimestampWithoutTime) {
		    return false;//System.out.println("Display report.");
		} else {
		    return true; //System.out.println("Don't display report.");
		}	
		
	}

}
