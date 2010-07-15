package com.sunz.lockers;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.sunz.utils.DateUtils;

public class lockTest {

	private static final Lock myLock = new ReentrantLock(true);
	
	private static final ExecutorService executorPool = Executors.newFixedThreadPool(3);
	
	public static void main(String[] args){
		fileTask F1 = new fileTask("F1");
		fileTask F2 = new fileTask("F2");
		fileTask F3 = new fileTask("F3");
		executorPool.submit(F1);
		executorPool.submit(F2);
		executorPool.submit(F3);
		executorPool.shutdown();
	}
	
	
	static class fileTask implements Callable<String>{
		private String name = null;
		
		public fileTask(String name) {
			this.name = name;
		}
		public String call(){
			try{
				System.out.println(DateUtils.now() +" Thread "+ name+" Trying");
				if(myLock.tryLock(2000, TimeUnit.MILLISECONDS)){
				System.out.println(DateUtils.now() +" Thread "+ name+" has acquired the Lock");
				Thread.sleep(5000);
				}else{
					System.out.println(DateUtils.now() +" Thread "+ name+" did not get the Lock");
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
				myLock.unlock();
			}
			return null;
		}
	}
}
