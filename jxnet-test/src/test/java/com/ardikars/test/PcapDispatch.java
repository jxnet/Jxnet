package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class PcapDispatch {
	
	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
		String dev = AllTests.deviceName;
		Pcap handler = Jxnet.pcapOpenLive(dev, 1500, 1, 2000, errbuf);
		if (handler == null) {
			throw new PcapCloseException(errbuf.toString());
		}
		
		Assert.assertNotEquals(null, handler);
		
		PcapHandler callback = new PcapHandler() {
			public void nextPacket(Object t, PcapPktHdr pph, ByteBuffer bb) {
				System.out.println("User   : " + t);
				System.out.println("PktHdr : " + pph);
				System.out.println("Data   : " + bb);
				Assert.assertNotEquals(null, pph);
				Assert.assertNotEquals(null, bb);
			}
		};
		
		if (Jxnet.pcapDispatch(handler, 10, callback, null) != Jxnet.OK) {
			System.out.println("Gagal");
		}
		Jxnet.pcapClose(handler);
	}
	
}
