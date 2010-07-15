package com.GR.beans;

public class Key {
	
	private String key;
	private String prefix;
	private String defaultValue;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Key(String key, String prefix, String defaultValue) {
		super();
		if(prefix != null && prefix.trim().length()!= 0){
			this.key = prefix + key;
		}else{
			this.key = key;
		}
		this.prefix = prefix;
		this.defaultValue = defaultValue;
	}

}
