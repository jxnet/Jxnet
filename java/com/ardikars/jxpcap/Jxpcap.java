package com.ardikars.jxpcap;

import java.util.List;

public class Jxpcap {
	
	//private native static void initIDs();
	
	public native static int findAllDevs(List<JxpcapIf> alldevsp, String errbuf);
	
	static {
		System.loadLibrary("jxpcap");
		try {
			Class.forName("com.ardikars.jxpcap.JxpcapIf");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
