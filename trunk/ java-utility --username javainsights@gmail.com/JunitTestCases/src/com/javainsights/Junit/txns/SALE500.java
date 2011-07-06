package com.javainsights.Junit.txns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import com.javainsights.Junit.communication.Connection;
import com.javainsights.Junit.utils.ConfigDTO;
import com.javainsights.Junit.utils.LengthUtils;
import com.javainsights.Junit.utils.PrepareHeaders;
import com.javainsights.Junit.utils.PrepareLoginMessage;
import com.javainsights.Junit.utils.PrepareSale;

public class SALE500 extends TestCase implements Callable<Boolean> {
	
	private static int no_of_Threads = 5;
	private static final ExecutorService executorPool=Executors.newFixedThreadPool(no_of_Threads);
	private static int AC_COUNTER = 1;
	private static final String[] deviceIDs = {"27690340",
											   "24957913",
											   "12345678",
											   "24972539",
											   "24972537",
											   "24927137",
											   "27789833",
											   "27690343",
											   "27690346",
											   "12000000",
											   "24973673",
											   "28080521",
											   "24973582",
											   "24971408"};
	private static final String[] tables = {"50","51","52","53","54"};
	private static final String[] employees = {"1","1812","22","23","11"};
	private static int deviceIDCounter = 0;
	private static int tableCounter = 0;
	@Override
	public Boolean call() throws Exception {
		try {
			/*
			 * Send Perfect LOGIN REQUEST FIRST
			 */
			connection = new Connection();
			ConfigDTO dto = PrepareLoginMessage.getConfig();
			connection.initialize(dto.getHostname(),
								  dto.getPort(),
								  dto.getTimeOut(),
								  dto.getCertName(),
								  dto.getPassword());
			if(deviceIDCounter >= 13){
				deviceIDCounter = 0;
			}
			if(AC_COUNTER > 99){
				AC_COUNTER = 0;
			}
			if(tableCounter >= 5){
				tableCounter = 0;
			}
			String deviceID = deviceIDs[deviceIDCounter++];
			String ac = (AC_COUNTER++) + "";
			String table = tables[tableCounter++];
			String sessionCounter = Integer.toString((int)(Math.random()*1000));
			ac = Integer.parseInt(ac) <= 9 ? ("0" +ac + dto.getActivationCode()) : (ac + dto.getActivationCode());
			logIn = PrepareHeaders.getLoginHeader(ac, deviceID, sessionCounter) + "," + PrepareLoginMessage.getEmployeeNumber();
			//message = LengthUtils.getLength(","+ message) + "," + message;
			//logIn = PrepareSale.getLoginRequest();
			logIn = LengthUtils.getLength(","+ logIn) + "," + logIn;
			String response = connection.send(logIn);
			/*
			 * Get and Send GetTableRequest
			 */
			getTable = PrepareSale.getGetTableRequest(response, ac, deviceID, sessionCounter, table);
			getTable = LengthUtils.getLength(","+ getTable) + "," + getTable;
			response = connection.send(getTable);
			/*
			 * Edit Table Request
			 */
			editTable = PrepareSale.getEditTableRequest(response, false, false, ac, deviceID, sessionCounter);
			editTable = LengthUtils.getLength(","+ editTable) + "," + editTable;
			response = connection.send(editTable);
			/*
			 * Disconnect Request
			 */
			disconnect = PrepareSale.getDisconnectRequest(response, ac, deviceID, sessionCounter);
			disconnect = LengthUtils.getLength(","+ disconnect) + "," + disconnect;
			response = connection.send(disconnect);
			connection.dispose();
			//Thread.sleep(100);				
		} catch (RuntimeException e) {
			try {
				disconnect = PrepareHeaders.getDisconnectHeader();
				disconnect = LengthUtils.getLength("," + disconnect) + ","
						+ disconnect;
				connection.send(disconnect);
				e.printStackTrace();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	private String logIn = null;
	private String getTable = null;
	private String editTable = null;
	private String disconnect = null;
	private Connection connection = null;
	
	@Override
	protected void setUp() throws Exception {		
		super.setUp();
	}
	
	public void testSuccessSale() throws Exception{
		List<SALE500> txns = new ArrayList<SALE500>();
		for (int k = 0; k < 18; k++) {		
		for (int j = 0; j < 100; j++) {
			txns.clear();
			for(int i=0; i < no_of_Threads; i++){
				SALE500 txn = new SALE500();
				txns.add(txn);
			}
			deviceIDCounter = 0;
			//AC_COUNTER = 1;			
			executorPool.invokeAll(txns);
			ConfigDTO dto = PrepareLoginMessage.getConfig();
			System.err.println("###################################################################");
			System.err.println("                      Loop >"+j +"<>"+k+"<"+ dto.getSessionCounter()+"                       ");
			System.err.println("###################################################################");
			dto.increaseCounter();
			Thread.sleep(1000);
		}
		Thread.sleep(20000);
		}
	}

	

}
