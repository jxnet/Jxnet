
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.util;

public final class Pointer {
	
	private long address;
	
	public long getAddress() {
		return address;
	}
	
	@Override
	public String toString() {
		return String.valueOf(address);
	}
	
}