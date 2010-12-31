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
package com.jovialjava.utils;


import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
//import java.nio.ByteBuffer;

//import org.apache.log4j.Logger;

import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.params.Constants;


public class Sender implements SerialPortEventListener {

	private SerialPort serialPort = null;
	InputStream in = null;
	OutputStream out = null;
	
	/*
	 * Private variable
	 */
	private Byte[] checkBytes = null;
	private boolean neadCheck = false;
	private String waitString = null;
	private String resp = "";
	private String oneTimeResponse = "";
	private Exception exception = null;
	private boolean isException = false;
	private boolean isLRC = false;
	private boolean notifyOnNextByte = false;
	
	/*
	 * Hex Data response
	 */
	String hexData = "";
	
	
	//private static Logger logger = Logger.getLogger(Sender.class);
	
	public Sender(SerialPort serialPort) throws Exception{
		this.serialPort = serialPort;
		/*
		 * Add the EVENT Listener
		 */
		this.serialPort.addEventListener(this);
		this.serialPort.notifyOnDataAvailable(true);		
	}
	
	public boolean send(byte[] data, String wait, int waitTime, Byte... notifyByte) throws RS232Exception{
	//	logger.debug("Sending data to SerialPort Device");
		try{
			checkBytes = notifyByte;
			this.waitString = wait;
			this.neadCheck = false;
			/*
			 * Get the Stream
			 */
			this.serialPort.setRTS(true);			
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */			
			out.write(data);
			out.flush();
			this.serialPort.setRTS(false);
			/*
			 * Wait for an Event to happen
			 */
			if(waitString != null){	
				this.neadCheck = true;
				synchronized (waitString) {	
					waitString.wait(waitTime);	
			}}
			/*
			 * Check for exception
			 */			
			this.checkEventException();
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */			
			if(! neadCheck){				
				return true;
			}else{
				return false;
			}
			
		}catch(IOException e){
			String additionMSG = "IO Exception while sending the data to serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);			
		}catch(InterruptedException e){
			String additionMSG = "Interruption while waiting to for data from Serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);
		}catch(RS232Exception e){
			this.isException = false;
			this.exception = null;
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
		}		
	}
	
