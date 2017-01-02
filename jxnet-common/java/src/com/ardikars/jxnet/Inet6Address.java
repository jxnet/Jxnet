
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class Inet6Address extends InetAddress {

	public static final short IPV6_ADDRESS_LENGTH = 16;

	private byte[] address = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];

	private Inet6Address(byte[] address) {
		this.address = address;
	}

	public static Inet6Address valueOf(byte[] address) {
		return new Inet6Address(address);
	}

	public byte[] toBytes() {
		return address;
	}

	public String toString() {
		int cmprHextet = -1;
		int cmprSize = 0;
		for(int hextet = 0; hextet <7;) {
			int curByte = hextet * 2;
			int size = 0;
			while(curByte < address.length && address[curByte] == 0
					&& address[curByte + 1] == 0) {
				curByte += 2;
				size++;
			}
			if(size > cmprSize) {
				cmprHextet = hextet;
				cmprSize = size;
			}
			hextet = (curByte / 2) + 1;
		}
		StringBuilder sb = new StringBuilder(39);
		if(cmprHextet == -1 || cmprSize < 2) {
			ipv6toStr(sb, address, 0, 8);
			return sb.toString();
		}
		ipv6toStr(sb, address, 0, cmprHextet);
		sb.append(new char[] {':',':'});
		ipv6toStr(sb, address, cmprHextet + cmprSize, 8);
		return sb.toString();
	}

	private static final void ipv6toStr(StringBuilder sb, byte[] src,
										int fromHextet, int toHextet) {
		for (int i = fromHextet; i < toHextet; i++) {
			sb.append(Integer.toHexString(((src[i << 1] << 8) & 0xff00)
					| (src[(i << 1) + 1] & 0xff)));
			if (i < toHextet - 1) {
				sb.append(':');
			}
		}
	}

}

