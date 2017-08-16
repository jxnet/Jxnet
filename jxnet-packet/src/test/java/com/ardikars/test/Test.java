package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ethernet.Ethernet;

import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {

        PacketListener.Map<String> callback = new PacketListener.Map<String>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pktHdr, Map<Class, Packet> packets) {
                Ethernet ethernet = (Ethernet) packets.get(Ethernet.class);
                ethernet.forEachRemaining(packet -> System.out.println(packet));
                System.out.println("===========================");
            }
        };

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("/root/Downloads/IPv6_NDP.cap", errbuf);

        PacketListener.loop(pcap, -1, callback, "");
        Jxnet.PcapClose(pcap);
    }

}
