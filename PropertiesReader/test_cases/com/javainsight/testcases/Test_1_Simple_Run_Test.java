package com.javainsight.testcases;

import org.apache.log4j.PropertyConfigurator;

import com.javainsight.reader.PropReader;

public class Test_1_Simple_Run_Test {
	
	public static void main(String[] args) {
		try{
			PropertyConfigurator.configure("config/log4j.properties");
			PropReader reader = new PropReader("config", 15);
			reader.returnMapValue();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
