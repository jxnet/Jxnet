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
import com.ardikars.jxnet.exception.DeviceNotFoundException;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * This class represents an Media Access Control (MAC) address.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public final class MacAddress {

	/**
	 * MAC Address Length.
	 */
	public static final int MAC_ADDRESS_LENGTH = 6;

	/**
	 * Zero MAC Address (00:00:00:00:00:00).
	 */
	public static final MacAddress ZERO = valueOf("00:00:00:00:00:00");

	/**
	 * Dummy MAC Address (de:ad:be:ef:c0:fe).
	 */
	public static final MacAddress DUMMY = valueOf("de:ad:be:ef:c0:fe");

	/**
	 * Broadcast MAC Address (ff:ff:ff:ff:ff:ff).
	 */
	public static final MacAddress BROADCAST = valueOf("ff:ff:ff:ff:ff:ff");

	/**
	 * Multicast Address.
	 */
	public static final MacAddress IPV4_MULTICAST = valueOf("01:00:5e:00:00:00");

	public static final MacAddress IPV4_MULTICAST_MASK = valueOf("ff:ff:ff:80:00:00");

	private byte[] address = new byte[MAC_ADDRESS_LENGTH];

	private MacAddress(byte[] address) {
		Validate.nullPointer(address);
		Validate.notIllegalArgument(address.length == MAC_ADDRESS_LENGTH);
		this.address = Arrays.copyOf(address, MacAddress.MAC_ADDRESS_LENGTH);
	}

	/**
	 * Determines the MacAddress address.
	 * @param stringAddress MAC string address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(String stringAddress) {
		stringAddress = Validate.nullPointer(stringAddress, "00:00:00:00:00:00");
		final String[] elements = stringAddress.split(":|-");
		Validate.notIllegalArgument(elements.length == MAC_ADDRESS_LENGTH);
		final byte[] b = new byte[MAC_ADDRESS_LENGTH];
		for (int i = 0; i < MAC_ADDRESS_LENGTH; i++) {
			final String element = elements[i];
			b[i] = (byte) Integer.parseInt(element, 16);
		}
		return new MacAddress(b);
	}

	/**
	 * Determines the MacAddress address.
	 * @param bytesAddress MAC bytes address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(final byte[] bytesAddress) {
		return new MacAddress(bytesAddress);
	}

	/**
	 * Determines the MacAddress address.
	 * @param longAddress MAC long address.
	 * @return an Mac address object.
	 */
	public static MacAddress valueOf(final long longAddress) {
		final byte[] bytes = new byte[] {
				(byte) (longAddress >> 40 & 0xff),
				(byte) (longAddress >> 32 & 0xff),
				(byte) (longAddress >> 24 & 0xff),
				(byte) (longAddress >> 16 & 0xff),
				(byte) (longAddress >> 8 & 0xff),
				(byte) (longAddress >> 0 & 0xff)};
		return new MacAddress(bytes);
	}

	/**
	 * Create MacAddress instance from NIC name.
	 * @param nicName NIC name.
	 * @return MacAddress instance.
	 * @throws DeviceNotFoundException device not found exception.
	 */
	public static native MacAddress fromNicName(final String nicName) throws DeviceNotFoundException;

	/**
	 * Validate given mac string address.
	 * @param stringAddress mac string address.
	 * @return a {@code boolean} indicating if the stringAddress is a valid mac address; or false otherwise.
	 */
	public static boolean isValidAddress(final String stringAddress) {
		Validate.nullPointer(stringAddress);
		return Pattern.matches("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$", stringAddress);
	}

	/**
	 * Returns length of MAC Address.
	 * @return MAC Address length.
	 */
	public int length() {
		return this.address.length;
	}

	/**
	 * Returns bytes MAC Address.
	 * @return bytes MAC Address.
	 */
	public byte[] toBytes() {
		return Arrays.copyOf(this.address, this.address.length);
	}

	/**
	 * Returning long MAC Address.
	 * @return long MAC Address.
	 */
	public long toLong() {
		long addr = 0;
		for (int i = 0; i < MAC_ADDRESS_LENGTH; i++) {
			long tmp = (this.address[i] & 0xffL) << (5 - i) * 8;
			addr |= tmp;
		}
		return addr;
	}

	/**
	 * Return true if Broadcast MAC Address.
	 * @return true if Broadcast MAC Address, false otherwise.
	 */
	public boolean isBroadcast() {
		for (final byte b : this.address) {
			if (b != -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Return true if Multicast MAC Address.
	 * @return true if Multicast MAC Address, false otherwise.
	 */
	public boolean isMulticast() {
		if (this.isBroadcast()) {
			return false;
		}
		return (this.address[0] & 0x01) != 0;
	}

	/**
	 *
	 * @return returns true if the MAC address represented by this object is
	 *         a globally unique address; otherwise false.
	 */
	public boolean isGloballyUnique() {
		return (address[0] & 2) == 0;
	}

	/**
	 *
	 * @return true if the MAC address represented by this object is
	 *         a unicast address; otherwise false.
	 */
	public boolean isUnicast() {
		return (address[0] & 1) == 0;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MacAddress that = (MacAddress) o;

		return Arrays.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(address);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (final byte b : this.address) {
			if (sb.length() > 0) {
				sb.append(":");
			}
			String hex = Integer.toHexString(b & 0xff);
			sb.append(hex.length() == 1 ? "0" + hex : hex);
		}
		return sb.toString();
	}

}
