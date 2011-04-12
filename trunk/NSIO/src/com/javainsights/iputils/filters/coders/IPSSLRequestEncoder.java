package com.javainsights.iputils.filters.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.javainsights.iputils.contants.ErrorCodeConstant;

public class IPSSLRequestEncoder implements ProtocolEncoder {

	private static Logger log = Logger.getLogger(IPSSLRequestEncoder.class);
	
	@Override
	public void dispose(IoSession session) throws Exception {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out)
			throws Exception {
		try {
			String request = message.toString();					
			IoBuffer outBuffer = IoBuffer.allocate(request.length(), false);
			outBuffer.setAutoExpand(true);
			for(byte b : request.getBytes()){
				outBuffer.put(b);
			}
			outBuffer.flip();
			
			log.debug("TCP IP SSL OUTGOING MESSAGE <"+ outBuffer.getHexDump()+">");
			out.write(outBuffer);
		} catch (RuntimeException e) {
			log.error("ERROR WHILE GENERATING THE REQUEST FOR HOST "+ e.getMessage());
			throw new IllegalStateException(ErrorCodeConstant.COMMUNICATION_ERROR, e);			
		}
	}

}
