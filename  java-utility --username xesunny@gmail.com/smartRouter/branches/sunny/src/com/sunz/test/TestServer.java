package com.sunz.test;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.GR.interfaces.Bean;
import com.GR.reader.PropReader;
import com.GR.beans.serverBean;
import com.sunz.in.Server;

public class TestServer {

	private static Logger logger = Logger.getLogger(TestServer.class);
	
	public static void main(String... args){
		PropertyConfigurator.configure("config/log4j.properties");
		PropReader p = new PropReader("serverProperties");
		
		try{
			Map<String, Bean> map = p.returnMapValue();
			serverBean myBean = (serverBean)map.get("server.properties");
			
			for(int i=0; i<10; i++){
				System.out.println(i + " "+ myBean.getHostAddress());
				Thread.sleep(5000);
			}
			String host = myBean.getHostAddress();
			int[] ports = myBean.getPorts();
			//System.out.println(Thread.activeCount());
			for(int port : ports){
				Server s = new Server(host, port, map);
				Thread thread = new Thread(s);
				thread.setName(port+"");
				thread.start();
			}
			//System.out.println(Thread.activeCount());
		}catch(Exception e){
			e.printStackTrace();			
		}finally{
			p.shutDown();
			//System.out.println(Thread.activeCount());
		}
	}
	/*
	public static void main(String[] args){
		String directory2Monitor = "serverProperties";
		logger.debug(":::::::::::::::::::::::::::::::::::");
		logger.debug("Monitor Thread has been started");
		logger.debug("Directory under monitor is :"+ directory2Monitor);
		File f = new File(directory2Monitor);
		logger.debug(new File(directory2Monitor+"/Test").isDirectory());
		if(f.isDirectory()){
			for(String fileName : f.list()){
				if(!(new File(fileName).isDirectory())){
					logger.debug(fileName +"  "+(new File(fileName).isFile())+ " "+(new File(fileName).isDirectory()));
					logger.debug(new File(fileName).canWrite());
					//this.load.push(fileName);
				}
				logger.debug("file Name -->" + fileName);
			}
//			if(this.load.size() > 0){
//				this.folderEventList.add(FolderEvent.LOAD);
//			}
			logger.debug(":::::::::::::::::::::::::::::::::::");
			
		}else{
			logger.debug(directory2Monitor + " is not a direcotry\n" );			
		}
		
		if(f.isDirectory()){
			for(File file : f.listFiles()){
				logger.debug(file.getName()+ " "+file.isDirectory());
			}
		}
	}*/
}
