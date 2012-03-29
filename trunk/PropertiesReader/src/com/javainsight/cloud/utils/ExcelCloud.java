package com.javainsight.cloud.utils;

import java.util.List;

import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

public class ExcelCloud {
	
		
	public List<SpreadsheetEntry> getFileList() {
		try{
			List<SpreadsheetEntry> spreadsheets = ServiceFactory.getSpreadSheets();
			for(SpreadsheetEntry entry : spreadsheets){
				for(Person p : entry.getAuthors()){
					if(p.getEmail().equalsIgnoreCase(Constants.MASTER_EMAIL_ADD))
						System.err.println(entry.getTitle().getPlainText() + " >---> " + p.getName() + "/"+ p.getEmail());
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
