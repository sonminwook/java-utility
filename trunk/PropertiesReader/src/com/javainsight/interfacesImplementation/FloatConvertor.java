package com.javainsight.interfacesImplementation;

import com.javainsight.interfaces.Convertor;

/**
 * This class will be the float encoding-decoding 
 * version of convertor<T> interface.
 * @author thegoodcode engineer
 *
 */
public class FloatConvertor implements Convertor<Float> {

	/**
	 * This method will convert the incoming String to float.
	 * it string can not be converted the Number format exception will be
	 * thrown.
	 */
	@Override
	public Float decode(String value) throws NumberFormatException {
		Float decimal = null;
		try {
			decimal = Float.valueOf(value);
		} catch (NumberFormatException e) {
			NumberFormatException nfe = new NumberFormatException("NFE while converting ["+value+"] into Float");
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"decode", this.getClass().getPackage().toString(), -1);
			nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
       	 	throw nfe;			
		}
		return decimal;
	}

	/**
	 * This method will check the incoming string,
	 * if the value can be converted into float then
	 * the incoming value will be passed as it as return argument.
	 * otherwise Number format Exception will be thrown.
	 */
	@Override
	public String encode(String value) {
	  try {
			Float.valueOf(value);
		} catch (NumberFormatException e) {
			NumberFormatException nfe = new NumberFormatException("NFE while converting ["+value+"] into Float");
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"encode", this.getClass().getPackage().toString(), -1);
			nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
       	 	throw nfe;	
		}
		return value;
	}

}
