package com.globalblue.util;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * This class will return the date in different formats
 * @author Sunny Jain
 * @Date 07-Jun-2010 
 */
public class CurrentDate {
	
	/**
	 * This class will return the currnet date in passed format.<br>
	 * @param format : it will be the format for current date<br> 
	 * for example -<b> "yyMMdd", "yyyy", "MMMM","MMM", "EEEE",
	 * "MM/dd/yy","yyyy.MM.dd.HH.mm.ss","E, dd MMM yyyy HH:mm:ss Z" </b>
	 * @return - formatted date in String format.
	 */
	public String getCurrentDate(String format){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);			
	}

	/**
	 * This method will return the Current date in 
	 * yyMMdd format.
	 * @return - Current date in yyMMdd string format.
	 */
	public String getDateInyyMMdd(){
		return getCurrentDate("yyMMdd");
	}
	
}
