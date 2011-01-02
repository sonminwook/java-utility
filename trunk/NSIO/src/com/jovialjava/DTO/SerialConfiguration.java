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
package com.jovialjava.DTO;

import com.jovialjava.utils.params.*;

public class SerialConfiguration {

	//==========Variable strictly used by internally==========
	private Baud baudRate = null;
	private Parity parityBit = null;
	private StopBits stop = null;
	private DataBits data = null;
	private FlowControl flow = null;
	private String comPort = null;
	private int inputBufferSize = 1024;
	private int outputBufferSize = 1024;
	
	
	/*
	 * Default values to be used in case if a terminal doesn't
	 * have any specific requirement.
	 */
	public SerialConfiguration(){
		this.baudRate = Baud.BAUD_9600;
		this.parityBit = Parity.NONE;
		this.stop = StopBits.ONE;
		this.flow = FlowControl.NONE;
		this.data = DataBits.EIGHT;
		this.comPort = "COM1";
	}
	
	public Baud getBaud() {
		return baudRate;
	}
	public Parity getParity() {
		return parityBit;
	}
	public StopBits getStopBits() {
		return stop;
	}
	public DataBits getDataBits() {
		return data;
	}
	public FlowControl getFlowControl() {
		return flow;
	}
	public String getComPort() {
		return comPort;
	}
	public int getInputBufferSize() {
		return inputBufferSize;
	}

	public int getOutputBufferSize() {
		return outputBufferSize;
	}

	
	/*
	 * Setter method starts here..........
	 */
	public void setBaud(String baud) {		
		this.baudRate = Baud.parse(baud);
	}
	public void setParity(String parity) {
		this.parityBit = Parity.parse(parity);
	}
	public void setStopBits(String stopBits) {
		this.stop = StopBits.parse(stopBits);
	}
	public void setDataBits(String dataBits) {
		this.data = DataBits.parse(dataBits);		
	}
	public void setFlowControl(String flowControl) {
		this.flow = FlowControl.parse(flowControl);
	}
	public void setComPort(String comPort) {
		this.comPort = comPort;
	}
	public void setInputBufferSize(String inputBufferSize) {
		try{
			this.inputBufferSize = Integer.parseInt(inputBufferSize);
		}catch(Exception e){
			this.inputBufferSize = 1024;
		}
	}

	public void setOutputBufferSize(String outputBufferSize) {
		try{
			this.outputBufferSize = Integer.parseInt(outputBufferSize);
		}catch(Exception e){
			this.outputBufferSize = 1024;
		}
	}
}