	public boolean sendnWaitForLRC(byte[] data, String wait, int waitTime, Byte... notifyByte) throws RS232Exception{
		//	logger.debug("Sending data to SerialPort Device");
			try{
				checkBytes = notifyByte;
				this.waitString = wait;
				this.neadCheck = false;
				this.isLRC = true;
				if(data == null){
					return this.send(wait, waitTime, notifyByte);
				}
				/*
				 * Get the Stream
				 */
				this.serialPort.setRTS(true);			
				in = this.serialPort.getInputStream();
				out = this.serialPort.getOutputStream();
				/*
				 * Send the Data
				 */			
				out.write(data);
				out.flush();
				this.serialPort.setRTS(false);
				/*
				 * Wait for an Event to happen
				 */
				if(waitString != null){	
					this.neadCheck = true;
					synchronized (waitString) {	
						waitString.wait(waitTime);	
				}}
				/*
				 * Check for exception
				 */			
				this.checkEventException();
				/*
				 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
				 */			
				if(! neadCheck){				
					return true;
				}else{
					return false;
				}
				
			}catch(IOException e){
				String additionMSG = "IO Exception while sending the data to serial Device";
				throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);			
			}catch(InterruptedException e){
				String additionMSG = "Interruption while waiting to for data from Serial Device";
				throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);
			}catch(RS232Exception e){
				this.isException = false;
				this.exception = null;
				throw e;
			}catch(Exception e){
				throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
			}finally{
				this.isLRC = false;
			}
		}
	
	public boolean sendNWait1Byte(byte[] data, String wait, int waitTime, Byte... notifyByte)
										throws RS232Exception{
		//logger.debug("Sending data to SerialPort Device");
		try{
			checkBytes = notifyByte;
			this.waitString = wait;
			this.neadCheck = false;
			/*
			 * Get the Stream
			 */
			this.serialPort.setRTS(true);
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */
			out.write(data);		
			out.flush();
			this.serialPort.setRTS(false);
			/*
			 * Wait for an Event to happen
			 */
			if(waitString != null){				
				synchronized (waitString) {
					this.neadCheck = true;
					waitString.wait(waitTime);					
			}}
			this.checkEventException();
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(! neadCheck){
				return true;				
			}else{
				return false;
			}
			
		}catch(IOException e){
			String additionMSG = "IO Exception while sending the data to serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);			
		}catch(InterruptedException e){
			String additionMSG = "Interruption while waiting to for data from Serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);
		}catch(RS232Exception e){
			this.isException = false;
			this.exception = null;
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
		}	
	}
	
	/*
	 * This method will be called when we are waiting for response.
	 */
	public boolean send(String wait, int waitTime, Byte... notifyByte) throws RS232Exception{
		try{
			checkBytes = notifyByte;
			this.waitString = wait;
			this.neadCheck = false;
			/*
			 * Wait for response
			 */
			if(waitString != null){
				synchronized (waitString) {
					this.neadCheck = true;
					waitString.wait(waitTime);				
				}
			}
			this.checkEventException();
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(! neadCheck){
				/*
				 * Get the response and clear the buffer.
				 */
//				responseByte = new byte[respnseLength];
//				this.response.get(responseByte);
//				this.response.clear();
				return true;
			}else{
				/*
				 * Still make a check if the response was received, while we were processing at front end.
				 */
				byte[] bytes = this.resp.getBytes();
				for(byte b : bytes){
					for(byte check : checkBytes){
						if(b == check){
							return true;
						}
					}
				}
				return false;
			}
			
		}catch(InterruptedException e){
			String additionMSG = "Interruption while waiting to for data from Serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);
		}catch(RS232Exception e){
			this.isException = false;
			this.exception = null;
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
		}		
	}
	
	/*
	 * This method will send the data and recieve whatever the Serial device sends.
	 */
	public boolean sendNRecieve(byte[] data, String wait, int waitTime) throws RS232Exception{
		try{
			//checkBytes = notifyByte;
			this.waitString = wait;			
			/*
			 * Get the Stream
			 */
			this.serialPort.setRTS(true);
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */
			out.write(data);
			out.flush();
			this.serialPort.setRTS(false);
			/*
			 * Wait for specified time and return back with whatever Data is recieved.
			 */
			if(waitString != null){	
				synchronized (waitString) {
					if(waitTime > 0){
					waitString.wait(waitTime);
					}				
			}}
			this.checkEventException();
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(this.oneTimeResponse.length() > 0){
				this.resp = this.oneTimeResponse;
				return true;				
			}else{
				return false;
			}
			
		}catch(IOException e){
			String additionMSG = "IO Exception while sending the data to serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);			
		}catch(InterruptedException e){
			String additionMSG = "Interruption while waiting to for data from Serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);
		}catch(RS232Exception e){
			this.isException = false;
			this.exception = null;
			throw e;
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
		}	
		
	}
	
	/*
	 * This method will be called to close off the communication with Serial device.
	 */
	public boolean close() throws RS232Exception{
		//logger.debug("Closing the communciation with serial device ");
		try{
			/*
			 * Lets first remove the listener.
			 */
			this.serialPort.removeEventListener();
			/*
			 * Lets close the in and out streams
			 */
			if(this.in != null){
				this.in.close();
			}
			if(this.out != null){
				this.out.flush();
				this.out.close();
			}
			/*
			 * Lets close the serial port.
			 */
			this.serialPort.close();
		}catch(IOException e){
			String additionMSG = "IO Exception while closing the IO stream with Serial Device";
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG+ additionMSG, e);			
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, e);			
		}finally{
			this.in = null;
			this.out = null;
			this.serialPort = null;
		}
		return true;
	}
	
	public String getResponse(){
		return resp;
	}
	
	public void clearResponse(){
		this.resp = "";
	}
	
	private void checkEventException() throws RS232Exception{
		if(this.isException){
			throw new RS232Exception(Constants.NSIO_SEND_ERR_CODE, Constants.SEND_ERR_MSG, this.exception);
		}
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) {		
		int size = 0;
		try{			
			size = in.available();			
			/*
			 * Checking the EVENT Type.
			 */
			switch(event.getEventType()){
			case SerialPortEvent.BI: break;
	        case SerialPortEvent.OE: break;	       
	        case SerialPortEvent.FE: break;
	        case SerialPortEvent.PE: break;
	        case SerialPortEvent.CD: break;
	        case SerialPortEvent.CTS:break;
	        case SerialPortEvent.DSR:break;
	        case SerialPortEvent.RI: break;
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY: break;	        
	        case SerialPortEvent.DATA_AVAILABLE:{
	        	byte[] read = new byte[size];
	        	while (in.available() > 0) {
	        		for(int i=0; i < size; i++){
	        			int byt = in.read();
	        			if((byte)byt == -1){
	        				size = size - i;
	        				break;
	        			}else{
	        				read[i] = (byte)byt;
	        				String hexChar = Integer.toString(byt, 16);
	        				hexChar = hexChar.length() == 1? "0"+hexChar : hexChar;	        				
	        				hexData = hexData + hexChar; 
	        				oneTimeResponse += new Character((char)(byte)byt);	        				
	        			}	        			
	        		} 	
                }
	        	/*
	        	 * It means in the last block we received the ETX, if we received more bytes, that means LRC is received.
	        	 */
	        	if(this.notifyOnNextByte){
	        		this.notifyOnNextByte = false;
	        		if(waitString!=null){
        				this.neadCheck = false;
        				synchronized (waitString) { 
        					resp += hexData;                					                					
        					oneTimeResponse = "";
        					hexData = "";
        					waitString.notify();               					
						}
        			} 
	        	}
	        	   
                /*
                 * Every time terminal sends us somethign..we will check if it has Required Character
                 */
              if(this.checkBytes != null){
                for(int i=0; i< size; i++){
                	byte b = read[i];
                	for(byte check : checkBytes){                		
                		if(b == check && this.neadCheck){
                			/*
                			 * Special provision for LRC
                			 */
                			if(isLRC && (b == Constants.ETX)){
                				/*
                				 * This is the situation where we received a block of bytes ending with ETX.
                				 * We need to wait for next Byte
                				 */
                				if( i == (size -1)){
                					this.notifyOnNextByte = true;
                					break; //Break the loop as we have to wait for next byte to notify
                				}
                			}                			
                			if(waitString!=null){
                				this.neadCheck = false;
                				synchronized (waitString) { 
                					resp += hexData;                					                					
                					oneTimeResponse = "";
                					hexData = "";
                					waitString.notify();                					
								}
                			} // end of null check
                		} // end of b==check
                	} // End of Inner For loop                  	
   				} // End of For loop
               }// End of null check of checkBytes                
    		}// END OF DATA AVAILABLE CASE                
         } // END OF SWITCH
		}catch(Exception e){
			isException = true;
			this.exception = e;
			if(waitString!=null){
				synchronized (waitString) {
					this.neadCheck = false;
					waitString.notify();
				}
			}				
		}

	}
}
