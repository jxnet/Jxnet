package com.ardikars.test;

import com.ardikars.jxnet.exception.JxnetException;
import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;

public class PcapOpenOffline {

	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
        Pcap handler = PcapOpenOffline("dump.pcapng", errbuf);
        if(handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }
        if (PcapLoop(handler, -1, AllTests.handler, null) != 0) {
            String err = PcapGetErr(handler);
            PcapClose(handler);
            throw new JxnetException(err);
        }
        PcapClose(handler);
	}
		
}
