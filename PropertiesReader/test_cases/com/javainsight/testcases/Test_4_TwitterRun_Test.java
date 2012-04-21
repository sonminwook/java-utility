package com.javainsight.testcases;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import com.javainsight.reader.PropReader;

public class Test_4_TwitterRun_Test {

	public static void main(String[] args) {
		try {
			File file = new File("config/Weirdoo");
			file.createNewFile();
			
			PropertyConfigurator.configure("config/log4j.properties");
			PropReader reader = new PropReader("config", 2);
			reader.returnMapValue();
			Thread.sleep(1200000);
			reader.shutDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
