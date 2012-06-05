package com.thegoodcode.ipserialswitch.back.serial;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.serial.SerialAddress;
import org.apache.mina.transport.serial.SerialConnector;

import com.thegoodcode.ipserialswitch.back.serial.coders.SerialCodecFactory;
import com.thegoodcode.ipserialswitch.back.serial.coders.SerialLoggingFilter;
import com.thegoodcode.ipserialswitch.beans.ClientConnector;
import com.thegoodcode.ipserialswitch.beans.Config;

public class Client implements ClientConnector{
	
	private IoSession session = null;
	private IoConnector connector = null;
	private Logger log = Logger.getLogger(Client.class);

	@Override
	public IoSession connect(Config config, IoSession frontSession) {
		SerialConfig serial = (SerialConfig)config;

		connector = new SerialConnector();
		connector.setHandler( new SerialHandler(frontSession) );
		DefaultIoFilterChainBuilder chain = connector.getFilterChain();
		
		chain.addFirst("LOG", new SerialLoggingFilter());
		chain.addLast("CODERS", new ProtocolCodecFilter(new SerialCodecFactory( Charset.forName("ISO-8859-1"))));
		
		SerialAddress portAddress= new SerialAddress(  serial.getPort(),
														serial.getBauds().getValue(),
														serial.getDatabits(),//DataBits.DATABITS_8,
														serial.getStopBits(), //StopBits.BITS_1,
														serial.getParity(), //Parity.NONE,
														serial.getFlowControl()); //FlowControl.NONE );
		
		ConnectFuture future = connector.connect( portAddress );
		try {
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
