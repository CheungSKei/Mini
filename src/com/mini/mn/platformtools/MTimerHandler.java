package com.mini.mn.platformtools;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class MTimerHandler extends Handler {
	private static int timerID; // 静态变量，保证ID唯一。当ID超过整形最大值时，应该把它恢复为0
	private static final int MAX_TIMERID = 0x2000;
	private final int myTimerID;
	private final boolean mLoop;
	private long mLoopInterval = 0;

	public interface CallBack {
		boolean onTimerExpired();
	}

	private final CallBack mCallBack;

	public MTimerHandler(final CallBack callback, final boolean loop) {
		mCallBack = callback;
		myTimerID = incTimerID();
		mLoop = loop;
	}

	public MTimerHandler(final Looper looper, final CallBack callback, final boolean loop) {
		super(looper);
		mCallBack = callback;
		myTimerID = incTimerID();
		mLoop = loop;
	}

	private static int incTimerID() {
		if (timerID >= MAX_TIMERID) {
			timerID = 0;
		}
		return ++timerID;
	}

	@Override
	protected void finalize() throws Throwable {
		stopTimer();
		super.finalize();
	}

	@Override
	public void handleMessage(final Message msg) {
		if (msg.what == myTimerID) {
			if (mCallBack == null) {
				return;
			}

			if (!mCallBack.onTimerExpired()) {
				return;
			}

			if (mLoop) {
				sendEmptyMessageDelayed(myTimerID, mLoopInterval);
			}
		}
	}

	public void startTimer(final long interval) {
		mLoopInterval = interval;

		stopTimer();
		sendEmptyMessageDelayed(myTimerID, interval);
	}

	public void stopTimer() {
		removeMessages(myTimerID);
	}

	public boolean stopped() {
		return !hasMessages(myTimerID);
	}

}
