package com.mini.mn.algorithm;

/**
 * 整形数据(long, int)与byte数组互换
 * 
 * @Author zorro
 */
public final class TypeTransform {

	private TypeTransform() {

	}

	/**
	 * 将byte数组逆序存储
	 */
	private static byte[] reverseByteArray(byte[] b) {
		int len = b.length;
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			buf[i] = b[len - 1 - i];
		}
		return buf;
	}

	/**
	 * 将int型整数转为byte数组,低字节在前,高字节在后
	 */
	public static byte[] intToByteArrayLH(int n) {
		byte[] intBuf = new byte[4];
		for (int i = 0; i < 4; i++) {
			int offset = i * 8;
			intBuf[i] = (byte) ((n >> offset) & 0xff);
		}
		return intBuf;
	}

	/**
	 * 将long整型数转为byte数组,低字节在前,高字节在后
	 */
	public static byte[] longToByteArrayLH(long n) {
		byte[] longBuf = new byte[8];
		for (int i = 0; i < 8; i++) {
			int offset = i * 8;
			longBuf[i] = (byte) (n >> offset);
		}
		return longBuf;
	}

	/**
	 * 将int型整数转为byte数组,高字节在前,低字节在后
	 */
	public static byte[] intToByteArrayHL(int n) {
		return reverseByteArray(intToByteArrayLH(n));
	}

	/**
	 * 将long整型数转为byte数组,高字节在前,低字节在后
	 */
	public static byte[] longToByteArrayHL(long n) {
		return reverseByteArray(longToByteArrayLH(n));
	}

	/**
	 * 将byte数组(低字节在前,高字节在后)转为int型数值
	 */
	public static int byteArrayLHToInt(byte[] b) {
		return byteArrayLHToInt(b, 0);
	}

	public static int byteArrayLHToInt(byte[] b, int offset) {
		return (int) (((b[3 + offset] & 0xff) << 24) | ((b[2 + offset] & 0xff) << 16) | ((b[1 + offset] & 0xff) << 8) | ((b[offset] & 0xff) << 0));
	}

	/**
	 * 将byte数组(低字节在前,高字节在后)转为long型数值
	 */
	public static long byteArrayLHToLong(byte[] b) {
		return byteArrayLHToLong(b, 0);
	}

	public static long byteArrayLHToLong(byte[] b, int offset) {
		return ((((long) b[7 + offset] & 0xff) << 56) | (((long) b[6 + offset] & 0xff) << 48) | (((long) b[5] & 0xff) << 40) | (((long) b[4 + offset] & 0xff) << 32) | (((long) b[3 + offset] & 0xff) << 24)
				| (((long) b[2 + offset] & 0xff) << 16) | (((long) b[1 + offset] & 0xff) << 8) | (((long) b[offset] & 0xff) << 0));
	}

	/**
	 * 将byte数组(高字节在前,低字节在后)转为int型数值
	 */
	public static int byteArrayHLToInt(byte[] b) {
		return byteArrayHLToInt(b, 0);
	}

	public static int byteArrayHLToInt(byte[] b, int offset) {
		return (int) (((b[offset] & 0xff) << 24) | ((b[1 + offset] & 0xff) << 16) | ((b[2 + offset] & 0xff) << 8) | ((b[3 + offset] & 0xff) << 0));
	}

	/**
	 * 将byte数组(高字节在前,低字节在后)转为long型数值
	 */
	public static long byteArrayHLToLong(byte[] b, int offset) {
		return ((((long) b[offset] & 0xff) << 56) | (((long) b[1 + offset] & 0xff) << 48) | (((long) b[2 + offset] & 0xff) << 40) | (((long) b[3 + offset] & 0xff) << 32) | (((long) b[4 + offset] & 0xff) << 24)
				| (((long) b[5 + offset] & 0xff) << 16) | (((long) b[6 + offset] & 0xff) << 8) | (((long) b[7 + offset] & 0xff) << 0));
	}

	public static long byteArrayHLToLong(byte[] b) {
		return byteArrayHLToLong(b, 0);
	}
}
