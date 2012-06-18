package com.thegoodcode.ipserialswitch.back.serial;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.thegoodcode.eventframework.collections.list.TGCList;
import com.thegoodcode.ipserialswitch.front.constants.SessionContext;
import com.thegoodcode.ipserialswitch.protocol.EventListener;

public class SerialHandler extends IoHandlerAdapter {
	
	private static Logger log = Logger.getLogger(SerialHandler.class);
	private boolean sendException = true;
	private TGCList<String> datalist = null;
	
	public SerialHandler(IoSession frontSession){
		datalist = new TGCList<String>();
		datalist.addListener(new EventListener(frontSession));
	}
	
	
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		log.debug("Cleaning up session session for "+ session.getRemoteAddress());
		this.sessionClosed(session);
	}
	

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		
	}


	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
		if(session.isConnected()){
			if(session.getAttribute(SessionContext.CLOSE) != null){
				session.close(true);
			}
		}
	}


	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {			
		if(this.sendException){
			this.sendException = false;	
			if(cause instanceof SSLHandshakeException || cause instanceof IOException){
				log.error("EXCEPTION", cause);
				this.sessionClosed(session);
			}else{	
				log.error("EXCEPTION", cause);		
				session.write("EXCEPTION");
			}
		}else{
			this.sessionClosed(session);
		}
	}
	
	
	
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {				
		datalist.add(message.toString());
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.debug("Connection ["+session.getRemoteAddress() +"] closed");
		session.getFilterChain().clear();		
		session.close(true);	
		super.sessionClosed(session);
	}
	

}
