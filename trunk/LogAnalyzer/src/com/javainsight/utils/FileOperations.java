package com.javainsight.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;



public class FileOperations {
	private static String lineFecthingObject = new String("lineFecthingObject");
	private static String fileFetchingObject = new String("fileFetchingObject");
	
	/*
	 * This method will read the file and give the lines which matches the expression.
	 * FullMatch - Time intensive process (Test Results : 158200 lines > 3.3 seconds (Total Matches : 600))
	 * Contain Match - Less Time intensive process (Test Results : 158200 lines > 0.4 seconds (Total Matches : 600))
	 */
	public List<String> fetchLines(String filePath, String regex, boolean isFullMatch) throws IOException{
		synchronized (lineFecthingObject) {
			List<String> matchedLines = new ArrayList<String>(); 
			LineIterator iterator = FileUtils.lineIterator(new File(filePath), "UTF-8");
			
			try{
				while(iterator.hasNext()){
					String line = iterator.nextLine();
					if(isFullMatch){
						if(line.matches(regex)){
							matchedLines.add(line);
						}
					}else{
						if(line.contains(regex)){
							matchedLines.add(line);
						}
					}
				}
			}finally{
				LineIterator.closeQuietly(iterator);
			}
			return matchedLines;
		}
		
	}
	
	/**
	 * This method is thread safe.
	 * @param directory
	 * @param namePattern
	 * @param cutOffTime
	 * @param isAND
	 * @param isSubDirectory
	 * @return
	 */
	public Collection<File> filterFiles(String directory,
										String[] namePattern,
										long cutOffTime,
										boolean isAND,
										boolean isSubDirectory) {
		
		synchronized (fileFetchingObject) {
			/*
			 * Step 1: Prepare the File Filter logic
			 */
			IOFileFilter fileFilter = null;
			if (namePattern != null) {
				RegexFileFilter[] nameFileFilter = new RegexFileFilter[namePattern.length];
				for (int i = 0; i < namePattern.length; i++) {
					nameFileFilter[i] = new RegexFileFilter(namePattern[i],
							Pattern.CASE_INSENSITIVE);
				}
				
				if (isAND) {
					fileFilter = FileFilterUtils.and(FileFilterUtils.ageFileFilter(cutOffTime, false),
													FileFilterUtils.or(nameFileFilter));
				} else {
					fileFilter = FileFilterUtils.or(FileFilterUtils.ageFileFilter(cutOffTime), 
													FileFilterUtils.or(nameFileFilter));
				}
			} else if (cutOffTime != 0) {
				/*
				 * This means - no file name patterns are provided, one may want to sort file names by time.
				 */
				fileFilter = FileFilterUtils.ageFileFilter(cutOffTime, false);
			} else {
				/*
				 * This means one wants to find all files from the directory.
				 */
				fileFilter = FileFilterUtils.trueFileFilter();
			}
			System.out.println(fileFilter.toString());
			/*
			 * Step 2: Retrieve the logic
			 */
			Collection<File> files = null;
			if (isSubDirectory) {
				files = FileUtils.listFiles(new File(directory), fileFilter,
						null);
			} else {
				files = FileUtils.listFiles(new File(directory), fileFilter,
						TrueFileFilter.INSTANCE);
			}
			return files;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
