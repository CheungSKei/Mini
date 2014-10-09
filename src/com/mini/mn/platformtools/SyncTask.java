package com.mini.mn.platformtools;

import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import android.os.Handler;

public abstract class SyncTask<R> {
	private static final String TAG = "MicroMsg.SDK.SyncTask";

	private R result;
	private Object lock = new Object();

	private final long timeout;

	// statistic code
	private long begin;
	private long wait;

	public SyncTask() {
		this(0, null);
	}

	public SyncTask(final long timeout, final R defaultRet) {
		this.timeout = timeout;
		this.result = defaultRet;
	}

	public void setResult(final R ret) {
		result = ret;
		synchronized (lock) {
			lock.notify();
		}

	}

	private Runnable task = new Runnable() {

		@Override
		public void run() {
			wait = Util.ticksToNow(begin);
			setResult(SyncTask.this.run());
		}

	};

	public R exec(final Handler handler) {

		if (handler == null) {
			// same thread
			Log.d(TAG, "null handler, task in exec thread, return now");
			return run();
		}

		final long runningTid = Thread.currentThread().getId();
		final long handlerTid = handler.getLooper().getThread().getId();

		if (runningTid == handlerTid) {
			// same thread
			Log.d(TAG, "same tid, task in exec thread, return now");
			return run();
		}

		// post and run
		begin = Util.currentTicks();
		try {
			synchronized (lock) {
				handler.post(task);
				lock.wait(timeout);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		final long run = Util.ticksToNow(begin);
		Log.v(TAG, "sync task done, return=%s, cost=%d(wait=%d, run=%d)", "" + result, run, wait, run - wait);
		return result;
	}

	protected abstract R run();

}
