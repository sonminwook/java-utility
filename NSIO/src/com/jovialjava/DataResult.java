/*-------------------------------------------------------------------------
Copyright [2010] [Sunny Jain (email:xesunny@gmail.com)]
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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
	 * Indicates that operation was successful
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
