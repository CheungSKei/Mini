package com.mini.mn.platformtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.mini.mn.util.Log;

import android.content.Context;

public final class FilesCopy {
	private static final String TAG = "FilesCopy";

	private FilesCopy() {
	}

	public static boolean copyFile(String srcPath, String destPath) {
		final int length = 16 * 1024; // 16K
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		boolean isOK = false;
		try {
			inStream = new FileInputStream(srcPath);
			outStream = new FileOutputStream(destPath);
			byte[] buffer = new byte[length];
			int ins;
			while ((ins = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, ins);
			}
			isOK = true;
		} catch (Exception e) {
			isOK = false;
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					isOK = false;
					e.printStackTrace();
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					isOK = false;
					e.printStackTrace();
				}
			}
		}
		return isOK;
	}

	public static boolean copy(String from, String to, boolean deleteSrcFile) {
		File fileFrom = new File(from);
		if (!fileFrom.exists()) {
			return false;
		}

		File fileTo = new File(to);
		if (fileFrom.isFile()) {
			if ((!fileTo.isFile()) && (fileTo.exists())) {
				return false;
			}
			copyFile(from, to);
			if (deleteSrcFile) {
				fileFrom.delete();
			}
		} else if (fileFrom.isDirectory()) {
			if (!fileTo.exists()) {
				fileTo.mkdir();
			}
			if (!fileTo.isDirectory()) {
				return false;
			}
			String[] fileList;
			fileList = fileFrom.list();
			for (int i = 0; i < fileList.length; i++) {
				copy(from + "/" + fileList[i], to + "/" + fileList[i],
						deleteSrcFile);
			}
		}
		return true;
	}

	public static boolean copyAssets(Context context, String srcPath,
			String destPath) {
		final int length = 16 * 1024; // 16K
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		boolean isOK = false;
		try {
			InputStream is = context.getAssets().open(srcPath);
			outStream = new FileOutputStream(destPath);
			byte[] buffer = new byte[length];
			int ins;
			while ((ins = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, ins);
			}

			File file = new File(destPath);
			// 根据两个文件大小判断是否复制正确
			is.close();
			is = context.getAssets().open(srcPath);
			int srcSize = is.available();
			if (file.exists() && (srcSize == file.length())) {
				isOK = true;
			} else {
				isOK = false;
			}
		} catch (Exception e) {
			isOK = false;
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					isOK = false;
					Log.e(TAG, null, e);
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					isOK = false;
					Log.e(TAG, null, e);
				}
			}
		}
		return isOK;
	}
}
