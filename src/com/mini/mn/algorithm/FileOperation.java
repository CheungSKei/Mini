package com.mini.mn.algorithm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class FileOperation {

	public static int appendBuf(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte)
	{
		return appendBuf(paramString1, paramString2 + paramString3, paramArrayOfByte);
	}
	
	public static int appendBuf(String paramString1, String paramString2, byte[] paramArrayOfByte)
	{
		if (paramArrayOfByte == null) {
			return -2;
		}
		try
		{
			File localFile = new File(paramString1);
			if (!localFile.exists()) {
				localFile.mkdirs();
			}
			
			String str = paramString1 + paramString2;
			localFile = new File(str);
			
			if (!localFile.exists()) {
				localFile.createNewFile();
			}
			
			BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile, true));
			localBufferedOutputStream.write(paramArrayOfByte);

			localBufferedOutputStream.flush();

			localBufferedOutputStream.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static int appendToFile(String paramString, byte[] paramArrayOfByte)
	{
		if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0)) {
			return -2;
		}
		try
		{
			FileOutputStream localFileOutputStream = new FileOutputStream(paramString, true);
			localFileOutputStream.write(paramArrayOfByte, 0, paramArrayOfByte.length);
			localFileOutputStream.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static int appendToFile(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	{
		if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0) || (paramArrayOfByte.length < paramInt1 + paramInt2)) {
			return -2;
		}
		try
		{
			FileOutputStream localFileOutputStream = new FileOutputStream(paramString, true);
			localFileOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
			localFileOutputStream.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static int writeFile(String paramString, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
	{
		if (paramArrayOfByte == null) {
			return -2;
		}
		if (paramArrayOfByte.length < paramInt1 + paramInt2) {
			return -3;
		}
		try
		{
			FileOutputStream localFileOutputStream = new FileOutputStream(paramString, false);
			localFileOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
			localFileOutputStream.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
			return -1;
		}
		return 0;
	}
	
	public static byte[] readFromFile(String paramString, int paramInt1, int paramInt2)
	{
		if (paramString == null) {
			return null;
		}
		
		File localFile = new File(paramString);
		if (!localFile.exists()) {
			return null;
		}
		
		if (paramInt2 == -1) {
			paramInt2 = (int)localFile.length();
		}
		
		if (paramInt1 < 0) {
			return null;
		}
		
		if (paramInt2 <= 0) {
			return null;
		}
		
		if (paramInt1 + paramInt2 > (int)localFile.length()) {
			return null;
		}
		
		byte[] arrayOfByte = null;
		try
		{
			RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString, "r");
			arrayOfByte = new byte[paramInt2];
			localRandomAccessFile.seek(paramInt1);
			localRandomAccessFile.readFully(arrayOfByte);
			localRandomAccessFile.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		return arrayOfByte;
	}
	
	public static byte[] readFromFileV2(String paramString, int paramInt1, int paramInt2)
	{
		if (paramString == null) {
			return null;
		}
		File localFile = new File(paramString);
		if (!localFile.exists()) {
			return null;
		}
		if (paramInt2 == -1) {
			paramInt2 = (int)localFile.length();
		}
		if (paramInt1 < 0) {
			return null;
		}
		if (paramInt2 <= 0) {
			return null;
		}
		if (paramInt1 + paramInt2 > (int)localFile.length()) {
			paramInt2 = (int)localFile.length() - paramInt1;
		}
		byte[] arrayOfByte = null;
		try
		{
			RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramString, "r");
			arrayOfByte = new byte[paramInt2];
			localRandomAccessFile.seek(paramInt1);
			localRandomAccessFile.readFully(arrayOfByte);
			localRandomAccessFile.close();
		}
		catch (Exception localException)
		{
			localException.printStackTrace();
		}
		return arrayOfByte;
	}
	
	public static int readFileLength(String paramString)
	{
		if (paramString == null) {
			return 0;
		}
		File localFile = new File(paramString);
		if (!localFile.exists()) {
			return 0;
		}
		return (int)localFile.length();
	}
	
	public static final String identifyImgType(String paramString)
	{
		if ((paramString == null) || (paramString.equals(""))) {
			return null;
		}
		byte[] arrayOfByte = readFromFile(paramString, 0, 2);
		if ((arrayOfByte == null) || (arrayOfByte.length < 2)) {
			return null;
		}
		String str = ".jpg";
		int i = arrayOfByte[0];
		if (i < 0) {
			i += 256;
		}
		int j = arrayOfByte[1];
		if (j < 0) {
			j += 256;
		}
		if ((i == 66) && (j == 77)) {
			str = ".bmp";
		} else if ((i == 255) && (j == 216)) {
			str = ".jpg";
		} else if ((i == 137) && (j == 80)) {
			str = ".png";
		} else if ((i == 71) && (j == 73)) {
			str = ".gif";
		}
		return str;
	}
	
	public static final void renameFile(String paramString1, String paramString2, String paramString3)
	{
		if ((paramString1 == null) || (paramString2 == null) || (paramString3 == null)) {
			return;
		}
		File localFile1 = new File(paramString1 + paramString2);
		File localFile2 = new File(paramString1 + paramString3);
		if (localFile1.exists()) {
			localFile1.renameTo(localFile2);
		}
	}
	
	public static final boolean fileExists(String paramString)
	{
		if (paramString == null) {
			return false;
		}
		File localFile = new File(paramString);
		if (localFile.exists()) {
			return true;
		}
		return false;
	}
	
	public static final boolean deleteFile(String paramString)
	{
		if (paramString == null) {
			return true;
		}
		File localFile = new File(paramString);
		if (localFile.exists()) {
			return localFile.delete();
		}
		return true;
	}
	
	public static final boolean deleteDir(File paramFile)
	{
		if ((paramFile == null) || (!paramFile.exists())) {
			return false;
		}
		if (paramFile.isFile())
		{
			paramFile.delete();
		}
		else if (paramFile.isDirectory())
		{
			File[] arrayOfFile = paramFile.listFiles();
			for (int i = 0; i < arrayOfFile.length; i++) {
				deleteDir(arrayOfFile[i]);
			}
		}
		paramFile.delete();
		return true;
	}
	
	public static final String getFileExt(String paramString)
	{
		File localFile = new File(paramString);
		String str = localFile.getName();
		if ((str == null) || (str.length() <= 0)) {
			str = paramString;
		}
		int i = str.lastIndexOf('.');
		if ((i <= 0) || (i == str.length() - 1)) {
			return "";
		}
		return str.substring(i + 1);
	}
}
