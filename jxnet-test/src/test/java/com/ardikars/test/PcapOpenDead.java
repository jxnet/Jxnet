package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

public class PcapOpenDead {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = PcapOpenDead(1, AllTests.snaplen);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		} else {
			System.out.println("OK");
			PcapClose(handler);
		}
		Assert.assertNotEquals(null, handler);
	}
	
}
