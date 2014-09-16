package com.mini.mn.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import android.text.TextUtils;
import android.util.Base64;

/**
 * �ַ���������.
 * 
 * @version 2.0.0
 * @date 2014-01-29
 * @author S.Kei.Cheung
 */
public class StringUtils {
	
	private StringUtils() {}

	/**
	 * �ж��ַ��Ƿ�Ϊnull��մ�.
	 * 
	 * @param src ���жϵ��ַ�
	 * @return
	 */
	public static boolean isEmpty(String src) {
		return src == null || "".equals(src.trim())
				|| "null".equalsIgnoreCase(src);
	}
	
	/**
	 * ��ָ�����ַ�������Base64����.
	 * 
	 * @param src ��������ַ���
	 * @return
	 */
	public static String base64Encode(String src) {
		return Base64.encodeToString(src.getBytes(), Base64.DEFAULT);
	}
	
	/**
     * ��֤����������ʽ�Ƿ����
     * 
     * @param email
     * @return �Ƿ�Ϸ�
     */
	public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 = "\\w+@(\\w+\\.){1,3}\\w+";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }
	
	/**
	 * String�Ƿ��������ַ�
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static boolean StringSpecial(String str) throws PatternSyntaxException {      
		boolean tag = true;
		final String regEx = "[^a-zA-Z0-9]";
		final Pattern pattern = Pattern.compile(regEx);
		final Matcher mat = pattern.matcher(str);
		if (mat.find()) {
			tag = false;
		}
		return tag;
    }
	
	/**
	 * ��ת��
	 * @param url
	 * @return
	 */
	public static String unescapeUnicode(String url) {
		if (isEmpty(url)) {
			return "";
		}
		String result = url;
		String[][] unescape = new String[][]{
				new String[]{"\\\\", ""},
				new String[]{"\\\"", "\""},
				new String[]{"\\'", "'"},
		};
		for (String[] item : unescape) {
			result = result.replaceAll(item[0], item[1]);
		}
		
		return result;
	}
	
	/**
	 * ���ַ���������double����
	 * 
	 * @param value
	 * @return
	 */
	public static double convertStringToDouble(String value) {
		double rlt = 0;
		try {
			rlt = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			// ignore.
		}
		return rlt;
	}
	
	/**
	 * ת�����ո�ʽΪ yyyy-MM-dd.
	 * 
	 * @param birthday
	 * @return
	 */
	public static String replaceBirthday(String birthday) {
		if (isEmpty(birthday)) {
			return "";
		}
		
		return birthday.replaceAll("(\\d{4}-\\d{1,2}-\\d{1,2}).*", "$1");
	}
	
	/**
	 * �����б�nullֵ����
	 * 
	 * @param value ��Ҫ�����ֵ
	 * @return ������ֵ
	 * */
	public static String transformNullValue(String value) {
		return isEmpty(value) ? "��ѡ��" : value; 
	}
	
	/**
	 * �ַ�����дת��Сд����
	 * 
	 * @param str ��Ҫ�����ֵ
	 * @return ������ֵ
	 * */
	public static String changeAa(String str) {
    	
    	StringBuffer sb = new StringBuffer();
    	for(int i=0; i<str.length(); i++) {
    		
    		char c = str.charAt(i); 
    		if(c >= 'A' && c <= 'Z') c = (char)(c - 'A' + 'a'); 
    		sb.append(c);
    	}
    	
    	return sb.toString();
    }
	
	/**
	 * ���ַ���ת��Ϊ����
	 * 
	 * @param value
	 * @return
	 */
	public static int parseInt(String value) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * �õ���Ӣ�Ļ���ַ�������.
	 * 
	 * @param src
	 * @param endcoding
	 * @return
	 */
	public static int getChineseLength(String src, String endcoding) {
		// ���巵�ص��ַ�������
		int len = 0;
		try {
			int j = 0;
			// ����ָ������õ�byte[]
			byte[] byteValue = src.getBytes(endcoding);
			while (true) {
				short tmpst = (short) (byteValue[j] & 0xF0);
				if (tmpst >= 0xB0) {
					if (tmpst < 0xC0) {
						j += 2;
						len += 2;
					} else if ((tmpst == 0xC0) || (tmpst == 0xD0)) {
						j += 2;
						len += 2;
					} else if (tmpst == 0xE0) {
						j += 3;
						len += 2;
					} else if (tmpst == 0xF0) {
						short tmpst0 = (short) (((short) byteValue[j]) & 0x0F);
						if (tmpst0 == 0) {
							j += 4;
							len += 2;
						} else if ((tmpst0 > 0) && (tmpst0 < 12)) {
							j += 5;
							len += 2;
						} else if (tmpst0 > 11) {
							j += 6;
							len += 2;
						}
					}
				} else {
					j += 1;
					len += 1;
				}
				if (j > byteValue.length - 1) {
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			return 0;
		}
		return len;
	}
	
	/**
	 * ��ָ�����ַ�������Base64����.
	 * 
	 * @param src ��������ַ���
	 * @return
	 */
	public static String base64Decode(String src) {
		return new String(Base64.decode(src, Base64.DEFAULT));
	}
	
	/**
	 * �õ�UTF-8�������Ӣ�Ļ���ַ�������.
	 * 
	 * @param src
	 * @return
	 */
	public static int getChineseLength(String src) {
		return getChineseLength(src, "UTF-8");
	}
	
	public static int getLength(String src) {
		if (src == null) {
			return 0;
		}
		return src.length();
	}
	
	/**
	 * �Ƴ��ַ��������еĵ�һ��Ԫ��.
	 * 
	 * @param original
	 * @return
	 */
	public static String[] removeFirstItem(String[] original) {
        if (original == null || original.length == 0) {
        	return new String[]{};
        }
		int newLength = original.length - 1;
        String[] copy = new String[newLength];
        System.arraycopy(original, 1, copy, 0, newLength);
        return copy;
	}
	
	/**
	 * �ֻ�����������ʽ
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles){        
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");        
        Matcher m = p.matcher(mobiles);    
        return m.matches();        
    }
	
	/**
	 * ����������ʽ
	 * @param password
	 * @return
	 */
	public static boolean isPasswordFormat(String password){        
        Pattern p = Pattern.compile("^[A-Za-z0-9]*$");      
        Matcher m = p.matcher(password);    
        return m.matches();        
    }
	
	/**
	 * �ǳƺ��ֻ���������ʽ
	 * @param nickNameID
	 * @return
	 */
	public static boolean isNumberFormat(String nickNameID){        
        Pattern p = Pattern.compile("^[0-9]*$");      
        Matcher m = p.matcher(nickNameID);    
        return m.matches();        
    }
	
	/**
	 * ��Ӣ������������ʽ(4-16λ)
	 * @param str
	 * @return
	 */
	public static boolean isChineseEnglishFormat(String str){        
        Pattern p = Pattern.compile("^[\\u4E00-\\u9FA5\\uF900-\\uFA2DA-Za-z0-9]+$");      
        Matcher m = p.matcher(str); 
        str=str.replaceAll("[^\\x00-\\xff]", "**");	//ƥ��˫�ֽ��ַ�
        int length = str.length();
        return m.matches()&&(length>=4&&length<=16);
    }
	
	/** 
     * ��ȡһ���ַ��ĳ���(�����ա������ַ�����Ϊ2),��������Ӣ��,������ֲ����ã�����ȡһ���ַ�λ 
     *  
     * @param str ԭʼ�ַ��� 
     * @param specialCharsLength ��ȡ����(�����ա������ַ�����Ϊ2) 
     * @return 
     */  
    public static String trim(String str, int specialCharsLength) {  
        if (str == null || "".equals(str) || specialCharsLength < 1) {  
            return "";  
        }  
        char[] chars = str.toCharArray();  
        int charsLength = getCharsLength(chars, specialCharsLength);  
        return new String(chars, 0, charsLength);  
    }
    
    /** 
     * ��ȡһ���ַ��ĳ��ȣ����볤���к����ա������ַ�����Ϊ2����������������ַ�������Ϊ1 
     * @param chars һ���ַ� 
     * @param specialCharsLength ���볤�ȣ������ա������ַ�����Ϊ2 
     * @return ������ȣ������ַ�������Ϊ1 
     */  
    private static int getCharsLength(char[] chars, int specialCharsLength) {  
        int count = 0;  
        int normalCharsLength = 0;  
        for (int i = 0; i < chars.length; i++) {  
            int specialCharLength = getSpecialCharLength(chars[i]);  
            if (count <= specialCharsLength - specialCharLength) {  
                count += specialCharLength;  
                normalCharsLength++;  
            } else {  
                break;  
            }  
        }  
        return normalCharsLength;  
    }  
    
    
    /** 
     * ��ȡһ���ַ��ĳ��ȣ����볤���к����ա������ַ�����Ϊ2����������������ַ�������Ϊ1 
     * @param str һ���ַ� 
     * @return ������ȣ������ַ�������Ϊ1 
     */  
    public static int getStringLength(String str) {  
        int normalCharsLength = 0;  
        char[] chars = str.toCharArray();  
        for (int i = 0; i < chars.length; i++) {  
          normalCharsLength+=getSpecialCharLength(chars[i]);  
        }  
        return normalCharsLength;
    }  
    
    /** 
     * �ַ�����ȡ�����볤���к����ա������ַ�����Ϊ2����������������ַ�������Ϊ1����������β������<b>...</b>
     * @param str һ���ַ� 
     * @param limitLength �������ƣ�������ȡβ������<b>...</b>
     * @return ������ȣ������ַ�������Ϊ1 
     */  
    public static String subLength(String str,int limitLength) {  
    	final int length=getStringLength(str);
    	String content=str;
    	if(length>limitLength){
    		content=StringUtils.trim(str,limitLength)+"...";
    	}
    	return content;
    }  
  
    /** 
     * ��ȡ�ַ����ȣ������ա������ַ�����Ϊ2��ASCII����ַ�����Ϊ1 
     * @param c �ַ� 
     * @return �ַ����� 
     */  
    private static int getSpecialCharLength(char c) {  
        if (isLetter(c)) {  
            return 1;  
        } else {  
            return 2;  
        }  
    }  
  
    /** 
     * �ж�һ���ַ���Ascill�ַ����������ַ����纺���գ������ַ��� 
     *  
     * @param char c, ��Ҫ�жϵ��ַ� 
     * @return boolean, ����true,Ascill�ַ� 
     */  
    private static boolean isLetter(char c) {  
        int k = 0x80;  
        return c / k == 0 ? true : false;  
    }

    /**
     * �ַ����÷��Ÿ���
     * @param strs
     * @return
     */
    public static String apartString(CharSequence s, String[] strs) {
    	return TextUtils.join(s, strs);
    }

    /**
     * Ϊ�ַ������˫����
     * @param obj �ַ���
     * @return ����˫���ź�������ַ���
     */
    public static String addQuotationMarks(String obj){
    	return "\""+obj+"\"";
    }
    
    /**
     * checks if a String is whitespace,empty("") or null
     * @param str
     * @return
     */
    public static boolean isBlacnk(String str){
    	int strLen;
    	if(str == null || (strLen = str.length()) == 0){
    		return true;
    	}
    	for(int i = 0; i < strLen; i++){
    		if((Character.isWhitespace(str.charAt(i)) == false)){
    			return false;
    		}
    	}
    	return true;
    }
    
    /**
     * checks if a String is not empty (""),not null and whitespace only.
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str){
    	return !StringUtils.isBlacnk(str);
    }
}
