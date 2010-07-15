package com.sunz.threadspool;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.sunz.dto.TCPIPRequest;
import com.sunz.in.Server;

public class RequestWorker implements Callable<String>{
	
	private static Logger logger = Logger.getLogger(RequestWorker.class);
	private final ExecutorService executorPool = Executors.newCachedThreadPool();
	private Server parentThread = null;
	private TCPIPRequest client = new TCPIPRequest();
	private SelectionKey key = null;
	private String NAME = null;
	
	//----------------TMP VARIABLES TO STORE INFORMATION FOR TCPIP REQUEST----------------
	String identity = null;
	String request = null;
	
	
	public RequestWorker(SelectionKey key, String identity, Server parentThread){
		this.client.setIdentityString(identity);
		this.key = key;
		this.parentThread = parentThread;
		this.NAME = Thread.currentThread().getName()+"_"+
		identity.substring(identity.lastIndexOf(":")+1)+"_REQ";
		
	}
	
	public void updateKey(SelectionKey key){
		this.key = key;
	}
	
	public int setRequest(String requestMsg){
		this.client.setRequest(requestMsg);
		return 0;
	}
		
	public void kill(){
		logger.info("Killing the REQUEST WORKER as client has disconnected");
		executorPool.shutdown();
		//this.client.setConnected(false);
	}
	
	public String call(){
		//-----------PREPARING THE TCPIP REQUEST OBJECT------------------
		
		int num = this.client.getRequestNumber();
		//Thread.currentThread().setName(NAME+"_"+num);
		
		//--------------DONE--------------------------------------------
		
		
		try{
			Thread.currentThread().setName(NAME+"_"+num);	
			logger.debug("Waiting here...sleeping");			
			//Thread.currentThread().sleep(2000);
			logger.debug("sleep finishes");
		}catch(Exception e){
			e.printStackTrace();
		}		
	//	this.client.setResponse(num +" "+request, num);
		/*
		 * Shooting a Response Thread for each Response.
		 */
		ResponseWorker respWorker = new ResponseWorker(this.client, parentThread, key, num);
		parentThread.getRespPool().add(this.client.getIdentityString(), respWorker);
		logger.debug(parentThread.getRespPool().toString());
		logger.debug(this.client.toString());
		executorPool.submit(respWorker);
		//logger.debug("Response -> "+this.client.getResponse());
		return null;
		
	}

}
