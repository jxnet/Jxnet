package com.ardikars.test;


import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;

public class Test {

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenLive("wlan0", 65535, 1, 2000, errbuf);
        if (pcap == null) {
            System.out.println(errbuf.toString());
        } else {
            System.out.println(pcap + " : " +pcap.getDataLinkType());
        }
        Jxnet.PcapClose(pcap);
    }

}
