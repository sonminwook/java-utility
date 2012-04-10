package com.javainsight.tweet.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.google.gdata.data.Person;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;

public class TwitterCloud {
	
		
	public List<SpreadsheetEntry> getFileList() {
		try{
			List<SpreadsheetEntry> spreadsheets = TwitterServiceFactory.getSpreadSheets();
			for(SpreadsheetEntry entry : spreadsheets){
				boolean isExit = false;
				if(entry.getTitle().getPlainText().equalsIgnoreCase(Constants.LICENSE)){
					isExit = true;
					for(Person p : entry.getAuthors()){					
							if(p.getEmail().equalsIgnoreCase(Constants.MASTER_EMAIL_ADD)){
								// Verify the details here...
								isExit = false; //new VerifyLicense().readWorkSheet(entry.getTitle().getPlainText(), entry, null, false);
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
	
	public Map<String, List<String>> getFileList(String directory) throws Exception{
        boolean recursive = false;
        Map<String, List<String>> fileDataMap = new HashMap<String, List<String>>();

		for(File file : FileUtils.listFiles(new File(directory), null, recursive)){
			List<String> fileData = FileUtils.readLines(file);
			fileDataMap.put(file.getName(), fileData);
		}
		return fileDataMap;
	}
	

	
	public static void main(String[] args) {
		try {
//			service.setUserCredentials("javainsights@gmail.com", "1qaz1234");
//			UserToken auth_token = (UserToken) service.getAuthTokenFactory().getAuthToken();
//			String token = auth_token.getValue(); // token is '12345abcde'
//			System.err.println(token);
			new TwitterCloud().getFileList("config");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
