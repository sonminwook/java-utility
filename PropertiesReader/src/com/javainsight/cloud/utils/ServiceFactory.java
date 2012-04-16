package com.javainsight.cloud.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

public class ServiceFactory {
	
	private static SpreadsheetService service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
	private static List<SpreadsheetEntry> spreadsheets = null;
	private static Map<URL, WorksheetFeed> workSheetFeedMap = new HashMap<URL, WorksheetFeed>();
	private static Map<ListQuery, ListFeed> listQueryMap = new HashMap<ListQuery, ListFeed>();
	
	public static final SpreadsheetService getService() throws Exception{
		if(service == null){			
			service = new SpreadsheetService(Constants.CLOUD_SERVICE_NAME);
		}
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
	
	public static synchronized final void reset(){		
		service = null;
		spreadsheets = null;
		listQueryMap.clear();
		workSheetFeedMap.clear();
	}

}
