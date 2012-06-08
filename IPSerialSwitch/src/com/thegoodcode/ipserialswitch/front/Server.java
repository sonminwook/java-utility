package com.thegoodcode.ipserialswitch.front;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.thegoodcode.ipserialswitch.back.ip.server.IPServerClient;
import com.thegoodcode.ipserialswitch.back.serial.Client;
import com.thegoodcode.ipserialswitch.beans.Configure;
import com.thegoodcode.ipserialswitch.beans.FrontBean;
import com.thegoodcode.ipserialswitch.front.filters.LogFilter;
import com.thegoodcode.ipserialswitch.front.filters.coders.CodecFactory;

public class Server {
	
	 	private static FrontBean config = null; 
	    private static Logger log = Logger.getLogger(Server.class);
	    
	    public static void main( String[] args ) 
	    {
	    	
	    	try{
	    		PropertyConfigurator.configure("IPSerialSwitch.properties");
	    		
	    		config = Configure.getFront();
     	    	if(Configure.isLoggingEnabled()){
		    		log.setLevel(Level.INFO);
		    	}else{
		    		log.setLevel(Level.FATAL);
		    	}
	    	   
     	    NioSocketAcceptor acceptor = new NioSocketAcceptor(1);
	        
	        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
	        chain.addFirst("log" , new LogFilter());
	    	chain.addAfter("log", "threads" , new ExecutorFilter(1, 100, IoEventType.MESSAGE_RECEIVED, IoEventType.MESSAGE_SENT));
	    	chain.addAfter("threads", "factory",  new ProtocolCodecFilter(new CodecFactory( Charset.forName("UTF-8"))));
	    	
	    	
	      //  acceptor.setHandler( new Handler(new Client(), Configure.getSerialConfig()) );
	    	  acceptor.setHandler( new Handler(new IPServerClient(), Configure.getSerialConfig()) );
	        acceptor.setReuseAddress(false);
	        acceptor.getSessionConfig().setReadBufferSize( config.getReadBufferSize() );
	        acceptor.getSessionConfig().setBothIdleTime(config.getSessionWriteIdleTime());
	        acceptor.bind( new InetSocketAddress(config.getPort()) );
	        
	        log.info("Server UP/port ["+config.getPort()+"]/Read-Buffer ["+ config.getReadBufferSize()+"]/session-clearance ["+config.getSessionWriteIdleTime()+"] seconds");

       
	       }catch (Exception e) {
	    		log.error("EXCEPTION",e);    			
		} 
	   }    	
	    
}
