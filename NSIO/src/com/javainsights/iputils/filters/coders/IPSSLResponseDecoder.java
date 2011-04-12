package com.javainsights.iputils.filters.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.javainsights.iputils.contants.ErrorCodeConstant;

public class IPSSLResponseDecoder extends CumulativeProtocolDecoder {

	private static Logger log = Logger.getLogger(IPSSLResponseDecoder.class);
	private int length = 0;
	private String response = "";
	private static final String DECODED_LENGTH_KEY = "DECODED_LENGTH_KEY";
	private static final String DECODED_REQUEST_KEY = "DECODED_REQUEST_KEY";
	private static final String REQUEST = "ONEINTERFACE_PAY_@TABLE_REQUEST";
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {		
		if(in.remaining() > 0){
			/*
			 * Lets get the HEX DUMP FIRST
			 */
			String hexDump = in.getHexDump();
			hexDump = hexDump.replaceAll(" ", "");
			byte[] data = new byte[in.remaining()];
			/*
			 * Get the request from previous length
			 */
			if(session.getAttribute(DECODED_REQUEST_KEY) != null){
				response = session.getAttribute(DECODED_REQUEST_KEY).toString();
			}
			for( int i=0; i < data.length ; i ++){
				data[i] = in.get();
			}			
			response += new String(data);			
			session.setAttribute(DECODED_REQUEST_KEY, response);		
			/*
			 * First 3 bytes include length FF-LENGTH
			 */
			if(response.length() > 3){
				if(session.getAttribute(DECODED_LENGTH_KEY) == null){					
					String asciiValue = response.substring(1, 3);
					//String hexValue = this.asciiToHex(asciiValue);
					String hexValue = hexDump.substring(2,6);
					length = Integer.parseInt(hexValue, 16);
					log.debug("["+asciiValue + "] --> "+ hexValue + " --> "+ length);
					session.setAttribute(DECODED_LENGTH_KEY	, length);										
				}else{
					length = Integer.parseInt(session.getAttribute(DECODED_LENGTH_KEY).toString());
				}
			}else{				return false;
			}
			/*
			 * we have got the LENGTH, check if data is complete
			 */
			if(length > 0){				
				if(response.substring(3).length() >=  length){
					log.info(" MSG RCVD SUCCESSFULLY");
				try{
						String resp = response.substring(3,length+3);
						response = "";
						
						/*
						 * When everything is SUCCESSFUL
						 */
						session.setAttribute(REQUEST, resp);
						/*
						 * REQUEST VALIDATED SUCCESSFULLY
						 */
						out.write(resp);
					}catch(Exception e){
						response = "";
						throw new Exception(ErrorCodeConstant.UNKNOWN_ERROR_FROM_TERMINAL + "WHILE GETTING RESPONSE FROM HOST",e);						
					}					
					/*
					 * remove the session values for next session.
					 */
					session.removeAttribute(DECODED_LENGTH_KEY);
					session.removeAttribute(DECODED_REQUEST_KEY);								
					/*
					 * return true;
					 */
					return true;
				}else{					
					log.info("COMPLETE MSG NOT RCVD, WAITING FOR MORE BYTES - Expected ["+ length+"] Rcvd ["+ response.substring(2).length()+"]");
					return false;
				}
			}else{
				return false;
			}			
		}		
		return false;
	}
	

}
