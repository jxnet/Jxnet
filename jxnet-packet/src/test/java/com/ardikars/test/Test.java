package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.AbstractPacketListener;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.icmp.ICMPv6;
import com.ardikars.jxnet.packet.ip.IPv6;
import com.ardikars.jxnet.packet.tcp.TCP;

import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {

        PacketHandler<String> callback = new PacketHandler<String>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pktHdr, Map<Class, Packet> packets) {
                Ethernet ethernet = (Ethernet) packets.get(Ethernet.class);
                /*if (ethernet.getPacket() instanceof IPv6) {
                    IPv6 iPv6 = (IPv6) ethernet.getPacket();
                    System.out.println(iPv6);
                } else {
                    System.out.println(ethernet);
                }*/
                System.out.println(ethernet.getPacket());
            }
        };

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("/root/Downloads/IPv6_NDP.cap", errbuf);

        PacketHelper.loop(pcap, -1, callback, "");
        Jxnet.PcapClose(pcap);
    }

}
