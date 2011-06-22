package com.javainsights.Junit.filters.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class JUnitProtocolEncoder implements ProtocolEncoder {

	private static Logger log = Logger.getLogger(JUnitProtocolEncoder.class);
	
	@Override
	public void dispose(IoSession arg0) throws Exception {
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
			throw e;			
		}

	}

}
