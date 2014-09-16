package com.mini.mn.util;

import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public final class LocaleUtil {
	private static final String TAG = "MicroMsg.LocaleUtil";

	// Language
	public static final String LANGUAGE_DEFAULT = "language_default";
	public static final String LANGUAGE_KEY = "language_key";

	public static final String TAIWAN = "zh_TW";
	public static final String HONGKONG = "zh_HK";
	public static final String CHINA = "zh_CN";
	public static final String ENGLISH = "en";
	public static final String THAI = "th"; // 泰文
	public static final String INDONESIAN = "id"; // 印度尼西亚
	public static final String VIETNAMESE = "vi"; // 越南语
	public static final String PORTUGUESE = "pt"; // 葡萄牙语
	public static final String SPANISH = "es"; // 西班牙语
	public static final String RUSSIAN = "ru"; 	//俄语
	public static final String ARABIC = "ar"; 	//阿拉伯语
	public static final String HEBREW = "iw"; 	//希伯来语
	public static final String POLISH = "pl"; 	//波兰语
	public static final String HINDI = "hi"; 	//印地语
	public static final String JAPANESE = "ja"; 	//日语
	public static final String ITALIAN = "it"; 	//意大利语
	public static final String KOREAN = "ko"; 	//韩语
	public static final String MALAY = "ms"; 	//马来语
	public static final String TURKEY = "tr";   //土耳其



	private LocaleUtil() {

	}

	public static boolean isLanguageSupported(String lang){
		if (Util.isNullOrNil(lang)) {
			return false;
		}
		if (lang.equalsIgnoreCase(TAIWAN) || lang.equalsIgnoreCase(HONGKONG) || lang.equalsIgnoreCase(CHINA)
				|| lang.equalsIgnoreCase(ENGLISH) || lang.equalsIgnoreCase(THAI) || lang.equals(INDONESIAN) || lang.equals(VIETNAMESE)
				|| lang.equalsIgnoreCase(PORTUGUESE) || lang.equalsIgnoreCase(SPANISH) || lang.equalsIgnoreCase(RUSSIAN) 
				|| lang.equalsIgnoreCase(ARABIC) || lang.equalsIgnoreCase(HEBREW) || lang.equalsIgnoreCase(POLISH)
				|| lang.equalsIgnoreCase(HINDI) || lang.equalsIgnoreCase(JAPANESE) || lang.equalsIgnoreCase(ITALIAN)
				|| lang.equalsIgnoreCase(KOREAN) || lang.equalsIgnoreCase(MALAY) || lang.equalsIgnoreCase(TURKEY)) {
			return true;
		}
		return false;
	}
	
	/**
	 * update resource locale
	 * 
	 * @param context
	 * @param locale
	 */
	public static void updateApplicationResourceLocale(Context context, Locale locale) {
		final Resources resources = context.getResources();
		final Configuration config = resources.getConfiguration();
		if (config.locale.equals(locale)) {
			return;
		}
		DisplayMetrics dm = resources.getDisplayMetrics();
		config.locale = locale;
		resources.updateConfiguration(config, dm);

		// update global locale for context
		Resources.getSystem().updateConfiguration(config, dm);
	}

	public static Locale transLanguageToLocale(String country) {
		if (country.equals(LocaleUtil.TAIWAN) || country.equals(LocaleUtil.HONGKONG)) {
			return Locale.TAIWAN;
		} else if (country.equals(LocaleUtil.ENGLISH)) {
			return Locale.ENGLISH;
		} else if (country.equals(LocaleUtil.CHINA)) {
			return Locale.CHINA;
		} else if (country.equalsIgnoreCase(LocaleUtil.THAI)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.INDONESIAN)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.VIETNAMESE)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.PORTUGUESE)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.SPANISH)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.RUSSIAN)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		}  else if (country.equalsIgnoreCase(LocaleUtil.ARABIC)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.HEBREW)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.POLISH)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.HINDI)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.JAPANESE)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.ITALIAN)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.KOREAN)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.MALAY)) {
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else if (country.equalsIgnoreCase(LocaleUtil.TURKEY)) { 
			Locale localeTemp = new Locale(country);
			return localeTemp;
		} else {
			Log.e(TAG, "transLanguageToLocale country = " + country);
			return Locale.ENGLISH;
		}
	}
	
	public static String getCurrentCountryCode(){
		return Locale.getDefault().getCountry().trim();
	}

	private static String filterLanguage(final String in) {
		// EN
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(ENGLISH)) {
				return lang;
			}
		}

		// TC
		{
			final String lang = Locale.getDefault().getLanguage().trim() + "_" + Locale.getDefault().getCountry().trim();
			if (lang.equals(TAIWAN) || lang.equals(HONGKONG)) {
				return TAIWAN;
			}
		}

		// CHINA
		{
			final String lang = Locale.getDefault().getLanguage().trim() + "_" + Locale.getDefault().getCountry().trim();
			if (lang.equals(CHINA)) {
				return CHINA;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(THAI)) {
				return THAI;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(INDONESIAN)) {
				return INDONESIAN;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(VIETNAMESE)) {
				return VIETNAMESE;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(PORTUGUESE)) {
				return PORTUGUESE;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(SPANISH)) {
				return SPANISH;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(RUSSIAN)) {
				return RUSSIAN;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(ARABIC)) {
				return ARABIC;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(HEBREW)) {
				return HEBREW;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(POLISH)) {
				return POLISH;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(HINDI)) {
				return HINDI;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(JAPANESE)) {
				return JAPANESE;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(ITALIAN)) {
				return ITALIAN;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(KOREAN)) {
				return KOREAN;
			}
		}
		
		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(MALAY)) {
				return MALAY;
			}
		}

		{
			final String lang = Locale.getDefault().getLanguage().trim();
			if (lang.equals(TURKEY)) {
				return TURKEY;
			}
		}
		
		return in;
	}

	public static String getApplicationLanguage() {
		// FROM LOCAL
		{
			final String lang = Util.nullAsNil(SystemProperty.getProperty(LANGUAGE_KEY));
			if (lang.length() > 0 && !lang.equals(LANGUAGE_DEFAULT)) {
				return lang;
			}
		}

		return filterLanguage(ENGLISH);
	}

	public static String loadApplicationLanguage(SharedPreferences sp, Context context) {
		// READ
		{
			final String lang = Util.nullAsNil(sp.getString(LANGUAGE_KEY, null));
			if (lang.length() > 0 && !lang.equals(LANGUAGE_DEFAULT)) {
				SystemProperty.setProperty(LANGUAGE_KEY, lang);
				return lang;
			}
		}

		final String filterLang = filterLanguage(ENGLISH);
		SystemProperty.setProperty(LANGUAGE_KEY, filterLang);
		return filterLang;
	}

	/**
	 * 获取用户的多语言设置,单纯加载设置，不做其他处理
	 * 
	 * @param sp
	 * @param context
	 * @return
	 */
	public static String loadApplicationLanguageSettings(SharedPreferences sp, Context context) {
		final String lang = Util.nullAsNil(sp.getString(LANGUAGE_KEY, null));
		if (!Util.isNullOrNil(lang)) {
			return lang;
		} else {
			return LANGUAGE_DEFAULT;
		}
	}

	public static void saveApplicationLanguage(SharedPreferences sp, Context context, String language) {
		if (sp.edit().putString(LANGUAGE_KEY, language).commit()) {
			SystemProperty.setProperty(LANGUAGE_KEY, language);
			Log.w(TAG, "save application lang as:" + language);
		} else {
			Log.e(TAG, "saving application lang failed");
		}
	}
}