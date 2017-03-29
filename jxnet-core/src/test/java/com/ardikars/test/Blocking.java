package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import org.junit.*;

public class Blocking {


    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = AllTests.openHandle();
        int res;
        System.out.println("Set blocking.");
        if ((res = Jxnet.PcapSetNonBlock(pcap, 0, errbuf)) == -1) {
            System.err.println("Error set blocking: " + errbuf.toString());
        }
        res = Jxnet.PcapGetNonBlock(pcap, errbuf);
        System.out.println("Non Blocking  = " + res + " -> message " + errbuf.toString());
        System.out.println("==================");
        System.out.println("Set non blocking.");
        if ((res = Jxnet.PcapSetNonBlock(pcap, 1, errbuf)) == -1) {
            System.err.println("Error set non blocking: " + errbuf.toString());
        }
        res = Jxnet.PcapGetNonBlock(pcap, errbuf);
        System.out.println("Non Blocking  = " + res + " -> message " + errbuf.toString());

        Jxnet.PcapClose(pcap);
    }

}
