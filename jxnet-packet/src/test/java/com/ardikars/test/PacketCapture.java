package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketHelper;

public class PacketCapture {


    public static void main(String[] args) throws Exception {

        StringBuilder errbuf = new StringBuilder();
        String source = Jxnet.PcapLookupDev(errbuf);
        if (source == null) {
            throw new Exception("Failed to find interface.");
        }
        Pcap pcap = Jxnet.PcapOpenLive(source, 1500, 1, 2000, errbuf);
        if (pcap == null) {
            throw new Exception("Failed to open handler to " + source);
        }
        Jxnet.PcapClose(pcap);

    }

}
