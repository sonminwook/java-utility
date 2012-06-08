package com.thegoodcode.ipserialswitch.protocol;

import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;

import com.thegoodcode.eventframework.collections.EventProperties;
import com.thegoodcode.eventframework.collections.TGCEvent;
import com.thegoodcode.eventframework.collections.TGCListener;
import com.thegoodcode.eventframework.collections.EventProperties.ThreadPriority;
import com.thegoodcode.eventframework.collections.list.TGCList;

public class IPServerListener implements TGCListener<String> {

	private static Logger log = Logger.getLogger(IPServerListener.class);
	private IoSession session = null;
	
	public IPServerListener(IoSession sesison){
		this.session = sesison;
	}
	
	@Override
	@EventProperties(Priority=ThreadPriority.MEDUIM,spawnThread=false)
	public void clear(TGCEvent<String> arg0) {

	}

	@Override
	@EventProperties(Priority=ThreadPriority.HIGH , spawnThread=true)
	public void valueAdded(TGCEvent<String> event) {
		this.session.write(event.tgcObject());
		@SuppressWarnings("unchecked")
		TGCList<String> data = event.getSource(TGCList.class);
		data.remove(event.tgcObject());	
	}

	@Override
	@EventProperties(Priority=ThreadPriority.MEDUIM,spawnThread=false)
	public void valueRemoved(TGCEvent<String> arg0) {
	}

	@Override
	@EventProperties(Priority=ThreadPriority.MEDUIM,spawnThread=false)
	public void valueRemovedByIndex(TGCEvent<String> arg0) {
		log.error("Removing index");
	}

	@Override
	@EventProperties(Priority=ThreadPriority.MEDUIM,spawnThread=false)
	public void valuesAdded(TGCEvent<String> arg0) {
	}


}
