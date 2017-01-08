package com.ardikars.test;

import org.junit.Assert;
import org.junit.Test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.util.Loader;

public class PcapOpenOffline {

	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
        Pcap handler = Jxnet.pcapOpenOffline("dump.pcapng", errbuf);
        if(handler == null) {
            throw new PcapCloseException(errbuf.toString());
        } else {
            System.out.println("OK");
            Jxnet.pcapClose(handler);
        }
        Assert.assertNotEquals(null, handler);
	}
		
}
