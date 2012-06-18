package com.thegoodcode.ipserialswitch.front;

import java.io.IOException;

import javax.net.ssl.SSLHandshakeException;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.thegoodcode.eventframework.collections.list.TGCList;
import com.thegoodcode.ipserialswitch.beans.ClientConnector;
import com.thegoodcode.ipserialswitch.beans.Config;
import com.thegoodcode.ipserialswitch.front.constants.SessionContext;
import com.thegoodcode.ipserialswitch.protocol.EventListener;

public class Handler extends IoHandlerAdapter {
	
	private static Logger log = Logger.getLogger(Handler.class);
	private boolean sendException = true;
	private TGCList<String> datalist = new TGCList<String>();
	
    private ClientConnector back = null;
    private Config config = null;
    
    public Handler(ClientConnector back, Config config){
    	this.back = back;
    	this.config = config;
    }

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
	    IoSession backSession = back.connect(this.config, session);
		datalist.addListener(new EventListener(backSession));

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		super.sessionIdle(session, status);
		this.sessionClosed(session);
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
				this.sessionClosed(session);
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
		session.getFilterChain().clear();		
		session.close(true);		
		super.sessionClosed(session);
		back.disconnect();
	}
	
	
}
