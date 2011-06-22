import com.globalblue.oneInterface.OneInterfaceServer;



public class RUN {

	
	public static void main(String[] args){
		try{
			System.out.println("Start OneInterfce");
			String base64Regex = "\\p{Alnum}{1,}[+/=]{0,}";
			String base64 = "VGhpcyBpcyBzaW1wbGUgQVNDSUkgQmFzZTY0IGZvciBTdGFja092ZXJmbG93IGV4bWFwbGUu";
			System.out.println(base64.matches(base64Regex));			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
