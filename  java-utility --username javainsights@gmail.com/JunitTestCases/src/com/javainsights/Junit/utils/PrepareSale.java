package com.javainsights.Junit.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javainsights.Junit.PATObjects.Tender;
import com.javainsights.Junit.constants.Constant;

public class PrepareSale {

	public static String getLoginRequest(){
		return PrepareLoginMessage.getPerfectLoginRequest();
	}
	
	public static String getGetTableRequest(String loginResponse){
		loginResponse = loginResponse.substring(1);
		System.out.println(loginResponse);
		if(loginResponse.matches(Constant.ERROR_REGEX)){
			throw new IllegalArgumentException("ERROR LOGIN RESPONSE -"+ loginResponse);
		}else if(loginResponse.matches(Constant.SUCCESS_LOGIN_WITHOUT_TABLE)){
			return PrepareHeaders.getGetTableHeader() + "," + PrepareLoginMessage.getConfig().getValidTableNumber();
		}else if(loginResponse.matches(Constant.SUCCESS_LOGIN_WITH_TABLE)){
			String tableNum = getTableNumbers(loginResponse);
			return PrepareHeaders.getGetTableHeader() + "," + tableNum;
		}else{
			throw new IllegalArgumentException("INVALID RESPONSE -"+ loginResponse);
		}
	}
	
	public static String getEditTableRequest(String getTableResponse, boolean isTip, boolean isDCC){
		getTableResponse = getTableResponse.substring(1);
		if(getTableResponse.matches(Constant.ERROR_REGEX)){
			throw new IllegalArgumentException("ERROR GET TABLE RESPONSE -"+ getTableResponse);
		}
		else if(getTableResponse.matches(Constant.SUCCESS_GET_TABLE)){
			return PrepareHeaders.getEditTableHeader() + "," + "ADD," + getClearedTable(getTableResponse, isTip, isDCC, 10);
		}		
		else{
			throw new IllegalArgumentException("INVALID RESPONSE -"+ getTableResponse);
		}
	}
	
	public static String getEditTableRequest(String getTableResponse, boolean isTip, boolean isDCC, int split){
		getTableResponse = getTableResponse.substring(1);
		if(getTableResponse.matches(Constant.ERROR_REGEX)){
			throw new IllegalArgumentException("ERROR GET TABLE RESPONSE -"+ getTableResponse);
		}
		else if(getTableResponse.matches(Constant.SUCCESS_GET_TABLE)){
			return PrepareHeaders.getEditTableHeader() + "," + "ADD," + getClearedTable(getTableResponse, isTip, isDCC, split);
		}		
		else{
			throw new IllegalArgumentException("INVALID RESPONSE -"+ getTableResponse);
		}
	}
	
	public static String getDisconnectRequest(String editTableResponse){
		editTableResponse = editTableResponse.substring(1);
		if(editTableResponse.matches(Constant.ERROR_REGEX)){
			throw new IllegalArgumentException("ERROR EDIT TABLE RESPONSE -"+ editTableResponse);
		}
		else if(editTableResponse.matches(Constant.SUCCESS_EDIT_TABLE)){
			return PrepareHeaders.getDisconnectHeader();
		}		
		else{
			throw new IllegalArgumentException("INVALID RESPONSE -"+ editTableResponse);
		}
	}	
	
	
	public static void main(String... args){
		System.out.println(getEditTableRequest(",1.0,T,P,123456,13141231,9812371,XXXXX-XXXXX-XXXXX-XXXXX,0,21,5,"+
			"{5##PENDING#1110000#"+
				"{120##01#1233300#"+
					"{1#Y#CREDIT#01#53#541801XXXXXX0004#2012#06031#" +
						"GBP#550#0#0#MC#53###01#N#550#GBP####1#90003400#03789967#90003400#DISCLAIMER#000000#000000}" +
				"#}" +
				"{120##01#1233300#"+
					"{1#Y#CREDIT#01#53#541801XXXXXX0004#2012#06031#" +
							"GBP#550#0#0#MC#53###01#N#550#GBP####1#90003400#03789967#90003400#DISCLAIMER#000000#000000}" +
				"#}" +
				"}" +
				"{6##PENDING#1110000#"+
				"{120##01#1233300#"+
					"{1#Y#CREDIT#01#53#541801XXXXXX0004#2012#06031#" +
						"GBP#550#0#0#MC#53###01#N#550#GBP####1#90003400#03789967#90003400#DISCLAIMER#000000#000000}" +
				"#}" +
				"{120##01#1233300#"+
					"{1#Y#CREDIT#01#53#541801XXXXXX0004#2012#06031#" +
							"GBP#550#0#0#MC#53###01#N#550#GBP####1#90003400#03789967#90003400#DISCLAIMER#000000#000000}" +
				"#}" +
				"}", false, false));
		
		System.out.println(getGetTableRequest(",1.0,E,P,1234567890,TID123,MID123,XXXXX-XXXXX-XXXXX-XXXXX,0,1"));
		
		System.out.println(getDisconnectRequest(",1.0,M,P,1234567890,TID123,MID123,XXXXX-XXXXX-XXXXX-XXXXX,0,1," +
				"{15##COMPLETED#0#" +
							"{1001##01#0##" +
									"ICAgICAgIE1pY3JvcyBEZW1vIFN5c3RlbSAgICAgICAjICAgICAgTUlDUk9TIFN5c3RlbSwgSW5jLiAgICAgICAjICA3MDMxIENvbHVtYmlhIEdhdGV3YXkgRHJpdmUgICAjICAgICAgIENvbHVtYmlhLCBNRCAyMTA0NiAgICAgICAjICAgICAgICAgIDQ0My0yODUtNjAwMCAgICAgICAgICAjICAgICAgICAgd3d3Lm1pY3Jvcy5jb20gICMgMTAzIFJhY2hlbCMtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLSNUYmwgNTEvMSAgICBDaGsgMTAwMSAgICAgIEdzdCAgMSMgICAgICAgIEphbjI3JzExIDExOjQxQU0gICAgICAgICMtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLSNEZWxheWVkIE9yZGVyICAgSmFuMjcnMTEgMTI6MDlQTSMgIDEgU3BlY2lhbCAjMiAgICAgICAgICA0MC4wMCMgICAxMDAwNDE2MzAxMDI3MDAwMDQ2IyAgICBWaXNhICAgICAgICAgIDQwLjAwIyAj" +
										"ICAgIEZvb2QgICAgICAgICAgICAgICAgNDAuMDAjICAgIFBheW1lbnQgICAgICAgICAgICAgNTAwMCMtLS0tMTAzIENoZWNrIENsb3NlZCAxMjowM1BNLS0tLSM=}}"));
	}
	
