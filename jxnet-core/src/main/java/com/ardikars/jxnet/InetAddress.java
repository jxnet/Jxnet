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

/**
 * This class is a abstraction for Internet Protocol (IP) address.
 * @author Ardika Rommy Sanjaya
 * @since 1.0.0
 */
public abstract class InetAddress implements Cloneable {

	protected byte[] address;

	public abstract byte[] toBytes();

	/**
	 * Create instance of Inet4Address or Inet6Address/
	 * @param ipString ipv4 or ipv6 string address.
	 * @return returns Inet4Address or Inet6Address.
	 */
	public static InetAddress valueOf(final String ipString) {
		if (ipString.contains(":")) {
			return Inet6Address.valueOf(ipString);
		} else {
			return Inet4Address.valueOf(ipString);
		}
	}

	/**
	 * Validate ipv4 or ipv6 address.
	 * @param ipString ipv4 or ipv6 string address.
	 * @return returns true is valid, false otherwise.
	 */
	public static boolean isValidAddress(final String ipString) {
		try {
			valueOf(ipString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
