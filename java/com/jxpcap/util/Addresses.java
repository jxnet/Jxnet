package com.jxpcap.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Addresses {
	public static String bytesToStringMacAddr(byte[] bytes) {
        if(bytes == null) { return null; }
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (byte s : bytes) {
            if (!isFirst) {
                sb.append(":");
            } else {
                isFirst = false;
            }
            sb.append(String.format("%02x", s & 0xff));
        }
        return sb.toString();
    }
    public static byte[] stringToBytesMacAddr(String string) {
        String[] bytes = string.split(":");
        byte[] b = new byte[bytes.length];
        for (int i=0; i<bytes.length; i++) {
            BigInteger temp = new BigInteger(bytes[i], 16);
            byte[] raw = temp.toByteArray();
            b[i] = raw[raw.length - 1];
        }
        return b;
    }

    public static String bytesToStringIPAddr(byte[] bytes) {
        StringBuilder sb = new StringBuilder(15);
        for(int i=0; i<bytes.length; i++) {
            if(i>0) {
                sb.append(".");
            }
            sb.append(bytes[i] & 0xFF);
        }
        return sb.toString();
    }
    public static byte[] stringToBytesIPAddr(String string) {
        InetAddress ip_addr = null;
        try {
            ip_addr = InetAddress.getByName(string);
        } catch (UnknownHostException ex) {
            System.out.println("Invalid IP Address.");
        }
        return ip_addr.getAddress();
    }
}
