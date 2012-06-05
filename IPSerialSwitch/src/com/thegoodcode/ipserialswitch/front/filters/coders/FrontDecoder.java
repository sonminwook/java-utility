package com.thegoodcode.ipserialswitch.front.filters.coders;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class FrontDecoder extends CumulativeProtocolDecoder {

	@Override
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {		
		if(in.remaining() > 1){
			String hexDump = in.getHexDump();
			hexDump = hexDump.replaceAll(" ", "");
			byte[] data = new byte[in.remaining()];
			for( int i=0; i < data.length ; i ++){
				data[i] = in.get();
			}			
			out.write(new String(data));			
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception {
		super.dispose(session);
	}

}