	private static String getTableNumbers(String loginResponse){
		Pattern pattern = Pattern.compile(Constant.SUCCESS_LOGIN_WITHOUT_TABLE);
		Matcher match = pattern.matcher(loginResponse);
		
		if(match.find()){
			int length = match.group().length()+1;
			String tableDatas = loginResponse.substring(length);
			System.out.println(tableDatas);
			String[] tables = tableDatas.split(Constant.TABLE_NUMBER_SPLITTER);						
			return tables[0].substring(1);
		}
		return null;
	}
	
	private static String getClearedTable(String getTableResponse, boolean isTip, boolean isDCC, int split){
		Pattern pattern = Pattern.compile(Constant.HEADER_REGEX);
		Matcher match = pattern.matcher(getTableResponse);
		String tableNumber = null;
		String checkNumber = null;
		String amount = null;
		String newAmt = null;
		if(match.find()){
			int length = match.group().length();
			String tableDatas = getTableResponse.substring(length);			
			tableNumber = tableDatas.split(",")[0];
			
			String table = tableDatas;			
			amount = getAmount(table.substring(3));
			checkNumber = table.substring(table.indexOf("{", 4)+1, table.indexOf("#",table.indexOf("{", 4)));			
			newAmt = Integer.toString(Integer.parseInt(amount)/split);
			System.out.println(tableNumber + " -- > " + checkNumber + " ---> " + amount + " ---> "+ newAmt);
			
		}
		if(!isTip){
			if(!isDCC){
				String clearedTable = "{"+ tableNumber+ "##COMPLETED#0#" +
							  "{"+ checkNumber+ "##01#0#" +
							  	Tender.sale_1_Tender_no_dcc_no_tip(newAmt)+"#" +
							  "}" +
							  "}";
				return clearedTable;
			}else{
				String clearedTable = "{"+ tableNumber+ "##COMPLETED#0#" +
				  "{"+ checkNumber+ "##01#0#" +
				  	Tender.sale_1_Tender_dcc_no_tip(newAmt)+"#" +
				  "}" +
				  "}";
				return clearedTable;
			}
		}else{
			if(!isDCC){
				String clearedTable = "{"+ tableNumber+ "##COMPLETED#0#" +
									"{"+ checkNumber+ "##01#0#" +
									Tender.sale_1_Tender_no_dcc_with_tip(newAmt)+"#" +
									"}" +
									"}";
				return clearedTable;
			}else{
				String clearedTable = "{"+ tableNumber+ "##COMPLETED#0#" +
										"{"+ checkNumber+ "##01#0#" +
										Tender.sale_1_Tender_dcc_with_tip(newAmt)+"#" +
										"}" +
									"}";
				return clearedTable;
			}
		}
	}
	
	
	
	private static String getAmount(String table){
		String[] data = table.split("#");
		return data[3];
	}
	
}
