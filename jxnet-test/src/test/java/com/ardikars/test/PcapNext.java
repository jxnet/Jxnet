package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class PcapNext {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = Jxnet.pcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		}
		
		Assert.assertNotEquals(null, handler);
		boolean err = false;
		PcapPktHdr pkthdr = new PcapPktHdr();
		for (int i = 0; i < 10; i++) {
			ByteBuffer buf = Jxnet.pcapNext(handler, pkthdr);
			System.out.println(buf);
			if (buf == null || pkthdr == null) {
				err = true;
			}
		}
		Assert.assertFalse(err);
		Jxnet.pcapClose(handler);
	}
	
}
