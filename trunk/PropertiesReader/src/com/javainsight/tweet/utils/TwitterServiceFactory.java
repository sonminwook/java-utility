package com.javainsight.tweet.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterServiceFactory {
	
	private static Logger logger = Logger.getLogger(TwitterServiceFactory.class);
	public static Twitter twitter = null;
	

	public static void setTwitter(Twitter twit){
		twitter = twit;
	}
	
	public static final void reset(){		
		
	}
	
	static Map<Long, String> getTimeLine(){
		// gets Twitter instance with default credentials
		
        List<String> tweets = new ArrayList<String>();
        Map<Long, String> tweetMap = new HashMap<Long, String>();
        try {
        	List<Status> statuses;
        	String user;
            user = twitter.verifyCredentials().getScreenName();
            statuses = twitter.getUserTimeline();
            
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            	tweets.add(status.getText());
            	tweetMap.put(status.getId(), status.getText());
                
            }
        } catch (TwitterException te) {
        	logger.error("Failed to retrieve tweets: " + te.getMessage(), te);
        }
        return tweetMap;
	}
	
	public List<String> searchTweets(String searchHashTag){
	//	Twitter twitter = new TwitterFactory().getInstance();
		List<String> tweetsInstructions = new ArrayList<String>();
        try {
            
           
            Query query = new Query(searchHashTag);
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
			
         //   Twitter twitter = new TwitterFactory().getInstance();
            twitter.retweetStatus(id);
        } catch (TwitterException te) {
            logger.error("Failed to retweet: " + te.getMessage(), te);
        }
	}
	
	public static boolean deleteStatus(long id, String status){
		try {
			
         //   Twitter twitter = new TwitterFactory().getInstance();
			twitter.updateStatus(status + " Accepted on "+ new SimpleDateFormat("dd-MMM-yy HH:mm:ss").format(new Date()));
            twitter.destroyStatus(id); 
            return true;
        } catch (TwitterException te) {
        	if(te.getStatusCode() != 403){
        		logger.error("Failed to retweet: " + te.getMessage());
        	}
        }catch(Exception e){
        	logger.error("Failed to retweet: " + e.getMessage());
        }
        return false;
	}
	
	public void retweetStatus(String user){
		try {
        //    Twitter twitter = new TwitterFactory().getInstance();
            Long id = twitter.showUser(user).getStatus().getId();
            twitter.retweetStatus(id);
        } catch (TwitterException te) {
            logger.error("Failed to retweet: " + te.getMessage(), te);
        }
	}

}
