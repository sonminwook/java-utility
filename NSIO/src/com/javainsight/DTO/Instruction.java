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

import java.util.concurrent.locks.Lock;
import com.javainsight.DataOperation;
import com.javainsight.DataResult;
import com.javainsight.utils.HexToBytes;

public class Instruction {

	private DataOperation operation = null;
	private DataResult result = DataResult.FAILED;
	private Lock locker = null;
	private String wait = null;
	private Integer timeOutInSeconds = 0;
	private String response = null;
	private SerialConfiguration config = null;
	private Byte[] notifyingBytes = null;
	/*
	 * Setting Request
	 */
	private byte[] unsignRequestBytes = null;
	
	public void setRequest(String request, byte[] unsignRequestBytes){
		if(request == null || "".equals(request)){
			this.unsignRequestBytes = unsignRequestBytes;
		}else{
			this.unsignRequestBytes = HexToBytes.toUnsignedByte(request);
		}
	}
	
	public void setHexRequest(String hexRequest){
		this.unsignRequestBytes = HexToBytes.toUnsignedByte(hexRequest);
	}
	
	public byte[] getRequest(){
		return this.unsignRequestBytes;
	}
	
	public DataOperation getOperation() {
		return operation;
	}
	public DataResult getResult() {
		return result;
	}
	public Lock getLocker() {
		return locker;
	}
	public Integer getTimeOutInSeconds() {
		return timeOutInSeconds;
	}
	public String getResponse() {
		return response;
	}
	public SerialConfiguration getConfig() {
		return config;
	}
	public void setOperation(DataOperation operation) {
		this.operation = operation;
	}
	public void setResult(DataResult result) {
		this.result = result;
	}
	public void setLocker(Lock locker) {
		this.locker = locker;
	}
	public void setTimeOutInMilliSeconds(Integer timeOutInSeconds) {
		this.timeOutInSeconds = timeOutInSeconds;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public void setConfig(SerialConfiguration config) {
		this.config = config;
	}

	public String getWait() {
		return wait;
	}

	public void setWait(String wait) {
		this.wait = wait;
	}

	public Byte[] getNotifyingBytes() {
		return notifyingBytes;
	}

	public void setNotifyingBytes(Byte... notifyingBytes) {
		this.notifyingBytes = notifyingBytes;
	}
}
