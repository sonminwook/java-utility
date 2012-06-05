package com.thegoodcode.ipserialswitch.back.serial.coders;


import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;


public class SerialDecoder extends CumulativeProtocolDecoder {

	private String response = "";
	

	
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		if(in.remaining() > 0){
			response = in.getHexDump().replaceAll(" ", "");				
				byte[] data = new byte[in.remaining()];				
				for( int i=0; i < data.length ; i ++){
					data[i] = in.get();
				}				
				out.write(response);						
				return true;				
			}else{				
				return false;
			}	
	}
	

}
