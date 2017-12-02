package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

// 1
public class PcapFindAllDevsTest {
	
	@Test
	public void run() {
		StringBuilder errbuf = new StringBuilder();
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		PcapFindAllDevs(alldevsp, errbuf);
		for (PcapIf dev : alldevsp) {
			System.out.println("================================================\n\n");
			System.out.println("Name                  = " + dev.getName());
			System.out.println("Description           = " + dev.getDescription());
			System.out.println("Flags                 = " + dev.getFlags());
			
			for (PcapAddr addr : dev.getAddresses()) {
				if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
					System.out.println("------------------------------------------------");
					System.out.println("IPv4");
					System.out.println("Addr                   = " + addr.getAddr().toString());
					System.out.println("Netmask                = " + addr.getNetmask().toString());
					System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
					System.out.println("DstAddr                = " + addr.getDstAddr().toString());
					System.out.println("------------------------------------------------");
				} else if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET6) {
					System.out.println("------------------------------------------------");
					System.out.println("IPv6");
					System.out.println("Addr                   = " + addr.getAddr().toString());
					System.out.println("Netmask                = " + addr.getNetmask().toString());
					System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
					System.out.println("DstAddr                = " + addr.getDstAddr().toString());
					System.out.println("------------------------------------------------");
				}
			}
			//System.out.println(dev.getAddresses());
			
			Assert.assertNotEquals(null, dev.getName());
			System.out.println("================================================\n\n");
		}
	}
	
}
