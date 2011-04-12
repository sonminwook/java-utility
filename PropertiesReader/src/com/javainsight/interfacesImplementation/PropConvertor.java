package com.javainsight.interfacesImplementation;

import java.io.IOException;
import com.javainsight.enums.ConfigurationHelper;
import com.javainsight.interfaces.PropHandler;

public abstract class PropConvertor implements PropHandler {
	
	protected ConfigurationHelper helper = new ConfigurationHelper();
	protected String keyName = null;	
	

	@Override
	public void loadPropertiesFile(String location) throws IOException {
		helper.loadPropertiesFile(location);
		keyName = location.substring(location.lastIndexOf("\\")+1);

	}

}
