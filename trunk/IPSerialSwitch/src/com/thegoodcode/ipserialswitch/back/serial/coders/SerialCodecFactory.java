package com.thegoodcode.ipserialswitch.back.serial.coders;


import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;

public class SerialCodecFactory extends TextLineCodecFactory{
	
	public SerialCodecFactory(Charset charset){
		super(charset);
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) {
		return new SerialDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) {
		return new SerialEncoder( );
	}

	
}
