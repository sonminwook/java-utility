package com.javainsights.iputils.filters.utils;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;


public class LengthEncoder {
	
	private static Logger log = Logger.getLogger(LengthEncoder.class);
	public String getLength(String response, IoSession session){
		int length = response.length();
		
		/*
		 * Convert into to Hex
		 */
		String hexStr = Integer.toString(length, 16);
		
		/*
		 * Check length of hexStr, it should be maximum of 4
		 */		
		
		if(hexStr.length() < 4){
			for(int i= hexStr.length(); i < 4; i++){
				hexStr = "0"+hexStr;
			}			
		}else if(hexStr.length() > 4){
			log.error("Length ["+ length+"] Hex ["+ hexStr+"] has crossed Maximum limit FFFF");
			//TODO Throw exception
		}
		
		/*
		 * Convert to ASCII
		 */	
		String ascii = this.hexToASCII(hexStr.toUpperCase());
		log.info("Response Length ["+ length+ "] Hex ["+ hexStr.toUpperCase()+ "] ASCII ["+ ascii + "]");
		return ascii;
	}
	
	
	private String hexToASCII(String hex){       
        if(hex.length()%2 != 0){
           log.error("requires EVEN number of chars");
           return null;
        }
        StringBuilder sb = new StringBuilder();               
        //Convert Hex 0232343536AB into two characters stream.
        for( int i=0; i < hex.length()-1; i+=2 ){
             /*
              * Grab the hex in pairs
              */
            String output = hex.substring(i, (i + 2));
            /*
             * Convert Hex to Decimal
             */
            int decimal = Integer.parseInt(output, 16);                 
            sb.append((char)decimal);             
        }           
        return sb.toString();
  } 
	
	public String asciiToHex(String ascii){
        StringBuilder hex = new StringBuilder();
        
        for (int i=0; i < ascii.length(); i++) {
            hex.append(Integer.toHexString(ascii.charAt(i)));
        }       
        return hex.toString();
    }
	
}
