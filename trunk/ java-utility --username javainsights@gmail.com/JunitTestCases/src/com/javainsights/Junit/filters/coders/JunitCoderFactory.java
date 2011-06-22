package com.javainsights.Junit.filters.coders;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class JunitCoderFactory extends TextLineCodecFactory{
	
	public JunitCoderFactory(Charset charset){
		super(charset);
	}
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) {		
		return new JunitProtocolDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		/*
		 * Using our OWN Protocol Encoder
		 */		
		return new JUnitProtocolEncoder( );
	}

}
