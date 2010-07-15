package com.sunz.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.sunz.dto.TCPIPRequest;
import com.sunz.jObjectPool.Pool;
import com.sunz.threadspool.ResponseWorker;

public class OA_WRITE_HANDLER {
	
	private static Logger logger = Logger.getLogger(OA_WRITE_HANDLER.class);
	
	public void write(SelectionKey key, Pool<String, ResponseWorker> clientRespPool){
		SocketChannel clientSocketChannel = (SocketChannel)key.channel();
		String identity = clientSocketChannel.socket().getRemoteSocketAddress()+"";
		ResponseWorker respWorker = clientRespPool.get(identity);
		TCPIPRequest client = respWorker.getClient();
		if(client != null){
			logger.info(respWorker.toString());
			logger.info(client.toString());
			String response = client.getResponse();
			logger.info("Response =" + response);
			ByteBuffer readBuffer = ByteBuffer.allocate(4000);
			if(response != null){
			readBuffer.put(response.getBytes());
			
			readBuffer.flip();
			try {
				clientSocketChannel.write(readBuffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
			
		}
		key.interestOps(SelectionKey.OP_READ);
		
	}

}
