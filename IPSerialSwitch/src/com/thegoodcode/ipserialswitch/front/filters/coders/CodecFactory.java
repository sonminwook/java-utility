package com.thegoodcode.ipserialswitch.front.filters.coders;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class CodecFactory extends TextLineCodecFactory{
	
	public CodecFactory(Charset charset){
		super(charset);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) {
		return new FrontDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		return new FrontEncoder( );
	}

	
}
