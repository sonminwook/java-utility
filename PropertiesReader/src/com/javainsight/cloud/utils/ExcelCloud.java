package com.javainsight.cloud.utils;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;

public class ExcelCloud {
	
	private SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	{
		service.setUserToken(Constants.SPREADSHEET_AUTH_TOKEN);
	}
		
	public List<SpreadsheetEntry> getFileList() {
		try{
		Date date = new Date();
		URL metafeedUrl = new URL(Constants.SPREADSHEET_URL);
		System.err.println("Step 1 " + (new Date().getTime() - date.getTime()) + " ms");
		date = new Date();
		
		SpreadsheetFeed feed = service.getFeed(metafeedUrl, SpreadsheetFeed.class);
		System.err.println("Step 2 " + (new Date().getTime() - date.getTime()) + " ms");
		date = new Date();
		
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();
		System.err.println("Step 3 " + (new Date().getTime() - date.getTime()) + " ms");
		date = new Date();
		
		for (int i = 0; i < spreadsheets.size(); i++) {
			SpreadsheetEntry entry = spreadsheets.get(i);
			System.out.println(entry.getTitle().getPlainText());
			System.err.println("sub Steps " + (new Date().getTime() - date.getTime()) + " ms");
			date = new Date();
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
