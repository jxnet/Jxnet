package com.ardikars.test;

import java.util.regex.Pattern;

/**
 * Created by root on 7/1/17.
 */
public class HexUtils4Test {

    private static final Pattern NO_SEPARATOR_HEX_STRING_PATTERN
            = Pattern.compile("\\A([0-9a-fA-F][0-9a-fA-F])+\\z");

    public static byte[] parseHex(String hexStream) {
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
