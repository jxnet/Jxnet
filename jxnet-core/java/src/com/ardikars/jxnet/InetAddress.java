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

public abstract class InetAddress {

	public abstract byte[] toBytes();

	public static InetAddress valueOf(String ipString) {
		if (ipString.contains(":")) {
			try {
				return Inet6Address.valueOf(ipString);
			} catch (Exception ex) {
				throw new IllegalArgumentException("");
			}
		} else {
			try {
				return Inet4Address.valueOf(ipString);
			} catch (Exception ex) {
				throw new IllegalArgumentException("");
			}
		}
	}

	public static boolean isValidAddress(String ipString) {
		try {
			return (valueOf(ipString) == null) ? false : true;
		} catch (Exception ex) {
			return false;
		}
	}

}
