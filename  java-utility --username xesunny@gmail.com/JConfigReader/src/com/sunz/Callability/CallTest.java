package com.sunz.Callability;

import java.util.concurrent.Callable;
import com.sunz.utils.*;

public class CallTest implements Callable<String>{
	
	private String name = null;
	private int count = 0;
	
	public CallTest(String name){
		this.name = name;
	}
	
	public String call(){
		System.out.println(DateUtils.now()+"-Inside call method of ["+name+"]");
		this.count++;
		if(count == 5){
			throw new NullPointerException(DateUtils.now()+"-Count has reached 5");
		}
		return this.name;
	}

}
