package com.javainsights.Junit.txns;

import junit.framework.TestCase;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareSale;

public class SALE500 extends TestCase {
	
	private String logIn = null;
	private String getTable = null;
	private String editTable = null;
	private String disconnect = null;
	private Connection connection = null;
	
	@Override
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
	
	public void testSuccessSale() throws Exception{
		for(int i=0; i<1; i++){
			try {
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
				editTable = PrepareSale.getEditTableRequest(response, false, false);
				editTable = LengthUtils.getLength(","+ editTable) + "," + editTable;
				response = connection.send(editTable);
				/*
				 * Disconnect Request
				 */
				disconnect = PrepareSale.getDisconnectRequest(response);
				disconnect = LengthUtils.getLength(","+ disconnect) + "," + disconnect;
				response = connection.send(disconnect);
				Thread.sleep(1000);
				//this.setUp();
			} catch (RuntimeException e) {
				//disconnect = PrepareHeaders.getDisconnectHeader();
				//disconnect = LengthUtils.getLength(","+ disconnect) + "," + disconnect;
				//connection.send(disconnect);
				e.printStackTrace();
			}
		}
	}

	

}
