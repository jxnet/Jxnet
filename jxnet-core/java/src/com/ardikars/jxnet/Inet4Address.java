
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import java.util.Arrays;

public final class Inet4Address implements InetAddress {

	public static final int IPV4_ADDRESS_LENGTH = 4;
	
	private byte[] address = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];

	private Inet4Address(final byte[] address) {
		this.address = address;
	}
	
	public static Inet4Address valueOf(String address) {
		final String[] octets = address.split("\\.");
        if(octets.length != 4) {
            throw new IllegalArgumentException("Specified IPv4 address must"
                    + "contain 4 sets of numerical digits separated by periods");
        }

        final byte[] result = new byte[4];
        for(int i = 0; i < 4; ++i) {
            result[i] = Integer.valueOf(octets[i]).byteValue();
        }
        return new Inet4Address(result);
	}
	
	public static Inet4Address valueOf(byte[] address) {
		if (address.length != Inet4Address.IPV4_ADDRESS_LENGTH) {
            throw new IllegalArgumentException("the length is not "
                                                       + Inet4Address.IPV4_ADDRESS_LENGTH);
        }
		return new Inet4Address(address);
	}
	
	public static Inet4Address valueOf(int address) {
		return new Inet4Address(new byte[] {(byte) (address >>> 24),
                (byte) (address >>> 16), (byte) (address >>> 8),
                (byte) address});
	}
	
	public void update(Inet4Address address) {
		this.address = address.toByteArray();
	}

	public int toInt() {
        int ip = 0;
        for(int i = 0; i < Inet4Address.IPV4_ADDRESS_LENGTH; i++) {
            final int t = (address[i] & 0xff) << (3 - i) * 8;
            ip |= t;
        }
        return ip;
    }
	
	public byte[] toByteArray() {
		return Arrays.copyOf(this.address, this.address.length);
	}
	
	public short getSaFamily() {
		return 2;
	}
	
	public String toString() {
		return (address[0] & 0xFF) + "." + (address[1] & 0xFF) + "." + (address[2] & 0xFF) + "." + (address[3] & 0xFF);
	}

}