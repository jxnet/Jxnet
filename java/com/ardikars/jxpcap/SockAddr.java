package com.ardikars.jxpcap;

public class SockAddr {
	
	private native static void initIDs();

	public final static int AF_INET = 2;
	
	public final static int AF_INET6 = 23;
	
	public short sa_family;
	
	public byte[] data;
	
	private int u(byte b) {
		return (b >= 0) ? b : b + 256;
	}
	
	@Override
	public String toString() {
		switch (sa_family) {
			case AF_INET:
				return u(data[0]) + "." +u(data[1]) + "." + u(data[2]) + "." + u(data[3]);
			case AF_INET6:
				return data[0] + "." + data[1] + "." + data[2] + "." + data[3];
			default:
				return "[ Family: "+sa_family+"]";
		}
	}
	static {
		initIDs();
	}
}
