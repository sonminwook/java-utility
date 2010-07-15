package com.GR.interfacesImplementation;

import com.GR.interfaces.Convertor;

public class BooleanConvertor implements Convertor<Boolean> {

	/**
	 * This method will help to decode a String value to
	 * boolean value. 
	 * this method will return true if the passed value is
	 * not null and equal ignore case = 'true'.
	 */
	@Override
	public Boolean decode(String value) {
		Boolean bool = null;
		bool = Boolean.valueOf(value);
		return bool;
	}

	/**
	 * This method will convert the incoming String
	 * into boolean string.
	 * if incoming string is
	 * true = returned value is true
	 * TRUE = returned value is true.
	 * weird = returned value is false.
	 * false = returned value is false.
	 */
	@Override
	public String encode(String value) {
		Boolean bool = Boolean.valueOf(value);
		return bool.toString();		
	}

}
