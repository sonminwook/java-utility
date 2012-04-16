package com.javainsight.tweet;

import java.util.List;

import org.apache.log4j.Logger;

import twitter4j.TwitterException;

import com.javainsight.tweet.utils.TwitterServiceFactory;

public final class TwitterHandler {
	
	private static Logger logger = Logger.getLogger(TwitterHandler.class);
	
	public void handle(List<String> tweetLines){
		try{
			for(String str : tweetLines){
				try {
					TwitterServiceFactory.twitter.updateStatus(str);
				} catch (TwitterException te) {
					int code = te.getStatusCode();
					if(code != 403){
						logger.error("Unable to update status >"+ str+"< "+ te.getMessage());
					}
				}
			}
		}
		catch(Exception e){
			logger.error("Unable to update status "+ e.getMessage());
		}
	}

}
