package com.thegoodcode.ipserialswitch.back.ip.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.thegoodcode.ipserialswitch.back.ip.server.coders.IPServerCodecFactory;
import com.thegoodcode.ipserialswitch.back.ip.server.coders.IPServerLoggingFilter;
import com.thegoodcode.ipserialswitch.back.serial.Client;
import com.thegoodcode.ipserialswitch.back.serial.SerialConfig;
import com.thegoodcode.ipserialswitch.back.serial.SerialHandler;
import com.thegoodcode.ipserialswitch.back.serial.coders.SerialCodecFactory;
import com.thegoodcode.ipserialswitch.back.serial.coders.SerialLoggingFilter;
import com.thegoodcode.ipserialswitch.beans.ClientConnector;
import com.thegoodcode.ipserialswitch.beans.Config;

public class IPServerClient implements ClientConnector {

	private IoSession session = null;
	private IoConnector connector = null;
	private Logger log = Logger.getLogger(Client.class);

	@Override
	public IoSession connect(Config config, IoSession frontSession) {
		//SerialConfig serial = (SerialConfig)config;

		connector = new NioSocketConnector();
		connector.setHandler( new IPServerHandler(frontSession) );
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		
		chain.addFirst("LOG", new IPServerLoggingFilter());
		chain.addLast("CODERS", new ProtocolCodecFilter(new IPServerCodecFactory( Charset.forName("ISO-8859-1"))));
		
		try {
			ConnectFuture future = connector.connect( new InetSocketAddress("localhost",9002));
			future.await();
			session = future.getSession();
	        log.info("connected to [" + session.getRemoteAddress()+"][" + session.getId()+"]");
			return session;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean disconnect() {
		if(session != null && session.isConnected()){
			session.close(true);
			connector.dispose(true);
		}
		return true;
	}
	
	

}
