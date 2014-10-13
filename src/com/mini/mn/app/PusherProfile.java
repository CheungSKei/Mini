package com.mini.mn.app;

import com.mini.mn.BuildConfig;
import com.mini.mn.booter.Shell;

import android.content.res.Configuration;

final class PusherProfile extends MiniApplication.Profile {
	public static final String PROCESS_NAME = MiniApplicationContext.getPackageName() + ":push";

	private Shell cp = new Shell();

	public PusherProfile(MiniApplication app) {
		super(app);
	}

	@Override
	public void onCreate() {

		if (BuildConfig.DEBUG) {
			cp.init(MiniApplicationContext.getContext());
		}
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		// TODO Auto-generated method stub

	}

	@Override
	public String toString() {
		return PROCESS_NAME;
	}
}
