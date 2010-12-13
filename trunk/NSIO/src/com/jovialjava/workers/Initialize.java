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
package com.jovialjava.workers;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.util.Stack;
import java.util.concurrent.Callable;

import com.jovialjava.DTO.SerialConfiguration;
import com.jovialjava.exceptions.RS232Exception;
import com.jovialjava.utils.params.Constants;

public class Initialize implements Callable<Boolean> {
	
	//private static Logger logger = Logger.getLogger(Initialize.class);
	private SerialConfiguration config = null;
	private SerialPort serialPort = null;
	private Stack<SerialPort> serialPortQueue = null;
	
	public Initialize(SerialConfiguration config,
					Stack<SerialPort> seriaStack){
		this.config = config;
		this.serialPortQueue= seriaStack;
	}

	@Override
	public Boolean call() throws RS232Exception{
		CommPortIdentifier portIdentifier = null;
		try{
			/*
			 * Step 1: Getting a port Identified for COM PORT
			 */			
			portIdentifier = CommPortIdentifier.getPortIdentifier(this.config.getComPort());
			/*
			 * Step 2: Check if Port is already occupied by some other process.
			 */
			if(portIdentifier.isCurrentlyOwned()){
				String additionMSG = "\r\n"+this.config.getComPort()+ " Port in already in use, owner ["+ portIdentifier.getCurrentOwner()+"]";
				throw new RS232Exception(Constants.INIT_ERROR_CODE_4, Constants.INIT_ERR_MSG + additionMSG);
			}
			/*
			 * Step 3: Open the port.
			 * Opens the communications port. open obtains exclusive ownership of the port.
			 * If the port is owned by some other application, a PORT_OWNERSHIP_REQUESTED event is 
			 * propagated using the CommPortOwnershipListener event mechanism.
			 * If the application that owns the port calls close during the event processing,
			 * then this open will succeed. There is one InputStream and one OutputStream associated with each port.
			 * After a port is opened with open, then all calls to getInputStream will 
			 * return the same stream object until close is called. 
			 * appname - Name of application making this call. This name will become the owner of the port. Useful when resolving ownership contention.
    		 * timeout - Time in milliseconds to block waiting for port open. 
			 */
			this.serialPort = (SerialPort)portIdentifier.open("JavaInsight", 5000);
			/*
			 * Step 4: Setting up serial Parameters.
			 * TODO : Invalid param Entry check here
			 */			
			this.serialPort.setSerialPortParams(this.config.getBaud().getValue(),
													this.config.getDataBits().getValue(),
													this.config.getStopBits().getValue(),
													this.config.getParity().getValue());
			this.serialPort.setFlowControlMode(this.config.getFlowControl().getValue());
			
			/*
			System.out.println("Serial Communication Params : \r\nCOMPORT ["+ this.config.getComPort()
													+"] -\r\n Baud ["+ this.serialPort.getBaudRate()
													+"]\r\n DataBits ["+ this.serialPort.getDataBits()
													+"]\r\n StopBits ["+ this.serialPort.getStopBits()
													+"]\r\n Parity ["+ this.serialPort.getParity()
													+"]\r\n FlowControl ["+ this.serialPort.getFlowControlMode()+"]"); 
			*//*
			 * Step 5: Setting up input and output buffer size.
			 */
			this.serialPort.setInputBufferSize(this.config.getInputBufferSize());
			this.serialPort.setOutputBufferSize(this.config.getOutputBufferSize());
			this.serialPort.setDTR(true);
			//this.serialPort.setRTS(true);
			this.serialPortQueue.push(this.serialPort);
		}catch(PortInUseException e){
			String additionMSG = "\r\n"+this.config.getComPort()+ " Port in already in use, owner ["+ portIdentifier.getCurrentOwner()+"]";
			throw new RS232Exception(Constants.INIT_ERROR_CODE_4, Constants.INIT_ERR_MSG + additionMSG);
		}catch(UnsupportedCommOperationException e){
			String config = "BAUD["+this.config.getBaud().getValue()+"]DATABITS["+this.config.getDataBits().getValue()+"]STOPBITS["+this.config.getStopBits().getValue()+"]PARITY["+
							this.config.getParity().getValue()+"]FLOW CONTROL["+this.config.getFlowControl().getValue()+"]";			
			String additionMSG = "\r\n CONFIG " + config+" is NOT SUPPORTED";
			throw new RS232Exception(Constants.INIT_ERROR_CODE_4, Constants.INIT_ERR_MSG+ additionMSG, e);	
		}catch(NoSuchPortException e){
			String additionMSG = "\r\n" + this.config.getComPort()+ " DOESN'T EXIST";
			throw new RS232Exception(Constants.INIT_ERROR_CODE_4, Constants.INIT_ERR_MSG+ additionMSG, e);		
		}catch(RS232Exception e){ throw e; }
		 catch(Exception e){			
			String additionMSG = "\r\n" + this.config.getComPort()+ " Port failed to Initialize";
			throw new RS232Exception(Constants.INIT_ERROR_CODE_4, Constants.INIT_ERR_MSG+ additionMSG, e);
		}
		return true;
	}

}
