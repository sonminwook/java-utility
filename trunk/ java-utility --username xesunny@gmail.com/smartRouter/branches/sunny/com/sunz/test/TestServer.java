package com.sunz.test;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.GR.beans.serverBean;
import com.GR.interfaces.Bean;
import com.GR.reader.PropReader;
import com.sunz.in.Server;

public class TestServer {

	private static Logger logger = Logger.getLogger(TestServer.class);
	public static void main(String... args){
		PropertyConfigurator.configure("config/log4j.properties");
		PropReader p = new PropReader("serverProperties");
		
		try{
			Map<String, Bean> map = p.returnMapValue();
			serverBean myBean = (serverBean)map.get("server.properties");
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
			//p.shutDown();
			//System.out.println(Thread.activeCount());
		}
	}
}
