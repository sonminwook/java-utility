package com.javainsights.Junit.Enums;

public enum POSEntryMode {
	SWIPE("01"),
	MANUAL("02"),
	CNP("03"),
	FALLBACK("04"),
	EMV("05"),
	CONTACTLESS("06");
	
	private String value;
	
	private POSEntryMode(String value){
		this.value = value;
	}
	
	public static String parse(int randomValue){
		switch(randomValue){
		case 1:
			return SWIPE.value;
		case 2:
			return MANUAL.value;
		case 3:
			return CNP.value;
		case 4:
			return FALLBACK.value;
		case 5:
			return EMV.value;
		case 6:
			return CONTACTLESS.value;
		default:
			return SWIPE.value;
		}
	}
}
