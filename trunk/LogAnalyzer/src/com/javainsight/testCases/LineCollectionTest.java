package com.javainsight.testCases;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.javainsight.utils.FileOperations;

public class LineCollectionTest extends TestCase {

	protected void setUp() throws Exception {
	}

	public void testFetchLines() throws Exception{
		Date timeToStart = new Date();
		FileOperations operation = new FileOperations();
		List<String> lines = operation.fetchLines("testFiles/Pay@Table_log.log.2011-05-27", ".*(M,P,){1}.*", true);
		System.out.println("Total Lines -" + lines.size() );
//		for(String line : lines){
//			System.out.println(line);
//		}
		System.out.println("Total TimeTaken -" + (new Date().getTime() - timeToStart.getTime()));
	}
	
	public void testFetchLines_2() throws Exception{
		Date timeToStart = new Date();
		FileOperations operation = new FileOperations();
		List<String> lines = operation.fetchLines("testFiles/Pay@Table_log.log.2011-05-27", "M,P,", false);
		System.out.println("Total Lines -" + lines.size() );
//		for(String line : lines){
//			System.out.println(line);
//		}
		System.out.println("Total TimeTaken -" + (new Date().getTime() - timeToStart.getTime()));
	}

}
