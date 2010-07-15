package com.GR.interfacesImplementation;

import com.GR.interfaces.Convertor;

public class CharacterConvertor implements Convertor<Character> {

	/**
	 * This method will convert incoming String into 
	 * character. The incoming string must have 
	 * a length of 1 character otherwise illegal argument
	 * exceptionwill be thrown.
	 */
	@Override
	public Character decode(String value) throws IllegalArgumentException{
		Character character = null;
		if(value.length() == 1){
		character = Character.valueOf(value.charAt(0));
		}else{
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"decode","com.sunz.interfacesImplementation" , -1);
			IllegalArgumentException e = new IllegalArgumentException("String \""+value+"\" is not a character");
			e.setStackTrace(new StackTraceElement[]{stackTrace});
			throw e;
		}
		return character;
	}

	/**
	 * this method will check the passed string.
	 * If it have a length of one character thent the passed string will be passed
	 * as it is.
	 */
	@Override
	public String encode(String value) throws IllegalArgumentException {
		if(value.length() == 1){
			return value;
		}else{
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"encode","com.sunz.interfacesImplementation" , -1);
			IllegalArgumentException e = new IllegalArgumentException("String \""+value+"\" is not a character");
			e.setStackTrace(new StackTraceElement[]{stackTrace});
			throw e;
		}
		
	}

}
