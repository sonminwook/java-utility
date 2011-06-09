package com.javainsight.handler;

import java.util.Map;

import com.javainsight.beans.FileSearchBean;
import com.javainsight.enums.Delimiter;
import com.javainsight.interfaces.Bean;
import com.javainsight.interfacesImplementation.PropConvertor;

public class FileSearchHandler extends PropConvertor {
	
	private FileSearchBean myBean = new FileSearchBean();

	@Override
	public void addToSession(Map<String, Bean> map) {
		map.put(this.keyName, this.myBean);
	}

	@Override
	public void initialize() throws IllegalArgumentException,
			NumberFormatException {
		String[] fileNames = this.helper.getStringArrayValue("FileNamesRegex", null, ".*", Delimiter.PERCENTAGE);
		this.myBean.setFileNamesRegex(fileNames);
		this.myBean.setCutOffDate(this.helper.getIntValue("CutOffDate", null, "0"));
		this.myBean.setCutOffTime(this.helper.getFloatValue("CutOffHours", null, "0.0"));
		this.myBean.setDirectory(this.helper.getStringValue("Directory", null, "."));
		this.myBean.setAnd(this.helper.getBooleanValue("ISfileNameANDtime", null, "true"));
		this.myBean.setSubdirectory(this.helper.getBooleanValue("SearchSubDirectories", null, "false"));
	}

}
