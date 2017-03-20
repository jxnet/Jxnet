package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class PcapDump {
	
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
		}

		PcapHandler<String> callback = (user, h, bytes) -> {
			Jxnet.PcapDump(dumper, h, bytes);
		};

		Jxnet.PcapLoop(handler, 5, callback, null);

		Assert.assertFalse(error);
		Assert.assertNotEquals(null, dumper);
		PcapClose(handler);
		PcapDumpClose(dumper);
	}
	
}
