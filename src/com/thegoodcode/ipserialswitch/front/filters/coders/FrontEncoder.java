package com.thegoodcode.ipserialswitch.front.filters.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.thegoodcode.ipserialswitch.front.constants.Constant;

public class FrontEncoder implements ProtocolEncoder {

	private static Logger log = Logger.getLogger(FrontEncoder.class);
	@Override
	public void dispose(IoSession session) throws Exception {
	}

	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		
		
		try {
			String response = "";
			response = this.convertHexToString(message+"");

			
			IoBuffer outBuffer = IoBuffer.allocate(response.length(), false);
			outBuffer.setAutoExpand(true);
			for(byte b : response.getBytes(Constant.RESPONSE_ENCODING)){
				outBuffer.put(b);
			}
			outBuffer.flip();
			log.info("IP MSG << [" + outBuffer.remaining() + "]/Dump [" + outBuffer.getHexDump()+"]");
			out.write(outBuffer);
		} catch (RuntimeException e) {
			log.error("Exception", e);
			session.close(true);
		}
	}
	
	private String convertHexToString(String hex){
		 
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
