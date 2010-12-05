package com.javainsight.utils;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.params.Constants;

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
		
	private ByteBuffer response = ByteBuffer.allocate(Constants.responseBufferCapacity);
	private int respnseLength = 0;
	private byte[] responseByte = null;
	private String resp = "";
	
	
	
	private static Logger logger = Logger.getLogger(Sender.class);
	
	public Sender(SerialPort serialPort) throws RS232Exception{
		try{
			this.serialPort = serialPort;
			/*
			 * Add the EVENT Listener
			 */
			this.serialPort.addEventListener(this);
			this.serialPort.notifyOnDataAvailable(true);		
		}catch(Exception e){
			throw new RS232Exception(Constants.NSIO_ERROR_CODE_9, Constants.TOO_MANY_LISTEN_ERR_MSG, e);
		}
	}
	
	public boolean send(byte[] data, String wait, int waitTime, Byte... notifyByte){
		logger.debug("Sending data to SerialPort Device");
		try{
			checkBytes = notifyByte;
			this.waitString = wait;
			this.neadCheck = false;
			/*
			 * Get the Stream
			 */
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */
			out.write(data);
			out.flush();
			/*
			 * Wait for an Event to happen
			 */
			if(waitString != null){				
				synchronized (waitString) {	
					this.neadCheck = true;
					waitString.wait(waitTime);					
			}}
								
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */			
			if(! neadCheck){				
				return true;
			}else{
				return false;
			}
			
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_1 + ")!!!ERROR!!!- While sending the data to Serial Device");
			logger.error("Exception", e);
			return false;
		}		
	}
	
	public boolean sendNWait1Byte(byte[] data, String wait, int waitTime, Byte... notifyByte){
		logger.debug("Sending data to SerialPort Device");
		try{
			checkBytes = notifyByte;
			this.waitString = wait;
			this.neadCheck = false;
			/*
			 * Get the Stream
			 */
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */
			out.write(data);
			out.flush();
			/*
			 * Wait for an Event to happen
			 */
			if(waitString != null){				
				synchronized (waitString) {
					this.neadCheck = true;
					waitString.wait(waitTime);					
			}}
			
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(! neadCheck){
				return true;				
			}else{
				return false;
			}
			
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_1 + ")!!!ERROR!!!- While sending the data to Serial Device");
			logger.error("Exception", e);
			return false;
		}		
	}
	
	/*
	 * This method will be called when we are waiting for response.
	 */
	public boolean send(String wait, int waitTime, Byte... notifyByte){
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
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(! neadCheck){
				/*
				 * Get the response and clear the buffer.
				 */
				responseByte = new byte[respnseLength];
				this.response.get(responseByte);
				this.response.clear();
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
			
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_2 + ")!!!ERROR!!!- While collection the response from Serial Device");
			logger.error("Exception", e);
			return false;
		}		
	}
	
	/*
	 * This method will send the data and recieve whatever the Serial device sends.
	 */
	public boolean sendNRecieve(byte[] data, String wait, int waitTime){
		try{
			//checkBytes = notifyByte;
			this.waitString = wait;			
			/*
			 * Get the Stream
			 */
			in = this.serialPort.getInputStream();
			out = this.serialPort.getOutputStream();
			/*
			 * Send the Data
			 */
			out.write(data);
			out.flush();
			/*
			 * Wait for specified time and return back with whatever Data is recieved.
			 */
			if(waitString != null){				
				synchronized (waitString) {					
					waitString.wait(waitTime);					
			}}
			
			/*
			 * If waitString was notified, that means response was receieved. so Neadcheck should be false.
			 */
			if(this.resp.length() > 0){
				return true;				
			}else{
				return false;
			}
			
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_1 + ")!!!ERROR!!!- While sending the data to Serial Device");
			logger.error("Exception", e);
			return false;
		}	
		
	}
	
	/*
	 * This method will be called to close off the communication with Serial device.
	 */
	public boolean close(){
		logger.debug("Closing the communciation with serial device ");
		try{
			/*
			 * Lets first remove the listener.
			 */
			this.serialPort.removeEventListener();
			/*
			 * Lets close the in and out streams
			 */
			this.in.close();
			this.out.flush();
			this.out.close();
			/*
			 * Lets close the serial port.
			 */
			this.serialPort.close();
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_3 + ")!!!ERROR!!!- While closing the connection to Serial Device");
			logger.error("Exception", e);
			return false;
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
	        case SerialPortEvent.CTS: break;
	        case SerialPortEvent.DSR: break;
	        case SerialPortEvent.RI: break;
	        case SerialPortEvent.OUTPUT_BUFFER_EMPTY: break;	        
	        case SerialPortEvent.DATA_AVAILABLE:{
	        	byte[] read = new byte[size];
	        	while (in.available() > 0) {
                    	int num = in.read(read);
                    	this.respnseLength += num;
                }
	        	/*
	        	 * Store the data into response
	        	 */
	        	//System.out.println(new String(read));
	        	resp += new String(read);
                response.put(read);                
                /*
                 * Every time terminal sends us somethign..we will check if it has Required Character
                 */
                if(this.checkBytes != null){
                for(byte b : read){ 
                	//System.out.print(b);
                	for(byte check : checkBytes){
                		//System.out.print((b==check) + "  ");
                		if(b == check && this.neadCheck){
                			if(waitString!=null){
                				this.neadCheck = false;
                				synchronized (waitString) {                					
                					waitString.notify();                					                					
								}
                			} // end of null check
                		} // end of b==check
                	} // End of Inner For loop                	
   				} // End of For loop
                }// End of null check of checkBytes 
                //ystem.out.println();
    		}// END OF DATA AVAILABLE CASE                
         } // END OF SWITCH
		}catch(Exception e){
			logger.error(Constants.NSIO_ERROR_CODE_4 + ")!!!ERROR!!!- In the Event listener");
			logger.error("Exception", e);
			if(waitString!=null){
				synchronized (waitString) {
					this.neadCheck = false;
					waitString.notify();
				}
			}
		}

	}
}
