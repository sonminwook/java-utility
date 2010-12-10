/*-------------------------------------------------------------------------
Copyright [2010] [Sunny Jain (email:xesunny@gmail.com)]
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--------------------------------------------------------------------------*/
package com.javainsight;

import gnu.io.SerialPort;

import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import org.apache.log4j.Logger;
import com.javainsight.DTO.Instruction;
import com.javainsight.DTO.SerialConfiguration;
import com.javainsight.exceptions.RS232Exception;
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
 * This is the main class with which user will Interact mainly.
 * @author sjain
 * @date 07-DEC-2010
 */

public class RS232Executor {
	
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
	private final ExecutorService executorPool = Executors.newCachedThreadPool();
	
	/**
	 * Instruction Object must be Initialized properly.
	 * @param instruction
	 */
	public RS232Executor(Instruction instruction){
		this.instruction = instruction;
		this.config = instruction.getConfig();
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
				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
			}case CLR_SEND_N_WAIT_FOR_ETX:{
				this.getSender().clearResponse();
				
				Response response = new Response(instruction.getRequest(), this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
				status = executorPool.submit(response).get();
				instruction.setResult(status ? DataResult.RESPONSE : DataResult.NO_DATA);
				if(status){				
					instruction.setResponse(this.getSender().getResponse());
				}
				break;
				
			}case WAIT_FOR_ETX :{
				Response response = new Response(null, this.getSender(), instruction.getWait(), instruction.getTimeOutMilliSecond());
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
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_10, null, e);
		}
		
		
	}
	
	/*
	 * Get the SerialPort and Initialize the Sender 
	 */
	private Sender getSender() throws RS232Exception{
		if(this.sender == null){
			Sender send = null;
				if(!this.serialPortQueue.isEmpty() && this.serialPortQueue.peek() == null){
					throw new RS232Exception(Constants.NSIO_ERROR_CODE_8,Constants.PLS_INIT_FIRST_ERR_MSG);
				}else{	 
					try{
						send = new Sender(this.serialPortQueue.peek());
						sender = send;
					}catch(Exception e){
						throw new RS232Exception("2222","q3w2qe",e);
					}
							
				}
		}
		return sender;		
	}
	

}
