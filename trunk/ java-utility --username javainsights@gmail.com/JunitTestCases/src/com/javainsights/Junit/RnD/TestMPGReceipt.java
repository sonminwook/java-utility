package com.javainsights.Junit.RnD;

import java.security.SecureRandom;

import com.globalblue.oneInterface.mpg.utils.ReceiptUtils;

import junit.framework.TestCase;

public class TestMPGReceipt extends TestCase {
	
	private static String testData =
		"       Micros Demo System       #" +
		"      MICROS System, Inc.       #" +
		"  7031 Columbia Gateway Drive   #" +
		"       Columbia, MD 21046       #" +
		"          443-285-6000          #" +
		"         www.micros.com  #" +
		" 103 Rachel#" +
		"--------------------------------#" +
		"Tbl 51/1    Chk "+new SecureRandom().nextInt()+"      Gst  1#" +
		"        Jan27'11 11:41AM        #" +
		"--------------------------------#" +
		"Delayed Order   Jan27'11 12:09PM#" +
		"  1 Special #" +
		"2          40.00#" +
		"   1000416301027000046#" + 
		"    VISA          40.00#" +
		" #" +
		"    Food                40.00#" +
		"    Payment             "+Math.abs(new SecureRandom().nextInt())+"#" +
		"----103 Check Closed 12:03PM----#";

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testAdjustLength() {
		String result = new ReceiptUtils().adjustLength(this.testData, "#", 40);
		
		String[] lines = result.split("#");
		
		for(String line : lines){
			System.out.println(line);
			assertEquals(40, line.length());
		}
		//fail("Not yet implemented"); // TODO
	}

}
