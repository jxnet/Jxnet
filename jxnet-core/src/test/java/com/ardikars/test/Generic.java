package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import org.junit.*;

public class Generic {

    @org.junit.Test
    public void run() {
        Pcap pcap = AllTests.openHandle();
        System.out.println("Libversion : " + Jxnet.PcapLibVersion());
        System.out.println("Major Version : " + Jxnet.PcapMajorVersion(pcap));
        System.out.println("Minor Version : " + Jxnet.PcapMinorVersion(pcap));
        System.out.println("Is Swapped    : " + Jxnet.PcapIsSwapped(pcap));
        System.out.println("Snapshot      : " + Jxnet.PcapSnapshot(pcap));
        Jxnet.PcapClose(pcap);
    }

}
