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

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Preconditions;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Inet6Address extends InetAddress {

	public static final Inet6Address LOCALHOST = valueOf("::1");
	
	public static final short IPV6_ADDRESS_LENGTH = 16;
	
	private byte[] address = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];

	public Inet6Address() {
	}

	private Inet6Address(byte[] address) {
		this.address = address;
	}

	/**
	 * Create Inet6Address object.
	 * @param address ipv6 bytes address.
	 * @return Inet6Address object.
	 */
	public static Inet6Address valueOf(byte[] address) {
		Preconditions.CheckNotNull(address);
		Preconditions.CheckArgument(address.length == IPV6_ADDRESS_LENGTH);
		return new Inet6Address(address);
	}

	/**
	 * Create Inet6Address object.
	 * @param inet6Address ipv6 string address.
	 * @return Inet6Address object.
	 */
	public static Inet6Address valueOf(String inet6Address) {

		Preconditions.CheckNotNull(inet6Address);

		final int IPV6_MAX_HEX_GROUPS = 8;
		final int IPV6_MAX_HEX_DIGITS_PER_GROUP = 4;

		boolean containsCompressedZeroes = inet6Address.contains("::");
		if (containsCompressedZeroes && (inet6Address.indexOf("::") != inet6Address.lastIndexOf("::"))) {
			throw new IllegalArgumentException("");
		}
		if ((inet6Address.startsWith(":") && !inet6Address.startsWith("::"))
				|| (inet6Address.endsWith(":") && !inet6Address.endsWith("::"))) {
			throw new IllegalArgumentException("");
		}
		String[] parts = inet6Address.split(":");
		if (containsCompressedZeroes) {
			List<String> partsAsList = new ArrayList<String>(Arrays.asList(parts));
			if (inet6Address.endsWith("::")) {
				partsAsList.add("");
			} else if (inet6Address.startsWith("::") && !partsAsList.isEmpty()) {
				partsAsList.remove(0);
			}
			parts = partsAsList.toArray(new String[partsAsList.size()]);
		}
		if (parts.length > IPV6_MAX_HEX_GROUPS && parts.length < 3) {
			throw new IllegalArgumentException("");
		}
		int validOctets = 0;
		int emptyOctets = 0;
		for (int index = 0; index < parts.length; index++) {
			String octet = parts[index];
			if (octet.length() == 0) {
				emptyOctets++;
				if (emptyOctets > 1) {
					throw new IllegalArgumentException("");
				}
			} else {
				emptyOctets = 0;
				if (index == parts.length - 1 && octet.contains(".")) {
					byte[] quad;
					try {
						quad = Inet4Address.valueOf(octet).toBytes();
						String initialPart = inet6Address.substring(0, inet6Address.lastIndexOf(":") + 1);
						String penultimate = Integer.toHexString(((quad[0] & 0xff) << 8) | (quad[1] & 0xff));
						String ultimate = Integer.toHexString(((quad[2] & 0xff) << 8) | (quad[3] & 0xff));
						inet6Address = initialPart + penultimate + ultimate;
					} catch (Exception ex) {
						throw new IllegalArgumentException("");
					}
					validOctets += 2;
					continue;
				}
				if (octet.length() > IPV6_MAX_HEX_DIGITS_PER_GROUP) {
					throw new IllegalArgumentException("");
				}
			}
			validOctets++;
		}
		if (validOctets > IPV6_MAX_HEX_GROUPS || (validOctets < IPV6_MAX_HEX_GROUPS && !containsCompressedZeroes)) {
			throw new IllegalArgumentException("");
		}

		parts = inet6Address.split(":", 8 + 2);
		if (parts.length < 3 || parts.length > 8 + 1) {
			throw new IllegalArgumentException("");
		}
		int skipIndex = -1;
		for (int i = 1; i < parts.length - 1; i++) {
			if (parts[i].length() == 0) {
				if (skipIndex >= 0) {
					throw new IllegalArgumentException("");
				}
				skipIndex = i;
			}
		}
		int partsHi;
		int partsLo;
		if (skipIndex >= 0) {
			partsHi = skipIndex;
			partsLo = parts.length - skipIndex - 1;
			if (parts[0].length() == 0 && --partsHi != 0) {
				return null;
			}
			if (parts[parts.length - 1].length() == 0 && --partsLo != 0) {
				return null;
			}
		} else {
			partsHi = parts.length;
			partsLo = 0;
		}
		int partsSkipped = 8 - (partsHi + partsLo);
		if (!(skipIndex >= 0 ? partsSkipped >= 1 : partsSkipped == 0)) {
			return null;
		}
		ByteBuffer rawBytes = ByteBuffer.allocate(2 * 8);
		try {
			for (int i = 0; i < partsHi; i++) {
				rawBytes.putShort(parseHextet(parts[i]));
			}
			for (int i = 0; i < partsSkipped; i++) {
				rawBytes.putShort((short) 0);
			}
			for (int i = partsLo; i > 0; i--) {
				rawBytes.putShort(parseHextet(parts[parts.length - i]));
			}
		} catch (NumberFormatException ex) {
			return null;
		}
		return new Inet6Address(rawBytes.array());
	}

	/**
	 * Returning bytes address of Inet6Address.
	 * @return bytes ipv6 address.
	 */
	public byte[] toBytes() {
		return address;
	}

	private long toLong() {
		ByteBuffer bb = ByteBuffer.allocate(this.address.length);
		bb.put(this.address);
		return bb.getLong();
	}

	private static short parseHextet(String ipPart) {
		int hextet = Integer.parseInt(ipPart, 16);
		if (hextet > 0xffff) {
			throw new NumberFormatException();
		}
		return (short) hextet;
	}

	public void update(Inet6Address inet6address) {
		this.address = inet6address.toBytes();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj.getClass() != getClass())
			return false;
		if (obj instanceof Inet4Address) {
			final Inet4Address addr = (Inet4Address) obj;
			return Arrays.equals(this.address, addr.toBytes());
		}
		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = 17 * 3;
		for (int i=0; i<address.length; i++) {
			hashCode = hashCode * 3 + address[i];
		}
		return hashCode + super.hashCode();
	}

	public String toString() {
		int cmprHextet = -1;
		int cmprSize = 0;
		for (int hextet = 0; hextet < 7; ) {
			int curByte = hextet * 2;
			int size = 0;
			while (curByte < address.length && address[curByte] == 0
					&& address[curByte + 1] == 0) {
				curByte += 2;
				size++;
			}
			if (size > cmprSize) {
				cmprHextet = hextet;
				cmprSize = size;
			}
			hextet = (curByte / 2) + 1;
		}
		StringBuilder sb = new StringBuilder(39);
		if (cmprHextet == -1 || cmprSize < 2) {
			ipv6toStr(sb, address, 0, 8);
			return sb.toString();
		}
		ipv6toStr(sb, address, 0, cmprHextet);
		sb.append(new char[]{':', ':'});
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

