package com.javainsight.tweet.utils;

import java.net.URL;
import java.util.ArrayList;
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
import twitter4j.TwitterFactory;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

public class TwitterServiceFactory {
	
	private static SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	private static List<SpreadsheetEntry> spreadsheets = null;
	private static Map<URL, WorksheetFeed> workSheetFeedMap = new HashMap<URL, WorksheetFeed>();
	private static Map<ListQuery, ListFeed> listQueryMap = new HashMap<ListQuery, ListFeed>();
	
	private static Logger logger = Logger.getLogger(TwitterServiceFactory.class);
	
	public static final SpreadsheetService getService() throws Exception{
		if(service == null){			
			service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
		}
		//service.setUserToken(Constants.SPREADSHEET_AUTH_TOKEN);
		service.setUserCredentials(Constants.MASTER_EMAIL_ADD, Constants.PASSWORD);
		return service;
	}
	
	public static final List<SpreadsheetEntry> getSpreadSheets() throws Exception{
		if(spreadsheets == null){			
			URL metafeedUrl = new URL(Constants.SPREADSHEET_URL);
		    SpreadsheetFeed feed = getService().getFeed(metafeedUrl, SpreadsheetFeed.class);
		    spreadsheets = feed.getEntries();
		}
		return spreadsheets;
	}
	
	public static final WorksheetFeed getWorksheetFeed(URL worksheetFeedUrl) throws Exception{
		WorksheetFeed worksheetFeed = null;
		if(workSheetFeedMap.get(worksheetFeedUrl) == null){
			worksheetFeed = getService().getFeed(worksheetFeedUrl, WorksheetFeed.class);
			 workSheetFeedMap.put(worksheetFeedUrl, worksheetFeed);
		}else{
			worksheetFeed = workSheetFeedMap.get(worksheetFeedUrl);
		}
		return worksheetFeed;
	}
	
	public static final ListFeed getListFeed(ListQuery query) throws Exception{
		ListFeed listFeed = null;
		if(listQueryMap.get(query) == null){
			listFeed = getService().query(query, ListFeed.class);
			listQueryMap.put(query, listFeed);
		}else{
			listFeed = listQueryMap.get(query);
		}
	
		return listFeed;
	}
	
	public static final void reset(){		
		service = null;
		spreadsheets = null;
		listQueryMap.clear();
		workSheetFeedMap.clear();
	}
	
	static List<String> getTimeLine(){
		// gets Twitter instance with default credentials
        Twitter twitter = new TwitterFactory().getInstance();
        List<String> tweets = new ArrayList<String>();
        try {
        	List<Status> statuses;
        	String user;
            user = twitter.verifyCredentials().getScreenName();
            statuses = twitter.getUserTimeline();
            
            System.out.println("Showing @" + user + "'s user timeline.");
            for (Status status : statuses) {

                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            	tweets.add(status.getText());
                
            }
        } catch (TwitterException te) {
        	logger.error("Failed to retrieve tweets: " + te.getMessage(), te);
        }
        return tweets;
	}
	
	public List<String> searchTweets(String searchHashTag){
		Twitter twitter = new TwitterFactory().getInstance();
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

}
