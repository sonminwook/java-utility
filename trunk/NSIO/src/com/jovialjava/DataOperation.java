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
/**
 * This class classify the type of operation this API can support.
 * @author Sunny Jain
 * @version 1.0
 * @date 07-December-2010
 */
public enum DataOperation {

	/**
	 * This Operation will Initialize the Serial device using the
	 * <code>SerialConfiguration</code> Object. It is a mandatory
	 * operation. One must Initialize the device before communicate
	 * anything.
	 * <code>DataResult.SUCCESS</code> - In case of success
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	INITIALIZE,
	/**
	 * This Operation will send the data to serial device and come
	 * back immediately without waiting for any response.
	 * <code>DataResult.SUCCESS</code> - In case of success
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	SEND,
	/**
	 * This Operation will send the data to serial device and wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends ACK (0x06) or NACK (0x15) before timeout it will come back
	 * with <code>DataResult.ACK</code> or <code>DataResult.NACK</code>.
	 * If serial device doesn't send any data or any data that doesn't contain ACK/NACK 
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	SEND_N_WAIT_FOR_ACK,
	/**
	 * This Operation will send the data to serial device and wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends ENQ (0x05) before timeout it will come back
	 * with <code>DataResult.ENQ</code>.
	 * If serial device doesn't send any data or any data other than ENQ
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	SEND_N_WAIT_FOR_ENQ,
	/**
	 * This Operation will send the data to serial device and wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends ETX (0x03) before timeout it will come back
	 * with <code>DataResult.RESPONSE</code>.
	 * If serial device doesn't send any data or any data doesn't contain ETX (0x03)
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	SEND_N_WAIT_FOR_ETX,
	/**
	 * This operation will do the same operation as <strong><code>SEND_N_WAIT_FOR_ETX</code></strong>
	 * but before starting the operation it will clear the previous response stored in Cache.
	 */
	CLR_SEND_N_WAIT_FOR_ETX,
	/**
	 * This operation will not send anything except wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends ETX (0x03) before timeout it will come back
	 * with <code>DataResult.RESPONSE</code>.
	 * If serial device doesn't send any data or any data doesn't contain ETX (0x03)
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	WAIT_FOR_ETX,
	/**
	 * This operation will send the data and wait for a specified 
	 * time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * After the specified time, It will be back with two possible results
	 * <code>DataResult.RESPONSE</code> - If some data has been received.
	 * <code>DataResult.NO_DATA</code> - If no data has been received.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	SEND_N_WAIT_FOR_DATA,
	/**
	 * This Operation will send the data to serial device and wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends any byte supplied by using <code>Instrcution.setNotifyingBytes(Byte... byts)</code>
	 * before timeout it will come back with <code>DataResult.RESPONSE</code>.
	 * If serial device doesn't send any data or any data that doesn't contain any byte 
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */	
	SEND_N_WAIT_FOR_NOTIFYING_BYTS,
	/**
	 * This operation will not send anything except wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends any byte supplied by using <code>Instrcution.setNotifyingBytes(Byte... byts)</code>
	 * before timeout it will come back with <code>DataResult.RESPONSE</code>.
	 * If serial device doesn't send any data or any data that doesn't contain any byte 
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	WAIT_FOR_NOTIFYING_BYTS,
	/**
	 * This Operation will clear the previous response cache and then send the data to serial device and wait 
	 * for a specified time supplied in <code>Instruction.setTimeOutInMilliSeconds</code>.
	 * If serial device sends any byte supplied by using <code>Instrcution.setNotifyingBytes(Byte... byts)</code>
	 * before timeout it will come back with <code>DataResult.RESPONSE</code>.
	 * If serial device doesn't send any data or any data that doesn't contain any byte 
	 * before timeout it will come back with <code>DataResult.NO_DATA</code>.
	 * If one wants to see what data has been recieved even in case of <code>DataResult.NO_DATA</code>
	 * try - <code>Instruction.getResponse()</code> method.
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */	
	CLR_SEND_N_WAIT_FOR_NOTIFYING_BYTS,
	/**
	 * This operation will not interact with serial device, instead
	 * it will just cleare the response cache.
	 * <code>DataResult.SUCCESS</code> - In case of success
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	CLR_PREVIOUS_RESPONSE,
	/**
	 * This operation will close all the communication link between
	 * JVM and SerialDevice. This is a mandatory Operation in otherwise
	 * sometime it corrupts the SerialDevice.
	 * Possible Output-
	 * <code>DataResult.SUCCESS</code> - In case of success
	 * <code>DataResult.FAILED</code> - In case of any exception
	 */
	CLOSE_PORT;
}
