package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.BpfProgram;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDumper;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.exception.JxnetException;
import com.ardikars.jxnet.util.FormatUtils;
import com.ardikars.jxnet.util.HexUtils;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class PcapOpenDead {
	
	@Test
	public void run() throws PcapCloseException {
		int linktype = 1;
		byte[] packets = HexUtils.parseHex(AllTests.rawData);
		Pcap handler = PcapOpenDead(linktype, AllTests.snaplen);
		if (handler == null) {
			throw new JxnetException("Failed to open pcap dead handle.");
		}
		PcapDumper dumper = PcapDumpOpen(handler, "../sample-capture/dump_dead.pcapng");
		if (dumper == null) {
			PcapClose(handler);
			throw new JxnetException("Failed to open pcap dumper handle.");
		}
		ByteBuffer buf = ByteBuffer.allocateDirect(packets.length);
		buf.put(packets);
		PcapPktHdr pktHdr = new PcapPktHdr(packets.length, packets.length, 0, 0);
		PcapDump(dumper, pktHdr, buf);
		PcapDumpFlush(dumper);
		PcapDumpClose(dumper);
		PcapClose(handler);
	}
	
}
