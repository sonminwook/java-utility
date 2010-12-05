package com.javainsight.utils.params;

public class Constants {
	
	public static Byte ACK = new Byte((byte)6);
	public static Byte NACK = new Byte((byte)21);
	public static Byte STX = new Byte((byte)2);
	public static Byte ETX = new Byte((byte)3);
	public static Byte ENQ = new Byte((byte)5);
	public static int responseBufferCapacity = 1024;
	
	
	public static String NSIO_ERROR_CODE_1 = "1001";
	public static String NSIO_ERROR_CODE_2 = "1002";
	public static String NSIO_ERROR_CODE_3 = "1003";
	public static String NSIO_ERROR_CODE_4 = "1004";
	public static String NSIO_ERROR_CODE_5 = "1005";
	public static String NSIO_ERROR_CODE_6 = "1006";
	public static String NSIO_ERROR_CODE_7 = "1007";
	public static String NSIO_ERROR_CODE_8 = "1008";
	public static String NSIO_ERROR_CODE_9 = "1009";
	public static String NSIO_ERROR_CODE_10 = "1010";
	public static String RS232_NOTIFYING_BYTES_EXCEPTION = "1011";
	
	private static final String EXCEPTION = "!!!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!!!!!!!\r\n";
		
	public static final String NOTIFY_ERROR_MSG = EXCEPTION + " WHILE WAITING/RECEIVING USER SUPPLIED BYTES\r\n"+ EXCEPTION;
	public static final String ACK_NACK_ERR_MSG = EXCEPTION + "   WHILE WAITING/RECEIVING ACK/NACK BYTE\r\n" + EXCEPTION;
	public static final String ENQ_ERROR_MSG    = EXCEPTION + "    WHILE WAITING/RECIEVING ENQ BYTE\r\n" + EXCEPTION;
	public static final String INIT_ERR_MSG     = EXCEPTION + "    WHILE INITIALIZING SERIAL DEVICE\r\n" + EXCEPTION;
	public static final String CLOSE_ERR_MSG    = EXCEPTION + "WHILE CLOSING THE COMMUNICATION WITH SERIAL DEVICE\r\n" + EXCEPTION;
	public static final String RESPONSE_ERR_MSG = EXCEPTION + "    WHILE WAITING FOR RESPONSE/ETX\r\n" + EXCEPTION;
	public static final String SEND_ERR_MSG     = EXCEPTION + "   WHILE SENDING THE DATA TO SERIAL DEVICE\r\n" + EXCEPTION;
public static final String SEND_N_RECEIVE_ERR_MSG=EXCEPTION + "   WHILE RECIEVING THE DATA IN SUPPLIED TIME\r\n" + EXCEPTION;
	
	public static final String PLS_INIT_FIRST_ERR_MSG = EXCEPTION + " SERIAL DEVICE HAS NOT BEEN INITIALIZED, PLEASE EXECUTE THE INITIALIZATION COMMAND FIRST\r\n" + EXCEPTION;	
	public static final String TOO_MANY_LISTEN_ERR_MSG = EXCEPTION + " WHILE CREATING AN EVENT LISTENER FOR SERIAL DEVICE\r\n" + EXCEPTION;

}
