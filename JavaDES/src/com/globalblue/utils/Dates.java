package com.globalblue.utils;

import java.util.GregorianCalendar;

public class Dates {

	public static Integer getValue(String charactr){
		if(charactr.equalsIgnoreCase("0")){
			return 0;
		}else if(charactr.equalsIgnoreCase("1")){
			return 1;
		}else if(charactr.equalsIgnoreCase("2")){
			return 2;
		}else if(charactr.equalsIgnoreCase("3")){
			return 3;
		}else if(charactr.equalsIgnoreCase("4")){
			return 4;
		}else if(charactr.equalsIgnoreCase("5")){
			return 5;
		}else if(charactr.equalsIgnoreCase("6")){
			return 6;
		}else if(charactr.equalsIgnoreCase("7")){
			return 7;
		}else if(charactr.equalsIgnoreCase("8")){
			return 8;
		}else if(charactr.equalsIgnoreCase("9")){
			return 9;
		}else if(charactr.equalsIgnoreCase("A")){
			return 10;
		}else if(charactr.equalsIgnoreCase("B")){
			return 11;
		}else if(charactr.equalsIgnoreCase("C")){
			return 12;
		}else if(charactr.equalsIgnoreCase("D")){
			return 13;
		}else if(charactr.equalsIgnoreCase("E")){
			return 14;
		}else if(charactr.equalsIgnoreCase("F")){
			return 15;
		}else if(charactr.equalsIgnoreCase("G")){
			return 16;
		}else if(charactr.equalsIgnoreCase("H")){
			return 17;
		}else if(charactr.equalsIgnoreCase("I")){
			return 18;
		}else if(charactr.equalsIgnoreCase("J")){
			return 19;
		}else if(charactr.equalsIgnoreCase("K")){
			return 20;
		}else if(charactr.equalsIgnoreCase("L")){
			return 21;
		}else if(charactr.equalsIgnoreCase("M")){
			return 22;
		}else if(charactr.equalsIgnoreCase("N")){
			return 23;
		}else if(charactr.equalsIgnoreCase("O")){
			return 24;
		}else if(charactr.equalsIgnoreCase("P")){
			return 25;
		}else if(charactr.equalsIgnoreCase("Q")){
			return 26;
		}else if(charactr.equalsIgnoreCase("R")){
			return 27;
		}else if(charactr.equalsIgnoreCase("S")){
			return 28;
		}else if(charactr.equalsIgnoreCase("T")){
			return 29;
		}else if(charactr.equalsIgnoreCase("U")){
			return 30;
		}else if(charactr.equalsIgnoreCase("V")){
			return 31;
		}else if(charactr.equalsIgnoreCase("W")){
			return 32;
		}else if(charactr.equalsIgnoreCase("X")){
			return 33;
		}else if(charactr.equalsIgnoreCase("Y")){
			return 34;
		}else {
			return 35;
		}		
	}
	
	public static Integer getLastDayofMonth(int month, int year){
		switch(month){
		case 0:
			return 31;
		case 1:{
			if(isLeapYear(year)){
				return 29;
			}else{
				return 28;
			}
		}
		case 2:
			return 31;
		case 3:
			return 30;
		case 4:
			return 31;
		case 5:
			return 30;
		case 6:
			return 31;
		case 7:
			return 31;
		case 8:
			return 30;
		case 9:
			return 31;
		case 10:
			return 30;
		default:
			return 31;
		}
	}
	
	private static boolean isLeapYear(int year){	    	      
	    GregorianCalendar cal = new GregorianCalendar();
	      if(cal.isLeapYear(year))
	        return true;
	      else
	        return false;
	    }    
	
}
