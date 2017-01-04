package com.ardikars.test;

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.util.Loader;

public class PcapNext {

	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
        String dev = "wlan0";
        Pcap handler = Jxnet.pcapOpenLive(dev, 1500, 1, 2000, errbuf);
        if(handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }

        Assert.assertNotEquals(null, handler);
        
        PcapPktHdr pkthdr = new PcapPktHdr();
        for(int i=0; i<10; i++) {
            ByteBuffer buf = Jxnet.pcapNext(handler, pkthdr);
            System.out.println(buf);
            Assert.assertNotEquals(null, buf);
            Assert.assertNotEquals(null, pkthdr);
        }
        Jxnet.pcapClose(handler);
	}
	
	static {
		Loader.loadLibrary();
	}
}
