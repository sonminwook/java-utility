package com.javainsight.interfacesImplementation;

import com.javainsight.interfaces.Convertor;

/**
 * This class is normal String convertor. Just to fit
 * into architecture.
 * It will return whatever it will accept. 
 */
public class StringConvertor implements Convertor<String>{
	
	public String decode(String value){
		return value;
	}

	public String encode(String value){
		return String.valueOf(value);
	}
}
