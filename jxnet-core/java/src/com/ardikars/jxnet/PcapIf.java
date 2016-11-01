
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import java.util.ArrayList;
import java.util.List;

import com.ardikars.jxnet.util.AddrUtils;

public final class PcapIf {

	@SuppressWarnings("unused")
	private PcapIf next;
	
	private volatile String name;
	
	private volatile String description;
	
	private volatile List<PcapAddr> addresses = new ArrayList<PcapAddr>();
	
	private volatile int flags;
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public int getFlags() {
		return flags;
	}
	
	public List<PcapAddr> getAddresses() {
		return addresses;
	}
	
	public MacAddress getHardwareAddress() {
		return AddrUtils.getHardwareAddress(name);
	}

    @Override
    public String toString() {
        return name;
    }
    
}