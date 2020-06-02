package com.appeti.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;


public class Cache<K,V> {
	
	private class CacheObject<X>{
		private X value;
		private int ttl;
		private Date added;
		
		public CacheObject(X value,int ttl){
			this.value = value;
			this.ttl = ttl;
			this.added = new Date();
		}
		
		public boolean isExpired(){
			return new Date().after(DateUtils.addMinutes(added, ttl));
		}
		public X getValue() {
			return value;
		}
	}
	
	private Map<K,CacheObject<V>> cacheMap;
	private int ttl;
	private static final int DEFAULT_TTL = 30;
	
	public Cache(int ttl){
		this.cacheMap = new HashMap<K,CacheObject<V>>();
		this.ttl = ttl;
	}
	public Cache(){
		this(DEFAULT_TTL);
	}
	
	public void put(K key, V value){
		cacheMap.put(key, new CacheObject<V>(value, ttl));
	}

	public V get(K key){
		CacheObject<V> obj = cacheMap.get(key);
		if(obj == null || obj.isExpired())
			return null;
		else
			return obj.getValue();
	}
}
