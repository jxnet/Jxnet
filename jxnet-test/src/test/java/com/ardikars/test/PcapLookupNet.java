package com.ardikars.test;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.InetAddress;
import com.ardikars.jxnet.Jxnet;
import org.junit.Assert;
import org.junit.Test;

public class PcapLookupNet {
	
	@Test
	public void run() {
		StringBuilder errbuf = new StringBuilder();
		String source = AllTests.deviceName;
		InetAddress netp = Inet4Address.valueOf(0);
		InetAddress nmask = Inet4Address.valueOf(0);
		int res = Jxnet.pcapLookupNet(source, netp, nmask, errbuf);
		if (res != Jxnet.OK) {
			System.err.println(errbuf.toString());
		} else {
			System.out.println("netp  : " + netp);
			System.out.println("nmask : " + nmask);
		}
		Assert.assertEquals(res, Jxnet.OK);
	}
	
}
