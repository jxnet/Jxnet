package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import org.junit.Assert;
import org.junit.Test;

public class PcapLookupDev {
	
	@Test
	public void run() {
		StringBuilder errbuf = new StringBuilder();
		String dev = PcapLookupDev(errbuf);
		if (dev == null) {
			System.err.println(errbuf.toString());
		} else {
			System.out.println(dev);
		}
		Assert.assertNotEquals(null, dev);
		PcapIf source = Jxnet.LookupNetworkInterface(errbuf);
		if (source == null) {
			System.err.println(errbuf.toString());
		} else {
			System.out.println("Interface            : " + source.getName());
			for (PcapAddr addr : source.getAddresses()) {
				if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
					System.out.println("Address              : " + addr.getAddr());
					System.out.println("Netmask Address      : " + addr.getNetmask());
					System.out.println("Broadcast Address    : " + addr.getBroadAddr());
					System.out.println("Destination Address  : " + addr.getDstAddr());
				}
			}
			System.out.println("Descrition           : " + source.getDescription());
			System.out.println("Flags                : " + source.getFlags());
		}
		Assert.assertNotEquals(null, source);
	}
	
}
