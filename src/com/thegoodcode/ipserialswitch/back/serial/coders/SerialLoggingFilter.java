package com.thegoodcode.ipserialswitch.back.serial.coders;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class SerialLoggingFilter extends IoFilterAdapter {
	
	private static Logger log = Logger.getLogger(SerialLoggingFilter.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object msg) throws Exception {		
		IoBuffer bb = (IoBuffer)msg ;
		log.debug(new StringBuilder("SERIAL MSG << ["+ session.getRemoteAddress()+ "]: (").append(bb.remaining()).append(" Bytes) ").append(bb.getHexDump()).toString());		
		super.messageReceived(nextFilter, session, msg);		
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,	WriteRequest writeRequest) throws Exception {		
		super.messageSent(nextFilter, session, writeRequest);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {		
		super.sessionClosed(nextFilter, session);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
		super.sessionCreated(nextFilter, session);
	}
	
	


}
