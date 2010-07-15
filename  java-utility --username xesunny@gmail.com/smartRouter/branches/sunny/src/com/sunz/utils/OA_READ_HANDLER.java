package com.sunz.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.sunz.jObjectPool.Pool;
import com.sunz.threadspool.RequestWorker;

public class OA_READ_HANDLER {
	
	private static Logger logger = Logger.getLogger(OA_READ_HANDLER.class);
	private final int DISCONNECTION_REQ = -1;
	private final boolean FAILURE = false;
	private final boolean SUCCESS = true;
	
	private final ExecutorService executorPool = Executors.newCachedThreadPool();
	
	/**
	 * that if a channel is in blocking mode and there is at 
	 * least one byte remaining in the buffer then this method
	 * will block until at least one byte is read
	 * STEP1: Read the client's data.<br>
	 * STEP2: Check if client has disconnected by checking number of bytes read equal to -1.<br>
	 * STEP3: Flip the ByteBuffer.<br>
	 * STEP4: Process the Request Data and get the response.
	 * @param key
	 * @param BUFF_READ_SIZE
	 * @throws Exception
	 */
	public boolean read(SelectionKey key, int BUFF_READ_SIZE, boolean isBlocking, Pool<String, RequestWorker> clientReqPool) throws Exception{
		SocketChannel clientSocketChannel = (SocketChannel)key.channel();
		clientSocketChannel.configureBlocking(isBlocking);
		ByteBuffer readBuffer = ByteBuffer.allocate(BUFF_READ_SIZE);
		RequestWorker worker = clientReqPool.get(clientSocketChannel.socket().getRemoteSocketAddress()+"");
		worker.updateKey(key);
		int numRead = 0;
		readBuffer.clear();
		//----------------STEP1-----------------------
		try {
			numRead = clientSocketChannel.read(readBuffer);
		} catch (IOException e) {
			logger.error("!!! PROBLEM - WHILE READING DATA FROM CLIENT !!!" );
			logger.error("!!! Message !!! " +e.getMessage());
			throw e;
		}
		//-----------------STEP2---------------------
		if(numRead == this.DISCONNECTION_REQ){
			logger.info("Client ["+clientSocketChannel.socket().getRemoteSocketAddress()+"] has disconnected");
			//Temp
			worker.kill();
			executorPool.shutdown();
			clientSocketChannel.close();
			clientReqPool.remove(clientSocketChannel.socket().getRemoteSocketAddress()+"");
			return this.FAILURE;
		}else {
			//-----------------STEP3-----------------------
			readBuffer.flip();
			logger.info("Data ["+numRead+" Bytes] has been recieved from Client");
			byte[] byt = new byte[numRead];
				//	readBuffer.put(byt);
				for(int i=0; i<numRead;i++){	
						byt[i] = readBuffer.get(i);
				}		
			readBuffer.clear();
			worker.setRequest(new String(byt));
		}
		logger.info(clientReqPool.toString());
		logger.info(worker.toString());
		Future<String> result = executorPool.submit(worker);
		//logger.info(result.get());		
		return this.SUCCESS;
	}

}
