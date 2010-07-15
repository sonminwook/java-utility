package com.sunz.run;

import com.sunz.utils.DateUtils;

public class RunnableTask implements Runnable {
	
	private boolean isException = false;
	private int count = 0;
	
	public RunnableTask(boolean isException){
		this.isException = isException;
	}

	public void run() throws NullPointerException{
		if(isException){
			throw new NullPointerException("Testing");
		}else{
			count++;
			if(count == 5){
				System.out.println(DateUtils.now()+" - count has reached 5");
				throw new NullPointerException("DONE");
			}
			System.out.println(DateUtils.now()+" - Inside "+count + " thread");
		}
	}
}
