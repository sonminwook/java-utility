package com.javainsight.interfaces;

import java.io.IOException;
import java.util.Map;

public interface PropHandler {

	void loadPropertiesFile(String location) throws IOException;
	
	void initialize() throws IllegalArgumentException, NumberFormatException;
	
	void addToSession(Map<String, Bean> map);
}
