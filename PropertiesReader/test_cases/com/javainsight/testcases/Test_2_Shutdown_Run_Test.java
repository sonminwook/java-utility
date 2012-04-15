package com.javainsight.testcases;

import org.apache.log4j.PropertyConfigurator;

import com.javainsight.reader.PropReader;

public class Test_2_Shutdown_Run_Test {
	
	public static void main(String[] args) {
		try{
			PropertyConfigurator.configure("config/log4j.properties");
			PropReader reader = new PropReader("config", 15);
			reader.returnMapValue();
			Thread.sleep(10000);
			reader.shutDown();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
