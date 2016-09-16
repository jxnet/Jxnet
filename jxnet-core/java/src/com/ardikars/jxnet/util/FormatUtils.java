
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

public class FormatUtils {

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

}