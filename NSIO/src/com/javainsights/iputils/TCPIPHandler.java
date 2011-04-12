package com.javainsights.iputils;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

package com.javainsights.iputils.contants.ClientSessionConstant;
package com.javainsights.iputils.contants.ErrorCodeConstant;
package com.javainsights.utils.params.Constants;

public class TCPIPHandler extends IoHandlerAdapter {
	
	
	public static final String ERROR_CODE = "ERROR CODE";
	private static Logger log = Logger.getLogger(TCPIPHandler.class);
	private boolean sendException = true;
	
	private static Byte[] notifyBytes = null;
	private static boolean isLRC = false;
	private static String response = "";
	private static IoSession clientSession = null;
	
	private boolean notifyOnNextByte = false;
	
		
	static void setNotifyBytes(Byte...notifyBits){
		notifyBytes = notifyBits;
	}
	
	static void setLRC(boolean needLRC){
		isLRC = needLRC;
	}
	
	static void clearResponse(){
		response = "";
	}
	
	public static IoSession getClientSession(){
		return clientSession;
	}
	
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		super.sessionCreated(session);
		clientSession = session;
	}
	
	
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		
		/*
		 * Check if cause is forcefully closing of connection
		 */
		if(sendException){
			if(cause instanceof IOException){
				sendException = false;
			}
			session.setAttribute(ERROR_CODE, ErrorCodeConstant.COMMUNICATION_ERROR);
			Exception e= new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "Exception while communicating with host", new Exception(cause));			
			session.setAttribute(ClientSessionConstant.CLIENT_EXCEPTION, e);
			/*
			 * We need to notify the IPSSL CLIENT
			 */
			try{
				((Lock)session.getAttribute(ClientSessionConstant.LOCK)).lock();
				((Condition)session.getAttribute(ClientSessionConstant.CONDITION)).signal();
			}catch(Exception ex){
				log.error("!!!ERROR!!! While notifying IPSSL Client "+ ex.getMessage());
			}finally{
				((Lock)session.getAttribute(ClientSessionConstant.LOCK)).unlock();
			}
		}else{
			this.sessionClosed(session);
		}
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		log.debug("Message Received ["+ message.toString()+"]");
		
		byte[] msgBytes = message.toString().getBytes();
		response += message.toString();
		/*
		 * Check if NotifyBytes are not null
		 */
		if(notifyBytes != null){
			log.debug("Checking for notification bytes");
			for(Byte want : notifyBytes){
				for(int i=0; i < msgBytes.length; i++){
					Byte receive = msgBytes[i];					
					if(want.compareTo(receive) == 0 || this.notifyOnNextByte){
						log.debug("Its a Match !"+ want +" "+receive);
						session.setAttribute(ClientSessionConstant.HOST_RESPONSE, response);
						super.messageReceived(session, message);
						/*
						 * We need to notify the IP Sender
						 */
						if(isLRC && receive.compareTo(Constants.ETX)==0){
							log.debug("Going to wait for LRC");
							this.notifyOnNextByte = true;
							session.removeAttribute(ClientSessionConstant.HOST_RESPONSE);
							continue;
						}
						try{
							((Lock)session.getAttribute(ClientSessionConstant.LOCK)).lock();							
							((Condition)session.getAttribute(ClientSessionConstant.CONDITION)).signal();
						}catch(Exception e){
							e.printStackTrace();
						}finally{
							this.notifyOnNextByte = false;
							((Lock)session.getAttribute(ClientSessionConstant.LOCK)).unlock();
						}
					}
				}
			}
		}else{
			/*
			 * Notifybytes are equal to null which means keep collecting response and put into session
			 */			
			session.setAttribute(ClientSessionConstant.HOST_RESPONSE, response);
			super.messageReceived(session, message);
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {		
		super.messageSent(session, message);		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {		
		super.sessionClosed(session);		
		log.debug("TCP/IP SSL's session with client closed successfully");
	}
	
}
