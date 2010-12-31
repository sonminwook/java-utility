package com.jovialjava.utils.params;

public class Common {
	
	public static String convertHexToString(String hex){
		 
		  StringBuilder sb = new StringBuilder();
		  StringBuilder temp = new StringBuilder();		  
		  for( int i=0; i<hex.length()-1; i+=2 ){
	 	     String output = hex.substring(i, (i + 2));		      
		      int decimal = Integer.parseInt(output, 16);		      
		      sb.append((char)decimal);			      
		      temp.append(decimal);
		  }	 
		  return sb.toString();
	  }

}
