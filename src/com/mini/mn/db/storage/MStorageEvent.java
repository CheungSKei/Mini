package com.mini.mn.db.storage;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;

import android.os.Handler;
import android.os.Looper;

/**
 * base MStorageEvent template
 * 
 * @author kirozhao
 * 
 * @param <T>
 *            type of event listener
 * @param <E>
 *            type of event
 */
public abstract class MStorageEvent<T, E> {

	// private static final String TAG = "MicroMsg.SDK.MStorageEvent";

	private int locks = 0;

	private final Hashtable<T, Object> listeners = new Hashtable<T, Object>();
	private final CopyOnWriteArraySet<E> events = new CopyOnWriteArraySet<E>();

	/**
	 * increase lock count
	 */
	public void lock() {
		locks++;
	}

	/**
	 * decrease lock count
	 */
	public void unlock() {
		locks--;
		if (locks <= 0) {
			locks = 0;
			handleListeners();
		}
	}

	/**
	 * check locked status
	 * 
	 * @return is locked
	 */
	public boolean isLocked() {
		return locks > 0;
	}

	/**
	 * add event listener
	 * 
	 * @param listener
	 */
	public synchronized void add(final T listener, final Looper looper) {
		if (!listeners.containsKey(listener)) {
			if (looper != null) {
				listeners.put(listener, looper);

			} else {
				listeners.put(listener, new Object());
			}
		}
	}

	/**
	 * remove event listener
	 * 
	 * @param listener
	 */
	public synchronized void remove(final T listener) {
		listeners.remove(listener);
	}

	/**
	 * clear all event listener
	 */
	public synchronized void removeAll() {
		listeners.clear();
	}

	private synchronized Vector<T> cloneAll() {
		final Vector<T> set = new Vector<T>();
		set.addAll(listeners.keySet());
		return set;
	}

	/**
	 * prepare event
	 * 
	 * @param e
	 *            event object
	 * @return true if event hasn't been added
	 */
	public boolean event(final E e) {
		return events.add(e);
	}

	/**
	 * fire event to all listeners
	 */
	public void doNotify() {
		if (!isLocked()) {
			handleListeners();
		}
	}

	private void handleListeners() {
		final Vector<T> set = cloneAll();

		if (set == null || set.size() <= 0) {
			return;
		}

		Map<Looper, Handler> mapHandler = new HashMap<Looper, Handler>();
		for (final T li : set) {
			Object obj = listeners.get(li);
			for (final E s : events) {
				if (s == null) {
					continue;
				}

				if (obj == null) {
					continue;
				}

				if (obj instanceof Looper) {
					Looper loop = (Looper) obj;
					Handler h = mapHandler.get(loop);
					if (h == null) {
						h = new Handler(loop);
						mapHandler.put(loop, h);
					}

					h.post(new Runnable() {
						@Override
						public void run() {
							processEvent(li, s);
						}
					});

				} else {
					processEvent(li, s);
				}
			}
		}
		events.clear();
	}

	/**
	 * abstract method for listener to process event
	 * 
	 * @param listener
	 *            listener of type T
	 * @param event
	 *            event of type E
	 */
	protected abstract void processEvent(T listener, E event);
}
