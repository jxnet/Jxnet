
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class Inet6Address implements InetAddress {
	
	public static final short IPV6_ADDRESS_LENGTH = 16;
	
	private byte[] address = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];

	private Inet6Address(byte[] address) {
		this.address = address;
	}
	
	public static Inet6Address valueOf(byte[] address) {
		return new Inet6Address(address);
	}

	public byte[] toByteArray() {
		return address;
	}

	public short getSaFamily() {
		return 6;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder(39);
		boolean lastWasNumber = false;
		for (int i = 0; i < address.length; i++) {
			boolean thisIsNumber = address[i] >= 0;
			if (thisIsNumber) {
				if (lastWasNumber) {
					buf.append(':');
				}
				buf.append(Integer.toHexString(address[i]));
			} else {
				if (i == 0 || lastWasNumber) {
					buf.append("::");
				}
			}
			lastWasNumber = thisIsNumber;
	    }
		return buf.toString();
	}

}