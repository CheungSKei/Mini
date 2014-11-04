package com.mini.mn.compatible;

import com.mini.mn.util.Log;

public class AudioInfo {
	private static final String TAG = "VoipAudioInfo";

	public boolean hasAudioInfo = false;
	public int streamtype;
	public int smode;
	public int omode;
	public int ospeaker;
	public int operating;
	public int moperating;
	public int mstreamtype;
	
	public int mVoiceRecordMode;
	
	public int playEndDelay;

	/**
	 * operating
	 * //高四位为enable 低四位为disable 前三位为mode 后一位为是否setSpeakerphoneOn;
	 * 00010101
	 * 
	 */
	public AudioInfo() {
		reset();
	}

	public void reset() {
		hasAudioInfo = false;
		streamtype = -1;
		smode = -1;
		omode = -1;
		ospeaker = -1;
		operating = -1;
		moperating = -1;
		mstreamtype = -1;
		
		playEndDelay = -1;
		
		mVoiceRecordMode =-1;
	}

	public boolean canCustomSet() {
		if ((smode >= 0 && omode < 0) || (smode < 0 && omode >= 0) || ospeaker > 0) {
			return true;
		}
		return false;
	}

	public boolean canBitSet() {
		return operating >= 0;
	}

	public boolean canMBitSet() {
		return moperating >= 0;
	}

	public int getEnableMode() {
		if (canBitSet()) {
			int mode = (operating & 0x000000e0) >> 5;
			Log.d(TAG, "getEnableMode " + mode);
			if (mode == 7) {
				return -1;
			}
			return mode;
		}
		return -1;
	}

	public boolean enableSpeaker() {
		if (canBitSet()) {
			int enable = (operating & 0x00000010);
			Log.d(TAG, "enableSpeaker " + (enable > 0));
			return enable > 0;
		}
		return false;
	}

	public int getDisableMode() {
		if (canBitSet()) {
			int mode = (operating & 0x0000000e) >> 1;
			Log.d(TAG, "getDisableMode " + mode);
			if (mode == 7) {
				return -1;
			}
			return mode;
		}
		return -1;
	}

	public boolean disableSpeaker() {
		if (canBitSet()) {
			int enable = (operating & 0x00000001);
			Log.d(TAG, "disableSpeaker " + (enable > 0));
			return enable > 0;
		}
		return false;
	}

	public int getMEnableMode() {
		if (canMBitSet()) {
			int mode = (moperating & 0x000000e0) >> 5;
			Log.d(TAG, "getEnableMode " + mode);
			if (mode == 7) {
				return -1;
			}
			return mode;
		}
		return -1;
	}

	public boolean enableMSpeaker() {
		if (canMBitSet()) {
			int enable = (moperating & 0x00000010);
			Log.d(TAG, "enableSpeaker " + (enable > 0));
			return enable > 0;
		}
		return false;
	}

	public int getMDisableMode() {
		if (canMBitSet()) {
			int mode = (moperating & 0x0000000e) >> 1;
			Log.d(TAG, "getDisableMode " + mode);
			if (mode == 7) {
				return -1;
			}
			return mode;
		}
		return -1;
	}

	public boolean disableMSpeaker() {
		if (canMBitSet()) {
			int enable = (moperating & 0x00000001);
			Log.d(TAG, "disableSpeaker " + (enable > 0));
			return enable > 0;
		}
		return false;
	}

	public void dump() {
		Log.d(TAG, "streamtype " + streamtype);
		Log.d(TAG, "smode " + smode);
		Log.d(TAG, "omode " + omode);
		Log.d(TAG, "ospeaker " + ospeaker);
		Log.d(TAG, "operating" + operating);
		Log.d(TAG, "moperating" + moperating);
		Log.d(TAG, "mstreamtype" + mstreamtype);
		Log.d(TAG, "mVoiceRecordMode" + mVoiceRecordMode);
	}
}
