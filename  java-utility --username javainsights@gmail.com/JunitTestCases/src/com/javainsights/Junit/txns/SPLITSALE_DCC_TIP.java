package com.javainsights.Junit.txns;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareSale;

import junit.framework.TestCase;

public class SPLITSALE_DCC_TIP extends TestCase {
	
	private String logIn = null;
	private String getTable = null;
	private String editTable = null;
	private String disconnect = null;
	private Connection connection = null;

	protected void setUp() throws Exception {		
		connection = new Connection();
		ConfigDTO dto = PrepareLoginMessage.getConfig();
		connection.initialize(dto.getHostname(),
							  dto.getPort(),
							  dto.getTimeOut(),
							  dto.getCertName(),
							  dto.getPassword());
		super.setUp();
	}
	
	public void test2Split_No_Tip_and_Tip() throws Exception{		
		Thread.sleep((int)Math.abs(Math.random()*5000));
		/*
		 * Send Perfect LOGIN REQUEST FIRST
		 */
		logIn = PrepareSale.getLoginRequest();
		logIn = LengthUtils.getLength(","+ logIn) + "," + logIn;
		String response = connection.send(logIn);
		/*
		 * Get and Send GetTableRequest
		 */
		getTable = PrepareSale.getGetTableRequest(response);
		getTable = LengthUtils.getLength(","+ getTable) + "," + getTable;
		response = connection.send(getTable);
		/*
		 * Edit Table Request
		 */
		editTable = PrepareSale.getEditTableRequest(response, false, false, 50);
		editTable = LengthUtils.getLength(","+ editTable) + "," + editTable;
		String firstResponse = connection.send(editTable);
		
		editTable = PrepareSale.getEditTableRequest(response, true, false, 1); 
		editTable = LengthUtils.getLength(","+ editTable) + "," + editTable;
		String secondResponse = connection.send(editTable);
		/*
		 * Disconnect Request
		 */
		disconnect = PrepareSale.getDisconnectRequest(firstResponse);
		disconnect = PrepareSale.getDisconnectRequest(secondResponse);
		disconnect = LengthUtils.getLength(","+ disconnect) + "," + disconnect;
		response = connection.send(disconnect);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
