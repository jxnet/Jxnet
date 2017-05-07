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
import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Inet4Address extends InetAddress {

	public static final Inet4Address ZERO = valueOf(0);

	public static final Inet4Address LOCALHOST = valueOf("127.0.0.1");

	public static final int IPV4_ADDRESS_LENGTH = 4;
	
	private byte[] address = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];

	private Inet4Address() {

	}

	private Inet4Address(final byte[] address) {
		this.address = address;
	}

	/**
	 * Create Inet4Address object.
	 * @param inet4Address ipv4 string address.
	 * @return Inet4Address object.
	 */
	public static Inet4Address valueOf(final String inet4Address) {
		Preconditions.CheckNotNull(inet4Address);
		Preconditions.CheckArgument(inet4Address.matches("\\b((25[0–5]|2[0–4]\\d|[01]?\\d\\d?)(\\.)){3}(25[0–5]|2[0–4]\\d|[01]?\\d\\d?)\\b"));
		String[] parts = inet4Address.split("\\.");
		byte[] result = new byte[parts.length];
		Preconditions.CheckArgument(result.length == IPV4_ADDRESS_LENGTH);
		for (int i=0; i<result.length; i++) {
			if (parts[i].length() == 0 || parts[i] == null) {
				throw new IllegalArgumentException("");
			}
			if (parts[i].length() > 1 && parts[i].startsWith("0")) {
				throw new IllegalArgumentException("");
			}
			try {
				result[i] = Integer.valueOf(parts[i]).byteValue();
				if (result[i] > 0xffff) {
					throw new IllegalArgumentException("");
				}
			} catch (Exception ex) {
				throw new IllegalArgumentException("");
			}
		}
		return Inet4Address.valueOf(result);
	}

	/**
	 * Create IPv4Address object.
	 * @param address ipv4 bytes address.
	 * @return IPv4Address object.
	 */
	public static Inet4Address valueOf(final byte[] address) {
		Preconditions.CheckNotNull(address);
		Preconditions.CheckArgument(address.length == Inet4Address.IPV4_ADDRESS_LENGTH);
		return new Inet4Address(address);
	}

	/**
	 * Create Inet4Address object.
	 * @param address ipv4 int address.
	 * @return Inet4Address object.
	 */
	public static Inet4Address valueOf(final int address) {
		return new Inet4Address(new byte[]{(byte) (address >>> 24),
				(byte) (address >>> 16), (byte) (address >>> 8),
				(byte) address});
	}

	/**
	 * Returning int address of Inet4Address.
	 * @return int ipv4 address.
	 */
	public int toInt() {
		int ip = 0;
		for (int i = 0; i < Inet4Address.IPV4_ADDRESS_LENGTH; i++) {
			final int t = (this.address[i] & 0xff) << (3 - i) * 8;
			ip |= t;
		}
		return ip;
	}

	private long toLong() {
		ByteBuffer bb = ByteBuffer.allocate(this.address.length);
		bb.put(this.address);
		return bb.getLong();
	}

	/**
	 * Returning bytes address of Inet4Address.
	 * @return bytes ipv4 address.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	public void update(final Inet4Address inet4address) {
		this.address = inet4address.toBytes();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj.getClass() != this.getClass())
			return false;
		if (obj instanceof Inet4Address) {
			final Inet4Address addr = (Inet4Address) obj;
			return Arrays.equals(this.address, addr.toBytes());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * 37 + toInt();
	}

	public String toString() {
		return new StringBuilder()
				.append(this.address[0] & 0xff).append(".")
				.append(this.address[1] & 0xff).append(".")
				.append(this.address[2] & 0xff).append(".")
				.append(this.address[3] & 0xff).toString();
	}

}
