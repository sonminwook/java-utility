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


import com.jovialjava.DataOperation;
import com.jovialjava.DataResult;
import com.jovialjava.utils.HexToBytes;


/**
 * this is the key class and this is the interface between SerialCommunicator and
 * USER.
 * User will act as instrcutor and pass the instruciton to SerialCommunication and will
 * get the DATA Result in return.
 * @author sjain
 * @date 07-DEC-2010
 * @version 1.0
 */
public class Instruction {

	private DataOperation operation = null;
	private DataResult result = DataResult.FAILED;
	private String wait = new String("WAIT");
	private Integer timeOutInMilliSeconds = 100;
	private String response = null;
	private SerialConfiguration config = null;
	private Byte[] notifyingBytes = null;
	/*
	 * Setting Request
	 */
	private byte[] unsignRequestBytes = null;
	
	/**
	 * One needs to pass only one request at a time. It is always of
	 * best interest If unsigned byte data is send to Serial device.
	 * If one has unsigned data already, then one can call the method
	 * as - setRequest(null, byte data)
	 * If one doesn't have the data then he/she can call the method as
	 * setRequest(String data, null) - API will automatic convert it into
	 * HEX DUMP.
	 * @note - PLEASE CALL EITHER setHexRequest or setRequest method at a time.
	 * @param request
	 * @param unsignRequestBytes
	 */
	public void setRequest(String request, byte[] unsignRequestBytes){
		if(request == null || "".equals(request)){
			this.unsignRequestBytes = unsignRequestBytes;
		}else{
			this.unsignRequestBytes = HexToBytes.toUnsignedByte(HexToBytes.asciiToHex(request));
		}
	}
	/**
	 * If one has data in HEX form, then he/she can this method to set the
	 * data. API will convert it into unsigned HEX DUMP.
	 * @note - PLEASE CALL EITHER setHexRequest or setRequest method at a time.
	 * @param hexRequest
	 */
	public void setHexRequest(String hexRequest){
		this.unsignRequestBytes = HexToBytes.toUnsignedByte(hexRequest);
	}
	
	/**
	 * One can call this method in order to see how HEX DUMP of request looks alike.
	 * @return
	 */
	public byte[] getRequest(){
		return this.unsignRequestBytes;
	}
	
	/**
	 * In order to see which DATA OPERATION will be executed by Communicator.
	 * @return
	 */
	public DataOperation getOperation() {
		return operation;
	}
	
	/**
	 * In order to see the result of Operation completed by API
	 * @return
	 */
	public DataResult getResult() {
		return result;
	}
	/**
	 * In order to see the time for which API will wait for serial device to send data.
	 * @return
	 */
	public Integer getTimeOutMilliSecond() {
		return timeOutInMilliSeconds;
	}
	
	/**
	 * In order to get the response data. You will get data only if DataResult is "RESPONSE"
	 * @return
	 */
	public String getResponse() {
		return response;
	}
	
	/**
	 * In order to get the serial configuration used for Serial Communication.
	 * @return
	 */
	public SerialConfiguration getConfig() {
		return config;
	}
	
	/**
	 * In order to set the DATA OPeration to be completed by API
	 * @param operation
	 */
	public void setOperation(DataOperation operation) {
		this.operation = operation;
	}
	
	/**
	 * This method will be used internally by API to set the result of Serial communication.
	 * @param result
	 */
	public void setResult(DataResult result) {
		this.result = result;
	}
	
	/**
	 * In order to set the time used by API to wait for serial device to send the data.
	 * @param timeOutInSeconds
	 */
	public void setTimeOutInMilliSeconds(Integer timeOutInSeconds) {
		this.timeOutInMilliSeconds = timeOutInSeconds;
	}
	
	/**
	 * this method will be called internally by API to set the response received from serial device.
	 * @param response
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	
	/**
	 * This method must called by USER to set the serial configuration to be used by API for Serial communication.
	 * @param config
	 */
	public void setConfig(SerialConfiguration config) {
		this.config = config;
	}
    /**
     * This method will be used Internally
     * @return
     */
	public String getWait() {
		return wait;
	}
    
//	/** User needs to provide a String Object.
//	 * This has no function but it is MUST for normal working of API
//	 * and will help in differentiate amoung various instances of API.
//	 *
//	 */
//	public void setWait(String wait) {
//		this.wait = wait;
//	}


	/**
	 * This method will be used Internally.
	 * @return
	 */
	public Byte[] getNotifyingBytes() {
		return notifyingBytes;
	}
    
	/**
	 * Must called in conjugation with setDataOPeration(SEND_N_WAIT_FOR_NOTIFYING_BYTES),
	 * in this case API will wait untill either timeout happens or Serial device sends one of the notifying Bytes.
	 * @param notifyingBytes
	 */
	public void setNotifyingBytes(Byte... notifyingBytes) {
		this.notifyingBytes = notifyingBytes;
	}
}