import com.GR.beans.sunnyBean;
import com.GR.interfaces.PropertiesReader;
import com.GR.reader.PropReader;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PropertiesReader<Object> read1 = new PropReader();
		PropReader read = (PropReader)read1;
		read.loadPropertiesFile("C:/Documents and Settings/sjain/workspace/PropertiesReader/src/properties/PropertiesFileList.properties");
		System.out.println(read.returnMapValue().keySet());
		sunnyBean sunzBean = (sunnyBean)read.returnMapValue().get("sunny.properties");
		String lastnames[] = sunzBean.getLastName();
		System.out.println(sunzBean.getName() + " "+lastnames.length);
		
		for(String str : lastnames){
			System.out.println(str);
		}

	}

}
