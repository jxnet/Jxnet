
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

public final class AddrUtils {
	
	private static native byte[] GetMACAddress(String dev_name);

	private static native String GetGatewayAddress(String dev_name);
		
	public static byte[] getHardwareAddress(String dev_name) {
		return GetMACAddress(dev_name);
	}
	
	public static String getGatewayAddress(String dev_name) {
		return GetGatewayAddress(dev_name);
	}
	
}