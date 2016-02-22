package com.ardikars.jxpcap.util;

import com.ardikars.jxpcap.Jxpcap;
import com.ardikars.jxpcap.JxpcapIf;

public class JxpcapAddrUtils extends Jxpcap {

	private native static byte[] nativeGetHwAddr(String name);
	
	private native static byte[] nativeGetGwAddr(String name);
	
	public static byte[] getHwAddr(String name) {
		return nativeGetHwAddr(name);
	}
	
	public static byte[] getGwAddr(String name) {
		return nativeGetGwAddr(name);
	}
	
}
