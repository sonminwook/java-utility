package com.javainsight.cloud.utils;

import java.util.List;

import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

public class ExcelCloud {
	
		
	public List<SpreadsheetEntry> getFileList() {
		try{
			List<SpreadsheetEntry> spreadsheets = ServiceFactory.getSpreadSheets();
			System.err.println(spreadsheets);
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
					System.exit(1);
				}
			}
			return spreadsheets;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	public static void main(String[] args) {
		try {
//			service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
//			UserToken auth_token = (UserToken) service.getAuthTokenFactory().getAuthToken();
//			String token = auth_token.getValue(); // token is '12345abcde'
//			System.err.println(token);
			new ExcelCloud().getFileList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
