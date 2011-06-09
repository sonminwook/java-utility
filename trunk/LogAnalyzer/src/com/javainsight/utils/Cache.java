package com.javainsight.utils;

import java.util.Map;

import com.javainsight.constants.Constants;
import com.javainsight.interfaces.Bean;
import com.javainsight.reader.PropReader;

public class Cache {

	private static Map<String, Bean> jCache = null;
	
	public synchronized static Bean getBean(String keyName){
		if(jCache == null){
			PropReader p = new PropReader(Constants.DIRECTORY, Constants.jCACHE_UPDATE_INTERVAL);
			jCache = p.returnMapValue();			
		}
		return jCache.get(keyName);		
	}
}
