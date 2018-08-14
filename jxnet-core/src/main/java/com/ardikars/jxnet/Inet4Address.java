/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

import com.ardikars.common.util.Validate;

import java.util.Arrays;

/**
 * This class represents an Internet Protocol version 4 (IPv4) address.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class Inet4Address extends InetAddress {

	/**
	 * IPv4 Address (0.0.0.0).
	 */
	public static final Inet4Address ZERO = valueOf(0);

	/**
	 * IPv4 Loopback address (127.0.0.1).
	 */
	@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
	public static final Inet4Address LOCALHOST = valueOf("127.0.0.1");

	/**
	 * IPv4 Address Length.
	 */
	public static final int IPV4_ADDRESS_LENGTH = 4;

	private Inet4Address() {
		super();
	}

	private Inet4Address(final byte[] address) {
		super();
		Validate.nullPointer(address);
		Validate.notIllegalArgument(address.length == IPV4_ADDRESS_LENGTH);
		System.arraycopy(address, 0, this.address, 0, address.length);
	}

	/**
	 * Create Inet4Address instance.
	 * @param inet4Address ipv4 string address.
	 * @return returns Inet4Address instance.
	 */
	@SuppressWarnings("PMD.AvoidUsingHardCodedIP")
	public static Inet4Address valueOf(String inet4Address) {
		inet4Address = Validate.nullPointer(inet4Address, "0.0.0.0");
		final String[] parts = inet4Address.split("\\.");
		final byte[] result = new byte[parts.length];
		Validate.notIllegalArgument(result.length == IPV4_ADDRESS_LENGTH);
		for (int i = 0; i < result.length; i++) {
			Validate.notIllegalArgument(parts[i] != null || parts[i].length() != 0);
			Validate.notIllegalArgument(!(parts[i].length() > 1 && parts[i].startsWith("0")));
			result[i] = Integer.valueOf(parts[i]).byteValue();
			Validate.notIllegalArgument((result[i] & 0xff) <= 0xff);
		}
		return Inet4Address.valueOf(result);
	}

	/**
	 * Create IPv4Address instance.
	 * @param address ipv4 bytes address.
	 * @return returns IPv4Address instance.
	 */
	public static Inet4Address valueOf(final byte[] address) {
		return new Inet4Address(address);
	}

	/**
	 * Create Inet4Address instance.
	 * @param address ipv4 int address.
	 * @return returns Inet4Address instance.
	 */
	public static Inet4Address valueOf(final int address) {
		return new Inet4Address(new byte[]{(byte) (address >>> 24),
				(byte) (address >>> 16), (byte) (address >>> 8),
				(byte) address});
	}

	/**
	 * Returns int address of Inet4Address.
	 * @return returns ipv4 int address.
	 */
	public int toInt() {
		int ip = 0;
		for (int i = 0; i < Inet4Address.IPV4_ADDRESS_LENGTH; i++) {
			final int t = (this.address[i] & 0xff) << (3 - i) * 8;
			ip |= t;
		}
		return ip;
	}

	/**
	 * Returns bytes address of Inet4Address.
	 * @return returns ipv4 bytes address.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	/**
	 * Change value of Inet4address.
	 * @param inet4address Inet4Address.
	 */
	public void update(final Inet4Address inet4address) {
		Validate.nullPointer(inet4address);
		this.address = inet4address.toBytes();
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final Inet4Address that = (Inet4Address) o;

		return Arrays.equals(this.address, that.address);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.address);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		Inet4Address inet4Address = (Inet4Address) super.clone();
		return inet4Address;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.address[0] & 0xff).append('.');
		sb.append(this.address[1] & 0xff).append('.');
		sb.append(this.address[2] & 0xff).append('.');
		sb.append(this.address[3] & 0xff);
		return sb.toString();
	}

}
