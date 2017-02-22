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

package com.ardikars.jxnet.util;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.MacAddress;

public final class AddrUtils {
	
	private static native byte[] GetMACAddress(String dev_name);
	
	private static native String GetGatewayAddress(String dev_name);
	
	public static MacAddress getHardwareAddress(String dev_name) {
		byte[] macAddr = GetMACAddress(dev_name);
		return (macAddr == null ? null : MacAddress.valueOf(macAddr));
	}

	@Deprecated
	public static Inet4Address getGatewayAddress(String dev_name) {
		String gwAddr = GetGatewayAddress(dev_name);
		return (gwAddr == null ? null : Inet4Address.valueOf(gwAddr));
	}
	
}