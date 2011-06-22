package com.javainsights.Junit.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.javainsights.Junit.constants.ClientSessionConstant;



public class ConnectionHandler extends IoHandlerAdapter {
	
	
	private static Logger log = Logger.getLogger(ConnectionHandler.class);
	
	public ConnectionHandler(){
			super();			
	}
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		log.error("EXCEPTION -", cause);
		this.sessionClosed(session);		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		log.debug("Message Received ["+ message.toString()+"]");		
		session.setAttribute(ClientSessionConstant.HOST_RESPONSE, message.toString());
		super.messageReceived(session, message);
		/*
		 * We need to notify the IPSSL CLIENT
		 */
		try{
			((Lock)session.getAttribute(ClientSessionConstant.LOCK)).lock();
			((Condition)session.getAttribute(ClientSessionConstant.CONDITION)).signal();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			((Lock)session.getAttribute(ClientSessionConstant.LOCK)).unlock();
		}
				
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		log.debug("Sending REQUEST TO HOST");
		super.messageSent(session, message);		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {		
		super.sessionClosed(session);		
		log.debug("TCP/IP SSL's session with client closed successfully");
		/*
		 * We need to notify the IPSSL CLIENT
		 */
		try{
			((Lock)session.getAttribute(ClientSessionConstant.LOCK)).lock();
			((Condition)session.getAttribute(ClientSessionConstant.CONDITION)).signal();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			((Lock)session.getAttribute(ClientSessionConstant.LOCK)).unlock();
		}
	}	

}
