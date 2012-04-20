package com.javainsight.tweet.utils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javainsight.commons.io.FileUtils;

public class TwitterCloud {
	
		
	
	public Map<String, List<String>> getFileList(String directory) throws Exception{       
        Map<String, List<String>> fileDataMap = new HashMap<String, List<String>>();        
        for(String str : new File(directory).list()){
        	File file = new File(directory + File.separator + str);
        	if(file.isFile()){
        		List<String> fileData = FileUtils.readLines(file);
        		System.err.println("Putting File >"+ file.getName()+" with "+ fileData.size() + " lines");
        		fileDataMap.put(file.getName(), fileData);
        	}
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
