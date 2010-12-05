package com.javainsight.workers;

import java.util.concurrent.Callable;

import com.javainsight.exceptions.RS232Exception;
import com.javainsight.utils.Sender;
import com.javainsight.utils.params.Constants;

public class Close implements Callable<Boolean> {

	private Sender sender = null;
	
	public Close(Sender sender){
		this.sender = sender;
	}
	
	@Override
	public Boolean call()  throws RS232Exception{
		try{
			return this.sender.close();
		}catch(Exception e){
			throw  new RS232Exception(Constants.NSIO_ERROR_CODE_2, Constants.CLOSE_ERR_MSG, e);
		}
	}

}
