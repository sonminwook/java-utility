package com.gr.oneInterface.workers;

import java.util.concurrent.Callable;

import com.gr.oneInterface.utils.Sender;

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
