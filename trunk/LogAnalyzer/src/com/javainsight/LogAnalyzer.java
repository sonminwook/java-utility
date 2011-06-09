package com.javainsight;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.javainsight.Threads.Search;
import com.javainsight.utils.EventType;

public class LogAnalyzer {

	 private static Logger log = Logger.getLogger(LogAnalyzer.class);
	 private final ExecutorService executorPool = Executors.newCachedThreadPool();
	 private Collection<File> files = null;
			 
	public static void main(String[] args) {
		PropertyConfigurator.configure("config/log4j.xml");	
		new LogAnalyzer().execute(EventType.FILTER_FILES);
	}
	
	private void execute(EventType event){
		try {
			switch (event) {
			case FILTER_FILES:
				files = executorPool.submit(new Search()).get();
				break;

			default:
				break;
			}
			
			for(File file : files){
				log.debug("file " + file);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
