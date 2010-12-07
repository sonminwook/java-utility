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
package com.javainsight.DTO;

import com.javainsight.utils.params.*;

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
