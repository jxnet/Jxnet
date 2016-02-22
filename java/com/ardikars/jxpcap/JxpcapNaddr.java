package com.ardikars.jxpcap;

public class JxpcapNaddr {
	
	private native static void initIDs();
	
	private SockAddr addr;
	
	private SockAddr netmask;
	
	private SockAddr broadaddr;
	
	private SockAddr dstaddr;
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(String.valueOf(addr));
		out.append(String.valueOf(netmask));
		out.append(String.valueOf(broadaddr));
		out.append(String.valueOf(dstaddr));
		return out.toString();
	}
	
	static {
		initIDs();
	}
}
