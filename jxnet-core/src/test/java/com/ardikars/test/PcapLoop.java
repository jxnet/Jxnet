package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

import static com.ardikars.jxnet.Jxnet.*;
import static com.ardikars.jxnet.Jxnet.PcapLoop;
import static com.ardikars.jxnet.Jxnet.PcapOpenLive;

public class PcapLoop {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
		System.out.println(handler.getAddress());
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		}
		
		PcapHandler<String> callback = new PcapHandler<String>() {
			public void nextPacket(String t, PcapPktHdr pph, ByteBuffer bb) {
				//System.out.println("User   : " + t);
				System.out.println("PktHdr : " + pph);
				System.out.println("Data   : " + bb);
			}
		};
		
		if (PcapLoop(handler, 5, callback, null) != OK) {
			System.err.println("GAGAL");
		}
		PcapClose(handler);
	}
	
}
