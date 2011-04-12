import org.apache.log4j.PropertyConfigurator;

import com.globalblue.ACValidation;


public class Test {

	public static void main(String...args){
		PropertyConfigurator.configure("config/log4j.properties");
		try{
			String ac = "5298-6460-D41A-2F84-BE33";
			
			//ACValidation.firstTimeValidation(ac);
			ACValidation.validateAC(ac);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
