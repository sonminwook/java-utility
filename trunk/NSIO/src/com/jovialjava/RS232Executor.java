/*-------------------------------------------------------------------------
RS232Java - This program has been build to simplfy the serial communication by providing 
the implementation of common flow of serial communication world.
Copyright (C) 2010  Sunny Jain [email: xesunny@gmail.com ]

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--------------------------------------------------------------------------*/
package com.jovialjava;

import gnu.io.SerialPort;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import com.jovialjava.DTO.Instruction;
import com.jovialjava.DTO.SerialConfiguration;
import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.Sender;
import com.jovialjava.utils.params.Constants;
import com.jovialjava.workers.ACK_NACK;
import com.jovialjava.workers.Close;
import com.jovialjava.workers.ENQ;
import com.jovialjava.workers.Initialize;
import com.jovialjava.workers.Response;
import com.jovialjava.workers.Send;
import com.jovialjava.workers.SendNRecieve;

/**
 * This is the main class with which user will Interact mainly.
 * @author Sunny Jain
 * @date 07-DEC-2010
 */

public class RS232Executor{
	
	//private static Logger logger = Logger.getLogger(RS232Executor.class);
	/*
	 * An Instruction object is needed, it will be assigned the value sent by Instructor
	 */
	private Instruction instruction = null;
	/*
	 * A SerialConfiguration Object is required to initialized the Serial Device.
	 */
	private SerialConfiguration config = null;
	/*
	 * A synchronous Queue - for Serial Port
	 */
	private Stack<SerialPort> serialPortQueue = new Stack<SerialPort>();
	private Sender sender = null;
	private ExecutorService executorPool = Executors.newCachedThreadPool();
	
	public RS232Executor(){}
	
	/**
	 * Instruction Object must be Initialized properly.
	 * @param instruction
	 */
	public void init(Instruction instruction){
		this.instruction = instruction;
		this.config = instruction.getConfig();
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
	public void execute() throws RS232Exception{
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
				Initialize init = new Initialize(this.config, this.serialPortQueue);
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
				ACK_NACK ackSender = new ACK_NACK(instruction.getRequest(), this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
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
		}catch(RS232Exception e){
			instruction.setResult(DataResult.FAILED);
			this.executorPool.shutdown();
			throw e;
		}catch(Exception e){
			instruction.setResult(DataResult.FAILED);
			this.executorPool.shutdown();
			throw new RS232Exception(Constants.EXECUTOR_ERROR_CODE_10, e.getMessage(), e);
		}
		
		
	}
	
	/*
	 * Get the SerialPort and Initialize the Sender 
	 */
	private Sender getSender() throws RS232Exception{
		if(this.sender == null){
			Sender send = null;
				if(!this.serialPortQueue.isEmpty() && this.serialPortQueue.peek() == null){
					throw new RS232Exception(Constants.SENDER1_ERROR_CODE_8,Constants.PLS_INIT_FIRST_ERR_MSG);
				}else{	 
					try{
						send = new Sender(this.serialPortQueue.peek());
						sender = send;
					}catch(Exception e){
						throw new RS232Exception(Constants.SENDER2_ERROR_CODE, Constants.TOO_MANY_LISTEN_ERR_MSG, e);
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
