package com.mini.mn.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.widget.Toast;

import com.mini.mn.algorithm.MD5;
import com.mini.mn.algorithm.TypeTransform;
import com.mini.mn.app.MiniApplication;

/**
 * 
 * @author kirozhao
 * 
 */
public class Log {
	private static final String TAG = "MicroMsg.SDK.Log";

	public static final int LEVEL_VERBOSE = 0;
	public static final int LEVEL_DEBUG = 1;
	public static final int LEVEL_INFO = 2;
	public static final int LEVEL_WARNING = 3;
	public static final int LEVEL_ERROR = 4;
	public static final int LEVEL_FATAL = 5;
	public static final int LEVEL_NONE = 6;

	private static int level = LEVEL_VERBOSE;

	private static PrintStream p;
	private static byte[] desKey = null;

	protected Log() {

	}

	// removed in 4.0
	// public static native boolean setLogLevel(int level);

	// public static voidW setOutputStream(OutputStream os) {
	// Log.p = new PrintStream(os);
	// }

	public static void setOutputPath(String path, String logType, String username, int clientver) {
		if (path == null || path.length() == 0 || username == null || username.length() == 0) {
			return;
		}

		try {
			File file = new File(path);
			if (!file.exists()) {
				return;
			}

			final InputStream is = file.length() > 0 ? new FileInputStream(path) : null;
			final OutputStream os = new FileOutputStream(path, true);

			setOutputStream(is, os, logType, username, clientver);

			if (is != null) {
				is.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void setOutputStream(InputStream is, OutputStream os, String logType, String username, int clientver) {

		final long logCreateTime;

		try {
			Log.p = new PrintStream(new BufferedOutputStream(os));

			if (is != null) {
				final BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				logType = Util.nullAsNil(reader.readLine()).substring(2).trim(); // type
				username = Util.nullAsNil(reader.readLine()).substring(2).trim(); // username
				logCreateTime = Util.getLong(Util.nullAsNil(reader.readLine()).trim().substring(2), 0);
				Log.d(TAG, "using provided info, type=%s, user=%s, createtime=%d", logType, username, logCreateTime);

			} else {
				logCreateTime = System.currentTimeMillis();
				LogHelper.initLogHeader(p, logType, username, logCreateTime, clientver);
			}

			genDesKey(username, logCreateTime);

			android.util.Log.d(TAG, "set up out put stream");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reset() {
		p = null;
		desKey = null;
	}

	public static void setLevel(final int level, final boolean jni) {
		Log.level = level;
		android.util.Log.w(TAG, "new log level: " + level);

		if (jni) {
			// setLogLevel(level);
			android.util.Log.e(TAG, "no jni log level support");
		}
	}

	public static int getLevel() {
		return level;
	}

	/**
	 * use f(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void f(final String tag, final String msg) {
		f(tag, msg, (Object[]) null);
	}

	/**
	 * use e(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(final String tag, final String msg) {
		e(tag, msg, (Object[]) null);
	}

	/**
	 * use w(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(final String tag, final String msg) {
		w(tag, msg, (Object[]) null);
	}

	/**
	 * use i(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(final String tag, final String msg) {
		i(tag, msg, (Object[]) null);
	}

	/**
	 * use d(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(final String tag, final String msg) {
		d(tag, msg, (Object[]) null);
	}

	/**
	 * use v(tag, format, obj) instead
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(final String tag, final String msg) {
		v(tag, msg, (Object[]) null);
	}

	public static void f(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_FATAL) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.e(tag, msg);
			LogHelper.writeToStream(p, desKey, "F/" + tag, msg);

			// toast must in main thread
			MNHandlerThread.postToMainThread(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(MiniApplication.getContext(), msg, Toast.LENGTH_LONG).show();
				}

			});
		}
	}

	public static void e(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_ERROR) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.e(tag, msg);
			LogHelper.writeToStream(p, desKey, "E/" + tag, msg);
		}
	}

	public static void w(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_WARNING) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.w(tag, msg);
			LogHelper.writeToStream(p, desKey, "W/" + tag, msg);
		}
	}

	public static void i(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_INFO) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.i(tag, msg);
			LogHelper.writeToStream(p, desKey, "I/" + tag, msg);
		}
	}

	public static void d(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_DEBUG) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.d(tag, msg);
			LogHelper.writeToStream(p, desKey, "D/" + tag, msg);
		}
	}

	public static void v(final String tag, final String format, final Object... obj) {
		if (level <= LEVEL_VERBOSE) {
			final String msg = (obj == null ? format : String.format(format, obj));
			android.util.Log.v(tag, msg);
			LogHelper.writeToStream(p, desKey, "V/" + tag, msg);
		}
	}

	private static void genDesKey(String username, long logCreateTime) {
		StringBuffer sb = new StringBuffer();
		sb.append(username);
		sb.append(logCreateTime);
		sb.append("dfdhgc");
		desKey = (MD5.getMessageDigest(sb.toString().getBytes())).substring(7, 21).getBytes();
	}

	private static final String SYS_INFO;
	static {
		final StringBuilder sb = new StringBuilder();
		sb.append("VERSION.RELEASE:[" + android.os.Build.VERSION.RELEASE);
		sb.append("] VERSION.CODENAME:[" + android.os.Build.VERSION.CODENAME);
		sb.append("] VERSION.INCREMENTAL:[" + android.os.Build.VERSION.INCREMENTAL);
		sb.append("] BOARD:[" + android.os.Build.BOARD);
		sb.append("] DEVICE:[" + android.os.Build.DEVICE);
		sb.append("] DISPLAY:[" + android.os.Build.DISPLAY);
		sb.append("] FINGERPRINT:[" + android.os.Build.FINGERPRINT);
		sb.append("] HOST:[" + android.os.Build.HOST);
		sb.append("] MANUFACTURER:[" + android.os.Build.MANUFACTURER);
		sb.append("] MODEL:[" + android.os.Build.MODEL);
		sb.append("] PRODUCT:[" + android.os.Build.PRODUCT);
		sb.append("] TAGS:[" + android.os.Build.TAGS);
		sb.append("] TYPE:[" + android.os.Build.TYPE);
		sb.append("] USER:[" + android.os.Build.USER + "]");

		SYS_INFO = sb.toString();
	}

	public static String getSysInfo() {
		return SYS_INFO;
	}
}

final class LogHelper {

	private LogHelper() {
	}

	private static final String FORMAT = "MM-dd HH:mm:ss SSS";
	private static final java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(FORMAT);
	private static final byte[] END_ARRAY = new byte[] {
			4,
			0,
			0,
			0,
			(byte) 0xFF,
			(byte) 0xFF,
			(byte) 0xFF,
			0 };

	// DES encryption, 'LV' to serial
	public static void writeToStream(final PrintStream stream, byte[] desKey, final String tag, final String msg) {
		if (stream == null || Util.isNullOrNil(desKey) || Util.isNullOrNil(tag) || Util.isNullOrNil(msg)) {
			return;
		}

		synchronized (stream) {

			StringBuffer sb = new StringBuffer();
			sb.append(formatter.format(System.currentTimeMillis()));
			sb.append(" ").append(tag).append(" ").append(msg);

			String plainText = sb.toString();

			try {
				// DES.DESEncrypt(cipherText, plainText.getBytes(), desKey);

				// SecureRandom sr = new SecureRandom();
				// 从原始密匙数据创建DESKeySpec对象
				DESKeySpec dks = new DESKeySpec(desKey);
				// 创建一个密匙工厂，然后用它把DESKeySpec转换成
				// 一个SecretKey对象
				SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
				SecretKey securekey = keyFactory.generateSecret(dks);
				// Cipher对象实际完成加密操作
				Cipher cipher = Cipher.getInstance("DES");
				// 用密匙初始化Cipher对象
				cipher.init(Cipher.ENCRYPT_MODE, securekey);
				// 现在，获取数据并加密
				// 正式执行加密操作
				byte[] cipherText = cipher.doFinal(plainText.getBytes());

				stream.write(TypeTransform.intToByteArrayLH(cipherText.length));
				stream.write(cipherText);
				stream.write(END_ARRAY);

			} catch (Exception e) {
				e.printStackTrace();
			}
			stream.flush();

		}
	}

	public static void initLogHeader(final PrintStream stream, final String logType, final String username, final long logCreateTime, final int clientver) {
		if (stream == null || Util.isNullOrNil(username) || logCreateTime == 0) {
			return;
		}

		int count = 1;
		stream.println((count++) + " " + logType);
		stream.println((count++) + " " + username);
		stream.println((count++) + " " + logCreateTime);
		stream.println((count++) + " " + Integer.toHexString(clientver));
		stream.println((count++) + " " + android.os.Build.VERSION.RELEASE);
		stream.println((count++) + " " + android.os.Build.VERSION.CODENAME);
		stream.println((count++) + " " + android.os.Build.VERSION.INCREMENTAL);
		stream.println((count++) + " " + android.os.Build.BOARD);
		stream.println((count++) + " " + android.os.Build.DEVICE);
		stream.println((count++) + " " + android.os.Build.DISPLAY);
		stream.println((count++) + " " + android.os.Build.FINGERPRINT);
		// stream.println((count++) + " " + android.os.Build.HARDWARE); // for 1.6 compatible
		stream.println((count++) + " " + android.os.Build.HOST);
		stream.println((count++) + " " + android.os.Build.MANUFACTURER);
		stream.println((count++) + " " + android.os.Build.MODEL);
		stream.println((count++) + " " + android.os.Build.PRODUCT);
		// stream.println((count++) + " " + android.os.Build.RADIO); // for 1.6 compatible
		// p.println((count++) + " " + android.os.Build.SERIAL); // throw exception
		stream.println((count++) + " " + android.os.Build.TAGS);
		stream.println((count++) + " " + android.os.Build.TYPE);
		stream.println((count++) + " " + android.os.Build.USER);
		stream.println(); // newline —— end of log header
		stream.flush();
	}
}
