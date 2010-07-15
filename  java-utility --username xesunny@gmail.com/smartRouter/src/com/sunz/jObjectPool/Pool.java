package com.sunz.jObjectPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pool<K, V> {
	
	private Map<K, V> map = new HashMap<K, V>();
	private List<K>  keyList = new ArrayList<K>();
	private int size = -1;
	
	public Pool(){
		
	}
	public Pool(int size){
		this.size = size;
	}
	
	public void add(K key, V value){
		int beforeSize = map.size();		
		if(size != -1){
			if(beforeSize < size){
				map.put(key, value);
				keyList.add(key);
			}else{
				map.remove(keyList.get(0));
			}
		}else{
			map.put(key, value);
		}		
	}

	public V get(K key){
		return map.get(key);
	}
	
	public void remove(K key){
		map.remove(key);
		keyList.remove(key);
	}

	public void removePool(){
		this.map = null;
		this.keyList = null;
	}
	
	public String toString(){
		String tmpString = this.map.size()+" ->[";
		for(K key : this.map.keySet()){
			V value = this.map.get(key);
			tmpString = tmpString+ key + " " + value +", ";
		}
		return tmpString+"]";
	}
}
