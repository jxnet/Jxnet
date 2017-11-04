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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public class FormatUtils {

	@Deprecated
	public static ByteBuffer toDirectBuffer(ByteBuffer byteBuffer) {
		Validate.nullPointer(byteBuffer);
		if (byteBuffer.isDirect())
			return byteBuffer;
		ByteBuffer buffer = ByteBuffer.
				allocateDirect(byteBuffer.capacity());
		return buffer.put(byteBuffer);
	}

	@Deprecated
	public static ByteBuffer toDirectBuffer(byte[] bytes) {
		Validate.nullPointer(bytes);
		ByteBuffer buffer = ByteBuffer.
				allocateDirect(bytes.length);
		return buffer.put(bytes);
	}

	@Deprecated
	public static byte[] toBytes(ByteBuffer byteBuffer) {
		Validate.nullPointer(byteBuffer);
		if (!byteBuffer.hasRemaining()) {
			byteBuffer.flip();
		}
		byte[] buffer = new byte[byteBuffer.remaining()];
		byteBuffer.get(buffer);
		return buffer;
	}

	@Deprecated
	public static byte[] toBytes(String hexStr) {
		Validate.nullPointer(hexStr);
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
		Validate.nullPointer(bytes);
		ByteBuffer bb = ByteBuffer.allocate(bytes.length);
		bb.put(bytes);
		return bb.getLong();
	}

	@Deprecated
	public static String toHexString(byte[] data) {
		return toHexString(data, 0, data.length);
	}

	@Deprecated
	public static String toHexString(byte[] data, int offset, int length) {
		Validate.nullPointer(data);
		Validate.illegalArgument(offset >= 0 && length > 0);
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

	@Deprecated
	public static String toHexString(byte value) {
		String srt = Integer.toHexString((value) & 0xFF);
		if (srt.length() == 1)
			return ("0" + srt);
		return (srt);
	}

	@Deprecated
	public static String toHexString(short value) {
		String str = Integer.toHexString(value);
		String srt = Integer.toHexString((value) & 0xFFFF);
		if (srt.length() == 1)
			return ("0" + srt);
		return (srt);
	}

	@Deprecated
	public static String toHexString(int value) {
		String str = Integer.toHexString(value);
		String srt = Integer.toHexString(value);
		if (srt.length() == 1)
			return ("0" + srt);
		return (srt);
	}
	
	public static String toAscii(String hexStr) {
		Validate.nullPointer(hexStr);
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < hexStr.length(); i += 2) {
			String str = hexStr.substring(i, i + 2);
			sb.append((char) Integer.parseInt(str, 16));
		}
		return sb.toString();
	}

}
