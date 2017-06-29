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

import java.util.regex.Pattern;

import static com.ardikars.jxnet.Validate.CheckNotNull;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class HexUtils {

    private static final Pattern NO_SEPARATOR_HEX_STRING_PATTERN
            = Pattern.compile("\\A([0-9a-fA-F][0-9a-fA-F])+\\z");

    private static final String HEADER =
            "         +-------------------------------------------------+\n" +
            "         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |\n" +
            "+--------+-------------------------------------------------+----------------+\n";

    private static final String FOOTER =
            "+--------+-------------------------------------------------+----------------+";

    /**
     * Byte to hex value.
     * @param value value.
     * @return hex format.
     */
    public static String toHex(final byte value) {
        String srt = Integer.toHexString((value) & 0xff);
        if (srt.length() == 1)
            return ("0" + srt);
        return (srt);
    }

    /**
     * Byte array to hex stream.
     * @param data byte array.
     * @return hex stream.
     */
    public static String toHex(final byte[] data) {
        return toHex(data, 0, data.length);
    }

    /**
     * Byte array to hex stream.
     * @param data byte array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     */
    public static String toHex(final byte[] data, final int offset, final int length) {
        ArrayUtils.validateBounds(data, offset, length);
        StringBuilder sb = new StringBuilder();
        int l;
        if(data.length != length) {
            l = data.length - length;
        } else {
            l = length;
        }
        for(int i=offset; i<l; i++) {
            sb.append(toHex(data[i]));
        }
        return sb.toString();
    }

    /**
     * Short to hex stream.
     * @param value short array.
     * @return hex stream.
     */
    public static String toHex(final short value) {
        String srt = Integer.toHexString((value) & 0xFFFF);
        if (srt.length() == 1)
            return ("0" + srt);
        return (srt);
    }

    /**
     * Short array to hex stream.
     * @param values short array.
     * @return hex stream.
     */
    public static String toHex(final short[] values) {
        return toHex(values, 0, values.length);
    }

    /**
     * Short array to hex stream.
     * @param values short array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     */
    public static String toHex(final short[] values, final int offset, final int length) {
        ArrayUtils.validateBounds(values, offset, length);
        StringBuilder sb = new StringBuilder();
        for (short value : values) {
            sb.append(toHex(value));
        }
        return sb.toString();
    }

    /**
     * Int to hex stream.
     * @param value integer value.
     * @return hex stream.
     */
    public static String toHex(final int value) {
        String srt = Integer.toHexString(value);
        if (srt.length() == 1)
            return ("0" + srt);
        return (srt);
    }

    /**
     * Int array to hex stream.
     * @param values int array.
     * @return hex stream.
     */
    public static String toHex(final int[] values) {
        return toHex(values, 0, values.length);
    }

    /**
     * Int array to hex stream.
     * @param values int array.
     * @param offset offset.
     * @param length length.
     * @return hex stream.
     */
    public static String toHex(final int[] values, final int offset, final int length) {
        ArrayUtils.validateBounds(values, offset, length);
        StringBuilder sb = new StringBuilder();
        for (int value : values) {
            sb.append(toHex(value));
        }
        return sb.toString();
    }

    /**
     * Byte array to hex dump format.
     * @param data byte array.
     * @return hex dump format.
     */
    public static String toPrettyHexDump(final byte[] data) {
        return toPrettyHexDump(data, 0, data.length);
    }

    /**
     * Byte array to hex dump format.
     * @param data byte array.
     * @param offset offset.
     * @param length length.
     * @return hex dump format.
     */
    public static String toPrettyHexDump(final byte[] data, final int offset, final int length) {
        ArrayUtils.validateBounds(data, offset, length);
        StringBuilder result = new StringBuilder();
        StringBuilder builder = new StringBuilder();
        int pos = offset;
        int max = length;
        int lineNumber = 0;
        builder.append(HEADER);
        while (pos < max) {
            builder.append(String.format("%08d", lineNumber++) + " | ");
            int lineMax = Math.min(max - pos, 16);
            for (int i=0; i<lineMax; i++) {
                builder.append(toHex(data[pos+i]));
                builder.append(" ");
            }
            while (builder.length() < 48) {
                builder.append(" ");
            }
            builder.append("| ");
            for (int i=0; i<lineMax; i++) {
                char c = (char)data[pos+i];
                if ((c < 32) || (c > 127))
                    c = '.';
                builder.append(c);
            }
            builder.append("\n");
            result.append(builder);
            builder.setLength(0);
            pos += 16;
        }
        result.append(FOOTER);
        return result.toString();
    }

    /**
     * Hex stream to byte array.
     * @param hexStream hex stream.
     * @return byte array.
     */
    public static byte[] parseHex(String hexStream) {
        CheckNotNull(hexStream);
        if (hexStream.startsWith("0x")) {
            hexStream = hexStream.substring(2);
        }
        hexStream = hexStream.replaceAll("\\s+", "").trim();
        if (!NO_SEPARATOR_HEX_STRING_PATTERN.matcher(hexStream).matches()) {
            throw new IllegalArgumentException();
        }
        int len = hexStream.length();
        byte[] data = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexStream.charAt(i), 16) << 4)
                    + Character.digit(hexStream.charAt(i+1), 16));
        }
        return data;
    }

}
