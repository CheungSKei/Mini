package com.mini.mn.booter;

import java.util.HashMap;

import com.mini.mn.platformtools.Test;
import com.mini.mn.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class Shell {

	private static final String TAG = "MiniMsg.Shell";

	private Receiver receiver = null;

	private static HashMap<String, Action> ACTIONS = new HashMap<String, Action>();

	private static IntentFilter FILTER = new IntentFilter();
	static {
		createAction("wechat.shell.SET_NEXTRET", new Action() {

			@Override
			public void exec(Intent intent) {
				final int type = intent.getIntExtra("type", Integer.MAX_VALUE);
				final int err = intent.getIntExtra("error", 0);
				if (type == Integer.MAX_VALUE || err == 0) {
					return;
				}
				Log.w(TAG, "kiro set Test.pushNextErrorRet(type=%d, err=%d)", type, err);
				Test.pushNextErrorRet(type, err);
			}

		});

		createAction("wechat.shell.SET_LOGLEVEL", new Action() {

			@Override
			public void exec(Intent intent) {
				int level = intent.getIntExtra("level", Log.LEVEL_VERBOSE);
				Log.w(TAG, "kiro set Log.level=%d", Log.getLevel());
				Log.setLevel(level, false);
			}

		});

		createAction("wechat.shell.SET_CDNTRANS", new Action() {

			@Override
			public void exec(Intent intent) {
				Test.forceCDNTrans = intent.getBooleanExtra("value", false);
				Log.w(TAG, "kiro set Test.forceCDNTrans=%b", Test.forceCDNTrans);
			}

		});
		
		createAction("wechat.shell.SET_DKTEST", new Action() {

			@Override
			public void exec(Intent intent) {
				Test.TestForDKKey =  intent.getIntExtra("key", 0);
				Test.TestForDKVal =  intent.getIntExtra("val", 0);
				Log.w(TAG, "dkshell set [%d %d]", Test.TestForDKKey , Test.TestForDKVal);
			}

		});

	}

	private static void createAction(String action, Action a) {
		FILTER.addAction(action);
		ACTIONS.put(action, a);
	}

	public interface Action {
		void exec(Intent intent);
	}

	public static class Receiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();

			final Action a = ACTIONS.get(action);
			if (a == null) {
				Log.e(TAG, "no action found for %s", action);
				return;
			}

			Log.e(TAG, "shell action %s", action);
			a.exec(intent);
		}

	}

	public void init(Context context) {
		if (receiver == null) {
			receiver = new Receiver();
			context.registerReceiver(receiver, FILTER);
		}
	}

	public void destroy(Context context) {
		if (receiver != null) {
			context.unregisterReceiver(receiver);
			receiver = null;
		}
	}

}
