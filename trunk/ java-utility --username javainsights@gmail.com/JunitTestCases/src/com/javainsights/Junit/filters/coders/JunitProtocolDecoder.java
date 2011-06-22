package com.javainsights.Junit.filters.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.javainsights.Junit.constants.SessionConstant;

public class JunitProtocolDecoder extends CumulativeProtocolDecoder {

	private static Logger log = Logger.getLogger(JunitProtocolDecoder.class);
	private int length = 0;
	private String response = "";	
	
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		log.info("Decoder: Incoming (" + in.remaining() + " Bytes) [" + in.getHexDump()+"]");
		
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
			if(session.getAttribute(SessionConstant.DECODED_REQUEST_KEY) != null){
				response = session.getAttribute(SessionConstant.DECODED_REQUEST_KEY).toString();
			}
			for( int i=0; i < data.length ; i ++){
				data[i] = in.get();
			}			
			response += new String(data);			
			session.setAttribute(SessionConstant.DECODED_REQUEST_KEY, response);		
			/*
			 * First 2 bytes are length
			 */
			if(response.length() > 2){
				if(session.getAttribute(SessionConstant.DECODED_LENGTH_KEY) == null){					
					String asciiValue = response.substring(0, 2);
					//String hexValue = this.asciiToHex(asciiValue);
					String hexValue = hexDump.substring(0,4);
					length = Integer.parseInt(hexValue, 16);
					log.debug("["+asciiValue + "] --> "+ hexValue + " --> "+ length);
					session.setAttribute(SessionConstant.DECODED_LENGTH_KEY	, length);										
				}else{
					length = Integer.parseInt(session.getAttribute(SessionConstant.DECODED_LENGTH_KEY).toString());
				}
			}else{				return false;
			}
			/*
			 * we have got the LENGTH, check if data is complete
			 */
			if(length > 0){
				log.debug("REPONSE ["+ response+"]");
				if(response.substring(2).length() >=  length){
					log.info(" MSG RCVD SUCCESSFULLY");
				try{
						response = response.substring(2,length+2);
						
						/*
						 * When everything is SUCCESSFUL
						 */
						session.setAttribute(SessionConstant.REQUEST, response);
						/*
						 * REQUEST VALIDATED SUCCESSFULLY
						 */
						out.write(response);
					}catch(Exception e){
						log.error("WHILE GETTING RESPONSE FROM HOST");
						response = "";
						throw e;					
					}					
					/*
					 * remove the session values for next session.
					 */
					session.removeAttribute(SessionConstant.DECODED_LENGTH_KEY);
					session.removeAttribute(SessionConstant.DECODED_REQUEST_KEY);								
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
