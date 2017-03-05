package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDumper;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

public class PcapDumpOpen {
	
	@Test
	public void run() throws PcapCloseException {
		boolean error = false;
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		}
		PcapDumper dumper = PcapDumpOpen(handler, "dump.pcapng");
		if (dumper == null) {
			System.err.println(PcapGetErr(handler));
			error = true;
			PcapClose(handler);
		} else {
			System.out.println("OK");
			PcapDumpClose(dumper);
			PcapClose(handler);
		}
		Assert.assertFalse(error);
		Assert.assertNotEquals(null, dumper);
	}
	
}
