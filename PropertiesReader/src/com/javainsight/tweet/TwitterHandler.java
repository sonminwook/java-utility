package com.javainsight.tweet;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public final class TwitterHandler {
	
	public void handle(List<String> tweetLines){
		try{
			Twitter twitter = new TwitterFactory().getInstance();
			
			
			for(String str : tweetLines){
				Status status = twitter.updateStatus(str);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
