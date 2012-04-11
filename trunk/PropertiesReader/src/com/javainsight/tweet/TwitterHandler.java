package com.javainsight.tweet;

import java.util.List;

import com.javainsight.tweet.utils.TwitterServiceFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public final class TwitterHandler {
	
	public void handle(List<String> tweetLines){
		try{
			for(String str : tweetLines){
				Status status = TwitterServiceFactory.twitter.updateStatus(str);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
