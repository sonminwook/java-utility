package com.sunz.in;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.GR.beans.serverBean;
import com.GR.interfaces.Bean;
import com.sunz.jObjectPool.Pool;
import com.sunz.jObjectPool.PoolManager;
import com.sunz.threadspool.RequestWorker;
import com.sunz.threadspool.ResponseWorker;
import com.sunz.utils.OA_ACCEPT_HANDLER;
import com.sunz.utils.OA_READ_HANDLER;
import com.sunz.utils.OA_WRITE_HANDLER;


public class Server implements Runnable{
	
	//----------------SERVER THREAD PROPERTIES-------------------------
	private String host = null;
	private int port = 0;
	private int READ_BUFFER_SIZE=4000;
	//----------------- REQUEST AND RESPONSE POOL----------------------
	private Pool<String, RequestWorker> clientReqPool = null;
	private Pool<String, ResponseWorker> clientRespPool = null;
	
	//------------------ROUTING PROPERTIES-----------------------------
	private Map<String, String> routingSolution = new HashMap<String, String>();
	
	// The channel on which we'll accept connections
	private Selector selector = null;
	private ServerSocketChannel serverChannel=null;
	private SocketChannel clientSocketChannel = null;

	
	private static Logger logger = Logger.getLogger(Server.class);
	
	/**
	 * This is the constructor of Server. This will make one instance of Server to start listen on 
	 * host:port. If server can not be started on the particular host and port. It will throw an 
	 * Exception.
	 * @param host - it is the local host address.
	 * @param port - it is the port number of local machine where it will accept the connection.
	 * @param REQUEST_BUFFER_SIZE - The request message can be of vary length, Server will always use a 
	 * fix length buffer to recieve input message. This is the size of that Buffer.
	 * @param routingSolution - This is the routing solution available for all the message on this port.
	 * @throws IOException - Exception thrown in case if server can not be started on host and port.
	 */
	public Server(		String host,
						int port,
						Map<String, Bean> propertiesMap	)
						throws IOException{
		this.host = host;
		this.port = port;
		this.READ_BUFFER_SIZE = ((serverBean)propertiesMap.get("server.properties")).getREAD_SIZE();
		this.logger.setLevel(((serverBean)propertiesMap.get("server.properties")).getLoggingLevel()); 
		this.routingSolution = null;
		this.clientReqPool = new PoolManager<String,RequestWorker>().createPool(100);
		this.clientRespPool = new PoolManager<String, ResponseWorker>().createPool(100);
		this.selector = this._init();
	}
	
	/*
	 * This method will do 4 Main steps.
	 * 1) Create a Selector which will act as EVENT NOTIFIER.
	 * 2) Create a ServerSocketChannel which will open the machine to accept outside connection.
	 * 3) Bind the ServerSocketChannel to host and port. So now local machine can accept incoming connection
	 * on particular port.
	 * 4) Register the ServerSocketChannel with Selector and assign it as Event Notifier for
	 * incoming connection Request.
	 */
	private Selector _init() throws IOException{
		// Create a new selector
		Selector socketSelector = SelectorProvider.provider().openSelector();

		// Create a new non-blocking server socket channel
		this.serverChannel = ServerSocketChannel.open();
		serverChannel.configureBlocking(false);

		// Bind the server socket to the specified address and port
		InetSocketAddress isa = new InetSocketAddress(this.host, this.port);
		serverChannel.socket().bind(isa);

		// Register the server socket channel, indicating an interest in 
		// accepting new connections
		serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
		logger.info("Server started to listen on: @"+this.port);
		//logger.info("Routing solution availabe on port "+this.port+" are :"+this.routingSolution.toString());
		logger.info("!!! Waiting for Client !!!");
		return socketSelector;
	}
	
	
	
	public void run(){
		while (true) {
			try {
				// Process any pending changes
				/*synchronized (this.pendingChanges) {
					
					Iterator<ChangeRequest> changes = this.pendingChanges.iterator();
					while (changes.hasNext()) {
						logger.info("Preparing to send the response back to client");
						ChangeRequest change = (ChangeRequest) changes.next();
						switch (change.type) {
						case ChangeRequest.CHANGEOPS:
							SelectionKey key = change.socket.keyFor(this.selector);
							key.interestOps(change.ops);
						}
					}
					this.pendingChanges.clear();
				}*/
//				if(tempFlag && clientSocketChannel != null){
//					SelectionKey key = clientSocketChannel.keyFor(this.selector);
//					key.interestOps(SelectionKey.OP_WRITE);
//				}

				/*
				 * This is very important method. It will wait untill one of the operation
				 * happens on channels registered with this selectors.
				 * For example - In our case - this selector is registered with only 1 channel
				 * which is our local host and port. 
				 * Keys register with this channel are - OA_ACCEPT ( for the very first time, later 
				 * we will register more keys to it), OA_READ, OA_WRITE.
				 * When the first client will sent a connection Request to server. This method will wake up
				 * and next while loop will be executed.
				 */
				logger.info("~~~~~~ Selector is waiting for EVENT ~~~~~" );
				this.selector.select();	
				
				/*
				 * Here we will iterate over the keys on which event has happened.
				 * It may possible that one client has sent Connection Request 
				 * whereas one client is ready to send data.
				 */
				//Set<SelectionKey> eventKeys = this.selector.selectedKeys();
				logger.info(this.selector.selectedKeys().size()+ " Size of the key set");
				for(SelectionKey key : this.selector.selectedKeys()){
					this.selector.selectedKeys().remove(key);
					//logger.info(this.selector.selectedKeys().size()+ " Size of the key set.");
					if(!key.isValid()){
						continue;
					}
					if(key.isAcceptable()){
						logger.info("Accept incoming connection");
						this.clientSocketChannel = new OA_ACCEPT_HANDLER().accept(key, this.selector, this.clientReqPool, this);
						//do something for incoming connection
					}else if(key.isReadable()){
						logger.info("In Read");
						new OA_READ_HANDLER().read(key, this.READ_BUFFER_SIZE, false, this.clientReqPool);						
						//do something to read data from client
					}else if(key.isWritable()){
						//do something to write data for the client.
						logger.info("in WRITE");
						new OA_WRITE_HANDLER().write(key, clientRespPool);					
					}
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	public void wakeUpToSendResponse(SocketChannel clientSocketChannel ){
		logger.info("Parepare to Write data to Client");
		SelectionKey key = clientSocketChannel.keyFor(this.selector);
		key.interestOps(SelectionKey.OP_WRITE);
		this.selector.wakeup();
	}
	
	public Pool<String, ResponseWorker> getRespPool(){
		return this.clientRespPool;
	}
	
	public Pool<String, RequestWorker> getReqPool(){
		return this.clientReqPool;
	}
	
//	public static void main(String[] args){
//		PropertyConfigurator.configure("config/log4j.properties");
//		try {
//			Server s = new Server("127.0.0.1", 8080, 4000, null);
//			Thread t = new Thread(s);
//			t.setName("8080");
//			t.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			logger.error("ERROR ->", e);			
//		}
//	}
}
