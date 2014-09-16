package com.mini.mn.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ListView;

/**
 * ºÊ»›π§æﬂ¿‡
 * 
 * @version 1.0.0
 * @date 2014-06-16
 * @author S.Kei.Cheung
 */
public class BackwardSupportUtil {

	private static final String TAG = "MicroMsg.SDK.BackwardSupportUtil";
	public static final int ANDROID_API_LEVEL_16 = 16;
	private static final int ANDROID_API_LEVEL_8 = 8;

	public static class BitmapFactory {

		private static final float MDPI_DENSITY = 1.0f;
		private static final float HDPI_DENSITY = 1.5f;

		public static Bitmap decodeFile(final String file, final float densityScale) {
			final android.graphics.BitmapFactory.Options option = new android.graphics.BitmapFactory.Options();
			final float density = densityScale * android.util.DisplayMetrics.DENSITY_DEFAULT;
			option.inDensity = (int) density;
			final android.graphics.Bitmap bm = android.graphics.BitmapFactory.decodeFile(file, option);
			if (bm != null) {
				bm.setDensity((int) density);
			}
			return bm;
		}

		public static int fromDPToPix(final Context context, final float dp) {
			final DisplayMetrics metric = new DisplayMetrics();
			((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metric);
			// return (int) (dp * metric.densityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT);
			return Math.round(dp * metric.densityDpi / android.util.DisplayMetrics.DENSITY_DEFAULT);
		}

		public static Bitmap decodeStream(final InputStream is) {
			final android.graphics.BitmapFactory.Options option = new android.graphics.BitmapFactory.Options();
			option.inDensity = android.util.DisplayMetrics.DENSITY_DEFAULT;
			option.inPreferredConfig = Bitmap.Config.ARGB_8888;
			return android.graphics.BitmapFactory.decodeStream(is, null, option);
		}

		public static Bitmap decodeStream(InputStream is, float densityScale) {
			final android.graphics.BitmapFactory.Options option = new android.graphics.BitmapFactory.Options();
			option.inDensity = (int) (densityScale * android.util.DisplayMetrics.DENSITY_DEFAULT);
			option.inPreferredConfig = Bitmap.Config.ARGB_8888;
			return android.graphics.BitmapFactory.decodeStream(is, null, option);
		}

		public static String getDisplayDensityType(Context context) {
			final DisplayMetrics dm = context.getResources().getDisplayMetrics();
			final Configuration cfg = context.getResources().getConfiguration();

			String type = "";
			if (dm.density < MDPI_DENSITY) {
				type += "LDPI";
			} else if (dm.density >= HDPI_DENSITY) {
				type += "HDPI";
			} else {
				type += "MDPI";
			}

			type += (cfg.orientation == Configuration.ORIENTATION_LANDSCAPE) ? "_L" : "_P";
			return type;
		}

		public static Bitmap getBitmapFromURL(final String src) {
			HttpURLConnection conn = null;
			try {
				Log.d(TAG, "get bitmap from url:" + src);
				URL url = new URL(src);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.connect();
				InputStream input = connection.getInputStream();
				final Bitmap myBitmap = BitmapFactory.decodeStream(input);
				input.close();

				return myBitmap;

			} catch (IOException e) {
				Log.e(TAG, "get bitmap from url failed");
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}

			return null;
		}
	}

	public static class SmoothScrollFactory {
		public interface IScroll {
			void doScroll(ListView lv);

			void doScroll(final ListView lv, int position);
		}

		public SmoothScrollFactory() {

		}

		public static void scrollToTop(ListView lv) {
			if (android.os.Build.VERSION.SDK_INT >= ANDROID_API_LEVEL_8) {
				(new SmoothScrollToPosition22()).doScroll(lv);
			} else {
				(new SmoothScrollToPosition21below()).doScroll(lv);
			}
		}

		public static void scrollTo(ListView lv, int position) {
			if (android.os.Build.VERSION.SDK_INT >= ANDROID_API_LEVEL_8) {
				(new SmoothScrollToPosition22()).doScroll(lv, position);
			} else {
				(new SmoothScrollToPosition21below()).doScroll(lv, position);
			}
		}
	}

	public static class AnimationHelper {

		public interface IHelper {
			void cancelAnimation(View view, Animation ani);
		}

		public static void cancelAnimation(final View view, final Animation ani) {
			if (android.os.Build.VERSION.SDK_INT >= ANDROID_API_LEVEL_8) {
				(new AnimationHelperImpl22()).cancelAnimation(view, ani);
			} else {
				(new AnimationHelperImpl21below()).cancelAnimation(view, ani);
			}
		}

		public static void overridePendingTransition(Activity activity, int enterAnim, int exitAnim) {
			activity.overridePendingTransition(enterAnim, exitAnim);
		}
	}

	public static class ExifHelper {
		public static int getExifOrientation(String filepath) {

			int degree = 0;
			ExifInterface exif = null;
			try {
				exif = new ExifInterface(filepath);

			} catch (IOException ex) {
				Log.e(TAG, "cannot read exif" + ex);
			}

			if (exif != null) {
				int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
				if (orientation != -1) {
					// We only recognize a subset of orientation tag values.
					switch (orientation) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						degree = 90;
						break;

					case ExifInterface.ORIENTATION_ROTATE_180:
						degree = 180;
						break;

					case ExifInterface.ORIENTATION_ROTATE_270:
						degree = 270;
						break;

					default:
						break;
					}

				}
			}
			return degree;
		}
	}
}

class SmoothScrollToPosition22 implements BackwardSupportUtil.SmoothScrollFactory.IScroll {

	private static final int SCROLL_UNIT = 10;

	@Override
	public void doScroll(final ListView lv) {
		// If the ListView is scrolled to its maximum top or its maximum bottom the method may does nothing at all
		int first = lv.getFirstVisiblePosition();
		if (first > SCROLL_UNIT) {
			lv.setSelection(SCROLL_UNIT);
		}
		lv.smoothScrollToPosition(0);
	}

	@Override
	public void doScroll(final ListView lv, int position) {
		int first = lv.getFirstVisiblePosition();
		if (first > position && first - position > SCROLL_UNIT) {
			lv.setSelection(position + SCROLL_UNIT);
		} else if (first < position && position - first > SCROLL_UNIT) {
			lv.setSelection(position - SCROLL_UNIT);
		}
		lv.smoothScrollToPosition(position);
	}

}

class SmoothScrollToPosition21below implements BackwardSupportUtil.SmoothScrollFactory.IScroll {

	@Override
	public void doScroll(ListView lv) {
		lv.setSelection(0);
	}

	@Override
	public void doScroll(final ListView lv, int position) {
		lv.setSelection(position);
	}
}

class AnimationHelperImpl22 implements BackwardSupportUtil.AnimationHelper.IHelper {

	@Override
	public void cancelAnimation(View view, Animation ani) {
		ani.cancel();
	}

}

class AnimationHelperImpl21below implements BackwardSupportUtil.AnimationHelper.IHelper {

	@Override
	public void cancelAnimation(View view, Animation ani) {
		if (view != null) {
			view.setAnimation(null);
		}
	}

}
