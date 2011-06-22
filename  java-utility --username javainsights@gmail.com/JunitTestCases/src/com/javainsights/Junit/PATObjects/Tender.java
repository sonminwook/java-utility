package com.javainsights.Junit.PATObjects;

import java.math.BigDecimal;
import java.security.SecureRandom;

import com.javainsights.Junit.Enums.DatenTime;
import com.javainsights.Junit.Enums.POSEntryMode;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.PrepareLoginMessage;

public class Tender {
	
	private static ConfigDTO config = PrepareLoginMessage.getConfig();
	
	public static String sale_1_Tender_no_dcc_no_tip(String amount){
		String tender = "{" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"Y#" +
				"CASH#" +
				"01#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"444433XXXXXX1111#" +
				"XXXX#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"GBP#" +
				amount+"#" +
				"0#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"VD#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"#" +
				"RNDMTEXT#" +
				POSEntryMode.parse((int)Math.random()*7)+ "#" +
				"N#" +
				"#" +
				"#" +
				"#" +
				"#" +
				"#" +
				"3#" +
				"#" +
				"#" +
				"#" +
				"#" +
				DatenTime.getDate()+"#" +
				DatenTime.getTime()
				+"}";
		return tender;
	}
	
	public static String sale_1_Tender_no_dcc_with_tip(String amount){
		double randomExchangeRate = getExchangeRate();
		String tender = "{" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"Y#" +
				"CREDIT#" +
				"01#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"444433XXXXXX1111#" +
				"XXXX#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"GBP#" +
				amount+"#" +
				Math.abs(new SecureRandom().nextInt(9999)) + "#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"VD#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"#" +
				"RNDMTEXT#" +
				POSEntryMode.parse((int)Math.random()*7)+ "#" +
				"Y#" +
				amount + "#" +
				"GBP#" +
				getDCCAmount(amount, randomExchangeRate)+"#" +
				"USD#" +
				Integer.toString((int)(randomExchangeRate*1000))+"#" +
				"2#" +
				"#" +
				"#" +
				"#" +
				"I HAVE CHOSEN NOT TO USE THE MASTERCARD CURRENCY CONVERSION PROCESS AND AGREE THAT I WILL HAVE NO RECOURSE AGAINST MASTERCARD CONCERNING THE CURRENCY CONVERSION OR ITS DISCLOSURE.REFERENCE RATE BASED ON THE GLOBAL BLUE REFERENCE RATE OF TODAY. . Commission 0.00%.#" +
				DatenTime.getDate()+"#" +
				DatenTime.getTime()
				+"}";
		return tender;
	}
	
	public static String sale_1_Tender_dcc_no_tip(String amount){
		double randomExchangeRate = getExchangeRate();
		String tender = "{" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"Y#" +
				"CREDIT#" +
				"01#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"444433XXXXXX1111#" +
				"XXXX#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"GBP#" +
				amount+"#" +
				"0#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"JC#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"#" +
				"RNDMTEXT#" +
				POSEntryMode.parse((int)Math.random()*7)+ "#" +
				"Y#" +
				amount + "#" +
				"GBP#" +
				getDCCAmount(amount, randomExchangeRate)+"#" +
				"USD#" +
				Integer.toString((int)(randomExchangeRate*1000))+"#" +
				"1#" +
				"#" +
				"#" +
				"#" +
				"I HAVE CHOSEN NOT TO USE THE MASTERCARD CURRENCY CONVERSION PROCESS AND AGREE THAT I WILL HAVE NO RECOURSE AGAINST MASTERCARD CONCERNING THE CURRENCY CONVERSION OR ITS DISCLOSURE.REFERENCE RATE BASED ON THE GLOBAL BLUE REFERENCE RATE OF TODAY. . Commission 0.00%.#" +
				DatenTime.getDate()+"#" +
				DatenTime.getTime()
				+"}";
		return tender;
	}
	
	public static String sale_1_Tender_dcc_with_tip(String amount){
		double randomExchangeRate = getExchangeRate();
		int tipAmount = Math.abs(new SecureRandom().nextInt(9999));		
		String tender = "{" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"Y#" +
				"CREDIT#" +
				"01#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"444433XXXXXX1111#" +
				"XXXX#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"GBP#" +
				amount+"#" +
				tipAmount +"#" +
				Math.abs(new SecureRandom().nextInt(999999))+"#" +
				"VC#" +
				Math.abs(new SecureRandom().nextInt(999999999))+"#" +
				"#" +
				"RNDMTEXT#" +
				POSEntryMode.parse((int)Math.random()*7)+ "#" +
				"Y#" +
				(Integer.parseInt(amount) + tipAmount) + "#" +
				"GBP#" +
				getDCCAmount(Integer.toString(Integer.parseInt(amount) + tipAmount), randomExchangeRate)+"#" +
				"USD#" +
				Integer.toString((int)(randomExchangeRate*1000))+"#" +
				"1#" +
				"#" +
				"#" +
				"#" +
				"I HAVE CHOSEN NOT TO USE THE MASTERCARD CURRENCY CONVERSION PROCESS AND AGREE THAT I WILL HAVE NO RECOURSE AGAINST MASTERCARD CONCERNING THE CURRENCY CONVERSION OR ITS DISCLOSURE.REFERENCE RATE BASED ON THE GLOBAL BLUE REFERENCE RATE OF TODAY. . Commission 0.00%.#" +
				DatenTime.getDate()+"#" +
				DatenTime.getTime()
				+"}";
		return tender;
	}

	private static double getExchangeRate(){
		int rate = Math.abs(new SecureRandom().nextInt(2000));
		double exRate = rate < 1000 ? ((rate+1000)/1000.00): (rate/1000.00);
		return exRate;
	}
	
	private static String getDCCAmount(String amt, double rate){
		Double forAmt = Double.parseDouble(amt) * rate;		
		BigDecimal realAmt = new BigDecimal(forAmt);		
		return realAmt.intValue()+"";
	}
}
