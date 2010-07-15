package com.globalblue.util;

import org.xml.sax.*;
import java.util.Map;
import java.util.HashMap;

/**
 * This class is the most important class that will convert the XMLResponse
 * into a Hashmpa. This is the subclass of ContentHandler.
 * It containt all the event listener related to XMLparsing.
 * Most important are - StartElement and characters.
 * StartElement will be called when an element is being read.
 * characters will be called when the value of element is being read.
 * @author Sunny Jain
 * @Date 07-Jun-10
 * @version 1.0
 *
 */
public class ValueHandler implements ContentHandler
{
	private Map<String, String> valueMap = new HashMap<String, String>();
	private String tempKey = null;
	private String tempValue = null;
	private boolean isPut = false;
	public void setDocumentLocator (Locator locator) { 
	 // System.out.println("Hello from setDocumentLocator()!"); 
	}

    public void startDocument ()
	throws SAXException  { 
	//  System.out.println("Hello from startDocument()!"); 
	}

    public void endDocument()
	throws SAXException {  
    //	System.out.println("Hello from endDocument()!"); 
	}

    public void startPrefixMapping (String prefix, String uri)
	throws SAXException {  
	 // System.out.println("Hello from startPrefixMapping()!" + prefix + " "+uri); 
	}

    public void endPrefixMapping (String prefix)
	throws SAXException  {  
	  //System.out.println("Hello from endPrefixMapping()!" + prefix); 
	}

    /**
     * This method will store the value of Element in the key.
     * This key will be used in Hashmap.
     */
    public void startElement (String uri, String localName,
			      String qName, Attributes atts)
	throws SAXException  {  
	 // System.out.println("Element -->"+localName); 
	  if(tempKey == null){
		  tempKey = localName;
	  }else{
		  tempKey = tempKey + "." + localName;
	  }
	  	  
	}

    public void endElement (String uri, String localName,
			    String qName)
	throws SAXException {  
    	//System.out.println("End element" + localName + " uri "+ uri + " q "+ qName);
    	
    	if(tempKey.contains(".") && isPut){
    	//System.out.println("TempKey Before  [" + tempKey +"] value ["+tempValue+"]");
    		valueMap.put(tempKey, tempValue);
    		tempKey = tempKey.substring(0, tempKey.lastIndexOf("."));
    		isPut = false;    	
    	}else if(tempKey.contains(".")){
    		//System.out.println("TempKey Before  [" + tempKey +"] value ["+tempValue+"]");
    		tempKey = tempKey.substring(0, tempKey.lastIndexOf("."));
    	}
    	
    }

    /**
     * this method will store the value of element in the value.
     * both key and value will be stored in hashmap.
     */
    public void characters (char ch[], int start, int length)
	throws SAXException { 
    	String value = " ";
       	//System.out.print("length --> "+length +" Value --> [");
       	for(int i = start; i < (start+length);i++){
       		value = value + Character.toString(ch[i]);
       	}
       		tempValue = value.trim();
       		isPut = true;
       		//System.out.println("Temp value -" + tempValue);
       	}

    public void ignorableWhitespace (char ch[], int start, int length)
	throws SAXException {  
	  System.out.println("Hello from ignorableWhitespace()!"); 
	}

    public void processingInstruction (String target, String data)
	throws SAXException {  
    		System.out.println("Hello from processingInstruction()!"); 
	}

    public void skippedEntity (String name)
	throws SAXException {  
    	System.out.println("Hello from skippedEntity()!"); 
	}
    
    public Map<String, String> getValueMap(){
    	return this.valueMap;
    }
    
   
}
