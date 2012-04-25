package com.javainsight.cloud.utils;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.util.AuthenticationException;

public class ExcelCloud {
	
	private static Logger logger = Logger.getLogger(ExcelCloud.class);
		
	public List<SpreadsheetEntry> getFileList() {
		try{
			List<SpreadsheetEntry> spreadsheets = ServiceFactory.getSpreadSheets();
			if(Constants.license_check){
				for(SpreadsheetEntry entry : spreadsheets){
					boolean isExit = false;
					if(entry.getTitle().getPlainText().equalsIgnoreCase(Constants.LICENSE)){
						isExit = true;
						for(Person p : entry.getAuthors()){					
								if(p.getEmail().equalsIgnoreCase(Constants.MASTER_EMAIL_ADD)){
									// Verify the details here...
									isExit = new VerifyLicense().readWorkSheet(entry.getTitle().getPlainText(), entry, null, false);
								}
							}					
					}
					if(isExit){
						logger.error("License expired");
						System.exit(1);
					}
				}
			}
			return spreadsheets;
		}catch(AuthenticationException uhe){
			logger.error("Unable to hook up with cloud, either Internet not available or firewall blocking the route to google.com [" + 
					uhe.getMessage()+"]");
		}
		catch(Exception e){
			logger.error("Exception while fetching the file list from cloud", e);
		}
		
		return null;
	}

}
