package me.Penguin.SuperFishing.objects;

import java.util.HashMap;
import java.util.Set;

public class valueList<T, K> {
	
	
	private HashMap<T, Integer> map = new HashMap<T, Integer>();
	
	public valueList() {}
	
	public void addValue(T key, int val) {
		if (map.containsKey(key)) map.put(key, map.get(key) + val);			
		else map.put(key, val);		
	}
	
	public void removeVal(T key, int amount, boolean remove) {
		if (map.containsKey(key)) {
			int old = map.get(key);
			if (old-amount <= 0) if (remove) map.remove(key); else map.put(key, 0);
			else map.put(key, old-amount);
		}
	}
	
	public void remove(T key) {
		map.remove(key);
	}
	
	
	public int get(T key) {
		return map.containsKey(key) ? map.get(key) : null;
	}
	
	public Set<T> keySet() { 
		return map.keySet();
	}
	
	public boolean containsKey(T key) {
		return map.containsKey(key);
	}

}
