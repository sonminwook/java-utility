package com.javainsights.Junit.PATObjects;

public class Receipt {
	
	
	public static String getReceipt(String checkNum,String PAN, String amount){
		String cardType = "Unknown Card Type";
		if(PAN.startsWith("4")){
			cardType = "Visa";
		}else if(PAN.startsWith("5")){
			cardType = "MasterCard";			
		}else if(PAN.startsWith("36")){
			cardType = "Diners";			
		}else if(PAN.startsWith("37")){
			cardType = "American Express";			
		}
		String receipt = 
			"       Micros Demo System       #" +
			"      MICROS System, Inc.       #" +
			"  7031 Columbia Gateway Drive   #" +
			"       Columbia, MD 21046       #" +
			"          443-285-6000          #" +
			"         www.micros.com  #" +
			" 103 Rachel#" +
			"--------------------------------#" +
			"Tbl 51/1    Chk "+checkNum+"      Gst  1#" +
			"        Jan27'11 11:41AM        #" +
			"--------------------------------#" +
			"Delayed Order   Jan27'11 12:09PM#" +
			"  1 Special #" +
			"2          40.00#" +
			"   1000416301027000046#" + 
			"    "+cardType+"          40.00#" +
			" #" +
			"    Food                40.00#" +
			"    Payment             "+amount+"#" +
			"----103 Check Closed 12:03PM----#";
		return receipt;

	}
}
