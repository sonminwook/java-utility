package com.javainsight;

public enum DataOperation {

	INITIALIZE,
	SEND,
	SEND_N_WAIT_FOR_ACK,
	SEND_N_WAIT_FOR_ENQ,
	SEND_N_WAIT_FOR_ETX,
	WAIT_FOR_ETX,
	SEND_N_WAIT_FOR_DATA,
	CLOSE_PORT;
}
