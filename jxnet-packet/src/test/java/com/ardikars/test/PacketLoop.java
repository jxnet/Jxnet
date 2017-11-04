package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketListener;

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
        PacketListener.Map<String> callback = new PacketListener.Map<String>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pktHdr, Map<Class, Packet> packets) {
                System.out.println(packets);
            }
        };

        PacketListener.loop(pcap, -1, callback, null);
        Jxnet.PcapClose(pcap);
    }
}
