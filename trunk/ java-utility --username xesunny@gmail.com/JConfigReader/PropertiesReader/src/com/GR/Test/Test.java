package com.GR.Test;

import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.GR.interfaces.Bean;
import com.GR.reader.PropReader;
import com.GR.beans.sunnyBean;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private int count=0;
	static String obj = new String();

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
	//	new Test().doIT();
		PropReader p = new PropReader("bin/properties",60);
		Map<String, Bean> map = p.returnMapValue();
		sunnyBean myBean = (sunnyBean)map.get("sunny.xml");
		
//		PropReader p = new PropReader("bin/properties");
		try {
			for(int i=0; i<10; i++){
			System.out.println("Name "+i+" "+myBean.getName());
			//myBean.setName("Testing");
			Thread.currentThread().sleep(5000);	
			p.refresh();
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			p.shutDown();
		}
//
//		} catch(Exception e){
//			logger.info("!!!!!!!!!!!!!!!!!!!!!!!!Exception!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//	           logger.info("Exception "+ e.getClass().getName() + " Message "+e.getMessage());
//	           for(int i=1; i<=e.getStackTrace().length; i++){
//	        	   logger.error("Trace "+ i +": "+e.getStackTrace()[i-1]);
//	            }
//	           logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");	          
//		}finally{
//			logger.info("Main thread completed Successfully");
//	       // logger.info(Thread.activeCount());
//	        p.shutDown();
//	        //logger.info(Thread.activeCount());
//			// Runtime.getRuntime().addShutdownHook(new Thread(p));
//		//	System.exit(0);
//		}
	}
	
	public void doIT() {
		PropReader[] array = new PropReader[100];
		for(int i=0; i<100; i++){
			array[i] = new PropReader("bin/properties",50);
		}
		try{
			for(PropReader p : array){
				Testing t = new Testing(p);
				new Thread(t).start();
			}
		Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		while(true){
			if(count >= 99){
				System.out.println("******************************************"+count);
				break;
			}
		}
			
		}catch(Exception e){
			logger.info("!!!!!!!!!!!!!!!!!!!!!!!!Exception!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	           logger.info("Exception "+ e.getClass().getName() + " Message "+e.getMessage());
	           for(int i=1; i<=e.getStackTrace().length; i++){
	        	   logger.error("Trace "+ i +": "+e.getStackTrace()[i-1]);
	            }
	           logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");	
		}finally{
			for(PropReader p : array)
				p.shutDown();
		}
	}
	
	private class Testing implements Runnable{
		PropReader p = null;
		
		public Testing(PropReader p){
			this.p = p;
		}
		public void run() {
			for(int i=0; i<3; i++){
				try {
					Map<String, Bean> map = p.returnMapValue();
						Thread.currentThread().sleep(1000);
						sunnyBean sunz = (sunnyBean)map.get("sunny.xml");
						synchronized (obj) {
							logger.info("Name "+i+" "+sunz.getName());	
						}
						
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				}
			count++;
			//return null;
		}
	}

}
