package com.javainsights.Junit.communication;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.filter.util.SessionAttributeInitializingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.javainsights.Junit.constants.ClientSessionConstant;
import com.javainsights.Junit.filters.coders.JunitCoderFactory;
import com.javainsights.Junit.filters.logging.LoggingFilter;

public class Connection {
	
	private IoSession clientSession = null;
	private static Logger log = Logger.getLogger(Connection.class);	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	NioSocketConnector connector = null;
	
	public void dispose(){
		if(connector != null){
			connector.dispose(false);
		}
	}
	
	public boolean initialize(String HOSTNAME, int PORT, int timeout, String certName, String password) throws Exception{
		connector =  new NioSocketConnector();    
	    connector.setConnectTimeoutMillis(timeout);
	    connector.setHandler(new ConnectionHandler());		    		    
	    try {
	    	    addFilterChain(connector.getFilterChain(), certName, password);
	            ConnectFuture future = connector.connect(new InetSocketAddress(HOSTNAME, PORT));
	            future.awaitUninterruptibly();
	            clientSession = future.getSession();		            
	        } catch (Exception e) {
	        	log.error("Client is unable to connect to Host["+ HOSTNAME+"] PORT ["+ PORT +"]", e);
	            throw e;	            
	        }		    
	    
	   log.debug("________________________________________________________________________________");
	   log.debug("Client connected to Host["+ HOSTNAME+"]@["+ PORT +"] successfully");	   
	   log.debug("________________________________________________________________________________");
	   
	   clientSession.setAttribute(ClientSessionConstant.TIME_OUT_IN_MS, timeout);	  	   
	   return true;
	}
	
	
	@SuppressWarnings("unchecked")
	private static void addFilterChain(DefaultIoFilterChainBuilder chain , String certName, String password )
																	throws Exception{
				
		SessionAttributeInitializingFilter sessionInitFilter = new SessionAttributeInitializingFilter();	
		chain.addFirst(ClientSessionConstant.INIT_FILTER, sessionInitFilter);		
		chain.addAfter(ClientSessionConstant.INIT_FILTER,ClientSessionConstant.LOGGING_FILTER, new LoggingFilter());				
		chain.addAfter(ClientSessionConstant.LOGGING_FILTER,ClientSessionConstant.CODEC_FILTER,
													new ProtocolCodecFilter(new JunitCoderFactory(
																Charset.forName(ClientSessionConstant.CODEC_CHARSET))));
		
		/*
		 * ADDING SSL 
		 */
		if((certName != null) && (!"".equals(certName.trim()))){
			SSLContextGenerator sslGenerator = new SSLContextGenerator();
			SSLContext context =  sslGenerator.getClientContext(certName, password);
			if(context != null){
					SslFilter filter = new SslFilter(context);
					filter.setUseClientMode(true);
					chain.addFirst(ClientSessionConstant.SSL_FILTER, filter);
			}
		}
		
	}
	
	public String send(String request) throws Exception{
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
			this.condition.await(Integer.parseInt(clientSession.getAttribute(ClientSessionConstant.TIME_OUT_IN_MS).toString()),
												TimeUnit.MILLISECONDS);
			/*
			 * Either ERROR or RESPONSE HAS RECEIVED
			 */
			return clientSession.getAttribute(ClientSessionConstant.HOST_RESPONSE).toString();
		}catch(Exception e){
			if(this.clientSession.getAttribute(ClientSessionConstant.CLIENT_EXCEPTION) != null){
				throw e;
			}else{
				if(e instanceof NullPointerException){
					throw e; 
				}else{
					throw e;
				}
			}
		}
		finally{
			this.lock.unlock();
		}		
	}
	
	public boolean disconnect() throws Exception{
		try {
			this.clientSession.setAttribute(ClientSessionConstant.IS_GRACEFUL_CLOSE, "TRUE");
			CloseFuture future = this.clientSession.close(true);
			log.debug("Checking session closure " + future.isClosed() + " " + future.isDone());
			return true;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static void main(String... args){
		try {
			Connection connect = new Connection();
			connect.initialize("localhost", 9002, 18000, "config/Pay@Table_CLIENT.jks", "1qaz2wsx");
			connect.send("SUNNY");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
