package com.sunz.test;

import java.util.Map;

import com.GR.beans.sunnyBean;
import com.GR.interfaces.Bean;
import com.GR.interfaces.PropertiesReader;
import com.GR.reader.PropReader;

public class Test {

	private static Map<String, Bean> POJOMap = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		PropertiesReader<Object> prop = new PropReader();
			readLa(prop);
			//------------- GET LOCK FOR EACH TXN TO AVOID CONCURRENT ACCESS-----------
			for(int i=0; i<8;i++){
			prop.getLock().lock();
			try{
				System.out.println(":::::::::::::::::::::::::::::::::::::::::::::::::::");
				System.out.format("Waiting for %s seconds to let the txn flow completed\n", i*7);
				Thread.sleep(7000);
				sunnyBean sunz = (sunnyBean)POJOMap.get("sunny.properties");
				System.out.println(sunz.getName());
				System.out.println("txn flow completed");
			}finally{
				prop.getLock().unlock();
			}
			}
	}
	
	private static void readLa(PropertiesReader<Object> prop) throws Exception{
		prop.getLock().lock();
		System.out.println("Got the lock");
		try{
			Map<String, Bean> beanMap = prop.loadPropertiesFile("bin/properties/PropertiesFileList.properties");
			POJOMap = beanMap;
			System.out.println(beanMap.keySet());
			System.out.println(POJOMap.keySet());
		}finally{
			prop.getLock().unlock();
			System.out.println("Unlock the lock");
		}
	}

}
