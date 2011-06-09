package com.javainsight.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.javainsight.constants.Constants;

public class Expressions {
	
	public long getCutOffTime(int numberOfDays, double numberOfHours) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		String truncDate = format.format(new Date());
		Date currentDate = format.parse(truncDate);

		if(numberOfDays > 0){
			return 0;
		}else if(numberOfDays == 0){
			return currentDate.getTime() + (int)numberOfHours*60*1000;
		}else{			
			return currentDate.getTime() + 
					(numberOfDays*Constants.MILLISECOND_IN_A_DAY)
					+ (int)numberOfHours*60*1000;
		}		
	}
}
