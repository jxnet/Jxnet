
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

public final class PcapStat {
	
	private long ps_recv;
	
	private long ps_drop;
	
	private long ps_ifdrop;
	
	public long getPsRecv() {
		return ps_recv;
	}
	
	public long getPsDrop() {
		return ps_drop;
	}
	
	public long getPsIfdrop() {
		return ps_ifdrop;
	}

}
