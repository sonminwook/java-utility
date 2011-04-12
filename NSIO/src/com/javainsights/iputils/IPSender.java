package com.javainsights.iputils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.session.IoSession;

import com.gr.one1nterface.grecr.common.exception.CommunicatorException;
package com.javainsights.iputils.contants.ClientSessionConstant;
package com.javainsights.iputils.contants.ErrorCodeConstant;
package com.javainsights.utils.params.Common;


public class IPSender {
	
	private static Logger log = Logger.getLogger(IPSender.class);
	private IoSession clientSession = null;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private Object response = null;
	
	public IPSender(IoSession session){
		this.clientSession = session;
	}
	
	/**
	 * This method is to send request and wait for one byte response
	 * @param request - Request to be send
	 * @param timeout - Time period to wait
	 * @param waitBytes - Byte we expect to be received
	 * @return - status if we have received a byte or not
	 * @throws Exception - communication exception if there is any.
	 */
	public boolean sendNWait1Byte(byte[] request, int timeout, Byte... waitBytes) throws Exception{
		try{
			this.lock.lock();
			/*
			 * 	Send the request to Client and start waiting for one byte response
			 */
			TCPIPHandler.setNotifyBytes(waitBytes);
			clientSession.setAttribute(ClientSessionConstant.LOCK, lock);
			clientSession.setAttribute(ClientSessionConstant.CONDITION,condition);	
			clientSession.write(new String(request));			
			this.condition.await(timeout, TimeUnit.MILLISECONDS);
			
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			response =  clientSession.removeAttribute(ClientSessionConstant.HOST_RESPONSE);
			if(response == null){				
				return false;
			}else{				
				return true;
			}
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw (Exception)clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION);
			}else{
				if(e instanceof NullPointerException){
					throw new Exception(ErrorCodeConstant.TIME_OUT_ERROR + "TIME OUT with TERMINAL", new IllegalStateException("NO RESPONSE FROM HOST"));
				}else{
					throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "While Waiting for Response", e);
				}
			}
		}finally{
			this.lock.unlock();
		}		
	}
	
	
	public boolean sendNRecieve(byte[] request, String wait, int timeout) throws Exception{
		try{
			this.lock.lock();
			/*
			 * 	Send the request to Client and start waiting for one byte response
			 */
			TCPIPHandler.setNotifyBytes(null);
			clientSession.setAttribute(ClientSessionConstant.LOCK, lock);
			clientSession.setAttribute(ClientSessionConstant.CONDITION,condition);	
			clientSession.write(new String(request));
			this.condition.await(timeout, TimeUnit.MILLISECONDS);
			
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			response =  clientSession.removeAttribute(ClientSessionConstant.HOST_RESPONSE);
			if(response == null){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw (Exception)clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION);
			}else{
				if(e instanceof NullPointerException){
					throw new Exception(ErrorCodeConstant.TIME_OUT_ERROR + "TIME OUT with TERMINAL", new IllegalStateException("NO RESPONSE FROM HOST"));
				}else{
					throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "While Waiting for Response", e);
				}
			}
		}finally{
			this.lock.unlock();
		}		
	}
	
	/*
	 * This method will be called when we are waiting for response.
	 */
	public boolean send(String request, int timeout, Byte... notifyByte) throws Exception{
		try{
			this.lock.lock();
			/*
			 * 	Send the request to Client and start waiting for one byte response
			 */			
			TCPIPHandler.setNotifyBytes(notifyByte);
			clientSession.setAttribute(ClientSessionConstant.LOCK, lock);
			clientSession.setAttribute(ClientSessionConstant.CONDITION,condition);
			if(request != null){
				clientSession.write(new String(request));
			}
			this.condition.await(timeout, TimeUnit.MILLISECONDS);
			
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			response =  clientSession.removeAttribute(ClientSessionConstant.HOST_RESPONSE);
			if(response == null){
				return false;
			}else{
				return true;
			}
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw (Exception)clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION);
			}else{
				if(e instanceof NullPointerException){
					throw new Exception(ErrorCodeConstant.TIME_OUT_ERROR + "TIME OUT with TERMINAL", new IllegalStateException("NO RESPONSE FROM HOST"));
				}else{
					throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "While Waiting for Response", e);
				}
			}
		}finally{
			this.lock.unlock();
		}		
	}
	

	public String send(String request, int timeout)
												throws Exception {
		/*
		 * Wait until response is READY
		 */
		try{
			this.lock.lock();
			/*
			 * Sending request to clientSession
			 */			
			clientSession.setAttribute(ClientSessionConstant.LOCK, lock);
			clientSession.setAttribute(ClientSessionConstant.CONDITION,condition);			
			clientSession.write(request);
			this.condition.await(timeout, TimeUnit.MILLISECONDS);
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			return (String)clientSession.removeAttribute(ClientSessionConstant.HOST_RESPONSE);
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw (CommunicatorException)clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION);
			}else{
				if(e instanceof NullPointerException){
					throw new Exception(ErrorCodeConstant.TIME_OUT_ERROR + "TIME OUT with TERMINAL", new IllegalStateException("NO RESPONSE FROM HOST"));
				}else{
					throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "While Waiting for Response", e);
				}
			}
		}
		finally{
			this.lock.unlock();
		}		
	}
	
	public boolean sendnWaitForLRC(byte[] request, String wait, int timeout, Byte... notifyByte) throws Exception{
		try{
			this.lock.lock();
			/*
			 * 	Send the request to Client and start waiting for one byte response
			 */
			TCPIPHandler.setNotifyBytes(notifyByte);
			TCPIPHandler.setLRC(true);
			clientSession.setAttribute(ClientSessionConstant.LOCK, lock);
			clientSession.setAttribute(ClientSessionConstant.CONDITION,condition);
			if(request != null){
				clientSession.write(new String(request));
			}			
			this.condition.await(timeout, TimeUnit.MILLISECONDS);			
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			response =  clientSession.removeAttribute(ClientSessionConstant.HOST_RESPONSE);			
			if(response == null){				
				return false;
			}else{				
				return true;
			}
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw (Exception)clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION);
			}else{
				if(e instanceof NullPointerException){
					throw new Exception(ErrorCodeConstant.TIME_OUT_ERROR + "TIME OUT with TERMINAL", new IllegalStateException("NO RESPONSE FROM HOST"));
				}else{
					throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "While Waiting for Response", e);
				}
			}
		}finally{
			this.lock.unlock();
		}	
		
	}
	
	
	public void close() throws Exception {
		try {
			this.clientSession.setAttribute(ClientSessionConstant.IS_GRACEFUL_CLOSE, "TRUE");
			CloseFuture future = this.clientSession.close(true);
			log.debug("Checking session closure " + future.isClosed() + " " + future.isDone());
		} catch (Exception e) {
			throw new Exception(ErrorCodeConstant.COMMUNICATION_ERROR + "UNABLE TO CLOSE SESSION", e);
		}
		
	}
	
	public String getResponse(){
		if(this.response == null){
			return null;
		}else{
			return Common.asciiToHex(this.response.toString());			
		}
	}
	
	public void clearResponse(){
		log.debug("Clearing the response cache");
		TCPIPHandler.clearResponse();
		this.response = "";
	}

}
