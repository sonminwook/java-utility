package com.globalblue.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.xml.sax.SAXException;

/**
 * This class will help to create the touch file<br>
 * @author Sunny Jain
 * @Date 07-Jun-10
 * @version 1.0
 */
public class StoreInformation {
	
	/**
	 * This class will create a touch file in the temp directory. The file name will be
	 * fileNameKey=??, the ?? will be the value of tag in the response.
	 * @param XMLMessage - This is hashmap<String,String> of the XML response message.
	 * @param tag - value of tag in the response message whose value to be stored as a touch file.
	 * @param fileNameKey - Name of the touch file - possible value could be - Sale.txRef, Refund.txRef etc.<br>
	 * @throws IllegalArgumentException - If there is no such tag in the Hashmap.
	 * @throws SAXException - If someproblem while Converting the XMLResponse into Map.
	 * @throws IOException - If some exception while create the touch file.
	 */
	public void storeResponseTagInfo(String XMLMessage, String tag, String fileNameKey) throws 
															IllegalArgumentException,
															SAXException,
															IOException{
		
		InputStream is = new StringToStream().convert(XMLMessage);
		Map<String, String> valueMap = new XMLToMap().convertToMap(is);
		String value = valueMap.get(tag);
		if(value == null){
			throw new IllegalArgumentException("No value found for Tag ["+tag+"] in the response message");
		}else{
			String filePath = "tempFiles/"+fileNameKey+"="+value;
			File f = new File(filePath);
			f.createNewFile();
			System.out.println(f.getAbsolutePath());
		}
		
	}

}
