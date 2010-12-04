package com.javainsight;

import gnu.io.SerialPort;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.gr.one1nterface.grecr.common.exception.CommunicatorException;
import com.gr.one1nterface.grecr.common.interfaces.Communicator;
import com.javainsight.DTO.SerialConfiguration;
import com.javainsight.utils.HexToBytes;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;
import com.javainsight.workers.ACK_NACK;
import com.javainsight.workers.Close;
import com.javainsight.workers.ENQ;
import com.javainsight.workers.Initialize;
import com.javainsight.workers.Response;
import com.javainsight.workers.Send;
import com.javainsight.workers.SendNRecieve;

/**
 * This is new Serial IO Implementatin for OneInterface.
 * @author Sunny
 */
public class NSIOExecutor implements Communicator{

	private static Logger logger = Logger.getLogger(NSIOExecutor.class);
	/*
	 * ==================STACKS FOR DATA COMMUNICATION==================	
	 */
	private Stack<String> strDataStack = null;
	private Stack<byte[]> unsignedByteStack = null;
	private Stack<String> responseStack = null;
	private Stack<DataOperation> dataOprStack = null;
	private Stack<DataResult> resultStack = null;
	private  Stack<Integer> waitTimeStack = null;
	
	/*
	 * Other data objects to be shared
	 */
	private SerialConfiguration config = null;
	private String waitString = new String("waitString");
	
	/*
	 * QUEUES TO BE SHARED BETWEEN WORKER AND EXECUTOR
	 */
	private Stack<SerialPort> serialPortQueue = new Stack<SerialPort>();
	
	/*
	 * Other private variables.
	 */	
	private Sender sender = null;	
	private final ExecutorService executorPool = Executors.newCachedThreadPool();
	
