package com.javainsight.interfacesImplementation;

import com.javainsight.interfaces.Convertor;

/**
 * This class will help to convert String into Integer.
 * If Integer can not be converted into Integer than Number format
 * exception will be thrown.
 * @author thegoodcode engineer
 *
 */
public class IntegerConvertor implements Convertor<Integer> {

	@Override
	public Integer decode(String value) throws NumberFormatException {
		// TODO Auto-generated method stub
		Integer integer = null;
		try {
			 integer = Integer.valueOf(value);
		} catch (NumberFormatException e) {
			NumberFormatException nfe = new NumberFormatException("NFE while converting "+value+" to Integer");
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"decode", this.getClass().getPackage().toString(), -1);
			nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
       	 	throw nfe;			
		}
		return integer;
	}

	/**
	 * This method will check if the value, we are encoding before storing
	 * into configuration, can be converted into integer.
	 * If it can be converted, the incoming String value will be passed as it is
	 * as retun value.
	 */
	@Override
	public String encode(String value) throws NumberFormatException{
		// TODO Auto-generated method stub
		try{
			Integer.valueOf(value);
		}catch(NumberFormatException e){
			NumberFormatException nfe = new NumberFormatException("NFE while converting "+value+" to Integer");
			StackTraceElement stackTrace = new StackTraceElement(this.getClass().getName(),"encode", this.getClass().getPackage().toString(), -1);
			nfe.setStackTrace(new StackTraceElement[]{stackTrace});			
       	 	throw nfe;		
		}
		return value;
	}

}
