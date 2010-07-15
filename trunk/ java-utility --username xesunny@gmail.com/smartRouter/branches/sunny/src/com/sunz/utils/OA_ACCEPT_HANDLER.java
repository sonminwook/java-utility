package com.sunz.utils;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.sunz.in.Server;
import com.sunz.jObjectPool.Pool;
import com.sunz.threadspool.RequestWorker;


public class OA_ACCEPT_HANDLER {
	
	private static Logger logger = Logger.getLogger(OA_ACCEPT_HANDLER.class);
	private Server parentThread = null;
	
	/**
	 * [CLIENT]======[SocketChannel]====|=====[ServerSocketChannel]=====[Server]<br>
	 * | can be assumed as 'Port -- Socket' of server.<br>
	 * Initially we register our Selector with [ServerSocketChannel] for OA_ACCEPT
	 * operation.<br>
	 * We will do following operations here<br>
	 * 1)We can get the Channel from key by using key.channel method.<br>
	 * 2)We will accept the incoming request<br>
	 * 3)Since Client will put the data to be send on [SocketChannel] not [ServerSocketChannel]
	 * so we wil now register the selector with this channel and key will OA_READ.
	 * @param key
	 * @param selector
	 * @throws IOException
	 */
	public SocketChannel accept(SelectionKey key, Selector selector, Pool<String, RequestWorker> clientReqPool, Server parentThread) throws IOException{
		// Step 1- Get the SocketChannel of Server.
		ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();

		/*
		 *  Accept the connection and make it non-blocking
		 *  Socket can be achieved from client's socketchannel
		 *  as well as Server Socket channel.
		 */
		SocketChannel clientSocketChannel = serverSocketChannel.accept();
		Socket socket = clientSocketChannel.socket();
		clientSocketChannel.configureBlocking(false);
		/*
		 * Register the Client's Socket Channel with selector and Key as 'READ'.
		 */		
		clientSocketChannel.register(selector, SelectionKey.OP_READ);				

		logger.info("Check Point 2 :CLIENT CONNECTED : "+socket.getRemoteSocketAddress() + " -- PASS");
		parentThread.getReqPool().add(socket.getRemoteSocketAddress().toString(), new RequestWorker(key, socket.getRemoteSocketAddress()+"",parentThread));		
		return clientSocketChannel;
	}

}
