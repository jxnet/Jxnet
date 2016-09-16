
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class SockAddr {

	public final static int AF_INET = 2;
	
	public final static int AF_INET6 = 23;
	
	private volatile short sa_family;
	
	private volatile byte[] data;
	
	public short getSaFamily() {
		return sa_family;
	}
	
	public byte[] getData() {
		return data;
	}
	
	@Override
	public String toString() {
		switch (sa_family) {
			case AF_INET:
				return Inet4Address.valueOf(data).toString();
			case AF_INET6:
				return Inet6Address.valueOf(data).toString();
			default:
				return "Family : "+ sa_family;
		}
	}
	
}