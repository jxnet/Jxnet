
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class PcapAddr {

	@SuppressWarnings("unused")
	private PcapAddr next;
	
	private volatile SockAddr addr;
	
	private volatile SockAddr netmask;
	
	private volatile SockAddr broadaddr;
	
	private volatile SockAddr dstaddr;
	
	public SockAddr getAddr() {
		return addr;
	}
	
	public SockAddr getNetmask() {
		return netmask;
	}
	
	public SockAddr getBroadAddr() {
		return broadaddr;
	}
	
	public SockAddr getDstAddr() {
		return dstaddr;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append(String.valueOf(addr));
		out.append(String.valueOf(netmask));
		out.append(String.valueOf(broadaddr));
		out.append(String.valueOf(dstaddr));
		return out.toString();
	}
	
}