package com.ardikars.jxpcap;

import java.nio.ByteBuffer;
import java.util.List;

public class Jxpcap {
	
	private native static void initIDs();
	
	private long pcap = 0;
	
	private native static Jxpcap nativeOpenLive(String source, int snaplen, int promisc, int to_ms, StringBuilder errbuf);
	
	private native static int nativeFindAllDevs(List<JxpcapIf> alldevsp, StringBuilder errbuf);
	
	private native static int nativeSendPacket(Jxpcap jxpcap, ByteBuffer buf, int size);
	
	private native static String nativeLookupDev(StringBuilder errbuf);
	
	private native static String nativeGetErr(Jxpcap pcap);
	
	public static int sendPacket(Jxpcap jxpcap, ByteBuffer buf, int size) {
		return nativeSendPacket(jxpcap, buf, size);
	}
	public static int findAllDevs(List<JxpcapIf> alldevsp, StringBuilder errbuf) {
		return nativeFindAllDevs(alldevsp, errbuf);
	}
	
	public static void JxpcapFreeAllDevs(List<JxpcapIf> alldevs, StringBuilder errbuf) {
		if(alldevs == null)
			throw new NullPointerException("alldevs already freed.");
		alldevs.clear();
		errbuf.setLength(0);
	}
	
	public static String lookupDev(StringBuilder errbuf) {
		return nativeLookupDev(errbuf);
	}
	
	public static Jxpcap openLive(String source, int snaplen, boolean promisc, int to_ms, StringBuilder errbuf) {
		return nativeOpenLive(source, snaplen, promisc ? 1 : 0, to_ms, errbuf);
	}
	
	public static String getErr(Jxpcap pcap) {
		return nativeGetErr(pcap);
	}
	
	static {
		System.loadLibrary("jxpcap");
		initIDs();
		try {
			Class.forName("com.ardikars.jxpcap.JxpcapIf");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
