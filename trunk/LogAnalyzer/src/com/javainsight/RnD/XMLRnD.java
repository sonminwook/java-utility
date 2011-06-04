package com.javainsight.RnD;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class XMLRnD {
	
	public static void main(String[] args) {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File("config/test.properties")));
			prop.storeToXML(new FileOutputStream(new File("config/test.xml")), "XML EQUIV File", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