	/*
	 * Initializing 
	 */
	public void share(Stack<String> strDataStack,
					 Stack<byte[]> unsignedByteStack,
					 Stack<String> responseStack,
					 Stack<DataOperation> dataOprStack,
					 Stack<DataResult> resultStack,
					 Stack<Integer> waitTimeStack,
					 SerialConfiguration config ){
		this.strDataStack = strDataStack;
		this.unsignedByteStack = unsignedByteStack;
		this.responseStack = responseStack;
		this.dataOprStack = dataOprStack;
		this.resultStack = resultStack;
		this.waitTimeStack = waitTimeStack;
		//======Setting size============
		this.strDataStack.setSize(1);
		this.unsignedByteStack.setSize(1);
		this.responseStack.setSize(1);
		this.dataOprStack.setSize(1);
		this.resultStack.setSize(1);
		this.waitTimeStack.setSize(1);
		//=============================
		this.config = config;		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				logger.info("Shutting down all NSIO threads");
				executorPool.shutdownNow();	
			}
		});
	}
	
	public void execute(){
		byte[] reqBytes = null;
		boolean status = false;
		/*
		 * Step 1 : Get the type of Operation.
		 */
		DataOperation instruction = this.dataOprStack.pop();
	try{
		/*
		 * Do OPeration based on Operation.
		 */
		switch(instruction) {
		case INITIALIZE:{
			Initialize init = new Initialize(this.config, this.serialPortQueue);
			status = executorPool.submit(init).get();
			this.resultStack.push(status ? DataResult.SUCCESS : DataResult.FAILED);
			break;
		}
		case SEND:{
			reqBytes = this.getUnsignedData();
			Send send = new Send(reqBytes, this.getSender());
			status = executorPool.submit(send).get();	
			this.resultStack.push(status ? DataResult.SUCCESS : DataResult.FAILED);
			break;
			}
		case SEND_N_WAIT_FOR_ACK:{
			reqBytes = this.getUnsignedData();
			ACK_NACK ackSender = new ACK_NACK(reqBytes, this.getSender(), this.waitString, this.waitTimeStack.pop());
			DataResult result = executorPool.submit(ackSender).get();			
			this.resultStack.push(result);
			break;
		}
		case SEND_N_WAIT_FOR_ENQ:{
			reqBytes = this.getUnsignedData();
			ENQ ackSender = new ENQ(reqBytes, this.getSender(), this.waitString, this.waitTimeStack.pop());
			DataResult result = executorPool.submit(ackSender).get();			
			this.resultStack.push(result);
			break;
		}
		case SEND_N_WAIT_FOR_ETX:{
			reqBytes = this.getUnsignedData();
			Response response = new Response(reqBytes, this.getSender(), this.waitString, this.waitTimeStack.pop());
			status = executorPool.submit(response).get();
			this.resultStack.push(status ? DataResult.RESPONSE : DataResult.NO_DATA);
			if(status){
				this.responseStack.push(this.getSender().getResponse());
			}
			break;
		}
		case WAIT_FOR_ETX :{
			Response response = new Response(null, this.getSender(), this.waitString, this.waitTimeStack.pop());
			status = executorPool.submit(response).get();
			this.resultStack.push(status ? DataResult.RESPONSE : DataResult.NO_DATA);
			if(status){
				this.responseStack.push(this.getSender().getResponse());
			}
			break;
		}
		case SEND_N_WAIT_FOR_DATA:{
			reqBytes = this.getUnsignedData();
			SendNRecieve worker = new SendNRecieve(reqBytes, this.getSender(), this.waitString, this.waitTimeStack.pop());
			DataResult result = executorPool.submit(worker).get();			
			this.resultStack.push(result);
			if(result.equals(DataResult.RESPONSE)){
				this.responseStack.push(this.getSender().getResponse());
			}
			break;
		}
		case CLOSE_PORT:{
			Close close = new Close(this.getSender());
			status = executorPool.submit(close).get();
			this.resultStack.push(status ? DataResult.SUCCESS : DataResult.FAILED);
			this.sender = null;
			this.executorPool.shutdown();
			break;
		}
		
		}
	}catch(Exception e){
		logger.error(Constants.NSIO_ERROR_CODE_9+ ")!!!ERROR!!!- executing the communication Request");
		logger.error("Exception", e);
		this.resultStack.push(DataResult.FAILED);
	}
		
		
	}
	
	private byte[] getUnsignedData( ) throws CommunicatorException{
		/*
		 * Step 1.1: First check if data is available in RequestStack
		 */
		if(this.strDataStack.size() == 0){
			if(this.unsignedByteStack.size() == 0){
				return null;
			}else{
				return this.unsignedByteStack.pop();
			}
		}else{
			String request = this.strDataStack.pop();
			try {
				return HexToBytes.toUnsignedByte(HexToBytes.stringToHex(request));
			} catch (Exception e) {	
				logger.error(Constants.NSIO_ERROR_CODE_8+ ")!!!ERROR!!!- While converting the requesting to HexDump");
				throw new CommunicatorException(e.getMessage());
			}
		}
	}
	
	/*
	 * Get the SerialPort and Initialize the Sender 
	 */
	private Sender getSender() throws CommunicatorException{
		if(this.sender == null){
			Sender send = null;
				if(!this.serialPortQueue.isEmpty() && this.serialPortQueue.peek() == null){
					logger.error("Serial Device has not been Initialized, Please execute the Initialization command first");
					throw new CommunicatorException(Constants.NSIO_ERROR_CODE_7+ ") Serial Device has not been Initialized, Please execute the Initialization command first");
				}else{	 
						try {
							send = new Sender(this.serialPortQueue.peek());
							sender = send;
						} catch (Exception e) {
							logger.error(Constants.NSIO_ERROR_CODE_8+ ")!!!ERROR!!!- While initializing the Sender object");
							throw new CommunicatorException(e.getMessage());
						}		
				}
		}
		return sender;		
	}
	
	public void clearStacks(){
		this.dataOprStack.clear();
		this.resultStack.clear();
		this.strDataStack.clear();
		this.unsignedByteStack.clear();
		this.waitTimeStack.clear();
	}
	
	public void setStackSize(int size){
		//======Setting size============
		this.strDataStack.setSize(size);
		this.unsignedByteStack.setSize(size);
		this.responseStack.setSize(size);
		this.dataOprStack.setSize(size);
		this.resultStack.setSize(size);
		this.waitTimeStack.setSize(size);
		//=============================
	}
	
	@Override
	public void closePort() {}
	@Override
	public String getSpectraResponse(byte[] arg0, int arg1, int arg2)throws CommunicatorException {return null;}
	@Override
	public String getType() {return null;}
	@Override
	public boolean isChecked() throws CommunicatorException {return false;}
	@Override
	public boolean isSpectraAckStauts() throws CommunicatorException {return false;}
	@Override
	public int receive() throws CommunicatorException {return 0;}
	@Override
	public int receive(ByteBuffer arg0, int arg1) throws CommunicatorException {return 0;}
	@Override
	public int receive(ByteBuffer arg0) throws CommunicatorException {return 0;}
	@Override
	public byte[] receiveInfo(IoSession session)throws CommunicatorException {return null;}
	@Override
	public int send(byte arg0) throws CommunicatorException {return 0;}
	@Override
	public int send(ByteBuffer buffer, IoSession session) throws CommunicatorException{	return 0;}
	@Override
	public void setConfigurator(Map<String, String> arg0, boolean arg1)	throws CommunicatorException {}
	@Override
	public void setConfigurator(Map<String, String> arg0)	throws CommunicatorException {	}

}
