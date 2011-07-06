package com.javainsights.Junit.filters.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

public class LoggingFilter extends IoFilterAdapter {
	
	/*
	 * THE LOG
	 */
	private static Logger log = Logger.getLogger(LoggingFilter.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session,
			Object msg) throws Exception {
		NDC.push( new StringBuilder("[Session id=").append(session.getId()).append("]").toString() );
		
		IoBuffer bb = (IoBuffer)msg ;
		log.debug(new StringBuilder("OneInterface Incoming Message: (").append(bb.remaining()).append(" Bytes) ").append(bb.getHexDump()).toString());
		// all other calls (decode, process) happen synchronously within this call in the current thread
		NDC.pop();
		super.messageReceived(nextFilter, session, msg);		
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session,
			WriteRequest writeRequest) throws Exception {
		NDC.push( new StringBuilder("[Session id=").append(session.getId()).append("]").toString() );
		log.debug("TCP/IP SSL Outgoing Message :("+ writeRequest.getOriginalRequest()+")");
		//log.debug("TCP/IP SSL Outgoing Message :("+ writeRequest.getMessage()+")");
		//log.debug("TCP/IP SSL Outgoing Message :("+ writeRequest.toString()+")");
		NDC.pop();
		super.messageSent(nextFilter, session, writeRequest);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)
			throws Exception {
		NDC.push( new StringBuilder("[Session id=").append(session.getId()).append("]").toString() );
		log.debug("TCP/IP SSL's session with client has been closed");
		NDC.pop();
		super.sessionClosed(nextFilter, session);
	}


}
