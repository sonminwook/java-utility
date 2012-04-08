package com.javainsight.tweet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.javainsight.tweet.utils.Constants;

public class TweetUtils {
	
	private static Logger logger = Logger.getLogger(TweetUtils.class);
	
	public void postMessage(String message){
		try{
			Twitter twitter = new TwitterFactory().getInstance();
			//twitter.getGeoDetails(new GeoLocation(52, 0).toString());
			//twitter.
			Status status = twitter.updateStatus(message);
			logger.trace("Successfully updated the status to ["+ status.getText()+"]");
		}catch(Exception e){
			logger.error("Unable to update the twitter status", e);
		}
	}
	
	public List<String> searchTweets(String searchHashTag){
		Twitter twitter = new TwitterFactory().getInstance();
		List<String> tweetsInstructions = new ArrayList<String>();
        try {
            
           
            Query query = new Query(searchHashTag);
           // query.setGeoCode(new GeoLocation(52, 0), 40000, Query.KILOMETERS);
            QueryResult result = twitter.search(query);
            List<Tweet> tweets = result.getTweets();
            for (Tweet tweet : tweets) {
                logger.trace("@" + tweet.getFromUser() + " - " + tweet.getText());
                tweetsInstructions.add("@" + tweet.getFromUser() + Constants.USER_TWEET_SEPARATOR + tweet.getText());
            }
        } catch (TwitterException te) {
        	logger.error("Failed to search tweets:", te);
        }
        
        return tweetsInstructions;
	}
	
	public void retweetStatus(long id){
		try {
			
            Twitter twitter = new TwitterFactory().getInstance();
            twitter.retweetStatus(id);
        } catch (TwitterException te) {
            logger.error("Failed to retweet: " + te.getMessage(), te);
        }
	}
	
	public void retweetStatus(String user){
		try {
            Twitter twitter = new TwitterFactory().getInstance();
            Long id = twitter.showUser(user).getStatus().getId();
            twitter.retweetStatus(id);
        } catch (TwitterException te) {
            logger.error("Failed to retweet: " + te.getMessage(), te);
        }
	}
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.properties");
		String hashTrend = "#TableColumnproperties010242012";
		//new TweetUtils().postMessage("#TableColumnproperties010242012 was modified");
		List<String> replies = new TweetUtils().searchTweets(hashTrend);
		for(String str : replies){
			System.err.println(str);
		}
	}

}
