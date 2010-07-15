package com.GR.beans;

import org.apache.log4j.Level;

import com.GR.interfaces.Bean;

public class serverBean implements Bean{

	private String hostAddress=null;
	private int[] ports = null;
	private int READ_SIZE=0;
	private int WRITE_SIZE=0;
	private int REQ_POOL_SIZE;
	private int RES_POOL_SIZE;
	private Level loggingLevel = null;
	public String getHostAddress() {
		return hostAddress;
	}
	public void setHostAddress(String hostAddress) {
		this.hostAddress = hostAddress;
	}
	public int[] getPorts() {
		return ports;
	}
	public void setPorts(int[] ports) {
		this.ports = ports;
	}
	public int getREAD_SIZE() {
		return READ_SIZE;
	}
	public void setREAD_SIZE(int read_size) {
		READ_SIZE = read_size;
	}
	public int getWRITE_SIZE() {
		return WRITE_SIZE;
	}
	public void setWRITE_SIZE(int write_size) {
		WRITE_SIZE = write_size;
	}
	public int getREQ_POOL_SIZE() {
		return REQ_POOL_SIZE;
	}
	public void setREQ_POOL_SIZE(int req_pool_size) {
		REQ_POOL_SIZE = req_pool_size;
	}
	public int getRES_POOL_SIZE() {
		return RES_POOL_SIZE;
	}
	public void setRES_POOL_SIZE(int res_pool_size) {
		RES_POOL_SIZE = res_pool_size;
	}
	public Level getLoggingLevel() {
		return loggingLevel;
	}
	public void setLoggingLevel(Level loggingLevel) {
		this.loggingLevel = loggingLevel;
	}
}
