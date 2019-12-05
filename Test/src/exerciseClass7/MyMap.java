package exerciseClass7;

import java.util.ArrayList;
import java.util.List;

public class MyMap<K,V> {
	
	private List<K> keys;
	private List<V> values;
	
	public MyMap() {
		keys = new ArrayList<K>();
		values = new ArrayList<V>();
	}
	
	public void put(K key, V value){
		keys.add(key);
		values.add(value);
	}
	
	public V get(K key) {
		return values.get(keys.indexOf(key));
	}
	
}
