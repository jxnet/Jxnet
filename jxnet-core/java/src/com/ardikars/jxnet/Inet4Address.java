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

import java.nio.ByteBuffer;
import java.util.Arrays;

public final class Inet4Address extends InetAddress {
	
	public static final int IPV4_ADDRESS_LENGTH = 4;
	
	private byte[] address = new byte[Inet4Address.IPV4_ADDRESS_LENGTH];
	
	private Inet4Address(final byte[] address) {
		this.address = address;
	}
	
	public static Inet4Address valueOf(String inet4Address) {
		if (!inet4Address.matches("^([0-9.])+$") || inet4Address == null) {
			throw new IllegalArgumentException("");
		}
		String[] parts = inet4Address.split("\\.");
		byte[] result = new byte[parts.length];
		if (result.length != 4) {
			throw new IllegalArgumentException("");
		}
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
	
	public static Inet4Address valueOf(byte[] address) {
		if (address == null || address.length != Inet4Address.IPV4_ADDRESS_LENGTH ||
				address[0] == 0x0) {
			throw new IllegalArgumentException("the length is not "
					+ Inet4Address.IPV4_ADDRESS_LENGTH);
		}
		return new Inet4Address(address);
	}
	
	public static Inet4Address valueOf(int address) {
		return new Inet4Address(new byte[]{(byte) (address >>> 24),
				(byte) (address >>> 16), (byte) (address >>> 8),
				(byte) address});
	}

	public int toInt() {
		int ip = 0;
		for (int i = 0; i < Inet4Address.IPV4_ADDRESS_LENGTH; i++) {
			final int t = (address[i] & 0xff) << (3 - i) * 8;
			ip |= t;
		}
		return ip;
	}

	private long toLong() {
		ByteBuffer bb = ByteBuffer.allocate(this.address.length);
		bb.put(this.address);
		return bb.getLong();
	}
	
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
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
		return Long.hashCode(toLong());
	}

	public String toString() {
		return (address[0] & 0xFF) + "." + (address[1] & 0xFF) + "." + (address[2] & 0xFF) + "." + (address[3] & 0xFF);
	}
	
}
