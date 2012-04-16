package com.javainsight.tweet.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class TwitterCloud {
	
		
	
	public Map<String, List<String>> getFileList(String directory) throws Exception{
        boolean recursive = false;
        Map<String, List<String>> fileDataMap = new HashMap<String, List<String>>();

		for(File file : FileUtils.listFiles(new File(directory), null, recursive)){
			List<String> fileData = FileUtils.readLines(file);
			System.err.println("Putting File >"+ file.getName()+" with "+ fileData.size() + " lines");
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
