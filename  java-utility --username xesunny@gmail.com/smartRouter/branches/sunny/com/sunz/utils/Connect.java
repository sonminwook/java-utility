package com.sunz.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.GR.beans.DBBean;

import org.apache.log4j.Logger;

public class Connect {
	
	private static Logger logger = Logger.getLogger(Connect.class);
	
	/**
	 * This method will create the database and will return the connection pointing to that database
	 * @param dbBean - It is a POJO containing all the required parameter of Database.
	 * @return - Connection object pointing to Virtual Database
	 * @throws SQLException - In case of any problem while creating Database.
	 */
	public static Connection getConnection(DBBean dbBean) throws SQLException{
		Connection conn = null;
		try{
			Properties tempProp = new Properties();
			tempProp.put("user", dbBean.getUser());
			tempProp.put("password", dbBean.getPassword());
			String url = dbBean.getDefaultProtocol()+ dbBean.getSubprotocol() + dbBean.getDbName()
            + ";create="+dbBean.isCreate();			
			logger.debug("START URL -" + url);
			
			conn = DriverManager.getConnection(url , tempProp);
			conn.setAutoCommit(dbBean.isAutoCommit());
			
		}catch(SQLException sqle){
			logger.error(" !!! PROBLEM !!! - While creating Connection to Database ");
			printSQLException(sqle);			
			 try {
	             if (conn != null) {
	                 conn.close();
	                 conn = null;
	             }
	         } catch (SQLException sql) {
	        	 logger.error(" !!! PROBLEM !!! - While closing connection object ");
	             printSQLException(sql);
	         }
	         
			throw sqle;
		}		
		logger.info("Database["+dbBean.getDbName() +"] created and started in ["+dbBean.getDefaultFramework()+"] mode");
		
		return conn;
	}
	
	private static void printSQLException(SQLException sqle){
		logger.error(" !!! DETAILS !!! - SQL STATE ["+sqle.getSQLState()+"] " +
				"SQL ERROR CODE -["+sqle.getErrorCode()+"]");
		logger.error(" !!! MESSAGE !!! - "+ sqle.getMessage());	
	}

	/**
	 * This method will shut down the virtual database specified in dbBean.
	 */
	public static void dropDataBase(DBBean dbBean){
		 if (dbBean.getDefaultFramework().equals("embedded"))
         {
			try
             {
                 // the shutdown=true attribute shuts down Derby
            	 String url = dbBean.getDefaultProtocol()+dbBean.getSubprotocol()+dbBean.getDbName()+";shutdown="+dbBean.isShutDown();
            	 logger.debug("SHUT DOWN URL -" + url);
                
            	 DriverManager.getConnection(url);

                 // To shut down a specific database only, but keep the
                 // engine running (for example for connecting to other
                 // databases), specify a database in the connection URL:
                 //DriverManager.getConnection("jdbc:derby:" + dbName + ";shutdown=true");
                 
             }
             catch (SQLException se)
             {
                 if (( (se.getErrorCode() == 45000)
                         && ("08006".equals(se.getSQLState()) ))) {
                     // we got the expected exception
                	 logger.info("Database["+dbBean.getDbName() +"] shut down normally");
                     // Note that for single database shutdown, the expected
                     // SQL state is "08006", and the error code is 45000.
                	 //for others errorCode = 50000 and SQL STATE is XJ015
                 } else {
                     // if the error code or SQLState is different, we have
                     // an unexpected exception (shutdown failed)
                     logger.error("!!! PROBLEM !!! - "+"Database["+dbBean.getDbName() +"] did not shut down normally");
                     printSQLException(se);
                 }
             }
         }else{
        	 logger.info("Framework ["+dbBean.getDefaultFramework()+ "] is not embedded, Database was not shut down");
         }
	}
}
