package com.ardikars.test;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.InetAddress;
import static com.ardikars.jxnet.Jxnet.*;
import org.junit.Assert;
import org.junit.Test;

public class PcapLookupNetTest {
	
	@Test
	public void run() {
		StringBuilder errbuf = new StringBuilder();
		String source = AllTests.deviceName;
		Inet4Address netp = Inet4Address.valueOf(0);
		Inet4Address nmask = Inet4Address.valueOf(0);
		int res = PcapLookupNet(source, netp, nmask, errbuf);
		if (res != OK) {
			System.err.println(errbuf.toString());
		} else {
			System.out.println("netp  : " + netp);
			System.out.println("nmask : " + nmask);
		}
		Assert.assertEquals(res, OK);
	}
	
}
