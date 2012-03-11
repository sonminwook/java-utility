package com.javainsight.cloud.utils;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

public class ExcelCloud {
	
	private SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	{
		service.setUserToken(Constants.SPREADSHEET_AUTH_TOKEN);
	}
		
	public List<SpreadsheetEntry> getFileList() {
		try{

		URL metafeedUrl = new URL(Constants.SPREADSHEET_URL);
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
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
