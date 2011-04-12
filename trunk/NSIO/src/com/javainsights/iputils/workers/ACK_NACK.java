package com.javainsights.iputils.workers;

import java.util.concurrent.Callable;

package com.javainsights.DataResult;
package com.javainsights.exceptions.IPException;
package com.javainsights.iputils.IPSender;
package com.javainsights.utils.params.Common;
package com.javainsights.utils.params.Constants;

public class ACK_NACK implements Callable<DataResult> {
	
	private byte[] request = null;
	private IPSender sender = null;
	private String waitString = null;
	private int timeOut = 200;
		
	public ACK_NACK(byte[] request,
					IPSender sender,
					String waitString,
					Integer timeOut){
		this.request = request;
		this.sender = sender;
		this.waitString = waitString;
		this.timeOut = timeOut;
	}

	@Override
	public DataResult call() throws IPException {
		
		try{
			boolean status = this.sender.sendNWait1Byte(request, timeOut, Constants.NACK, Constants.ACK);
			if(!status){
				return DataResult.NO_DATA;
			}
			String response = this.sender.getResponse();		
			/*
			 * This response is in HEX. converting to ASCII
			 */
			response = response.length() == 1? "0"+response : response;
			response = Common.convertHexToString(response);
			byte[] bytes = response.getBytes();
			for( int i = bytes.length -1; i>=0; i++){
				byte b = bytes[i];
				if(b == Constants.ACK){	
					return DataResult.ACK;
				}
				if(b == Constants.NACK){
					return DataResult.NACK;
				}			
			}
			 return DataResult.NO_DATA;
			}
			catch(IPException e){
				throw e;
			}
			catch(Exception e){			
				throw new IPException(Constants.ACK_NAK_ERROR_CODE_1, Constants.ACK_NACK_ERR_MSG, e);
			}
	}

}
