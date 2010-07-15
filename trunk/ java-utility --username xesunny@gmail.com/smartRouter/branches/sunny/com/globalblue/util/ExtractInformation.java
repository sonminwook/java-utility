package com.globalblue.util;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
 * This class will help to retrieve the correct file names from the temp directory.<br> 
 * @author Sunny Jain
 * @date 07-Jun-10
 * @version 1.0
 */
public class ExtractInformation {

	/**
	 * This method will look for all the file names in the directory and
	 * create a list<String> of the file names which matches the passed name.<br>
	 * @param tag - passed File name
	 * @return - List<String> containing all matched file names.
	 */
	public List<String> extractResponseTagInfo(String tag){
		File directory = new File("tempFiles");
		String[] fileNames = null;
		List<String> tagValues = new ArrayList<String>();
		if(directory.isDirectory()){
				fileNames = directory.list();			
		}
		if(fileNames == null || fileNames.length == 0){
			throw new IllegalStateException("No Sales txn to be void found in the system");
		}else{
			for(String str : fileNames){
				if(str.contains(tag)){
					tagValues.add(str.substring(str.indexOf("=")+1)); 
				}
			}
		}
		return tagValues;
	}
}
