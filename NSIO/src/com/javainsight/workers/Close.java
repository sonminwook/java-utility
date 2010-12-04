package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.utils.Sender;

public class Close implements Callable<Boolean> {

	private Sender sender = null;
	
	public Close(Sender sender){
		this.sender = sender;
	}
	
	@Override
	public Boolean call()  {
		return this.sender.close();		
	}

}
