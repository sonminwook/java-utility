package com.javainsight.cloud.utils;

import java.util.List;
import java.util.concurrent.Callable;

public class WorkSheetReader implements Callable<List<String>> {
	
	private String fileName = null;

	public WorkSheetReader(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public List<String> call() throws Exception {
		List<String> lines = new ReadFile().readWorkSheet(fileName, null, false);
		return lines;
	}

}
