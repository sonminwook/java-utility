package com.thegoodcode.ipserialswitch.beans;

import org.apache.mina.core.session.IoSession;

public interface ClientConnector {

	IoSession connect(Config config, IoSession frontSession);
	boolean disconnect();
}
