package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import org.junit.*;

import java.nio.ByteBuffer;

public class PcapActivate {

    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapCreate(AllTests.deviceName, errbuf);
        Jxnet.PcapSetSnaplen(pcap, 1500);
        Jxnet.PcapSetPromisc(pcap, 1);
        Jxnet.PcapSetTimeout(pcap, 1000);
        Jxnet.PcapSetImmediateMode(pcap, 1);
        if (Jxnet.PcapCanSetRfMon(pcap) == 1) {
            System.out.println("Set rfmon.");
            Jxnet.PcapSetRfMon(pcap, 1);
        }
        Jxnet.PcapActivate(pcap);

        int i=0;
        ByteBuffer buffer;
        PcapPktHdr pktHdr = new PcapPktHdr();
        while (i < 10) {
            buffer = Jxnet.PcapNext(pcap, pktHdr);
            if (buffer == null) {
                System.out.println("Timeout.");
            } else {
                System.out.println(pktHdr);
            }
            i++;
        }
        Jxnet.PcapClose(pcap);
    }

}
