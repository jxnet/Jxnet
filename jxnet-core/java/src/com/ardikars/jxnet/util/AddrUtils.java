
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
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
	
	public static Inet4Address getGatewayAddress(String dev_name) {
		String gwAddr = GetGatewayAddress(dev_name);
		return (gwAddr == null ? null : Inet4Address.valueOf(gwAddr));
	}
	
}