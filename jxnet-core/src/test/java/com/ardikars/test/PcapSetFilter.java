package com.ardikars.test;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.*;

import java.nio.ByteBuffer;

import static com.ardikars.jxnet.Jxnet.*;

public class PcapSetFilter {

    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();
        String dev = AllTests.deviceName;
        Pcap handler = PcapOpenLive(dev, AllTests.snaplen, AllTests.promisc, AllTests.to_ms, errbuf);
        BpfProgram fp = new BpfProgram();
        Jxnet.PcapCompile(handler, fp, "icmp", 1, 0xffffff);
        Jxnet.PcapSetFilter(handler, fp);
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
