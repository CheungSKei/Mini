package com.mini.mn.network.socket;

import java.util.List;
import java.util.Vector;

import junit.framework.Assert;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.mini.mn.platformtools.MMHandlerThread;
import com.mini.mn.platformtools.MTimerHandler;
import com.mini.mn.task.BaseSocketTask;
import com.mini.mn.util.Log;

/**
 * 网络请求队列
 * 
 * @version 1.0.0
 * @date 2014-10-27
 * @author S.Kei.Cheung
 */
public final class NetSceneQueue {
	private static final String TAG = "MiniMsg.NetSceneQueue";

	private static final int MAX_RUNNING_SIZE = 20;

	private static NetSceneQueue instance = null;
	private IDispatcher autoAuth;
	private MMHandlerThread workerThread = null;
	private Vector<BaseSocketTask> runningQueue;
	private Vector<BaseSocketTask> waitingQueue;
	private final Handler handler;
	private boolean foreground = false;

	public interface IOnQueueIdle {

		void onQueueIdle(NetSceneQueue queue, boolean canQueue);

		void onPrepareDispatcher(NetSceneQueue queue);
	}

	private final IOnQueueIdle onQueueIdle;
	private int killProcessDelay = 15 * 60 * 1000; // 15 minutes
	private boolean thisProcessCanQueue = false; // very dangerous! be careful
	private MTimerHandler queueIdlePusher = new MTimerHandler(new MTimerHandler.CallBack() {

		@Override
		public boolean onTimerExpired() {
			if (onQueueIdle == null) {
				return false;
			}

			Log.v(TAG, "onQueueIdle, running=%d, waiting=%d, foreground=%b", runningQueue.size(), waitingQueue.size(), foreground);
			onQueueIdle.onQueueIdle(NetSceneQueue.this, thisProcessCanQueue && runningQueue.isEmpty() && waitingQueue.isEmpty());
			return true;
		}
	}, true);

	public void setKillProcessDelay(int interval) {
		killProcessDelay = interval;
	}

	public void setKillProcessStatus(boolean canKillProcess) {
		this.thisProcessCanQueue = canKillProcess;
		if (!thisProcessCanQueue) {
			queueIdlePusher.stopTimer();

		} else {
			Log.e(TAG, "the working process is ready to be killed");
			queueIdlePusher.startTimer(killProcessDelay);
		}
	}

	public boolean isForeground() {
		return foreground;
	}

	private NetSceneQueue(final IOnQueueIdle onQueueIdle) {
		runningQueue = new Vector<BaseSocketTask>();
		waitingQueue = new Vector<BaseSocketTask>();

		handler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(final Message msg) {
				doScene((BaseSocketTask) msg.obj);
			}
		};

