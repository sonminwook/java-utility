package com.javainsight.testCases;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.javainsight.utils.FileOperations;

public class FileFilterTest {
	
	private static String[] fileNames = null;
	private static long cutOffTime = 0;
	private static String directory = "C:\\Documents and Settings\\sjain\\Pay@Table\\Pay@Table\\logs\\4-5 June\\logs";

	@Before
	public void setUp() throws Exception {
		fileNames = new String[2];
		fileNames[0] = "(Pay@Table_log.log){1}.*"; 
		fileNames[1] = "(PayFile){1}.*";
		cutOffTime = new Date().getTime() - 24*60*60*1000*10;
	}

	@Test
	public void testFilterFiles() {
		Collection<File> files = new FileOperations().filterFiles(directory,
										fileNames,
										cutOffTime,
										true,
										false);
		for(File file : files){
			System.out.println(file.getName());
		}
	}

}
