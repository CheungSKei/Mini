package com.mini.mn.util;

import junit.framework.Assert;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;


public class MNHandlerThread {
	private static final String TAG = "MicroMsg.MNHandlerThread";

	public interface IWaitWorkThread {
		boolean doInBackground();

		boolean onPostExecute();
	}

	private HandlerThread thread = null;
	private Handler workerHandler = null;

	private void init() {
		Log.d(TAG, "MNHandlerThread init [%s]", Util.getStack());
		workerHandler = null;
		thread = new HandlerThread("MNHandlerThread", Thread.MIN_PRIORITY);
		thread.start();
	}

	public MNHandlerThread() {
		init();
	}

	public Looper getLooper() {
		return thread.getLooper();
	}

	public Handler getWorkerHandler() {
		if (workerHandler == null) {
			workerHandler = new Handler(thread.getLooper());
		}
		return workerHandler;
	}

	public int reset(final IWaitWorkThread waitWorkThread) {
		final IWaitWorkThread w = new IWaitWorkThread() {

			@Override
			public boolean onPostExecute() {
				if (waitWorkThread != null) {
					return waitWorkThread.onPostExecute();
				}
				return true;
			}

			@Override
			public boolean doInBackground() {
				if (waitWorkThread != null) {
					return waitWorkThread.doInBackground();
				}
				thread.quit();
				init();
				return true;
			}
		};
		return postAtFrontOfWorker(w);
	}

	public interface ResetCallback {
		public void callback();
	}

	public int syncReset(final ResetCallback callback) {
		Assert.assertTrue("syncReset should in mainThread", isMainThread());

		final Object lock = new byte[0];
		final IWaitWorkThread w = new IWaitWorkThread() {

			@Override
			public boolean onPostExecute() {
				Log.d(TAG, "syncReset onPostExecute");
				return true;
			}

			@Override
			public boolean doInBackground() {
				Log.d(TAG, "syncReset doInBackground");
				thread.quit();
				if (callback != null) {
					callback.callback();
				}
				init();
				synchronized (lock) {
					lock.notify();
				}
				return true;
			}
		};

		int ret = 0;
		synchronized (lock) {
			ret = postAtFrontOfWorker(w);
			if (ret == 0) {
				try {
					lock.wait();
				} catch (Exception e) {
				}
			}
		}
		return ret;
	}

	public int postToWorker(final Runnable r) {
		if (r == null) {
			return -1;
		}
		getWorkerHandler().post(r);
		return 0;
	}

	public int postAtFrontOfWorker(final IWaitWorkThread waitWorkThread) {
		if (waitWorkThread == null) {
			return -1;
		}
		boolean ret = (new Handler(getLooper())).postAtFrontOfQueue(new Runnable() {

			@Override
			public void run() {
				waitWorkThread.doInBackground();
				postToMainThreadAtFrontOfQueue(new Runnable() {
					public void run() {
						waitWorkThread.onPostExecute();
					}
				});
			}
		});
		return ret ? 0 : -2;
	}

	public static long mainThreadID = -1;

	public static void setMainThreadID(final long id) {
		if (mainThreadID < 0 && id > 0) {
			mainThreadID = id;
		}
	}

	public static boolean isMainThread() {
		Assert.assertFalse("mainThreadID not init ", mainThreadID == -1);
		return Thread.currentThread().getId() == mainThreadID;
	}

	public static void postToMainThread(final Runnable run) {
		if (run == null) {
			return;
		}
		new Handler(Looper.getMainLooper()).post(run);
	}

	public static void postToMainThreadDelayed(final Runnable run, long delayed) {
		if (run == null) {
			return;
		}
		new Handler(Looper.getMainLooper()).postDelayed(run, delayed);
	}

	private static void postToMainThreadAtFrontOfQueue(final Runnable run) {
		if (run == null) {
			return;
		}
		new Handler(Looper.getMainLooper()).postAtFrontOfQueue(run);
	}
}
