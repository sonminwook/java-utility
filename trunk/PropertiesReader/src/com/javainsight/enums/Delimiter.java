package com.javainsight.enums;

public enum Delimiter {
	COMMA(","),
	SEMICOLON(";"),
	COLON(":"),
	HASH("#"),
	HYPEN("-"),
	UNDERSCORE("_"),
	ASTERISK("*"),
	DOLLAR("$"),
	RATEOF("@"),
	PERCENTAGE("%");
	
	private String value;
	Delimiter(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
