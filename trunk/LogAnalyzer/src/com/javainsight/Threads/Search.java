package com.javainsight.Threads;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.Callable;

import com.javainsight.beans.FileSearchBean;
import com.javainsight.constants.Config;
import com.javainsight.constants.Constants;
import com.javainsight.utils.Cache;
import com.javainsight.utils.Expressions;
import com.javainsight.utils.FileOperations;

public class Search implements Callable<Collection<File>> {

	
	@Override
	public Collection<File> call() throws Exception {
		FileSearchBean fileSearchParams = (FileSearchBean)Cache.getBean(Config.FILE_SEARCH);
		long cutOffTime = new Expressions().getCutOffTime(fileSearchParams.getCutOffDate(),
														 fileSearchParams.getCutOffTime());
		
		return new FileOperations().filterFiles(fileSearchParams.getDirectory(),
										this.getFileNames(fileSearchParams),
										cutOffTime,
										fileSearchParams.isAnd(),
										fileSearchParams.isSubdirectory());
		
		
	}
	
	private String[] getFileNames(FileSearchBean fileSearchBean){
		String[] fileNames = new String[fileSearchBean.getFileNamesRegex().length];
		for(int i=0; i < fileSearchBean.getFileNamesRegex().length; i++){
			String fileParams = fileSearchBean.getFileNamesRegex()[i];
			fileNames[i] = fileParams.split(Constants.FILE_DATA_SPLITTER)[Constants.FILE_NAME_LOCATION];
		}
		
		for(String name : fileNames){
			System.out.println(name);
		}
		return fileNames;
	}

}
