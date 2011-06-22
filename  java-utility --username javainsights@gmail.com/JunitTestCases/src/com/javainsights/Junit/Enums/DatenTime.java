package com.javainsights.Junit.Enums;

import java.text.SimpleDateFormat;
import java.util.Date;

public enum DatenTime {

	DATE,
	TIME;
	
	public static String getDate(){
		SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
		return format.format(new Date());
	}
	
	public static String getTime(){
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(new Date());
	}
}
