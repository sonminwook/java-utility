package com.javainsight.testcases;

import org.apache.log4j.PropertyConfigurator;

import com.javainsight.reader.PropReader;

public class Test_3_Offline_Test {
	
	public static void main(String[] args) {
		try{
			PropertyConfigurator.configure("config/log4j.properties");
			PropReader reader = new PropReader("config", 15);
			reader.returnMapValue();
			Thread.sleep(120000);
			reader.shutDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
