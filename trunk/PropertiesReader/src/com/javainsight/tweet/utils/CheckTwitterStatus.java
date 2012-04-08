package com.javainsight.tweet.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gdata.data.spreadsheet.SpreadsheetEntry;



public class CheckTwitterStatus {
	
	private Map<String, Long> fileModificationMap = null;
	//private File dir = null;
//	private Collection<File> fileList = null;
	private List<SpreadsheetEntry> fileList = null;
	private Stack<String> revertQueue = null;
	private Stack<String> deleteQueue = null;
	private List<TwitterEvents> folderEventList = null;
	
	public CheckTwitterStatus(Map<String, Long> fileModificationMap,
							 				//File dir,
							 				Stack<String> revertQueue,
							 				Stack<String> deleteQueue,
							 				List<TwitterEvents> folderEventList){
		this.fileModificationMap = fileModificationMap;
		//this.dir = dir;		
		this.revertQueue = revertQueue;
		this.deleteQueue = deleteQueue;
		this.folderEventList = folderEventList;
	}
	
	public boolean check(){
		/*
		 * Step 1: Take the latest file listing
		 */
		System.err.println("Twitter Checker is running");
		List<String> tweets = TwitterServiceFactory.getTimeLine();
		/*
		 * Separate the REVERT commands. (JC:REVERT[filename])
		 */
		List<String> REVERT_CMDS = this.separateCommands(tweets, Constants.REVERT_COMMAND);
		List<String> DELETE_CMDS = this.separateCommands(tweets, Constants.DELETE_COMMAND);
		

		this.revertQueue.addAll(REVERT_CMDS);
		this.deleteQueue.addAll(DELETE_CMDS);

		if(REVERT_CMDS.size() > 0){
			this.folderEventList.add(TwitterEvents.REVERT);
		}
		if(REVERT_CMDS.size() > 0){
			this.folderEventList.add(TwitterEvents.DELETE);
		}
		
		if(this.revertQueue.size() > 0 || this.deleteQueue.size() > 0){
			return true;
		}else{
			return false;
		}		
	}
	
	private List<String> separateCommands(List<String> allTweets, String CMD_REGEX){
		List<String> cmds = new ArrayList<String>();
		Pattern MY_PATTERN = Pattern.compile(Constants.FILE_NAME_REGEX);
		for(String tweet : allTweets){
			if(tweet.matches(CMD_REGEX)){
				Matcher m = MY_PATTERN.matcher(tweet);
				while (m.find()) {
					cmds.add(m.group(1));    
				}

					
			}
		}
		return cmds;
	}
	
	
	public static void main(String[] args) {
		CheckTwitterStatus checker = new CheckTwitterStatus(null, new Stack<String>(), new Stack<String>(), new ArrayList<TwitterEvents>());
		System.err.println(checker.check());
	//	System.err.println("JC:REVERT<>".matches(Constants.REVERT_COMMAND));
	}

}
