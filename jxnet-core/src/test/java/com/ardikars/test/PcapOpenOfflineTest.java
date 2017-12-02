package com.ardikars.test;

import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.*;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 7
public class PcapOpenOfflineTest {

	@Test
	public void run() throws PcapCloseException {
		StringBuilder errbuf = new StringBuilder();
        Pcap handler = PcapOpenOffline("dump.pcap", errbuf);
        if(handler == null) {
            throw new PcapCloseException(errbuf.toString());
        }
        long start = System.currentTimeMillis();
        if (PcapLoop(handler, -1, AllTests.handler, null) != 0) {
            String err = PcapGetErr(handler);
            PcapClose(handler);
            throw new JxnetException(err);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("Start: " + start + ", end: " + end + " = " + (end - start));
        PcapClose(handler);
	}
		
}
