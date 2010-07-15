package com.sunz.Executors;

import java.util.Date;
import java.util.concurrent.*;

import com.sunz.Callability.CallTest;
import com.sunz.run.RunnableTask;
import com.sunz.utils.DateUtils;

public class ExecutorTest {
	
	private static final ScheduledExecutorService executorPool = Executors.newScheduledThreadPool(2);
	
	public static void main(String[] args){
		CallTest test1 = new CallTest("Test1");
		CallTest test2 = new CallTest("Test2");
		RunnableTask tsk = new RunnableTask(false);
		long startTime = new Date().getTime();
		try{
			ScheduledFuture<?> sFuture = executorPool.scheduleAtFixedRate(tsk, 0, 5, TimeUnit.SECONDS);
			System.out.println(DateUtils.now()+"-time took to execute -"+ (new Date().getTime() - startTime));
			System.out.println(sFuture.isDone() +" ");
			if(sFuture == null){
				System.out.println("Successful");
			}else{
				System.out.println("Not successful");
			}
			//System.out.println(sFuture.get());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
