package com.javainsights;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IoSession;


import com.javainsights.DTO.IPConfiguration;
import com.javainsights.DTO.Instruction;
import com.javainsights.exceptions.IPException;
import com.javainsights.exceptions.RS232Exception;
import com.javainsights.iputils.IPSender;
import com.javainsights.iputils.workers.ACK_NACK;
import com.javainsights.iputils.workers.Close;
import com.javainsights.iputils.workers.ENQ;
import com.javainsights.iputils.workers.Initialize;
import com.javainsights.iputils.workers.Response;
import com.javainsights.iputils.workers.Send;
import com.javainsights.iputils.workers.SendNRecieve;
import com.javainsights.utils.params.Constants;


public class IPExecutor {
	
	/*
	 * An Instruction object is needed, it will be assigned the value sent by Instructor
	 */
	private Instruction instruction = null;
	/*
	 * An IPConfiguration Object is required to initialized the IP Device.
	 */
	private IPConfiguration config = null;
	/*
	 * A synchronous Queue - for TCP/IP Port
	 */
	private Stack<IoSession> ipSessionQueue = new Stack<IoSession>();
	private IPSender sender = null;
	private ExecutorService executorPool = Executors.newCachedThreadPool();
	
	/**
	 * Instruction Object must be Initialized properly.
	 * @param instruction
	 */
	public void init(Instruction instruction){
		this.instruction = instruction;
		this.config = instruction.getIPConfig();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){				
				executorPool.shutdown();
			}
		});
	}	
	
	/**
	 * After every instruction passed to Instructor user must give
	 * a call to execute method.
	 * @throws RS232Exception
	 */
	public void execute() throws IPException{
			boolean status = false;
			/*
			 * Step 1 : Get the type of Operation.
			 */
			DataOperation operation = instruction.getOperation();
		try{
			/*
			 * Do OPeration based on Operation.
			 */
			switch(operation) {
			case INITIALIZE:{
				Initialize init = new Initialize(this.config, this.ipSessionQueue);
				/*
				 * In case if you want to "re-init" the same object again.
				 */
				if(executorPool.isShutdown()){
					executorPool = Executors.newCachedThreadPool();
				}
				status = executorPool.submit(init).get();
				instruction.setResult(status ? DataResult.SUCCESS : DataResult.FAILED);
				break;
			}
			case SEND:{
				Send send = new Send(instruction.getRequest(), this.getSender());
				status = executorPool.submit(send).get();	
				instruction.setResult(status ? DataResult.SUCCESS : DataResult.FAILED);
				break;
				}
			case SEND_N_WAIT_FOR_ACK:{
				ACK_NACK ackSender = new ACK_NACK(instruction.getRequest(),
													this.getSender(),
													instruction.getWait(),
													instruction.getTimeOutMilliSecond());
				DataResult result = executorPool.submit(ackSender).get();			
				instruction.setResult(result);
				break;
			}
			case SEND_N_WAIT_FOR_ENQ:{
				
				ENQ ackSender = new ENQ(instruction.getRequest(), this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
				DataResult result = executorPool.submit(ackSender).get();			
				instruction.setResult(result);
				break;
			}
			case SEND_N_WAIT_FOR_ETX:{
				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(), 
															instruction.getTimeOutMilliSecond(),null, false);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}case CLR_SEND_N_WAIT_FOR_ETX:{
				this.getSender().clearResponse();				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(),
									instruction.getTimeOutMilliSecond(), null, false);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
				
			}case WAIT_FOR_ETX :{
				Response response = new Response(null, this.getSender(), instruction.getWait(), 
																	instruction.getTimeOutMilliSecond(), null, false);
				status = executorPool.submit(response).get();			
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case SEND_N_WAIT_FOR_ETX_LRC:{
				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(), 
															instruction.getTimeOutMilliSecond(),null, true);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}case CLR_SEND_N_WAIT_FOR_ETX_LRC:{
				this.getSender().clearResponse();				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(),
									instruction.getTimeOutMilliSecond(), null, true);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
				
			}case WAIT_FOR_ETX_LRC :{
				Response response = new Response(null, this.getSender(), instruction.getWait(), 
																	instruction.getTimeOutMilliSecond(), null, true);
				status = executorPool.submit(response).get();			
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case SEND_N_WAIT_FOR_DATA:{
				SendNRecieve worker = new SendNRecieve(instruction.getRequest(), this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
				DataResult result = executorPool.submit(worker).get();			
				instruction.setResult(result);
				if(result.equals(DataResult.RESPONSE)){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case CLR_PREVIOUS_RESPONSE:{
				this.getSender().clearResponse();
				instruction.setResult(DataResult.SUCCESS);
				break;
			}case CLR_SEND_N_WAIT_FOR_NOTIFYING_BYTS:{
				this.getSender().clearResponse();
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(), 
													instruction.getTimeOutMilliSecond(), instruction.getNotifyingBytes(), false);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if (status) {
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case SEND_N_WAIT_FOR_NOTIFYING_BYTS:{
				Response response = new Response(instruction.getRequest(), this.getSender(), 
										instruction.getWait(), instruction.getTimeOutMilliSecond(),instruction.getNotifyingBytes(), false);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case WAIT_FOR_NOTIFYING_BYTS:{
				System.out.println("Inside wait for notifying bytes");
				Response response = new Response(null, this.getSender(),instruction.getWait(), instruction.getTimeOutMilliSecond(),
															instruction.getNotifyingBytes(), false);
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}
			case CLOSE_PORT:{
				try{
					Close close = new Close(this.getSender());
					status = executorPool.submit(close).get();
					instruction.setResult(status ? DataResult.SUCCESS : DataResult.FAILED);
					this.sender = null;				
					break;
				}finally{
					this.executorPool.shutdown();
				}
			}
			
			}
		}catch(IPException e){
			instruction.setResult(DataResult.FAILED);
			this.executorPool.shutdown();
			throw e;
		}catch(Exception e){
			instruction.setResult(DataResult.FAILED);
			this.executorPool.shutdown();
			throw new IPException(Constants.EXECUTOR_ERROR_CODE_10, e.getMessage(), e);
		}
		
		
	}
	
		
	private IPSender getSender() throws IPException{
		if(this.sender == null){
			IPSender send = null;
				if(!this.ipSessionQueue.isEmpty() && this.ipSessionQueue.peek() == null){
					throw new IPException(Constants.SENDER1_ERROR_CODE_8,Constants.PLS_INIT_FIRST_ERR_MSG);
				}else{	 
					try{
						send = new IPSender(this.ipSessionQueue.peek());
						sender = send;
					}catch(Exception e){
						throw new IPException(Constants.SENDER2_ERROR_CODE, Constants.TOO_MANY_LISTEN_ERR_MSG, e);
					}
							
				}
		}
		return sender;		
	}
	
	/*
	 * Implementing finalize method.
	 */
	@Override
	protected void finalize() {
		try{
			super.finalize();
			if(this.executorPool != null){				
				this.instruction.setOperation(DataOperation.CLOSE_PORT);
				this.execute();
			}
		}catch(Throwable t){}
	}

}
