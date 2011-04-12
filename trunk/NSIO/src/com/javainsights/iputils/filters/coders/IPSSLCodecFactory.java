package com.javainsights.iputils.filters.coders;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class IPSSLCodecFactory extends TextLineCodecFactory{
	
	public IPSSLCodecFactory(Charset charset){
		super(charset);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) {
		// TODO Auto-generated method stub
		//return super.getDecoder(session);
		return new IPSSLResponseDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		/*
		 * Using our OWN Proctol Encoder
		 */
		//return super.getEncoder(session);
		return new IPSSLRequestEncoder( );
	}

	
}
