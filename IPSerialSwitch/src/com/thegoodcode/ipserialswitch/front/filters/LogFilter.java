package com.thegoodcode.ipserialswitch.front.filters;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;

import com.thegoodcode.ipserialswitch.front.constants.SessionContext;


public class LogFilter extends IoFilterAdapter {
	
    private static Logger log = Logger.getLogger(LogFilter.class);

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object msg) throws Exception {		
		IoBuffer bb = (IoBuffer)msg ;
		log.info(new StringBuilder("IP MSG >> [").append(bb.remaining()).append("]/Dump [").append(bb.getHexDump()).toString()+"]");
		super.messageReceived(nextFilter, session, msg);		
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session)	throws Exception {
		if(session.getAttribute(SessionContext.CLOSE) != null){
			log.info("Switch is closing connection");
		}else{
			log.info("Connection closed from ["+session.getRemoteAddress() + "] to ["+ session.getLocalAddress()+"][" + session.getId()+"]");
		}
		super.sessionClosed(nextFilter, session);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
		log.info("Connection from ["+ session.getRemoteAddress() + "] to ["+ session.getLocalAddress()+"][" + session.getId()+"]");
		super.sessionCreated(nextFilter, session);			
	}
}
