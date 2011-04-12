package com.javainsight.threads;

import java.io.File;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import com.javainsight.enums.events.FolderEvent;

public class schedulor implements Runnable{

	private Stack<String> load = null;
	private Stack<String> unload = null;
	private List<FolderEvent> eventQueue = null;
	private String directory2Monitor = null;
	private Condition loadCondition = null;
	private Lock updateLock = null;
	//private String mainFileName = null;
	
	public schedulor(Stack<String> load,
					 Stack<String> unload,
					 List<FolderEvent> eventQueue,
					 String directory2Monitor,
					 Condition loadCondition,
					 Lock updateLock){
		this.load = load;
		this.unload = unload;
		this.eventQueue = eventQueue;
		this.directory2Monitor = directory2Monitor;
		this.loadCondition = loadCondition;
		this.updateLock = updateLock;
		//this.mainFileName = mainFileName;
	}
	
	public void run() {
		System.out.println(":::::::::::::::::::::::::::::::::::");
		System.out.println("Monitor Thread has been started");
		System.out.println("Directory under monitor is :"+ directory2Monitor);
		File f = new File(directory2Monitor);
		if(f.isDirectory()){
			for(String fileName : f.list()){
				System.out.println(fileName);
				this.load.push(fileName);				
			}			
			this.eventQueue.add(FolderEvent.LOAD);
			System.out.println(":::::::::::::::::::::::::::::::::::");
		}else{
			System.err.format("%s is not a direcotry\n", directory2Monitor);			
		}
		this.NOTIFY_MAIN_THREAD();
		
	}
	
	public void NOTIFY_MAIN_THREAD(){
		this.updateLock.lock();
		try{
			loadCondition.signal();
		}finally{
			this.updateLock.unlock();
		}
	}
}
