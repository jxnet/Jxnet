package com.ardikars.jxpcap;

import java.util.ArrayList;
import java.util.List;

public final class JxpcapAddr {
	
	private native static void initIDs();
	
	public JxpcapAddr next;
	
	public SockAddr addr;
	
	public SockAddr netmask;
	
	public SockAddr broadaddr;
	
	public SockAddr dstaddr;
	
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
		new SockAddr();
	}
}
