package com.globalblue.DTO;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.globalblue.constants.Constant;

public class Authentication implements Serializable {

	public static final long serialVersionUID = 94230892308523L;
	
	private String uniqueOneIPlusNumber = null;
	private String uniqueSystemNumber = null;
	private Date oneIPlusExpiryDate = null;
	private Date lastECRInitDate = null;
	
	public String getUniqueOneIPlusNumber() {
		return uniqueOneIPlusNumber;
	}
	public String getUniqueSystemNumber() {
		return uniqueSystemNumber;
	}
	public Date getOneIPlusExpiryDate() {
		return oneIPlusExpiryDate;
	}
	public void setUniqueOneIPlusNumber(String uniqueOneIPlusNumber) {
		this.uniqueOneIPlusNumber = uniqueOneIPlusNumber;
	}
	public void setUniqueSystemNumber(String uniqueSystemNumber) {
		this.uniqueSystemNumber = uniqueSystemNumber;
	}
	public void setOneIPlusExpiryDate(Date oneIPlusExpiryDate) {
		this.oneIPlusExpiryDate = oneIPlusExpiryDate;
	}
	
	public String getInfo(){
		SimpleDateFormat formatter = new SimpleDateFormat(Constant.EXPIRY_DATE_FORMAT);
		return this.uniqueSystemNumber + ", "+ this.uniqueOneIPlusNumber+", "+ formatter.format(oneIPlusExpiryDate);
	}
	public Date getLastECRInitDate() {
		return lastECRInitDate;
	}
	public void setLastECRInitDate(Date lastECRInitDate) {
		this.lastECRInitDate = lastECRInitDate;
	}
}
