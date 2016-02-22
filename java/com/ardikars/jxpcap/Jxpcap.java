package com.ardikars.jxpcap;

import java.nio.ByteBuffer;
import java.util.List;

public class Jxpcap {
	
	//private native static void initIDs();
	
	private native static int nativeSendPacket(Jxpcap jxpcap, ByteBuffer buf, int size);
	
	private native static int nativeFindAllDevs(List<JxpcapIf> alldevsp, String errbuf);
	
	public static int sendPacket(Jxpcap jxpcap, ByteBuffer buf, int size) {
		return nativeSendPacket(jxpcap, buf, size);
	}
	public static int findAllDevs(List<JxpcapIf> alldevsp, String errbuf) {
		return nativeFindAllDevs(alldevsp, errbuf);
	}
	
	static {
		System.loadLibrary("jxpcap");
		try {
			Class.forName("com.ardikars.jxpcap.JxpcapIf");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
