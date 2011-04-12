/*-------------------------------------------------------------------------
Encapsulation - This program has been build to make one person understand the concept
of Encapsulation.
Copyright (C) 2010  http://jovialjava.blogspot.com

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
/**
 * @author jovialjava Team
 */
public class Encapsulation {
	
    // This is the cake.
	private String cake = null;
	
	/*
	 * This is the getter guard, which provide you the cake if it is 
	 * available.
	 * It can not change the value of cake.
	 */
	public String getter(){
		if(cake != null){
			return cake;
		}else{
			System.err.println("Sorry I can not provide the cake");
			return null;
		}		
	}
	
	/*
	 * This is the setter guard, which will replace the cake 
	 * after doing the necessary tests.
	 */
	public void setter(String newCake){
		if(newCake.equals("POISIONUS CAKE")){
			System.err.println("Sorry I can not replace the cake");
		}else{
			this.cake = newCake;
		}
	}
}
