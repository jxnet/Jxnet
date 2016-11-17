package com.ardikars.test;

import org.junit.Assert;
import org.junit.Test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDumper;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.util.Loader;

public class PcapDumpOpen {

	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
        String dev = "wlan0";
        Pcap handler = Jxnet.pcapOpenLive(dev, 1500, 1, 2000, errbuf);
        if(handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }
        PcapDumper dumper = Jxnet.pcapDumpOpen(handler, "pcapdump.pcap");
        if(dumper == null) {
            System.err.println(Jxnet.pcapGetErr(handler));
            Jxnet.pcapClose(handler);
        } else {
            System.out.println("OK");
            Jxnet.pcapDumpClose(dumper);
            Jxnet.pcapClose(handler);
        }
        Assert.assertNotEquals(null, dumper);
	}
	
	static {
		Loader.loadLibrary();
	}
}
