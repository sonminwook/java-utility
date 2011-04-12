package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

import com.javainsights.exceptions.IPException;
import com.javainsights.iputils.IPSender;
import com.javainsights.utils.params.Constants;

public class Close implements Callable<Boolean> {

	private IPSender sender = null;
	
	public Close(IPSender sender){
		this.sender = sender;
	}
	
	@Override
	public Boolean call() throws IPException {
		try{
			this.sender.close();
			return true;
		}catch(IPException e){
			throw e;
		}catch(Exception e){
			throw  new IPException(Constants.CLOSE_ERROR_CODE_2, Constants.CLOSE_ERR_MSG, e);
		}
	}

}
