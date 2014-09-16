package com.mini.mn.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;

import com.mini.mn.R;
import com.mini.mn.algorithm.MD5;
import com.mini.mn.app.MiniApplication;

public final class Util {
	private static final String TAG = "MicroMsg.Util";

	public static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;

	public static final long MILLSECONDS_OF_SECOND = 1000L;
	public static final long MILLSECONDS_OF_MINUTE = 60 * MILLSECONDS_OF_SECOND;
	public static final long MILLSECONDS_OF_HOUR = 60 * MILLSECONDS_OF_MINUTE;
	public static final long MILLSECONDS_OF_DAY = 24 * MILLSECONDS_OF_HOUR;
	public static final long SECOND_OF_MINUTE = 60L;
	public static final long MINUTE_OF_HOUR = 60L;
	public static final long MAX_32BIT_VALUE = 0x00000000FFFFFFFFL;

	public static final int MIN_ACCOUNT_LENGTH = 6;
	public static final int MAX_ACCOUNT_LENGTH = 20;
	// public static final int MIN_PASSWORD_LENGTH = 6; //update the pwd length
	public static final int MIN_PASSWORD_LENGTH = 4;
	public static final int MAX_PASSWORD_LENGTH = 9;

	public static final int BIT_OF_KB = 10;
	public static final int BIT_OF_MB = 20;
	public static final int BYTE_OF_KB = 1 << BIT_OF_KB;
	public static final int BYTE_OF_MB = 1 << BIT_OF_MB;

	public static final int MASK_4BIT = 0x0F;
	public static final int MASK_8BIT = 0xFF;
	public static final int MASK_16BIT = 0xFFFF;
	public static final int MASK_32BIT = 0xFFFFFFFF;

	public static final int BEGIN_TIME = 22;
	public static final int END_TIME = 8;
	public static final int DAY = 0;
	public static final String VOIP_NOT_WIFI_TIME = "voip_not_wifi_time";

	public static final int TYPE_WH = 1;
	public static final int TYPE_HW = 2;
	public static final int LIMITE_SIZE = 50;

	
	private static final int ANDROID_API_LEVEL_11 = 11;
	
	
	private Util() {

	}

	/**
	 * will drop some value
	 * 
	 * @param value
	 * @return
	 */
	public static String escapeSqlValue(String value) {
		if (value != null) {
			value = value.replace("\\[", "[[]");
			value = value.replace("%", "");
			value = value.replace("\\^", "");
			value = value.replace("'", "");
			value = value.replace("\\{", "");
			value = value.replace("\\}", "");
			value = value.replace("\"", "");
		}

		return value;
	}

