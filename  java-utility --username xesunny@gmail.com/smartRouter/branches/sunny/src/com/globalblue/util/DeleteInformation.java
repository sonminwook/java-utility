package com.globalblue.util;

/**
 * This class will help to delete the Touch file.
 * @author Sunny Jain
 * @Date 07-Jun-10
 * @version 1.0
 */
public class DeleteInformation {
	
	/**
	 * This method will delete the file from tempfile directory.<br>
	 * @param fileName - file name in the temp direcotry to be deleted.
	 */
	public void deleteResponseInformation(String fileName){
		java.io.File f = new java.io.File("tempFiles/"+fileName);
		if(f.isFile()){
			f.delete();
		}else{
			throw new IllegalStateException("Can not delete the temp Response file ["+f.getAbsolutePath()+"]");
		}
	}

}
