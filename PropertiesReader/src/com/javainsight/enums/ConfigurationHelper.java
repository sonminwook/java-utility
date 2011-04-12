package com.javainsight.enums;

import java.io.IOException;
import java.util.Set;

import com.javainsight.beans.Key;
import com.javainsight.interfacesImplementation.BooleanConvertor;
import com.javainsight.interfacesImplementation.CharacterConvertor;
import com.javainsight.interfacesImplementation.FloatConvertor;
import com.javainsight.interfacesImplementation.IntegerConvertor;
import com.javainsight.interfacesImplementation.StringConvertor;

/**
 * This class is responsible to get the correct formated values from properties file.<br>
 * The design is like this -
 * Helper Class <--- Convertor ---> Configuration Enum <------> properties file.<br>
 * Configuration enum will read the properties file and will store the values in a Map.<br>
 * Helper class will choose correct convertor and format the value read by enum.<br>
 * Helper class will return the correct formatted value.<br>
 * @author sjain
 *@version 1.2
 */
public class ConfigurationHelper {
	
	/**
	 * This method will use Integer convertor and convert the property value to an integer
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * @return - converted Int value of the key
	 */
	public int getIntValue(String key, String prefix, String defaultValue) 
														throws IllegalArgumentException,
														NumberFormatException{
		Key intKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(intKey.getDefaultValue());
		return Configuration.INSTANCE.getValue(intKey.getKey(), new IntegerConvertor());		
	}

	/**
	 * This method will use boolean convertor and convert the property value to an boolean
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * @return - converted boolean value of the key
	 */
	public boolean getBooleanValue(String key, String prefix, String defaultValue)
														throws IllegalArgumentException{
		Key booleanKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(booleanKey.getDefaultValue());
		return Configuration.INSTANCE.getValue(booleanKey.getKey(), new BooleanConvertor());
	}
	
	/**
	 * This method will use float convertor and convert the property value to an float
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * @return - converted float value of the key
	 */
	public float getFloatValue(String key, String prefix, String defaultValue)
														throws IllegalArgumentException,
															   NumberFormatException{
		Key floatKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(floatKey.getDefaultValue());
		return Configuration.INSTANCE.getValue(floatKey.getKey(), new FloatConvertor());
	}

	/**
	 * This method will use character convertor and convert the property value to an character
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * @return - converted char value of the key
	 */
	public char getCharacterValue(String key, String prefix, String defaultValue)
														throws IllegalArgumentException{
		Key charKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(charKey.getDefaultValue());
		return Configuration.INSTANCE.getValue(charKey.getKey(), new CharacterConvertor());
	}
	
	/**
	 * This method will use String convertor and convert the property value to an String
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * @return - converted String value of the key
	 */
	public String getStringValue(String key, String prefix, String defaultValue)
														throws IllegalArgumentException{
		Key stringKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(stringKey.getDefaultValue());
		return Configuration.INSTANCE.getValue(stringKey.getKey(), new StringConvertor());
	}
	
	/**
	 * This method will use String convertor and convert the property value to an String[]
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * Don't use null
	 * @param Delimiter - Type of Delimiter which will split the string in string array possibe values are ,,;,:,@,#,$,-,_,* etc
	 * @return - converted String[] value of the key
	 */
	public String[] getStringArrayValue(String key, String prefix, String defaultValue, Delimiter delimiter){
		Key stringKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(stringKey.getDefaultValue());
		String value = Configuration.INSTANCE.getValue(stringKey.getKey(), new StringConvertor());
		return value.split(delimiter.getValue());
	}
	
	/**
	 * This method will use String convertor and convert the property value to an int[]
	 * before returning.
	 * @param key - Key whose value need to be read.
	 * @param prefix - Prefix of the key
	 * @param defaultValue -- default value in case if there is no such key in properties file.
	 * Don't use null
	 * @param Delimiter - Type of Delimiter which will split the string in string array possibe values are ,,;,:,@,#,$,-,_,* etc
	 * @return - converted int[] value of the key
	 */
	public int[] getIntArrayValue(String key, String prefix, String defaultValue, Delimiter delimiter){
		Key stringKey = new Key(key, prefix, defaultValue);
		Configuration.INSTANCE.setDefaultValue(stringKey.getDefaultValue());
		String value = Configuration.INSTANCE.getValue(stringKey.getKey(), new StringConvertor());
		String[] strValueArray = value.split(delimiter.getValue());
		int[] intValueArray = new int[strValueArray.length];
		try {
			for(int i=0; i< strValueArray.length; i++){
				intValueArray[i] = Integer.parseInt(strValueArray[i]);
			}
		} catch (NumberFormatException e) {
			NumberFormatException nfe = new NumberFormatException("NFE while converting "+value+" to Integer");
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"getIntArrayValue", this.getClass().getPackage().toString(), -1);
			nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
       	 	throw nfe;			
		}
		return intValueArray;
	}

	/**
	 * This method will help to upload the properties file.
	 * @param location - location of properties file.
	 * @throws IOException - In case of any problem while reading the properties file.
	 */
	public void loadPropertiesFile(final String location) throws IOException{
		Configuration.INSTANCE.loadPropertiesFile(location);		
	}

	/**
	 * This method will help to get the size of properties file.
	 */
	public int getSize(){
		return Configuration.INSTANCE.getTotalNoOfValues();
	}
	
	/**
	 * This method will return all the keys present in a 
	 * property file.
	 */
	public Set<Object> getKeys(){
		return Configuration.INSTANCE.getKeys();
	}
}