	public static String listToString(List<String> srcList, String seperator) {
		if (srcList == null) {
			return "";
		}
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < srcList.size(); i++) {
			if (i == srcList.size() - 1) {
				result.append(srcList.get(i).trim());
			} else {
				result.append(srcList.get(i).trim() + seperator);
			}
		}
		return result.toString();
	}

	public static List<String> stringsToList(final String[] src) {
		if (src == null || src.length == 0) {
			return null;
		}
		final List<String> result = new ArrayList<String>();
		for (int i = 0; i < src.length; i++) {
			result.add(src[i]);
		}
		return result;
	}

	public static int getIntRandom(final int upLimit, final int downLimit) {
		if (upLimit <= downLimit) {
			Log.e(TAG, "getIntRandom failed upLimit:" + upLimit + "<= downLimit:" + downLimit);
			return 0;
		}
		final Random r = new Random(System.currentTimeMillis());
		return r.nextInt(upLimit - downLimit + 1) + downLimit;
	}

	// use to detect it is day or night
	public static boolean isDayTimeNow() {
		int hour = new GregorianCalendar().get(GregorianCalendar.HOUR_OF_DAY);
		final long cDawn = 6;
		final long cAfternoon = 18;
		return hour >= cDawn && hour < cAfternoon;
	}

	public static boolean isNightTime(int hour, int start, int end) {
		if (start > end) {
			return (hour >= start || hour <= end) ? true : false;
		} else if (start < end) {
			return (hour <= end && hour >= start) ? true : false;
		}
		return true;
	}

	@Deprecated
	public static String getTimeZone() {
		String timeZone = getTimeZoneDef();
		int index = timeZone.indexOf('+');
		if (index == -1) {
			index = timeZone.indexOf('-');
		}
		if (index == -1) {
			return "";
		}
		timeZone = timeZone.substring(index, index + 3);
		if (timeZone.charAt(1) == '0') {
			timeZone = timeZone.substring(0, 1) + timeZone.substring(2, 3);
		}
		return timeZone;
	}

	// 返回具体时区 GMT+8:00
	public static String getTimeZoneDef() {
		TimeZone timeZone = TimeZone.getDefault();
		boolean daylightTime = false;
		int style = TimeZone.LONG;
		if (style == TimeZone.SHORT || style == TimeZone.LONG) {
			boolean useDaylight = daylightTime && timeZone.useDaylightTime();
			int offset = timeZone.getRawOffset();
			if (useDaylight && timeZone instanceof SimpleTimeZone) {
				offset += ((SimpleTimeZone) timeZone).getDSTSavings();
			}
			offset /= SECOND_OF_MINUTE * 1000;
			char sign = '+';
			if (offset < 0) {
				sign = '-';
				offset = -offset;
			}
			return String.format("GMT%s%02d:%02d", sign, offset / MINUTE_OF_HOUR, offset % MINUTE_OF_HOUR);
		}
		throw new IllegalArgumentException();
	}

	public static String formatUnixTime(final long timestamp) {
		return new java.text.SimpleDateFormat("[yy-MM-dd HH:mm:ss]").format(new java.util.Date(timestamp * MILLSECONDS_OF_SECOND));
	}

	public static String formatTime(String format, final long time) {
		return new java.text.SimpleDateFormat(format).format(new java.util.Date(time * MILLSECONDS_OF_SECOND));
	}

	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isAlpha(final char ch) {
		return ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'));
	}

	public static boolean isNum(final char ch) {
		return (ch >= '0' && ch <= '9');
	}

	/**
	 * 验证是否QQ号码
	 * @param account
	 * @return
	 */
	public static boolean isValidQQNum(String account) {
		if (account == null || account.length() <= 0) {
			return false;
		}

		account = account.trim();
		try {

			final long uin = Long.valueOf(account);
			return (uin > 0 && uin <= MAX_32BIT_VALUE);

		} catch (final NumberFormatException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 验证是否Email
	 * @param account
	 * @return
	 */
	public static boolean isValidEmail(final String account) {
		if (account == null || account.length() <= 0) {
			return false;
		}

		return account.trim().matches("^[a-zA-Z0-9][\\w\\.-]*@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$");
	}

	public static boolean isValidAccount(String account) {

		if (account == null) {
			return false;
		}

		account = account.trim();

		// length > 6
		if (account.length() < MIN_ACCOUNT_LENGTH || account.length() > MAX_ACCOUNT_LENGTH) {
			return false;
		}

		// begin with alphabet
		if (!Util.isAlpha(account.charAt(0))) {
			return false;
		}

		// [a-z][A-Z][0-9][-_]
		for (int i = 0; i < account.length(); i++) {
			final char ch = account.charAt(i);
			if (!Util.isAlpha(ch) && !Util.isNum(ch) && ch != '-' && ch != '_') {
				return false;
			}
		}

		return true;
	}

	/**
	 * 验证密码
	 * @param account
	 * @return
	 */
	public static boolean isValidPassword(final String pass) {
		if (pass == null) {
			return false;
		}
		if (pass.length() < MIN_PASSWORD_LENGTH) {
			return false;
		}
		if (pass.length() >= MAX_PASSWORD_LENGTH) {
			return true;
		}
		try {
			Integer.parseInt(pass);
			return false;
		} catch (final NumberFormatException e) {
			return true;
		}
	}

	/**
	 * 取得BitmapFactory的Options参数
	 * @param account
	 * @return
	 */
	public static BitmapFactory.Options getImageOptions(final String path) {
		if (isNullOrNil(path)) {
			Log.e(TAG, "getImageOptions invalid path");
			return null;
		}

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
		}
		return options;
	}

	/**
	 * 保存Bitmap到File
	 * @param account
	 * @return
	 */
	public static void saveBitmapToImage(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format, final String dirPath, final String bitName, final boolean recycle) throws IOException {

		if (isNullOrNil(dirPath)) {
			throw new IOException("saveBitmapToImage dirPath null or nil");
		}
		if (isNullOrNil(bitName)) {
			throw new IOException("saveBitmapToImage bitName null or nil");
		}

		Log.d(TAG, "saving to " + dirPath + bitName);

		final File file = new File(dirPath + bitName);
		file.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
			bitmap.compress(format, quality, fOut);
			fOut.flush();
			if (recycle) {
				bitmap.recycle();
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveBitmapToImage(final Bitmap bitmap, final int quality, final Bitmap.CompressFormat format, final String pathName, final boolean recycle) throws IOException {

		if (isNullOrNil(pathName)) {
			throw new IOException("saveBitmapToImage pathName null or nil");
		}

		Log.d(TAG, "saving to " + pathName);

		final File file = new File(pathName);
		file.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(file);
			bitmap.compress(format, quality, fOut);
			fOut.flush();
			fOut.close();

			if (recycle) {
				bitmap.recycle();
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得固定大小缩略图
	 * @param raw		原图
	 * @param height
	 * @param width
	 * @param crop
	 * @return
	 */
	public static Bitmap extractThumeNail(final Bitmap raw, final int height, final int width, final boolean crop) {
		Bitmap bm = raw;

		final double beY = bm.getHeight() * 1.0 / height;
		final double beX = bm.getWidth() * 1.0 / width;

		int inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
		if (inSampleSize <= 1) {
			Log.d(TAG, "no need to scale");
			return bm;
		}

		int newHeight = height;
		int newWidth = width;
		if (crop) {
			if (beY > beX) {
				newHeight = (int) (newWidth * 1.0 * bm.getHeight() / bm.getWidth());
			} else {
				newWidth = (int) (newHeight * 1.0 * bm.getWidth() / bm.getHeight());
			}
		} else {
			if (beY < beX) {
				newHeight = (int) (newWidth * 1.0 * bm.getHeight() / bm.getWidth());
			} else {
				newWidth = (int) (newHeight * 1.0 * bm.getWidth() / bm.getHeight());
			}
		}

		Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
		final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
		if (scale != null && bm != scale) {
			bm.recycle();
			bm = scale;
		}

		if (crop) {
			final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
			if (cropped == null) {
				return bm;
			}
			if (bm != cropped) {
				bm.recycle();
			}
			bm = cropped;
			Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
		}
		return bm;
	}

	/**
	 * 获得固定大小缩略图
	 * @param path		原图路径
	 * @param height
	 * @param width
	 * @param crop
	 * @return
	 */
	public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {

		if (isNullOrNil(path)) {
			Log.e(TAG, "extractThumbNail path null or nil");
			return null;
		}
		if (height <= 0 || width <= 0) {
			Log.e(TAG, "extractThumbNail height:" + height + " width:" + width);
			return null;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();

		try {
			options.inJustDecodeBounds = true;
			Bitmap tmp = BitmapFactory.decodeFile(path, options);
			if (tmp != null) {
				tmp.recycle();
				tmp = null;
			}

			Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
			final double beY = options.outHeight * 1.0 / height;
			final double beX = options.outWidth * 1.0 / width;
			Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
			options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
			if (options.inSampleSize <= 1) {
				options.inSampleSize = 1;
				// options.inJustDecodeBounds = false;
				// Log.i(TAG, "bitmap required size=" + width + "x" + height +
				// ", orig=" + options.outWidth + "x" + options.outHeight +
				// ", sample=" + options.inSampleSize);
				// return BitmapFactory.decodeFile(path, options);
			}

			// NOTE: out of memory error
			while (options.outHeight * options.outWidth / options.inSampleSize / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
				options.inSampleSize++;
			}

			int newHeight = height;
			int newWidth = width;
			if (crop) {
				if (beY > beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			} else {
				if (beY < beX) {
					newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
				} else {
					newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
				}
			}

			options.inJustDecodeBounds = false;
			//if not set true, setPixels method may throw IllegalStateException 
			if(android.os.Build.VERSION.SDK_INT >= ANDROID_API_LEVEL_11){
				options.inMutable = true;
			}
			Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
			Bitmap bm = BitmapFactory.decodeFile(path, options);
			if (bm == null) {
				Log.e(TAG, "bitmap decode failed");
				return null;
			}

			Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
			final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
			if (bm != scale && scale != null) {
				bm.recycle();
				bm = scale;
			}

			if (crop) {
				final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
				if (cropped == null) {
					return bm;
				}

				if (cropped != bm) {
					bm.recycle();
					bm = cropped;
				}

				Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
			}
			return bm;

		} catch (final OutOfMemoryError e) {
			Log.e(TAG, "decode bitmap failed: " + e.getMessage());
			options = null;
		}

		return null;
	}

	/**
	 * 获得固定大小缩略图
	 * @param bitmap	原图bitmap
	 * @param height
	 * @param width
	 * @param crop
	 * @return
	 */
	public static Bitmap extractThumbNail(Bitmap bitmap, final int height, final int width, final boolean crop, boolean recycle) {

		if (height <= 0 || width <= 0) {
			Log.e(TAG, "extractThumbNail height:" + height + " width:" + width);
			return null;
		}

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.outHeight = bitmap.getHeight();
		options.outWidth = bitmap.getWidth();

		Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
		final double beY = options.outHeight * 1.0 / height;
		final double beX = options.outWidth * 1.0 / width;
		Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
		options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
		if (options.inSampleSize <= 1) {
			options.inSampleSize = 1;
		}

		// NOTE: out of memory error
		while (options.outHeight * options.outWidth / options.inSampleSize / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
			options.inSampleSize++;
		}

		int newHeight = height;
		int newWidth = width;
		if (crop) {
			if (beY > beX) {
				newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
			} else {
				newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
			}
		} else {
			if (beY < beX) {
				newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
			} else {
				newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
			}
		}

		options.inJustDecodeBounds = false;

		Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);

		final Bitmap scale = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
		if (scale != null) {
			if (recycle && bitmap != scale) {
				bitmap.recycle();
			}
			bitmap = scale;
		}

		if (crop) {
			final Bitmap cropped = Bitmap.createBitmap(bitmap, (bitmap.getWidth() - width) >> 1, (bitmap.getHeight() - height) >> 1, width, height);
			if (cropped == null) {
				return bitmap;
			}

			if (recycle && bitmap != cropped) {
				bitmap.recycle();
			}
			bitmap = cropped;
			Log.i(TAG, "bitmap croped size=" + bitmap.getWidth() + "x" + bitmap.getHeight());
		}
		return bitmap;
	}

	/**
	 * 中间截取Bitmap
	 * @param source	原图
	 * @param height
	 * @param width
	 * @param crop
	 * @return
	 */
	public static Bitmap getCenterCropBitmap(Bitmap source, int newWidth, int newHeight, boolean couldRecycleSourceBmp) {
		if (source == null) {
			return null;
		}
		final int sourceWidth = source.getWidth();
		final int sourceHeight = source.getHeight();

		float wScale = (float) newWidth / sourceWidth;
		float hScale = (float) newHeight / sourceHeight;
		float maxScale = Math.max(wScale, hScale);

		final float scaledWidth = maxScale * sourceWidth;
		final float scaledHeight = maxScale * sourceHeight;

		final float left = (newWidth - scaledWidth) / 2;
		final float top = (newHeight - scaledHeight) / 2;

		final RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

		Bitmap dest = Bitmap.createBitmap(newWidth, newHeight, source.getConfig());
		Canvas canvas = new Canvas(dest);
		canvas.drawBitmap(source, null, targetRect, null);

		if (couldRecycleSourceBmp) {
			source.recycle();
		}

		return dest;
	}

	public static boolean createThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String thumbFullPath, boolean checkDegree) {
		Bitmap bm = extractThumbNail(origPath, height, width, false);
		if (bm == null) {
			return false;
		}

		if (checkDegree) {
			int degree = BackwardSupportUtil.ExifHelper.getExifOrientation(origPath);
			bm = rotate(bm, degree);
		}

		try {
			Util.saveBitmapToImage(bm, quality, format, thumbFullPath, true);

		} catch (final IOException e) {
			Log.e(TAG, "create thumbnail from orig failed: " + thumbFullPath);
			return false;
		}

		return true;
	}

	public static boolean createThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName, boolean checkDegree) {
		return createThumbNail(origPath, height, width, format, quality, dirPath + thumbName, checkDegree);
	}

	public static boolean createThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName) {
		return createThumbNail(origPath, height, width, format, quality, dirPath, thumbName, false);
	}

	public static boolean createThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String thumbFullPath) {
		return createThumbNail(origPath, height, width, format, quality, thumbFullPath, false);
	}

	public static int isLongPicture(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap justBmp = BitmapFactory.decodeFile(filePath, options);
		if (justBmp != null) {
			justBmp.recycle();
		}
		float whDiv = (float) options.outWidth / (float) options.outHeight;
		float hwDiv = (float) options.outHeight / (float) options.outWidth;
		if (whDiv >= 2) {
			return TYPE_WH;
		}

		if (hwDiv >= 2) {
			return TYPE_HW;
		}
		return -1;
	}

	public static boolean createLongPictureThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName, int type) {
		return createLongPictureThumbNail(origPath, height, width, format, quality, dirPath + thumbName, type);
	}

	public static boolean createLongPictureThumbNail(final String origPath, final int height, final int width, final Bitmap.CompressFormat format, final int quality, final String thumbPath, int type) {

		Bitmap bm = null;
		Bitmap sbm = null;
		if (type == TYPE_WH) {
			bm = extractThumbNail(origPath, LIMITE_SIZE, width, true);
			sbm = Bitmap.createBitmap(bm, (bm.getWidth() - LIMITE_SIZE * 2) / 2, 0, LIMITE_SIZE * 2, LIMITE_SIZE);
		} else if (type == TYPE_HW) {
			bm = extractThumbNail(origPath, height, LIMITE_SIZE, true);
			sbm = Bitmap.createBitmap(bm, 0, (bm.getHeight() - LIMITE_SIZE * 2) / 2, LIMITE_SIZE, LIMITE_SIZE * 2);
		}

		if (bm != sbm) {
			bm.recycle();
		}

		if (sbm == null) {
			return false;
		}

		try {
			Util.saveBitmapToImage(sbm, quality, format, thumbPath, true);

		} catch (final IOException e) {
			Log.e(TAG, "create thumbnail from orig failed: " + thumbPath);
			return false;
		}
		return true;
	}

	public static boolean rotate(final String origPath, final int degree, final Bitmap.CompressFormat format, final int quality, final String thumbPath) {
		Bitmap bmp = BitmapFactory.decodeFile(origPath);
		if (bmp == null) {
			Log.e(TAG, "rotate: create bitmap fialed");
			return false;
		}
		float width = bmp.getWidth();
		float height = bmp.getHeight();

		Matrix m = new Matrix();
		m.setRotate(degree, width / 2, height / 2);
		Bitmap resultBmp = Bitmap.createBitmap(bmp, 0, 0, (int) width, (int) height, m, true);
		if (bmp != resultBmp) {
			bmp.recycle();
		}
		try {
			Util.saveBitmapToImage(resultBmp, quality, format, thumbPath, true);
		} catch (IOException e) {
			Log.e(TAG, "create thumbnail from orig failed: " + thumbPath);
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public static boolean rotate(final String origPath, final int degree, final Bitmap.CompressFormat format, final int quality, final String dirPath, final String thumbName) {
		return rotate(origPath, degree, format, quality, dirPath + thumbName);
	}

	public static Bitmap rotate(Bitmap temBmp, float degree) {
		if (degree == 0) {
			return temBmp;
		}

		Matrix m = new Matrix();
		m.reset();
		m.setRotate(degree, temBmp.getWidth() / 2, temBmp.getHeight() / 2);
		Bitmap resultBmp = Bitmap.createBitmap(temBmp, 0, 0, temBmp.getWidth(), temBmp.getHeight(), m, true);
		Log.d(TAG, "resultBmp is null: " + (resultBmp == null) + "  degree:" + degree);
		if (temBmp != resultBmp) {
			temBmp.recycle();
		}
		return resultBmp;
	}

	/**
	 * Rounded corner bitmaps on Android Posted on June 16, 2009 by ruibm
	 */
	public static Bitmap getRoundedCornerBitmap(final Bitmap bitmap, final boolean shouldRecyle, final float roundPx) {
		if (bitmap == null || bitmap.isRecycled()) {
			Log.e(TAG, "getRoundedCornerBitmap in bitmap is null");
			return null;
		}
		final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(output);

		final int color = 0xFFC0C0C0;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setFilterBitmap(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		if (shouldRecyle) {
			bitmap.recycle();
		}
		return output;
	}

	public static String getSizeKB(final long bytes) {
		final float cRound = 10;

		// > 1MB
		if ((bytes >> BIT_OF_MB) > 0) {
			return getSizeMB(bytes);
		}

		// > 0.5K
		if ((bytes >> (BIT_OF_KB - 1)) > 0) {
			final float bytesInKB = (Math.round(bytes * cRound / BYTE_OF_KB)) / cRound;
			return "" + bytesInKB + "KB";
		}

		return "" + bytes + "B";
	}

	public static String getSizeMB(final long bytes) {
		final float cRound = 10;

		final float bytesInMB = (Math.round(bytes * cRound / BYTE_OF_MB)) / cRound;
		return "" + bytesInMB + "MB";
	}

	public static String dumpArray(final Object[] stack) {
		final StringBuilder sb = new StringBuilder();
		for (final Object ste : stack) {
			sb.append(ste);
			sb.append(',');
		}
		return sb.toString();
	}

	public static String dumpHex(final byte[] src) {
		return dumpHex(src, 0, 0);
	}

	public static String dumpHex(final byte[] src, int offset, int len) {
		if (src == null) {
			return "(null)";
		}
		if (len <= 0) {
			len = src.length;
		}
		if (offset > len) {
			offset = 0;
		}
		final char[] hexDigits = {
				'0',
				'1',
				'2',
				'3',
				'4',
				'5',
				'6',
				'7',
				'8',
				'9',
				'a',
				'b',
				'c',
				'd',
				'e',
				'f' };

		final int j = len;
		final int cHexWidth = 3;
		final char[] str = new char[j * cHexWidth + j / 16];
		int k = 0;
		for (int i = offset; i < (len + offset); i++) {
			final byte byte0 = src[i];
			str[k++] = ' ';
			str[k++] = hexDigits[byte0 >>> 4 & MASK_4BIT];
			str[k++] = hexDigits[byte0 & MASK_4BIT];

			if (i % 16 == 0 && i > 0) {
				str[k++] = '\n';
			}
		}
		return new String(str);
	}

	public static Intent getInstallPackIntent(final String filePath, final Context context) {
		if (filePath == null || filePath.length() <= 0) {
			return null;
		}
		final Intent i = new Intent(Intent.ACTION_VIEW);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
		return i;
	}

	public static void installPack(final String filePath, final Context context) {
		context.startActivity(getInstallPackIntent(filePath, context));
	}

	private static final long[] VIRBRATOR_PATTERN = new long[] {
			300,
			200,
			300,
			200 };

	public static void shake(final Context context, final boolean shake) {
		final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		if (vibrator == null) {
			return;
		}
		if (shake) {
			vibrator.vibrate(VIRBRATOR_PATTERN, -1);
		} else {
			vibrator.cancel();
		}
	}

	public static MediaPlayer playSound(final Context context, final int pathId, boolean speakeron, final MediaPlayer.OnCompletionListener l) {
		try {
			final String path = context.getString(pathId);
			final AssetFileDescriptor afd = context.getAssets().openFd(path);
			final MediaPlayer player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			player.setAudioStreamType(speakeron ? AudioManager.STREAM_MUSIC : AudioManager.STREAM_VOICE_CALL);
			afd.close();
			player.prepare();

			player.setLooping(false);
			player.start();

			// release
			player.setOnCompletionListener(l);

			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static MediaPlayer playSound(final Context context, final int pathId, final MediaPlayer.OnCompletionListener l) {
		try {
			final String path = context.getString(pathId);
			final AssetFileDescriptor afd = context.getAssets().openFd(path);
			final MediaPlayer player = new MediaPlayer();
			player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			afd.close();
			player.prepare();

			player.setLooping(false);
			player.start();

			// release
			player.setOnCompletionListener(l);

			return player;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void playSound(final Context context, final int pathId) {
		playSound(context, pathId, new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	public static void playSound(final Context context, final int pathId, boolean speakeron) {
		playSound(context, pathId, speakeron, new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}

	public static void playEndSound(final Context context, final int pathId, boolean speakeron, final MediaPlayer.OnCompletionListener lis) {
		playSound(context, pathId, speakeron, new MediaPlayer.OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				if (lis != null) {
					lis.onCompletion(mp);
				}
			}
		});
	}

	public static long nowSecond() {
		return System.currentTimeMillis() / MILLSECONDS_OF_SECOND;
	}

	public static String formatSecToMin(int second) {
		return String.format("%d:%02d", second / SECOND_OF_MINUTE, second % SECOND_OF_MINUTE);
	}

	public static long nowMilliSecond() {
		return System.currentTimeMillis();
	}

	public static long secondsToNow(final long before) {
		return (System.currentTimeMillis() / MILLSECONDS_OF_SECOND - before);
	}

	public static long secondsToMilliSeconds(final int seconds) {
		return seconds * MILLSECONDS_OF_SECOND;
	}

	public static long milliSecondsToNow(final long before) {
		return (System.currentTimeMillis() - before);
	}

	public static long ticksToNow(final long before) {
		return (SystemClock.elapsedRealtime() - before);
	}

	private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

	public static long currentTicks() {
		return SystemClock.elapsedRealtime();
	}

	public static long currentDayInMills() {
		return (nowMilliSecond() / MILLSECONDS_OF_DAY) * MILLSECONDS_OF_DAY;
	}

	public static long currentWeekInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar week = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), now.get(GregorianCalendar.DAY_OF_MONTH));
		week.setTimeZone(GMT);
		final int offset = now.get(GregorianCalendar.DAY_OF_WEEK) - now.getFirstDayOfWeek();
		week.add(GregorianCalendar.DAY_OF_YEAR, -offset);
		return week.getTimeInMillis();
	}

	public static long currentMonthInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar month = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), 1);
		month.setTimeZone(GMT);
		return month.getTimeInMillis();
	}

	public static long currentYearInMills() {
		final GregorianCalendar now = new GregorianCalendar();
		final GregorianCalendar year = new GregorianCalendar(now.get(GregorianCalendar.YEAR), 1, 1);
		year.setTimeZone(GMT);
		return year.getTimeInMillis();
	}

	public static int getSystemVersion(final Context context, final int def) {
		if (context == null) {
			return def;
		}

		final int ver = android.provider.Settings.System.getInt(context.getContentResolver(), android.provider.Settings.System.SYS_PROP_SETTING_VERSION, def);
		return ver;
	}

	public static String getLine1Number(final Context context) {
		if (context == null) {
			return null;
		}

		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if (tm == null) {
				Log.e(TAG, "get line1 number failed, null tm");
				return null;
			}

		} catch (final SecurityException e) {
			Log.e(TAG, "getLine1Number failed, security exception");

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return null;
	}

	/**
	 * lock screen check
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLockScreen(final Context context) {
		try {
			final KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
			return km.inKeyguardRestrictedInputMode();

		} catch (final Exception ignore) {
			ignore.printStackTrace();
		}

		return false;
	}

	public static boolean isTopActivity(final Context context) {
		final String ctxName = context.getClass().getName();
		final String topName = getTopActivityName(context);
		Log.d(TAG, "top activity=" + topName + ", context=" + ctxName);
		return topName.equalsIgnoreCase(ctxName);
	}

	public static boolean isServiceRunning(final Context context, final String service) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> list = am.getRunningServices(Integer.MAX_VALUE);
		for (final RunningServiceInfo i : list) {
			if (i == null || i.service == null) {
				continue;
			}

			if (i.service.getClassName().toString().equals(service)) {
				Log.w(TAG, "service " + service + " is running");
				return true;
			}
		}

		Log.w(TAG, "service " + service + " is not running");
		return false;
	}

	public static boolean isProcessRunning(final Context context, String process) {

		if (process.startsWith(":")) {
			process = context.getPackageName() + process;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = am.getRunningAppProcesses();
		for (final RunningAppProcessInfo i : list) {
			if (i == null || i.processName == null) {
				continue;
			}

			if (i.processName.equals(process)) {
				Log.w(TAG, "process " + process + " is running");
				return true;
			}
		}

		Log.w(TAG, "process " + process + " is not running");
		return false;
	}

	public static String getTopActivityName(final Context context) {
		try {
			final ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
			final ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			final String topName = cn.getClassName();
			return topName;

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return "(null)";
	}

	public static boolean isTopApplication(final Context context) {
		try {
			final ActivityManager am = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
			final ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			final String topName = cn.getClassName();
			final String pkgName = context.getPackageName();
			Log.d(TAG, "top activity=" + topName + ", context=" + pkgName);
			return topName.contains(pkgName);

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean isIntentAvailable(final Context context, final Intent intent) {
		final PackageManager packageManager = context.getPackageManager();
		final List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	public static CharSequence formatPrefixInDay(final Context context, final long timeInDay) {
		
		Log.d(TAG,"formatPrefixInDay "+timeInDay);
		String todayStr;

		final long cDawn = 6 * MILLSECONDS_OF_HOUR;
		final long cMorning = 12 * MILLSECONDS_OF_HOUR;
		final long cNoon = 13 * MILLSECONDS_OF_HOUR;
		final long cAfternoon = 18 * MILLSECONDS_OF_HOUR;

		// invalid
		if (timeInDay < 0) {
			return "";

		} else if (timeInDay < cDawn) { // 00:00 ~ 05:59, dawn
			todayStr = context.getString(R.string.fmt_dawn);

		} else if (timeInDay < cMorning) { // 06:00 ~ 11:59, morning
			todayStr = context.getString(R.string.fmt_morning);

		} else if (timeInDay < cNoon) { // 12:00 ~ 12:59, noon
			todayStr = context.getString(R.string.fmt_noon);

		} else if (timeInDay < cAfternoon) { // 13:00 ~ 17:59, afternoon
			todayStr = context.getString(R.string.fmt_afternoon);

		} else { // 18:00 ~ 23:59, evening
			todayStr = context.getString(R.string.fmt_evening);
		}

		return todayStr;
	}
	
	public static CharSequence formatPrefixInDay(final Context context, final int hourInDay) {
		
		Log.d(TAG,"formatPrefixInDay "+hourInDay);
		String todayStr;

		final long cDawn = 6 ;
		final long cMorning = 12 ;
		final long cNoon = 13;
		final long cAfternoon = 18;

		// invalid
		if (hourInDay < 0) {
			return "";

		} else if (hourInDay < cDawn) { // 00:00 ~ 05:59, dawn
			todayStr = context.getString(R.string.fmt_dawn);

		} else if (hourInDay < cMorning) { // 06:00 ~ 11:59, morning
			todayStr = context.getString(R.string.fmt_morning);

		} else if (hourInDay < cNoon) { // 12:00 ~ 12:59, noon
			todayStr = context.getString(R.string.fmt_noon);

		} else if (hourInDay < cAfternoon) { // 13:00 ~ 17:59, afternoon
			todayStr = context.getString(R.string.fmt_afternoon);

		} else { // 18:00 ~ 23:59, evening
			todayStr = context.getString(R.string.fmt_evening);
		}

		return todayStr;
	}

	public static CharSequence formatTimeInListForOverSeaUser(final Context context, final long time, final boolean simple, Locale locale) {
		final GregorianCalendar now = new GregorianCalendar();

		// special time
		if (time < MILLSECONDS_OF_HOUR) {
			return "";
		}

		// //test
		// java.text.DateFormat[] formats = new java.text.DateFormat[] {
		// java.text.DateFormat.getDateInstance(
		// java.text.DateFormat.SHORT, locale),
		// java.text.DateFormat.getDateInstance(
		// java.text.DateFormat.MEDIUM, locale),
		// java.text.DateFormat.getDateInstance(java.text.DateFormat.LONG,
		// locale),
		// java.text.DateFormat.getDateInstance(java.text.DateFormat.FULL,
		// locale),
		// java.text.DateFormat.getTimeInstance(
		// java.text.DateFormat.SHORT, locale),
		// java.text.DateFormat.getTimeInstance(
		// java.text.DateFormat.MEDIUM, locale),
		// java.text.DateFormat.getTimeInstance(java.text.DateFormat.LONG,
		// locale),
		// java.text.DateFormat.getTimeInstance(java.text.DateFormat.FULL,
		// locale),
		// java.text.DateFormat.getDateTimeInstance(
		// java.text.DateFormat.SHORT, java.text.DateFormat.SHORT,
		// locale), };
		// for (java.text.DateFormat df : formats) {
		// Log.d(TAG, df.format(System.currentTimeMillis()));
		// }

		// today
		final GregorianCalendar today = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), now.get(GregorianCalendar.DAY_OF_MONTH));
		final long in24h = time - today.getTimeInMillis();
		if (in24h > 0 && in24h <= MILLSECONDS_OF_DAY) {
			java.text.DateFormat df = java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT, locale);
			return "" + df.format(time);
		}

		// yesterday
		final long in48h = time - today.getTimeInMillis() + MILLSECONDS_OF_DAY;
		if (in48h > 0 && in48h <= MILLSECONDS_OF_DAY) {
			return simple ? context.getString(R.string.fmt_pre_yesterday) : context.getString(R.string.fmt_pre_yesterday) + " " + java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT, locale).format(time);
		}

		final GregorianCalendar target = new GregorianCalendar();
		target.setTimeInMillis(time);

		// same week
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR) && now.get(GregorianCalendar.WEEK_OF_YEAR) == target.get(GregorianCalendar.WEEK_OF_YEAR)) {
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("E", locale);
			final String dow = "" + sdf.format(time);
			return simple ? dow : dow + " " + java.text.DateFormat.getTimeInstance(java.text.DateFormat.SHORT, locale).format(time);
		}

		// same year
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR)) {
			return simple ? java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, locale).format(time) : java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, locale).format(time);
		}

		return simple ? java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT, locale).format(time) : java.text.DateFormat.getDateTimeInstance(java.text.DateFormat.SHORT, java.text.DateFormat.SHORT, locale).format(time);
	}

	public static boolean isInTenMin(long time) {
		final long in10min = System.currentTimeMillis() - time;
		Log.d(TAG, "in10min  " + in10min);
		if (in10min >= -(10 * MILLSECONDS_OF_MINUTE) && in10min <= 10 * MILLSECONDS_OF_MINUTE) {
			return true;
		}
		return false;
	}

	public static boolean isTimeOut(final int timeInsec) {
		final long time = ((long) timeInsec) * 1000;
		long diff = time - System.currentTimeMillis();
		Log.d(TAG, "time " + time + "  systime " + System.currentTimeMillis() + " diff " + diff);
		if (diff < 0) {
			return true;
		}
		return false;
	}

	/**
	 * 取得音频提醒时间
	 * @param context
	 * @param timeInsec
	 * @return
	 */
	public static String getVoiceRemindDate(final Context context, final int timeInsec) {

		final long time = ((long) timeInsec) * 1000;
		final GregorianCalendar now = new GregorianCalendar();
		// special time
		if (time < MILLSECONDS_OF_HOUR) {
			return "";
		}

		// 今天
		final GregorianCalendar today = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), now.get(GregorianCalendar.DAY_OF_MONTH));
		long diff = time - today.getTimeInMillis();

		if (diff >= 0 && diff < MILLSECONDS_OF_DAY) {
			return "" + formatPrefixInDay(context, diff) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}

		// 昨天
		diff = time - (today.getTimeInMillis() - MILLSECONDS_OF_DAY);
		if (diff >= 0 && diff < MILLSECONDS_OF_DAY) {
			return context.getString(R.string.fmt_pre_yesterday) + " " + formatPrefixInDay(context, diff) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}

		// 前天
		diff = time - (today.getTimeInMillis() - 2 * MILLSECONDS_OF_DAY);
		if (diff >= 0 && diff < MILLSECONDS_OF_DAY) {
			return context.getString(R.string.fmt_pre_daybeforyesterday) + " " + formatPrefixInDay(context, diff) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}

		// 明天
		diff = time - (today.getTimeInMillis() + MILLSECONDS_OF_DAY);
		if (diff >= 0 && diff < MILLSECONDS_OF_DAY) {
			return context.getString(R.string.fmt_pre_tomorrow) + " " + formatPrefixInDay(context, diff) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}

		// 后天
		diff = time - (today.getTimeInMillis() + 2 * MILLSECONDS_OF_DAY);
		if (diff >= 0 && diff < MILLSECONDS_OF_DAY) {
			return context.getString(R.string.fmt_pre_dayaftertomorrow) + " " + formatPrefixInDay(context, diff) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}

		final GregorianCalendar target = new GregorianCalendar();
		target.setTimeInMillis(time);

		int hour =  target.get(GregorianCalendar.HOUR_OF_DAY);
		// 本周
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR) && now.get(GregorianCalendar.WEEK_OF_YEAR) == target.get(GregorianCalendar.WEEK_OF_YEAR)) {

			int dayOfWeek = target.get(GregorianCalendar.DAY_OF_WEEK);
			return getStringDayOfWeek(context, dayOfWeek) + " " + formatPrefixInDay(context, hour) + ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}
		// 下周
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR) && (now.get(GregorianCalendar.WEEK_OF_YEAR) + 1) == target.get(GregorianCalendar.WEEK_OF_YEAR)) {
			int dayOfWeek = target.get(GregorianCalendar.DAY_OF_WEEK);
			return getStringDayOfNextWeek(context, dayOfWeek) + " " + formatPrefixInDay(context, hour)+ ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}
		
		//今年
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR)) {
			return DateFormat.format(context.getString(R.string.fmt_date), time) + " " + formatPrefixInDay(context, hour)+ ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
		}
		return DateFormat.format(context.getString(R.string.fmt_longdate), time) + " " + formatPrefixInDay(context, hour)+ ";" + getStringNormalTime(context.getString(R.string.fmt_normal_time), time);
	}

	public static String getStringNormalTime(final String str, final long time) {
		
		String format = DateFormat.format(str, time).toString();
		if(Util.isNullOrNil(format)){
			
			return "";
		}
		format = format.trim();
		if(format.startsWith("0")){
			format = format.substring(1);
		}
		return format;
	}
	
	public static String getStringDayOfWeek(final Context context, int index) {
		String day = "";
		switch (index) {
		case GregorianCalendar.SUNDAY:
			day = context.getString(R.string.fmt_pre_week_sunday);
			break;
		case GregorianCalendar.MONDAY:
			day = context.getString(R.string.fmt_pre_week_monday);
			break;
		case GregorianCalendar.TUESDAY:
			day = context.getString(R.string.fmt_pre_week_tuesday);
			break;
		case GregorianCalendar.WEDNESDAY:
			day = context.getString(R.string.fmt_pre_week_wednesday);
			break;
		case GregorianCalendar.THURSDAY:
			day = context.getString(R.string.fmt_pre_week_thursday);
			break;
		case GregorianCalendar.FRIDAY:
			day = context.getString(R.string.fmt_pre_week_friday);
			break;
		case GregorianCalendar.SATURDAY:
			day = context.getString(R.string.fmt_pre_week_saturday);
			break;
		default:
			break;
		}
		return day;
	}

	public static String getStringDayOfNextWeek(final Context context, int index) {
		String day = "";
		switch (index) {
		case GregorianCalendar.SUNDAY:
			day = context.getString(R.string.fmt_pre_next_week_sunday);
			break;
		case GregorianCalendar.MONDAY:
			day = context.getString(R.string.fmt_pre_next_week_monday);
			break;
		case GregorianCalendar.TUESDAY:
			day = context.getString(R.string.fmt_pre_next_week_tuesday);
			break;
		case GregorianCalendar.WEDNESDAY:
			day = context.getString(R.string.fmt_pre_next_week_wednesday);
			break;
		case GregorianCalendar.THURSDAY:
			day = context.getString(R.string.fmt_pre_next_week_thursday);
			break;
		case GregorianCalendar.FRIDAY:
			day = context.getString(R.string.fmt_pre_next_week_friday);
			break;
		case GregorianCalendar.SATURDAY:
			day = context.getString(R.string.fmt_pre_next_week_saturday);
			break;
		default:
			break;
		}
		return day;
	}

	public static CharSequence formatTimeInList(final Context context, final long time, final boolean simple) {
		final String curLang = LocaleUtil.getApplicationLanguage();
		final Locale locale = LocaleUtil.transLanguageToLocale(curLang); // 改为获取应用当前语言对应的Locale
		if (!curLang.equalsIgnoreCase(LocaleUtil.CHINA)) { // if (!LocaleUtil.getApplicationLanguage().equalsIgnoreCase(LocaleUtil.CHINA)) {
			return formatTimeInListForOverSeaUser(context, time, simple, locale);
		}

		final GregorianCalendar now = new GregorianCalendar();

		// special time
		if (time < MILLSECONDS_OF_HOUR) {
			return "";
		}

		// today
		final GregorianCalendar today = new GregorianCalendar(now.get(GregorianCalendar.YEAR), now.get(GregorianCalendar.MONTH), now.get(GregorianCalendar.DAY_OF_MONTH));
		final long in24h = time - today.getTimeInMillis();
		if (in24h > 0 && in24h <= MILLSECONDS_OF_DAY) {
			return "" + formatPrefixInDay(context, in24h) + DateFormat.format(context.getString(R.string.fmt_patime), time);
		}

		// yesterday
		final long in48h = time - today.getTimeInMillis() + MILLSECONDS_OF_DAY;
		if (in48h > 0 && in48h <= MILLSECONDS_OF_DAY) {
			return simple ? context.getString(R.string.fmt_pre_yesterday) + " " + formatPrefixInDay(context, in48h) : context.getString(R.string.fmt_pre_yesterday) + " " + formatPrefixInDay(context, in48h)
					+ DateFormat.format(context.getString(R.string.fmt_patime), time);
		}

		final GregorianCalendar target = new GregorianCalendar();
		target.setTimeInMillis(time);

		// same week
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR) && now.get(GregorianCalendar.WEEK_OF_YEAR) == target.get(GregorianCalendar.WEEK_OF_YEAR)) {
			final String dow = "" + DateFormat.format("E ", target) + formatPrefixInDay(context, target.get(GregorianCalendar.HOUR_OF_DAY) * MILLSECONDS_OF_HOUR);
			return simple ? dow : dow + DateFormat.format(context.getString(R.string.fmt_patime), time);
		}

		// same year
		if (now.get(GregorianCalendar.YEAR) == target.get(GregorianCalendar.YEAR)) {
			return simple ? DateFormat.format(context.getString(R.string.fmt_date), time) : DateFormat.format(
					context.getString(R.string.fmt_datetime, formatPrefixInDay(context, target.get(GregorianCalendar.HOUR_OF_DAY) * MILLSECONDS_OF_HOUR)).toString(), time);
		}

		return simple ? DateFormat.format(context.getString(R.string.fmt_longdate), time) : DateFormat.format(
				context.getString(R.string.fmt_longtime, formatPrefixInDay(context, target.get(GregorianCalendar.HOUR_OF_DAY) * MILLSECONDS_OF_HOUR)).toString(), time);
	}

	public static void dumpMemoryUsage() {
		Log.w(TAG, "memory usage: h=%s/%s, e=%s/%s, n=%s/%s", getSizeKB(Debug.getGlobalAllocSize()), getSizeKB(Debug.getGlobalAllocSize() + Debug.getGlobalFreedSize()), getSizeKB(Debug.getGlobalExternalAllocSize()),
				getSizeKB(Debug.getGlobalExternalAllocSize() + Debug.getGlobalExternalFreedSize()), getSizeKB(Debug.getNativeHeapAllocatedSize()), getSizeKB(Debug.getNativeHeapSize()));
	}

	public static void freeBitmapMap(final Map<String, Bitmap> bitmaps) {
		final Iterator<Entry<String, Bitmap>> iter = bitmaps.entrySet().iterator();
		while (iter.hasNext()) {
			final Entry<String, Bitmap> entry = iter.next();
			final Bitmap bm = entry.getValue();
			if (bm != null) {
				bm.recycle();
			}
		}

		bitmaps.clear();
	}

	/**
	 * default value of null
	 * 
	 * @param object
	 * @return
	 */
	public static int nullAsNil(final Integer object) {
		return object == null ? 0 : object;
	}

	public static long nullAsNil(final Long object) {
		return object == null ? 0L : object;
	}

	public static String nullAsNil(final String object) {
		return object == null ? "" : object;
	}

	public static boolean nullAsTrue(final Boolean object) {
		return object == null ? true : object;
	}

	public static boolean nullAsFalse(final Boolean object) {
		return object == null ? false : object;
	}

	public static int nullAs(final Integer object, final int def) {
		return object == null ? def : object;
	}

	public static float nullAs(final Float object, final float def) {
		return object == null ? def : object;
	}

	public static long nullAs(final Long object, final long def) {
		return object == null ? def : object;
	}

	public static boolean nullAs(final Boolean object, final boolean def) {
		return object == null ? def : object;
	}

	public static String nullAs(final String object, final String def) {
		return object == null ? def : object;
	}

	public static Object nullAsObject(final Object object) {
		return object == null ? null : object;
	}

	/**
	 * auto integer or long
	 * 
	 * @param object
	 * @param def
	 * @return
	 */
	public static int nullAsInt(final Object object, final int def) {
		if (object == null) {
			return def;
		}

		if (object instanceof Integer) {
			return (Integer) object;
		}

		if (object instanceof Long) {
			return ((Long) object).intValue();
		}

		return def;
	}

	public static boolean isNullOrNil(final String object) {
		if ((object == null) || (object.length() <= 0)) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrNil(final byte[] object) {
		if ((object == null) || (object.length <= 0)) {
			return true;
		}
		return false;
	}

	@Deprecated
	public static int getInt(final String string, final int def) {
		try {
			return string == null ? def : Integer.parseInt(string);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	@Deprecated
	public static long getLong(final String string, final long def) {
		try {
			return string == null ? def : Long.parseLong(string);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	@Deprecated
	public static int getHex(final String string, int def) {
		if (string == null) {
			return def;
		}

		try {
			return (int) (Long.decode(string) & MAX_32BIT_VALUE);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return def;
	}

	/**
	 * ini parser
	 */
	@Deprecated
	public static Map<String, String> parseIni(final String ini) {
		if (ini == null || ini.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();

		final String[] lines = ini.split("\n");
		for (final String line : lines) {
			if (line == null || line.length() <= 0) {
				continue;
			}

			final String[] kv = line.trim().split("=", 2);
			if (kv == null || kv.length < 2) {
				continue;
			}

			// key
			final String key = kv[0];
			final String value = kv[1];
			if (key == null || key.length() <= 0 || !key.matches("^[a-zA-Z0-9_]*")) {
				continue;
			}

			values.put(key, value);
		}

		// final Iterator<Map.Entry<String, String>> iter =
		// values.entrySet().iterator();
		// while (iter.hasNext()) {
		// final Map.Entry<String, String> entry = (Map.Entry<String, String>)
		// iter.next();
		// Log.v(TAG, "key=" + entry.getKey() + " value=" + entry.getValue());
		// }
		return values;
	}

	/**
	 * XML parser
	 * 
	 * @param xml
	 * @param tag
	 * @param encode
	 * @return
	 */
	@Deprecated
	public static Map<String, String> parseXml(final String xml, final String tag, final String encode) {
		if (xml == null || xml.length() <= 0) {
			return null;
		}

		final Map<String, String> values = new HashMap<String, String>();
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
		if (builder == null) {
			Log.e(TAG, "new Document Builder failed");
			return null;
		}

		Document dom = null;
		try {
			final InputSource is = new InputSource(new ByteArrayInputStream(xml.getBytes()));
			if (encode != null) {
				is.setEncoding(encode);
			}
			dom = builder.parse(is);
			dom.normalize();

		} catch (final DOMException e) {
			e.printStackTrace();

		} catch (final SAXException e) {
			e.printStackTrace();
			return null;

		} catch (final IOException e) {
			e.printStackTrace();
			return null;

		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}

		if (dom == null) {
			Log.e(TAG, "new Document failed");
			return null;
		}

		final Element root = dom.getDocumentElement();
		if (root == null) {
			Log.e(TAG, "getDocumentElement failed");
			return null;
		}

		if (tag != null && tag.equals(root.getNodeName())) { // fix 4.0 xml
																// parse error
			putAllNode(values, "", root, 0);

		} else {
			final NodeList items = root.getElementsByTagName(tag);
			if (items.getLength() <= 0) {
				Log.e(TAG, "parse item null");
				return null;
			}
			if (items.getLength() > 1) {
				Log.w(TAG, "parse items more than one");
			}
			putAllNode(values, "", items.item(0), 0);
		}
		// 打印出来太多
		// final Iterator<Map.Entry<String, String>> iter =
		// values.entrySet().iterator();
		// while (iter.hasNext()) {
		// final Map.Entry<String, String> entry = (Map.Entry<String, String>)
		// iter.next();
		// Log.v(TAG, "key=" + entry.getKey() + " value=" + entry.getValue());
		// }
		return values;
	}

	private static void putAllNode(final Map<String, String> values, String prefix, final Node node, final int indexOf) {
		if (node.getNodeName().equals("#text")) {
			values.put(prefix, node.getNodeValue());

		} else if (node.getNodeName().equals("#cdata-section")) {
			values.put(prefix, node.getNodeValue());

		} else {
			prefix += "." + node.getNodeName() + ((indexOf > 0) ? indexOf : "");
			values.put(prefix, node.getNodeValue());

			// process attributes
			final NamedNodeMap properties = node.getAttributes();
			if (properties != null) {
				for (int i = 0; i < properties.getLength(); i++) {
					final Node p = properties.item(i);
					values.put(prefix + ".$" + p.getNodeName(), p.getNodeValue());
				}
			}

			// process sub node
			final HashMap<String, Integer> conflicts = new HashMap<String, Integer>();
			final NodeList items = node.getChildNodes();
			for (int i = 0; i < items.getLength(); i++) {
				final Node s = items.item(i);
				final int no = Util.nullAsNil(conflicts.get(s.getNodeName()));
				putAllNode(values, prefix, s, no);
				conflicts.put(s.getNodeName(), no + 1);
			}
		}
	}

	public static OnTouchListener genLinearPressedListener() {
		return new OnTouchListener() {
			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_OUTSIDE:
					v.post(new Runnable() {
						@Override
						public void run() {
							v.setPressed(false);
						}
					});
					break;
				case MotionEvent.ACTION_DOWN:
					v.setPressed(true);
					break;
				}
				return false;
			}
		};
	}

	/**
	 * MD5 password
	 */
	private static final int NEW_QQ_PASSWORD_MAX = 16;

	public static String getCutPasswordMD5(String rawPsw) {
		if (rawPsw == null) {
			rawPsw = "";
		}

		if (rawPsw.length() <= NEW_QQ_PASSWORD_MAX) {
			return getFullPasswordMD5(rawPsw);
		}

		return getFullPasswordMD5(rawPsw.substring(0, NEW_QQ_PASSWORD_MAX));
	}

	public static String getCutPassword(String rawPsw) {
		if (rawPsw != null && rawPsw.length() > NEW_QQ_PASSWORD_MAX) {

			return rawPsw.substring(0, NEW_QQ_PASSWORD_MAX);
		} else {
			return rawPsw;
		}
	}

	public static String getFullPasswordMD5(final String psw) {
		return MD5.getMessageDigest(psw.getBytes());
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	/**
	 * encodeByteArray 和 decodeString 成对使用
	 * 
	 * @param
	 * @return
	 */
	public static String encodeHexString(final byte[] ba) {
		StringBuilder out = new StringBuilder("");
		if (ba != null) {
			for (int i = 0; i < ba.length; i++) {
				out.append(String.format("%02x", (int) (ba[i] & MASK_8BIT)));
			}
		}

		return out.toString();
	}

	public static byte[] decodeHexString(final String in) {
		if (in == null || in.length() <= 0) {
			return new byte[0];
		}

		try {
			byte[] buf = new byte[in.length() / 2];
			final int cRadix = 16;
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (byte) (Integer.parseInt(in.substring(i * 2, (i * 2 + 2)), cRadix) & MASK_8BIT);
			}
			return buf;

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		return new byte[0];
	}

	private static final int TCP_HDR_FIX = 52;
	private static final int TCP_HDR_FIX_TOO = 40; // a balance value to control the netstat approach to real
	private static final int TCP_TX_FIX_BYTES = 60 * 2 + TCP_HDR_FIX; // connection over tcp
	private static final int TCP_RX_FIX_BYTES = TCP_HDR_FIX * 3; // disconnectiton over tcp
	private static final int MTU = 1514 - TCP_HDR_FIX;

	public static int guessHttpSendLength(final int i) {
		return TCP_TX_FIX_BYTES + TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessHttpRecvLength(final int i) {
		return TCP_RX_FIX_BYTES + TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessHttpContinueRecvLength(final int i) {
		return TCP_HDR_FIX + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessTcpConnectLength() {
		return TCP_TX_FIX_BYTES;
	}

	public static int guessTcpDisconnectLength() {
		return TCP_RX_FIX_BYTES;
	}

	public static int guessTcpSendLength(final int i) {
		return TCP_HDR_FIX_TOO + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int guessTcpRecvLength(final int i) {
		return TCP_HDR_FIX_TOO + ((i - 1) / MTU + 1) * TCP_HDR_FIX + i;
	}

	public static int[] splitToIntArray(final String text) {
		if (text == null) {
			return null;
		}

		final String[] va = text.split(":");

		final List<Integer> o = new ArrayList<Integer>();
		for (final String v : va) {
			if (v == null || v.length() <= 0) {
				continue;
			}

			try {
				final int i = Integer.valueOf(v);
				o.add(i);

			} catch (Exception e) {
				e.printStackTrace();
				Log.e(TAG, "invalid port num, ignore");
			}
		}

		final int[] oa = new int[o.size()];
		for (int i = 0; i < oa.length; i++) {
			oa[i] = o.get(i);
		}
		return oa;
	}

	public static int unzipFolder(String zipFileString, String outPathString) {

		try {
			android.util.Log.v("XZip", "UnZipFolder(String, String)");
			java.util.zip.ZipInputStream inZip = new java.util.zip.ZipInputStream(new java.io.FileInputStream(zipFileString));
			java.util.zip.ZipEntry zipEntry;
			String szName = "";

			while ((zipEntry = inZip.getNextEntry()) != null) {
				szName = zipEntry.getName();

				if (zipEntry.isDirectory()) {

					// get the folder name of the widget
					szName = szName.substring(0, szName.length() - 1);
					java.io.File folder = new java.io.File(outPathString + java.io.File.separator + szName);
					folder.mkdirs();

				} else {

					java.io.File file = new java.io.File(outPathString + java.io.File.separator + szName);
					file.createNewFile();
					// get the output stream of the file
					java.io.FileOutputStream out = new java.io.FileOutputStream(file);
					int len;
					byte[] buffer = new byte[1024];
					// read (len) bytes into buffer
					while ((len = inZip.read(buffer)) != -1) {
						// write (len) byte from buffer at the position 0
						out.write(buffer, 0, len);
						out.flush();
					}
					out.close();
				}
			} // end of while

			inZip.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -2;
		}
		return 0;
	}

	public static String getStack() {
		StackTraceElement[] stes = new Throwable().getStackTrace();
		if ((stes == null) || (stes.length < 2)) {
			return "";
		}

		String t = "";

		for (int i = 1; i < stes.length; i++) {
			if (!stes[i].getClassName().contains(MiniApplication.getContext().getPackageName())) {
				break;
			}
			t += "[" + stes[i].getClassName().substring(MiniApplication.getContext().getPackageName().length()) + ":" + stes[i].getMethodName() + "]";
		}
		return t;
	}

	private static final int SDCARD_WARNING_PENCENT = 95;
	private static final int SDCARD_MIN_SIZE = 50 * 1024 * 1024;

	public static boolean checkSDCardFull() {
		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			return false;
		}
		File sdcardDir = Environment.getExternalStorageDirectory();
		StatFs sf = new StatFs(sdcardDir.getPath());
		long blockCount = sf.getBlockCount();
		long availCount = sf.getAvailableBlocks();
		if (blockCount <= 0) {
			return false;
		}
		if (blockCount - availCount < 0) {
			return false;
		}
		int per = (int) ((blockCount - availCount) * 100 / blockCount);
		long availSize = ((long) sf.getBlockSize()) * sf.getAvailableBlocks();
		Log.d(TAG, "checkSDCardFull per:" + per + " blockCount:" + blockCount + " availCount:" + availCount + " availSize:" + availSize);
		if (SDCARD_WARNING_PENCENT > per) {
			return false;
		}
		if (availSize > SDCARD_MIN_SIZE) {
			return false;
		}
		return true;
	}

	public static boolean isSDCardAvail() {
		try {
			return ((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) && (new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath()).canWrite()));
		} catch (Exception e) {
			return false;
		}
	}

	public static String getHostIp() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr.hasMoreElements();) {
					InetAddress inetAddress = ipAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
		} catch (Exception e) {
		}
		return null;
	}

	public static boolean checkPermission(final Context context, final String perm) {
		if (context == null) {
			return false;
		}
		final String pkgName = context.getPackageName();
		final boolean ret = PackageManager.PERMISSION_GRANTED == context.getPackageManager().checkPermission(perm, pkgName);
		Log.d(TAG, pkgName + " has " + (ret ? "permission " : "no permission ") + perm);
		return ret;
	}

	public static boolean jump(final Context context, final String url) {
		final Intent jump = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		if (!isIntentAvailable(context, jump)) {
			Log.e(TAG, "jump to url failed, " + url);
			return false;
		}

		context.startActivity(jump);
		return true;
	}

	public static byte[] httpGet(final String url) {
		if (url == null || url.length() == 0) {
			Log.e(TAG, "httpGet, url is null");
			return null;
		}

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse resp = httpClient.execute(httpGet);
			if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				Log.e(TAG, "httpGet fail, status code = " + resp.getStatusLine().getStatusCode());
				return null;
			}

			return EntityUtils.toByteArray(resp.getEntity());

		} catch (Exception e) {
			Log.e(TAG, "httpGet exception, e = " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	// return desWidth & desHeight
	public static float[] calcScale(float srcWidth, float srcHeight, float maxWidth, float maxHeight) {
		float ratio = Math.min(maxWidth / srcWidth, maxHeight / srcHeight);

		float[] des = new float[2];
		des[0] = srcWidth * ratio;
		des[1] = srcHeight * ratio;

		return des;
	}

	public static String mapToXml(String title, LinkedHashMap<String, String> map) {
		StringBuilder sb = new StringBuilder();
		sb.append("<key>");
		Iterator<Entry<String, String>> iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if (key == null) {
				key = "unknow";
			}
			if (val == null) {
				val = "unknow";
			}
			sb.append("<" + key + ">");
			sb.append(val);
			sb.append("</" + key + ">");
		}
		sb.append("</key>");
		return sb.toString();
	}

	/**
	 * common method for hide vkb
	 * 
	 * @param context
	 */
	public static void hideVKB(final View view) {
		try {
			final Context context = view.getContext();
			final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm == null) {
				return;
			}

			imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final static double SCALE_THRESHOLD = 2;
	public final static double SCALE_HORIZONTAL = 1.2;

	public static boolean isLongVertical(int width, int height) {
		return height > width * SCALE_THRESHOLD;
	}

	public static boolean isLongHorizontal(int width, int height) {
		return width > height * SCALE_THRESHOLD;
	}

	public static void deleteOutOfDateFile(final String dir, final String filePrefix, final long timeLimitMS) {
		if (Util.isNullOrNil(dir)) {
			return;
		}
		File fileDir = new File(dir);
		if (!fileDir.exists() || !fileDir.isDirectory()) {
			return;
		}
		File[] images = fileDir.listFiles();
		for (File file : images) {
			if (!file.isFile()) {
				continue;
			}
			if (!file.getName().startsWith(filePrefix)) {
				continue;
			}
			if (Util.nowMilliSecond() - file.lastModified() - timeLimitMS < 0) {
				continue;
			}
			file.delete();
		}
	}

	public static void writeToFile(String content, String filename) {
		if (Util.isNullOrNil(content) && Util.isNullOrNil(filename)) {
			File dir = new File("/sdcard/Tencent/");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File file = new File("/sdcard/Tencent/" + filename);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
				}
			}

			try {
				FileOutputStream out = new FileOutputStream(file);
				out.write(content.getBytes());
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static byte[] readFromFile(String path) {
		if (isNullOrNil(path)) {
			Log.w(TAG, "readFromFile error, path is null or empty");
			return null;
		}

		File file = new File(path);
		if (!file.exists()) {
			Log.w(TAG, "readFromFile error, file is not exit, path = %s", path);
			return null;
		}

		FileInputStream fin = null;
		try {
			int size = (int) file.length();
			fin = new FileInputStream(file);

			byte[] buf = new byte[size];
			int count = fin.read(buf);
			if (count != size) {
				Log.w(TAG, "readFromFile error, size is not equal, path = %s, file length is %d, count is %d", path, size, count);
				return null;

			} else {
				return buf;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		Log.e(TAG, "readFromFile failed!");
		return null;
	}

	public static boolean writeToFile(String path, byte[] data) {
		if (isNullOrNil(path) || isNullOrNil(data)) {
			Log.w(TAG, "write to file error, path is null or empty, or data is empty");
			return false;
		}

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(path);
			fout.write(data);
			fout.flush();

		} catch (Exception e) {
			e.printStackTrace();
			Log.w(TAG, "write to file error");
			return false;

		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Log.d(TAG, "writeToFile ok!");
		return true;
	}

	public static boolean deleteFile(String path) {
		if (isNullOrNil(path)) {
			return false;
		}
		File file = new File(path);
		if (!file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			return false;
		}
		return file.delete();
	}

	public static String getMachineType() {
		return "android-" + android.os.Build.MODEL + "-" + android.os.Build.VERSION.SDK_INT;
	}

	/**
	 * 判断字符串中是否包含中文信息
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isChinese(String content) {
		if (isNullOrNil(content)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher matcher = pattern.matcher(content);
		return matcher.find();
	}

	/**
	 * 提取文本中的所有数字
	 * 
	 * @param content
	 * @return
	 */
	public static CharSequence filterNumber(CharSequence source) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c >= '0' && c <= '9') {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 清除电话号码里面的特殊字符
	 * 
	 * @param phoneNumberText
	 * @return
	 */
	public static String trimPhoneNumber(String phoneNumberText) {
		return phoneNumberText.replaceAll("[\\.\\-]", "").trim();
	}

	public static String getPrivacyPhoneNum(String phoneNum) {
		if (Util.isNullOrNil(phoneNum)) {
			return "";
		}

		if (phoneNum.length() > 5) {
			StringBuffer privacyPhoneNum = new StringBuffer();
			privacyPhoneNum.append(phoneNum.subSequence(0, phoneNum.length() - 5));
			privacyPhoneNum.append("****");
			privacyPhoneNum.append(phoneNum.charAt(phoneNum.length() - 1));
			return privacyPhoneNum.toString();
		}

		return phoneNum;
	}
}
