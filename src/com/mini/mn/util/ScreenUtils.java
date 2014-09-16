package com.mini.mn.util;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.mini.mn.app.MiniApplication;

/**
 * ��Ļ����
 * 
 * @version 1.0.0
 * @date 2013-1-29
 * @author S.Kei.Cheung
 */
public class ScreenUtils {

	private static float density = -1.0F;
	
	public static int fromDPToPix(int paramInt)
	{
		return Math.round(getDensity() * paramInt);
	}
	
	/**
	 * ��Ļ�ܶ�
	 * @param paramContext
	 * @return
	 */
	public static float getDensity()
	{
		if (density < 0.0F) {
			density = MiniApplication.getContext().getResources().getDisplayMetrics().density;
		}
		return density;
	}
	
	/**
	 * ͨ����ɫ��ԴIDȡ����ɫ״̬�б�
	 * @param paramInt
	 * @return
	 */
	public static ColorStateList getColorStateList(int paramInt)
	{
		return MiniApplication.getContext().getResources().getColorStateList(paramInt);
	}
	
	/**
	 * ͨ��ͼƬ��ԴIDȡ��Drawable
	 * @param paramInt
	 * @return
	 */
	public static Drawable getDrawable(int paramInt)
	{
		return MiniApplication.getContext().getResources().getDrawable(paramInt);
	}
	
	/**
	 * ����DP��С
	 * @param paramInt
	 * @return
	 */
	public static int getDimensionPixelSize(int paramInt)
	{
		return MiniApplication.getContext().getResources().getDimensionPixelSize(paramInt);
	}
	
	/**
	 * ȡ���ַ���
	 * @param paramInt
	 * @return
	 */
	public static String getString(int paramInt)
	{
		return MiniApplication.getContext().getResources().getString(paramInt);
	}
	
	/**
	 * ȡ��Dip��С
	 * @param paramInt
	 * @return
	 */
	public static int getDipSize(int paramInt)
	{
		return Math.round(paramInt / getDensity());
	}
}
