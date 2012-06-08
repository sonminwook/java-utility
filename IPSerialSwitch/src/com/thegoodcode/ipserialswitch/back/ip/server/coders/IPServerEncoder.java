package com.thegoodcode.ipserialswitch.back.ip.server.coders;


import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class IPServerEncoder implements ProtocolEncoder {

	private static Logger log = Logger.getLogger(IPServerEncoder.class);
	
	@Override
	public void dispose(IoSession session) throws Exception {
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
			
			log.debug("IP(BACK) MSG >> ["+ session.getRemoteAddress()+"]: ("+ request.length() +" Bytes)"+ outBuffer.getHexDump());
			out.write(outBuffer);
		} catch (RuntimeException e) {
			log.error("EXCEPTION", e);
		}
	}

}
