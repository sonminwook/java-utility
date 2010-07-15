package com.sunz.dto;

import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TCPIPRequest {
	
	private AtomicInteger requestNumber = new AtomicInteger(0);
	private ConcurrentHashMap<Integer, String> requestMap = new ConcurrentHashMap<Integer, String>();
	private ConcurrentHashMap<Integer, String> responseMap = new ConcurrentHashMap<Integer, String>();
	//private Set<Integer> sortedRequestSet = new TreeSet<Integer>(requestMap.keySet());
	private TreeSet<Integer> sortedResponseSet = new TreeSet<Integer>(responseMap.keySet());
	//private String response =null;
	private boolean isConnected = true;
	private String identityString=null;
	
	public String getRequest() {
		String request = requestMap.get(requestNumber.get());
		//requestNumber.decrementAndGet();
		return request;
	}
	
	public void setRequest(String request) {
		requestMap.putIfAbsent(requestNumber.incrementAndGet(), request);		
	}
	
	public int getRequestNumber(){
		return requestNumber.get();
	}
	
	public String getResponse( ) {
		String response = null;
		try {
			response = responseMap.get(sortedResponseSet.first());
			responseMap.remove(sortedResponseSet.first());
			requestMap.remove(sortedResponseSet.first());
			sortedResponseSet.remove(sortedResponseSet.first());			
			if(requestMap.size() == 0){
				requestNumber.set(0);
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;		
	}
	
	
	public void setResponse(String response, Integer responseNumber) {
		sortedResponseSet.add(responseNumber);
		this.responseMap.putIfAbsent(responseNumber, response);		
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public String getIdentityString() {
		return identityString;
	}
	public void setIdentityString(String identityString) {
		this.identityString = identityString;
	}
	

}
