package com.mini.mn.compatible;

import com.mini.mn.util.Log;


public class CommonInfo {

	private static final String TAG = "MiniMsg.CommonInfo";
	public int js = DeviceInfo.NOTCONFIG;
	public int stopBluetoothInBR = DeviceInfo.NOTCONFIG;
	public int stopBluetoothInBU = DeviceInfo.NOTCONFIG;
	public int startBluetoothSco = DeviceInfo.NOTCONFIG;
	public int setBluetoothScoOn = DeviceInfo.NOTCONFIG;
	public int voiceSearchFastMode = DeviceInfo.NOTCONFIG;
	public int pcmBufferRate = DeviceInfo.NOTCONFIG;

	public int pcmReadMode = DeviceInfo.NOTCONFIG;
	public boolean hasCommonInfo = false;
	public int disableJs = -1;
	public int audioPrePro = DeviceInfo.NOTCONFIG;
	public int voicemsgfd = DeviceInfo.NOTCONFIG;
	public int htcvoicemode = DeviceInfo.NOTCONFIG;
	public int speexBufferRate = DeviceInfo.NOTCONFIG;
	public int linespe = DeviceInfo.NOTCONFIG;
	public int extvideo = DeviceInfo.NOTCONFIG;
	public int extvideosam = DeviceInfo.NOTCONFIG;
	public int sysvideodegree = DeviceInfo.NOTCONFIG;
	public int sysvideofdegree = DeviceInfo.NOTCONFIG;
	public int extsharevcard = DeviceInfo.NOTCONFIG;
	public int mmnotify = DeviceInfo.NOTCONFIG;
	public int audioformat = DeviceInfo.NOTCONFIG;
	public int qrcam = DeviceInfo.NOTCONFIG;

	public CommonInfo() {
		reset();
	}

	public void reset() {

		js = DeviceInfo.NOTCONFIG;
		stopBluetoothInBR = DeviceInfo.NOTCONFIG;
		stopBluetoothInBU = DeviceInfo.NOTCONFIG;
		startBluetoothSco = DeviceInfo.NOTCONFIG;
		setBluetoothScoOn = DeviceInfo.NOTCONFIG;
		voiceSearchFastMode = DeviceInfo.NOTCONFIG;
		pcmReadMode = DeviceInfo.NOTCONFIG;
		pcmBufferRate = DeviceInfo.NOTCONFIG;
		audioPrePro = DeviceInfo.NOTCONFIG;
		voicemsgfd = DeviceInfo.NOTCONFIG;
		htcvoicemode = DeviceInfo.NOTCONFIG;
		hasCommonInfo = false;
		disableJs = -1;
		speexBufferRate = DeviceInfo.NOTCONFIG;
		linespe = DeviceInfo.NOTCONFIG;
		extvideo = DeviceInfo.NOTCONFIG;
		extvideosam = DeviceInfo.NOTCONFIG;
		sysvideodegree = DeviceInfo.NOTCONFIG;
		mmnotify = DeviceInfo.NOTCONFIG;
		extsharevcard = DeviceInfo.NOTCONFIG;
		audioformat = DeviceInfo.NOTCONFIG;
		qrcam = DeviceInfo.NOTCONFIG;
		sysvideofdegree = DeviceInfo.NOTCONFIG;
	}

	public int getStopBluetoothInBR() {

		return stopBluetoothInBR;
	}

	public int getStopBluetoothInBU() {

		return stopBluetoothInBU;
	}

	public int getStartBluetoothSco() {

		return startBluetoothSco;
	}

	public int getBluetoothScoOn() {

		return setBluetoothScoOn;
	}

	public int getJs() {

		return js;
	}

	public void dump() {
		Log.d(TAG, "js " + js);
		Log.d(TAG, "stopBluetoothInBR " + stopBluetoothInBR);
		Log.d(TAG, "stopBluetoothInBU " + stopBluetoothInBU);
		Log.d(TAG, "setBluetoothScoOn " + setBluetoothScoOn);
		Log.d(TAG, "startBluetoothSco " + startBluetoothSco);
		Log.d(TAG, "voiceSearchFastMode " + voiceSearchFastMode);
		Log.d(TAG, "pcmReadMode " + pcmReadMode);
		Log.d(TAG, "pcmBufferRate " + pcmBufferRate);
		Log.d(TAG, "audioPrePro " + audioPrePro);
		Log.d(TAG, "voicemsgfd " + voicemsgfd);
		Log.d(TAG, "htcvoicemode " + htcvoicemode);
		Log.d(TAG, "speexBufferRate " + speexBufferRate);
		Log.d(TAG, "linespe " + linespe);
		Log.d(TAG, "extvideo " + extvideo);
		Log.d(TAG, "extvideosam " + extvideosam);
		Log.d(TAG, "sysvideodegree " + sysvideodegree);
		Log.d(TAG, "mmnotify " + mmnotify);
		Log.d(TAG, "extsharevcard " + extsharevcard);
		Log.d(TAG, "audioformat " + audioformat);
		Log.d(TAG, "qrcam " + qrcam);
		Log.d(TAG, "sysvideofdegree " + sysvideofdegree);
		
	}
}
