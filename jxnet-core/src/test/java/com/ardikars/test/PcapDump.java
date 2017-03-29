package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.JxnetException;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class PcapDump {
	
	@Test
	public void run() throws PcapCloseException {
		boolean error = false;

		Pcap handler = AllTests.openHandle(); // Exception already thrown

		PcapDumper dumper = PcapDumpOpen(handler, "dump.pcapng");
		if (dumper == null) {
			System.err.println(PcapGetErr(handler));
			error = true;
			PcapClose(handler);
		} else {
			System.out.println("OK");
		}

		PcapHandler<String> callback = (user, h, bytes) -> {
			System.out.println("User   : " + user);
			System.out.println("Header : " + h);
			System.out.println("Packet : " + bytes);
			System.out.println("=======");
			Jxnet.PcapDump(dumper, h, bytes);
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
