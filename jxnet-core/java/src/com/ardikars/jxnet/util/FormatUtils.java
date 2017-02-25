/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.util;

import java.nio.ByteBuffer;
import static com.ardikars.jxnet.util.Preconditions.CheckNotNull;
import static com.ardikars.jxnet.util.Preconditions.CheckArgument;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 * @version 1.1.0
 */
public class FormatUtils {
	
	public static byte[] toBytes(String hexStr) {
		//CheckNotNull(hexStr);
		String src = hexStr.replaceAll("\\s+", "").trim();
		int len = src.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(src.charAt(i), 16) << 4)
					+ Character.digit(src.charAt(i+1), 16));
		}
		return data;
	}

	public static long toLong(byte[] bytes) {
		//CheckNotNull(bytes);
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		return bb.getLong();
	}

	
	public static String toHexString(byte[] data, int offset, int length) {
		//CheckNotNull(data);
		//checkArgument(offset > 0 && length > 0);
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
		//CheckNotNull(b);
		String s = Integer.toHexString((b) & 0xFF);
		if (s.length() == 1)
			return ("0" + s);
		return (s);
	}
	
	public static String toAscii(String hexStr) {
		//CheckNotNull(hexStr);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			sb.append((char) Integer.parseInt(str, 16));
		}
		return sb.toString();
	}
	
}
