package com.ardikars.jxpcap;

import java.nio.ByteBuffer;
import java.util.List;

public class Jxpcap {
	
	//private native static void initIDs();
	
	
	private native static int nativeOpenLive(String source, int snaplen, int promisc, int to_ms, StringBuilder errbuf);
	
	private native static int nativeFindAllDevs(List<JxpcapIf> alldevsp, StringBuilder errbuf);
	
	private native static int nativeSendPacket(Jxpcap jxpcap, ByteBuffer buf, int size);
	
	public static int sendPacket(Jxpcap jxpcap, ByteBuffer buf, int size) {
		return nativeSendPacket(jxpcap, buf, size);
	}
	public static int findAllDevs(List<JxpcapIf> alldevsp, StringBuilder errbuf) {
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
