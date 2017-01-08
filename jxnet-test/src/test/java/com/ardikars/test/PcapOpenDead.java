package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

public class PcapOpenDead {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = Jxnet.pcapOpenDead(1, 1500);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		} else {
			System.out.println("OK");
			Jxnet.pcapClose(handler);
		}
		Assert.assertNotEquals(null, handler);
	}
	
}
