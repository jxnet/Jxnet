package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

// 6
public class PcapDumpTest {
	
	@Test
	public void run() throws PcapCloseException {
		boolean error = false;

		Pcap handler = AllTests.openHandle(); // Exception already thrown

		final PcapDumper dumper = PcapDumpOpen(handler, "dump.pcap");
		if (dumper == null) {
			System.err.println(PcapGetErr(handler));
			error = true;
			PcapClose(handler);
		} else {
			System.out.println("OK");
		}

		PcapHandler<String> callback = new PcapHandler<String>() {
			@Override
			public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
				System.out.println("User   : " + user);
				System.out.println("Header : " + h);
				System.out.println("Packet : " + bytes);
				System.out.println("Write bytes: " + PcapDumpFTell(dumper));
				System.out.println("=======");
				PcapDump(dumper, h, bytes);
				PcapDumpFlush(dumper);
			}
		};

		if (Jxnet.PcapLoop(handler, AllTests.maxIteration, callback, null) != 0) {
			String err = PcapGetErr(handler);
			PcapDumpClose(dumper);
			PcapClose(handler);
			throw new JxnetException(err);
		}
		Assert.assertFalse(error);
		Assert.assertNotEquals(null, dumper);
		PcapDumpClose(dumper);
		PcapClose(handler);
	}
	
}
