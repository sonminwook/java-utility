import org.apache.log4j.PropertyConfigurator;

import com.javainsight.DataOperation;
import com.javainsight.RS232Executor;
import com.javainsight.DTO.Instruction;
import com.javainsight.DTO.SerialConfiguration;
import com.javainsight.exceptions.RS232Exception;


public class TEST {
	
	private SerialConfiguration config = null;
	private Instruction instruction = null;
	private String waitObj = new String();
	
	private void setConfig(){
		this.config = new SerialConfiguration();
		instruction = new Instruction();
		instruction.setConfig(config);
		
	}
	
	private void start(){
		
		this.setConfig();
		RS232Executor executor = null;
		try{
			String request = "023131323334353603";
			/*
			 * Init first
			 */
			instruction.setOperation(DataOperation.INITIALIZE);
			instruction.setRequest(request, null);
			instruction.setWait(waitObj);
			instruction.setTimeOutInSeconds(1000);
			
			executor = new RS232Executor(instruction);
			executor.execute();
			System.out.println("Initialization successful");
			/*
			 * send Reqest
			 */
			instruction.setOperation(DataOperation.SEND_N_WAIT_FOR_ETX);
			executor.execute();
			System.out.println(instruction.getResponse());
			
			instruction.setOperation(DataOperation.CLOSE_PORT);
			executor.execute();
		}catch(RS232Exception e){
			e.printStackTrace();
		}catch(Exception e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void main(String... args){
		PropertyConfigurator.configure("config/log4j.properties");
		new TEST().start();
	}
}
