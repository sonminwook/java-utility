package com.sunz.threadspool;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.sunz.dto.TCPIPRequest;
import com.sunz.in.Server;

public class ResponseWorker implements Callable<String>{

	private String response = null;
	private String clientID = null;	
	private TCPIPRequest client = null;
	private Server parentThread = null;
	private String NAME = null;
	private SelectionKey key = null;	
	private int num=0;
	
	private static Logger logger = Logger.getLogger(ResponseWorker.class);
	
	public ResponseWorker(TCPIPRequest client, Server parentThread, SelectionKey key, int num){
		this.client = client;
		this.parentThread = parentThread;
		this.key = key;		
		this.num = num;
		this.NAME = Thread.currentThread().getName().replace("REQ", "RES");
	}
	
	public TCPIPRequest getClient(){
		return this.client;
	}
	
	public String call(){	
		try{
			Thread.currentThread().setName(NAME);
			logger.debug("Preparing response...");			
			//Thread.currentThread().sleep(500);
			this.client.setResponse(num +" "+this.client.getRequest(), num);
			logger.debug("Response Prepared");
		}catch(Exception e){
			e.printStackTrace();
		}
		this.parentThread.wakeUpToSendResponse((SocketChannel)key.channel());		
		return null;
	}
}
