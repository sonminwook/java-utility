package com.javainsight.enums;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import com.javainsight.interfaces.Convertor;
import com.javainsight.interfacesImplementation.StringConvertor;
import com.javainsight.util.ReadProperties;
import com.javainsight.util.ReadXMLProperties;
import com.javainsight.util.Validate;

public enum Configuration {
	
	INSTANCE;
	private Properties cfg = new Properties();
	private String defaultValue;
	
	/*
	 * This is default constructor. Only private is allowed for enums.
	 */ 
	private Configuration(){}
	
	/**
	 * This method will load the properties file on the cfg.<br>
	 * It expects location to be a File<br>
	 * File should have read access<br>
	 * File should exists<br>
	 * All the properties will be loaded into cfg.
	 */
	public void loadPropertiesFile(final String location) throws IOException{
		if(location.substring(location.lastIndexOf(".")).equalsIgnoreCase(".XML")){
			cfg.clear();
			ReadXMLProperties.readProperties(location, cfg);
		}else{
			cfg.clear();
			ReadProperties.readProperties(location, cfg);
		}
	}
	
	/**
	 * This method will actually get the String value of a key from properties file.
	 * If someone wants to change the layout of string value to someother value,
	 * one can define the convetor class implementing Convertor interface.
	 * Generic has been used to make the things flexible.
	 * @param <T> Return of the class implementing Convertor<T> interface.
	 * @param key Value of the key
	 * @param convertor  class implementing Convertor interface.
	 * @return
	 * @throws IllegalArgumentException in case if any value is Null.
	 */
	public <T> T getValue(String key, Convertor<T> convertor) throws IllegalArgumentException{
		Validate validate = new Validate();
		validate.validateNull(true,"Key/Convertor is null",key, convertor);
		String value = cfg.getProperty(key);
		if(validate.validateNull(false,"Value corresponding to "+key+" is null",value)){
		value = getDefaultValue(key);
		}
		validate.validateNull(true,"Key ["+key+"] is invalid, It doesn't have any corresponding value", value);
		T t = convertor.decode(value);
		validate.validateNull(true, "Convertor output is Null,", t);
		return t; 
	}

	/**
	 * If someone is looking for normal String value of a key.
	 */
	public String getValue(String key){
		return getValue(key, new StringConvertor());
	}

	/**
	 * This method will help to put the value in the configuration properties file.
	 * We can change the String value to anyformat befor putting into properties file.
	 * @param <T> return type of convertor
	 * @param key key against value will be put.
	 * @param value value to be put in the properties file.
	 * @param convertor convertor used to encode the value.
	 * @return
	 * @throws IllegalArgumentException
	 */
	public <T> T setValue(String key, String value, Convertor<T> convertor) throws IllegalArgumentException{
		Validate validate = new Validate();
		validate.validateNull(true,"Key/Convertor is null",key, convertor);
		value = convertor.encode(value);
		validate.validateNull(true, "Convertor output is Null", value);
		cfg.setProperty(key, value);
		return convertor.decode(value);
	}
	
	/*
	 * This will help us to put String value in the configuration file.
	 */
	public void setValue(String key, String value){		
		setValue(key, value, new StringConvertor());
	}

	/*
	 * This method will return the default value of key.
	 */
	private String getDefaultValue(String key){
		return defaultValue;
	}
	
	public void setDefaultValue(String defaultValue){
		this.defaultValue = defaultValue;
	}
	
	public int getTotalNoOfValues(){		
		return cfg.size();
	}
	
	/*
	 * Added a new method to fetch all the keys in a properties file.
	 */
	public Set<Object> getKeys(){
		return cfg.keySet();
	}
	
}
