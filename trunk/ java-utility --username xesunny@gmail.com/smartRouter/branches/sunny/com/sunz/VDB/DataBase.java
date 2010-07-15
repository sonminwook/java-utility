package com.sunz.VDB;

import java.io.IOException;
import java.util.Map;
import java.sql.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.GR.interfaces.Bean;
import com.GR.interfaces.PropertiesReader;
import com.GR.reader.PropReader;

import com.GR.beans.*;
import com.sunz.utils.Connect;
import com.sunz.utils.TempECRMap;

public class DataBase {
	
	private DBBean dbBean = null;
	private TxTablesBean txTableBean = null;
	
	private static Logger logger = Logger.getLogger(DataBase.class);
	
	public static void main(String[] args) throws Exception{
		PropertyConfigurator.configure("config/log4j.properties");
		DataBase db = new DataBase();
		/*STEP-1 : Read all the reequired properties file and get the Bean Objects */
		db._init();
		
		/*STEP-2 : Create Vitrual Database and Get a connection object referring to it */
		Connection conn = Connect.getConnection(db.dbBean);
		
		/*STEP-3 : Create an Table*/
		Statement stmt = conn.createStatement();
		stmt.execute(db.txTableBean.getTB_ECRINIT_REQ());
		
		/*STEP-4 : Insert Data into Table */
		ECRInitBean ecrReq = new TempECRMap().getECRInitBean();
		String insert = "Insert into TB_ECRINIT_REQ values(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(insert);
		pstmt.setString(1, ecrReq.getPOSID());
		pstmt.setString(2, ecrReq.getTID());
		pstmt.setString(3, ecrReq.getUseACK());
		pstmt.setString(4, ecrReq.getUseLRC());
		pstmt.setString(5, ecrReq.getMagCard());
		pstmt.setString(6, ecrReq.getEMVCard());
		pstmt.setInt(7, ecrReq.getDecimals());
		pstmt.setString(8, ecrReq.getPrompt());
		pstmt.setString(9, ecrReq.getTermPrint());
		pstmt.setString(10, ecrReq.getDCC());
		pstmt.setString(11, ecrReq.getTFS());
		pstmt.setString(12, ecrReq.getActivationCode());
		logger.info("Record inserted -["+ pstmt.executeUpdate()+"]");
		//conn.commit();
		
		/* Step-5 : Fetch Data from DataBase */
		ResultSet rs = stmt.executeQuery("SELECT * FROM TB_ECRINIT_REQ");
		db.printResultSet(rs);
		
		Connect.dropDataBase(db.dbBean);
	}
	
	private void _init() throws IOException, InstantiationException,
											IllegalAccessException,
											ClassNotFoundException{
		PropertiesReader<Object> propReader = new PropReader();
		Map<String, Bean> map = propReader.loadPropertiesFile("config/PropertiesFileList.properties");
		this.dbBean = (DBBean)map.get("DB.properties");
		this.txTableBean = (TxTablesBean)map.get("TxTables.properties");
		System.out.println(this.txTableBean.getTB_ECRINIT_REQ());
		
	}
	
	private void printResultSet(ResultSet rs) throws SQLException{
		 ResultSetMetaData md = rs.getMetaData();
		 int numOfColumn = md.getColumnCount();
		 while(rs.next()){		 
			 for(int i=1; i<= numOfColumn; i++){
				 System.out.print(rs.getString(i)+ "  ");
			 }
			System.out.println();
		 }
	 }
	
}
