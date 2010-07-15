package com.sunz.jObjectPool;

public class PoolManager<K, V> {
	
	public Pool<K, V> createPool(int size){
		if(size > 0){
			Pool<K,V> pool = new Pool<K, V>(size);
			return pool;
		}else{
			Pool<K,V> pool = new Pool<K, V>();
			return pool;
		}
	}
	
	
}
