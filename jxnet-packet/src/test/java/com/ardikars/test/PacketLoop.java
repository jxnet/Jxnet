package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;

import java.util.Map;

public class PacketLoop {

    private static int index = 0;

    public static void main(String[] args) {
        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenLive("mon0", 65535, 1, 2000, errbuf);
        if (pcap == null) {
            System.err.println("ERROR.");
            return;
        }
        PacketHandler<String> callback = (arg, pktHdr, packets) -> {
            System.out.println(packets);
        };

        PacketHelper.loop(pcap, -1, callback, null);
        Jxnet.PcapClose(pcap);
    }
}
