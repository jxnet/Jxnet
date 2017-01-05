
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class PcapPktHdr {
	
	private int caplen;
	private int len;
	
	private int tv_sec;
	private long tv_usec;
	
	public PcapPktHdr() {
		this.caplen = 0;
		this.len = 0;
		this.tv_sec = 0;
		this.tv_usec = 0;
	}
	
	public int getCapLen() {
		return caplen;
	}
	
	public int getLen() {
		return len;
	}
	
	public int getTvSec() {
		return tv_sec;
	}
	
	public long getTvUsec() {
		return tv_usec;
	}
	
	@Override
	public String toString() {
		return "caplen = " + caplen + ", len = " + len +
				", tv_sec = " + tv_sec + ", tv_usec = " + tv_usec;
	}
	
}
