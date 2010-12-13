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
package com.jovialjava;

/**
 * This class determine the possible type of outcomes of different operations.
 * @author Sunny Jain
 * @Date 07-Dec-2010
 * @version 1.0
 */
public enum DataResult {

	/**
	 * Indicates that operation was successul
	 */
	SUCCESS,
	/**
	 * Indicates the operation either was failed or ended up with exception
	 */
	FAILED,
	/**
	 * Indicates that Serial device has sent ACK
	 */
	ACK,
	/**
	 * Indicated that Serial device has sent NACK
	 */
	NACK,
	/**
	 * Indicates that Serial device has send ENQ
	 */
	ENQ,
	/**
	 * Indicates that Seial Device has not sent the desired data
	 */
	NO_DATA,	
	/**
	 * Indicates that Serial device has sent the DATA...ETX..
	 */
	RESPONSE;
}