		this.onQueueIdle = onQueueIdle;
	}

	public void setAutoAuth(final IDispatcher autoAuth) {
		this.autoAuth = autoAuth;

		// going on
		waiting2running();
	}

	public void setWorkerThread(MMHandlerThread handlerThread) {
		this.workerThread = handlerThread;
	}

	public static NetSceneQueue getInstance(IOnQueueIdle onQueueIdle) {
		if (instance == null) {
			instance = new NetSceneQueue(onQueueIdle);
		}
		return instance;
	}

	public void reset() {
		clearRunningQueue();
		clearWaitingQueue();
	}
	
	private void clearWaitingQueue() {
		List<BaseSocketTask> tempWaitingQueue = waitingQueue;
		waitingQueue = new Vector<BaseSocketTask>();

		tempWaitingQueue.clear();
	}

	public void clearRunningQueue() {
		List<BaseSocketTask> tempRunningQueue = runningQueue;
		runningQueue = new Vector<BaseSocketTask>();

		tempRunningQueue.clear();
	}

	public void resetDispatcher() {
		if (autoAuth != null) {
			autoAuth = null;
		}
	}

	public IDispatcher getDispatcher() {
		return autoAuth;
	}

	public void cancel(final int sceneHashCode) {
		workerThread.postToWorker(new Runnable() {

			@Override
			public void run() {
				cancelImp(sceneHashCode);
			}
		});
	}

	private void cancelImp(final int sceneHashCode) {
		//
		for (BaseSocketTask scene : runningQueue) {
			if (scene != null && scene.hashCode() == sceneHashCode) {
				cancel(scene);
				return;
			}
		}
		for (BaseSocketTask scene : waitingQueue) {
			if (scene != null && scene.hashCode() == sceneHashCode) {
				cancel(scene);
				return;
			}
		}
	}

	public void cancel(final BaseSocketTask scene) {
		if (scene == null) {
			return;
		}
		waitingQueue.remove(scene);
		runningQueue.remove(scene);
	}

	public boolean doScene(final BaseSocketTask scene) {
		return doScene(scene, 0);
	}

	public boolean doScene(final BaseSocketTask scene, final int afterSec) {
		Assert.assertTrue(scene != null || afterSec >= 0);
		Assert.assertTrue("worker thread has not been set", this.workerThread != null);

		doSceneImp(scene, afterSec);
		return true;
	}

	private void doSceneImp(final BaseSocketTask scene, final int afterSec) {
		Log.i(TAG, "doSceneImp start: id=" + scene.hashCode() + " cur_running_cnt=" + runningQueue.size() + " cur_waiting_cnt=" + waitingQueue.size());
		if (afterSec == 0 && canDo(scene) && autoAuth != null) {
			Log.i(TAG, "run: id=" + scene.hashCode() + " cur_running_cnt=" + runningQueue.size());
			runningQueue.add(scene);
			Log.i(TAG, "runningQueue_size=" + runningQueue.size());

			// 执行doScene
			this.workerThread.postToWorker(new Runnable() {

				@Override
				public void run() {
					runningQueue.remove(scene);
					handler.post(new Runnable() {
						@Override
						public void run() {
							scene.commit();
						}
					});
				}
			});

		} else {
			if (afterSec > 0) {
				final Message msg = Message.obtain();
				msg.obj = scene;
				handler.sendMessageDelayed(msg, afterSec);
				Log.i(TAG, "timed: id=" + scene.hashCode() + " cur_after_sec=" + afterSec);

			} else {
				Log.i(TAG, "waited: id=" + scene.hashCode() + " cur_waiting_cnt=" + waitingQueue.size());
				waitingQueue.add(scene);
				Log.i(TAG, "waitingQueue_size = " + waitingQueue.size());
			}
		}
	}

	/**
	 * 把最大优先权的Scene从waiting转到running
	 */
	private void waiting2running() {
		if (waitingQueue.size() > 0) {
			BaseSocketTask scene = waitingQueue.get(0);
			int maxPriority = scene.getPriority();
			for (int i = 1; i < waitingQueue.size(); i++) {
				if (waitingQueue.get(i).getPriority() > maxPriority) {
					if (canDo(waitingQueue.get(i))) {
						scene = waitingQueue.get(i);
						maxPriority = scene.getPriority();
					}
				}
			}
			waitingQueue.remove(scene);
			Log.i(TAG, "waiting2running waitingQueue_size = " + waitingQueue.size());
			doSceneImp(scene, 0);
		}
	}

	/**
	 * 如果runningQueue队列还没有达到上限,则可运行
	 * @param scene
	 * @return
	 */
	private boolean canDo(final BaseSocketTask scene) {
		if (runningQueue.size() >= MAX_RUNNING_SIZE) {
			return false;
		}
		return true;
	}

	private static final int PREPARE_CHECK_INTERVAL = 100;

	private void prepareDispatcher() {
		if (onQueueIdle == null) {
			Log.e(TAG, "prepare dispatcher failed, null queue idle");
			return;
		}

		onQueueIdle.onPrepareDispatcher(this);

		new MTimerHandler(Looper.getMainLooper(), new MTimerHandler.CallBack() {

			private long timeout = 1000 / PREPARE_CHECK_INTERVAL;

			@Override
			public boolean onTimerExpired() {
				if (autoAuth == null && timeout-- > 0) {
					return true;
				}

				waiting2running();
				return false;
			}

		}, true).startTimer(PREPARE_CHECK_INTERVAL);
	}

}
