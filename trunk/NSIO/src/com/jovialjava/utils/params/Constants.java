/*-------------------------------------------------------------------------
RS232Java - This program has been build to simplfy the serial communication by providing 
the implementation of common flow of serial communication world.
Copyright (C) 2010  Sunny Jain [email: xesunny@gmail.com ]

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, version 3 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
--------------------------------------------------------------------------*/
package com.jovialjava.utils.params;

public class Constants {
	
	public static Byte ACK = new Byte((byte)6);
	public static Byte NACK = new Byte((byte)21);
	public static Byte STX = new Byte((byte)2);
	public static Byte ETX = new Byte((byte)3);
	public static Byte ENQ = new Byte((byte)5);
	public static int responseBufferCapacity = 1024;
	
	
	public static final String ACK_NAK_ERROR_CODE_1 = "1001";
	public static final String CLOSE_ERROR_CODE_2 = "1002";
	public static final String ENQ_ERROR_CODE_3 = "1003";
	public static final String INIT_ERROR_CODE_4 = "1004";
	public static final String RESPONSE_ERROR_CODE_5 = "1005";
	public static final String SEND_ERROR_CODE_6 = "1006";
	public static final String SEND_N_RECEIVE_ERROR_CODE_7 = "1007";
	public static final String SENDER1_ERROR_CODE_8 = "1008";
	public static final String SENDER2_ERROR_CODE = "1009";
	public static final String EXECUTOR_ERROR_CODE_10 = "1010";
	public static final String RS232_NOTIFYING_BYTES_EXCEPTION = "1011";
	public static final String NSIO_SEND_ERR_CODE = "1012";
	
	private static final String EXCEPTION = "\r\n!!!!!!!!!!!!!!!EXCEPTION!!!!!!!!!!!!!!!!!!!!\r\n";
	private static final String ERRORCODE = "\r\n!!!!!!!!!!!!!!!!!";
	private static final String ERRORTAIL = "!!!!!!!!!!!!!!!!!";

		
	public static final String NOTIFY_ERROR_MSG = EXCEPTION + " WHILE WAITING/RECEIVING USER SUPPLIED BYTES"+ ERRORCODE + RS232_NOTIFYING_BYTES_EXCEPTION +ERRORTAIL;
	public static final String ACK_NACK_ERR_MSG = EXCEPTION + "   WHILE WAITING/RECEIVING ACK/NACK BYTE" +  ERRORCODE + ACK_NAK_ERROR_CODE_1 +ERRORTAIL;
	public static final String ENQ_ERROR_MSG    = EXCEPTION + "    WHILE WAITING/RECIEVING ENQ BYTE" +  ERRORCODE + ENQ_ERROR_CODE_3 +ERRORTAIL;
	public static final String INIT_ERR_MSG     = EXCEPTION + "    WHILE INITIALIZING SERIAL DEVICE" +  ERRORCODE + INIT_ERROR_CODE_4 +ERRORTAIL;
	public static final String CLOSE_ERR_MSG    = EXCEPTION + "WHILE CLOSING THE COMMUNICATION WITH SERIAL DEVICE" +  ERRORCODE + CLOSE_ERROR_CODE_2 +ERRORTAIL;
	public static final String RESPONSE_ERR_MSG = EXCEPTION + "    WHILE WAITING FOR RESPONSE/ETX" +  ERRORCODE + RESPONSE_ERROR_CODE_5 +ERRORTAIL;
	public static final String SEND_ERR_MSG     = EXCEPTION + "   WHILE SENDING THE DATA TO SERIAL DEVICE" +  ERRORCODE + SEND_ERROR_CODE_6 +ERRORTAIL;
public static final String SEND_N_RECEIVE_ERR_MSG=EXCEPTION + "   WHILE RECIEVING THE DATA IN SUPPLIED TIME" +  ERRORCODE + SEND_N_RECEIVE_ERROR_CODE_7 +ERRORTAIL;
	
	public static final String PLS_INIT_FIRST_ERR_MSG = EXCEPTION + " SERIAL DEVICE HAS NOT BEEN INITIALIZED, PLEASE EXECUTE THE INITIALIZATION COMMAND FIRST" +  ERRORCODE + SENDER1_ERROR_CODE_8 +ERRORTAIL;	
	public static final String TOO_MANY_LISTEN_ERR_MSG = EXCEPTION + " WHILE CREATING AN EVENT LISTENER FOR SERIAL DEVICE" +  ERRORCODE + SENDER2_ERROR_CODE +ERRORTAIL;

}
