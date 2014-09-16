package com.mini.mn.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

//System.currentTimeMillis() / 1000
public class LRUMap<K, O> {

	public class TimeVal<OO> {
		public OO obj;
		public Long t;

		public TimeVal(OO obj) {
			this.obj = obj;
			UpTime();
		}

		public void UpTime() {
			this.t = System.currentTimeMillis();
		}

	}

	private Map<K, TimeVal<O>> internalMap = null;
	private int maxSize;
	private int delSize;
	private static final int defaultDelRef = 10;

	public interface PreRemoveCallback<K, O> {
		public void preRemoveCallback(K k, O o);
	}

	private PreRemoveCallback<K, O> preRemove = null;

	public LRUMap(int maxSize) {
		this(maxSize, null);
	}

	public LRUMap(int maxSize, PreRemoveCallback<K, O> preRemove) {
		this.maxSize = maxSize;
		this.delSize = 0;
		this.preRemove = preRemove;
		internalMap = new HashMap<K, TimeVal<O>>();
	}

	public int size() {
		return internalMap.size();
	}

	public void setMaxSize(int maxSize) {
		if (maxSize > 0) {
			this.maxSize = maxSize;
		}
	}

	public void setPerDeleteSize(int delSize) {
		if (delSize > 0) {
			this.delSize = delSize;
		}
	}

	private int getDelSize() {
		if (delSize < 1) {
			int d = maxSize / defaultDelRef;
			return (d < 1) ? 1 : d;
		}
		return this.delSize;
	}

	public boolean check(K key) {
		return internalMap.containsKey(key);
	}

	public boolean checkAndUpTime(K key) {
		if (internalMap.containsKey(key)) {
			internalMap.get(key).UpTime();
			return true;
		}
		return false;
	}

	public O get(K key) {
		// always update time
		// TimeVal<O> v = internalMap.get(key);
		// if (v == null) {
		// return null;
		// }
		// return v.obj;
		return getAndUptime(key);
	}

	public O getAndUptime(K key) {
		TimeVal<O> v = internalMap.get(key);
		if (v == null) {
			return null;
		}
		v.UpTime();
		return v.obj;
	}

	public void remove(K key) {
		if (!internalMap.containsKey(key)) {
			return;
		}
		if (preRemove != null) {
			preRemove.preRemoveCallback(key, internalMap.get(key).obj);
		}
		internalMap.remove(key);
	}

	public void clear() {
		internalMap.clear();
	}

	public interface OnClearListener<K, O> {
		public void onClear(K k, O o);
	}

	public void clear(OnClearListener<K, O> l) {
		if (internalMap != null) {

			if (l != null) {
				Iterator<Entry<K, TimeVal<O>>> iter = internalMap.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<K, TimeVal<O>> entry = iter.next();
					l.onClear(entry.getKey(), entry.getValue().obj);
				}
			}
			internalMap.clear();
		}
	}

	public void update(K key, O obj) {
		TimeVal<O> v = internalMap.get(key);
		if (v == null) {
			insert(key, obj);
		} else {
			internalMap.get(key).UpTime();
			internalMap.get(key).obj = obj;
		}
	}

	private void insert(K key, O obj) {
		TimeVal<O> tv = new TimeVal<O>(obj);
		internalMap.put(key, tv);

		if (internalMap.size() > maxSize) {
			ArrayList<Entry<K, TimeVal<O>>> l = new ArrayList<Entry<K, TimeVal<O>>>(internalMap.entrySet());
			Collections.sort(l, new Comparator<Map.Entry<K, TimeVal<O>>>() {
				public int compare(Map.Entry<K, TimeVal<O>> o1, Map.Entry<K, TimeVal<O>> o2) {
					return o1.getValue().t.compareTo(o2.getValue().t);
				}
			});
			int d = getDelSize();
			for (Entry<K, TimeVal<O>> e : l) {
				remove(e.getKey());
				d--;
				if (d <= 0) {
					break;
				}
			}
		}
	}
}
