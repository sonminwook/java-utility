package com.javainsight.util;

public class Validate {
	
	public boolean validateNull(boolean needException, String message, Object... obj){
		if(needException){
			for(Object ob: obj){
				if(ob == null){
					throw new IllegalArgumentException(message);
				}
			}
			return true;
			//Test comment - Merge Test 
		}else{
			for(Object ob: obj){
				if(ob == null){
					return true;
				}
			}
			return false;
		}
		
	}

	public boolean validateEmpty(boolean needException, String message, String... str){
		if(needException){
			for(String ob: str){
				if(ob.trim().length() == 0){
					throw new IllegalArgumentException(message);
				}
			}
			return true;
		}else{
			for(String ob: str){
				if(ob.trim().length() == 0){
					return true;
				}
			}
			return false;
		}
	}
}
