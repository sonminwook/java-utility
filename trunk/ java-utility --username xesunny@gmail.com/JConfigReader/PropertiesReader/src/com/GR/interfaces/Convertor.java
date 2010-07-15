package com.GR.interfaces;

public interface Convertor<T> {
	
	T decode(String value);
	
	String encode(String value);
}
