package com.javainsight.interfaces;

import java.io.IOException;
import java.util.Map;

public interface PropertiesReader<T> {

	public Map<String, Bean> loadPropertiesFile( ) throws IOException, InstantiationException,
															IllegalAccessException,
															ClassNotFoundException;	
	
}
