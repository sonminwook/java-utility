package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

package com.javainsights.DataResult;
package com.javainsights.exceptions.IPException;
package com.javainsights.exceptions.RS232Exception;
package com.javainsights.iputils.IPSender;
package com.javainsights.utils.params.Common;
package com.javainsights.utils.params.Constants;

public class ENQ implements Callable<DataResult> {

	private byte[] request = null;
	private IPSender sender = null;
	private String waitString = null;
	private int timeOut = 200;
		
	public ENQ(byte[] request,
					IPSender sender,
					String waitString,
					int timeOut){
		this.request = request;
		this.sender = sender;
		this.waitString = waitString;
		this.timeOut = timeOut;
	}
	
	@Override
	public DataResult call() throws IPException {
		try{
			boolean status = this.sender.sendNWait1Byte(request, timeOut, Constants.ENQ);
			if(!status){
				return DataResult.NO_DATA;
			}
			String response = this.sender.getResponse();
			/*
			 * This response is in HEX. converting to ASCII
			 */
			//response = response.length() == 1? "0"+response : response;
			//response = Common.convertHexToString(response);
			
			byte[] bytes = response.getBytes();
			for(byte b : bytes){
				if(b == Constants.ENQ){
					return DataResult.ENQ;
				}				
				if(b == Constants.ETX){
					return DataResult.RESPONSE;
				}
			}
			 return DataResult.NO_DATA;
			}catch(IPException e){
				throw e;
			}catch(Exception e){	
				throw new IPException(Constants.ENQ_ERROR_CODE_3, Constants.ENQ_ERROR_MSG, e);
			}
	}

}
