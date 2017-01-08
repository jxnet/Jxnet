package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

public class PcapOpenLive {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = Jxnet.pcapOpenLive(dev, 1500, 1, 2000, errbuf);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		} else {
			System.out.println("OK");
			Jxnet.pcapClose(handler);
		}
		Assert.assertNotEquals(null, handler);
	}
	
}
