package com.gr.oneInterface.DTO;

import com.gr.oneInterface.utils.params.*;

public class SerialConfiguration {

	//==========Variable strictly used by internally==========
	private Baud baudRate = null;
	private Parity parityBit = null;
	private StopBits stop = null;
	private DataBits data = null;
	private FlowControl flow = null;
	private String comPort = null;
	private int inputBufferSize = 0;
	private int outputBufferSize = 0;
	
	
	/*
	 * Default values to be used in case if a terminal doesn't
	 * have any specific requirement.
	 */
	public SerialConfiguration(){
		this.baudRate = Baud.BAUD_9600;
		this.parityBit = Parity.NONE;
		this.stop = StopBits.NONE;
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
