package com.sunz.Callability;

import java.util.concurrent.*;
import java.util.*;

import com.sunz.run.RunnableTask;


public class HighLevelConcurrency {

	private static final ExecutorService executorPool=Executors.newScheduledThreadPool(5);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		fileTask f1 = new fileTask("F1");
		fileTask f2 = new fileTask("F2");
		List<fileTask>	listOfTask = new ArrayList<fileTask>();
		listOfTask.add(f1);
		listOfTask.add(f2);
		RunnableTask runTest = new RunnableTask(false);
		
		System.out.println("1->"+Thread.activeCount());
		//Future<String> fut = executorPool.submit(f1); // Submitting the first callable task, launching a new stack
		List<Future<String>> returns = executorPool.invokeAll(listOfTask);
		//executorPool.shutdown();
		System.out.println("2->"+Thread.activeCount());
		try {
			for(Future<String> fut : returns){
			System.out.println(fut.get());
			System.out.println(fut.isDone());
		}
			Future<?> runTaskResult = executorPool.submit(runTest);
			System.out.println(runTaskResult.get());
			} catch (InterruptedException e) {
			e.printStackTrace();
			} catch (ExecutionException e) {
			e.printStackTrace();
		}
			
	}
	/*
	 *Callable<V> is defined as Callable<String>
	 *
	 */
	public static class fileTask implements Callable<String>{
		private String name = null;
		public fileTask(String name){
			this.name = name;
		}
				
		public String call( ) throws InterruptedException{
			System.out.println("Inside call method..!!");
			Thread.sleep(2000);
			System.out.println("Name"+this.name);
			return this.name;
		}
	}

}
