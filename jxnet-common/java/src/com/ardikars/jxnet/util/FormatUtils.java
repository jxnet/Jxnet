
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

public class FormatUtils {

	public static byte[] toBytes(String hexStr) {
		String src = hexStr.replaceAll("\\s+", "").trim();
		int len = src.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(src.charAt(i), 16) << 4)
					+ Character.digit(src.charAt(i+1), 16));
		}
		return data;
	}

	public static String toHexString(byte[] data, int offset, int length) {
		StringBuilder sb = new StringBuilder();
		int l;
		if(data.length != length) {
			l = data.length - length;
		} else {
			l = length;
		}
		for(int i=offset; i<l; i++) {
			sb.append(toHexString(data[i]));
		}
		return sb.toString();
	}

	public static String toHexString(byte b) {
		String s = Integer.toHexString((b) & 0xFF);
		if (s.length() == 1)
			return ("0" + s);
		return (s);
	}

	public static String toAscii(String hexStr) {
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			sb.append((char) Integer.parseInt(str, 16));
		}
		return sb.toString();
	}

}
