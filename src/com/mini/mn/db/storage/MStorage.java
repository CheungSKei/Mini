package com.mini.mn.db.storage;

import android.os.Looper;

/**
 * basic storage class with simple event support
 * 
 * @author kirozhao
 * 
 */
public abstract class MStorage {

	/**
	 * base anonymous event listener for MStorage
	 * 
	 * @author kirozhao
	 * 
	 */
	public interface IOnStorageChange {

		/**
		 * called when received notify event
		 * 
		 * @param event
		 *            event name
		 */
		void onNotifyChange(String event);
	}
	
	public interface IOnStorageLoaded {
		
		/**
		 * call when the storage is inited and data is loaded
		 */
		void onNotifyLoaded();
	}

	private final MStorageEvent<IOnStorageChange, String> defaults = new MStorageEvent<IOnStorageChange, String>() {

		@Override
		protected void processEvent(final IOnStorageChange listener, final String event) {
			MStorage.this.processEvent(listener, event);
		}
	};
	
	private final MStorageEvent<IOnStorageLoaded, String> loadedListener = new MStorageEvent<IOnStorageLoaded, String>() {

		@Override
		protected void processEvent(final IOnStorageLoaded listener, final String event) {
			MStorage.this.processLoaded(listener, event);
		}
	};
	

	/**
	 * lock event producer and cache all event
	 */
	public void lock() {
		defaults.lock();
	}

	/**
	 * unlock event producer and fire all cached event
	 */
	public void unlock() {
		defaults.unlock();
	}

	/**
	 * add event listener
	 * 
	 * @param listener
	 */
	public void add(final IOnStorageChange listener) {
		defaults.add(listener ,  Looper.getMainLooper());
	}
	
	public void addLoadedListener(final IOnStorageLoaded listener) {
		loadedListener.add(listener , Looper.getMainLooper());
	}

	/**
	 * remove event listener
	 * 
	 * @param listener
	 */
	public void remove(final IOnStorageChange listener) {
		defaults.remove(listener);
	}
	
	public void removeLoadedListener(final IOnStorageLoaded listener) {
		loadedListener.remove(listener);
	}

	private void processEvent(final IOnStorageChange listener, final String event) {
		listener.onNotifyChange(event);
	}
	
	private void processLoaded(final IOnStorageLoaded listener, final String event) {
		listener.onNotifyLoaded();
	}

	/**
	 * send default event and notify all listeners
	 */
	public void doNotify() {
		defaults.event("*");
		defaults.doNotify();
	}

	/**
	 * send named event and notify all listeners
	 * 
	 * @param name
	 *            event name
	 */
	public void doNotify(final String name) {
		defaults.event(name);
		defaults.doNotify();
	}
	
//	public abstract void init();
//	public abstract void release();
	
}
