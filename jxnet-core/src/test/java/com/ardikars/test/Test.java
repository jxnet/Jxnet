package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDirection;

/**
 * Jxnet Untested function
 *
 * PcapFile
 * PcapDumpFile
 * PcapDumpFOpen
 *
 * PcapCompileNoPcap
 */



public class Test {

    public static void main(String[] args) {

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenLive("eth0", 1500, 1, 500, errbuf);

        System.out.println(Jxnet.PcapSetDirection(pcap, PcapDirection.PCAP_D_OUT));

        Jxnet.PcapClose(pcap);
    }
}
