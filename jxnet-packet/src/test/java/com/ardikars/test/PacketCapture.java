package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.util.AddrUtils;

public class PacketCapture {


    public static void main(String[] args) throws Exception {
/*
        StringBuilder errbuf = new StringBuilder();
        String source = AddrUtils.LookupDev(errbuf);
        if (source == null) {
            throw new Exception("Failed to find interface.");
        }
        Pcap pcap = Jxnet.PcapOpenLive(source, 1500, 1, 2000, errbuf);
        if (pcap == null) {
            throw new Exception("Failed to open handler to " + source);
        }
        PacketHandler<String> callback = (arg, h, packets) -> {
            Ethernet ethernet = (Ethernet) packets.get(Ethernet.class);
            if (ethernet != null) {
                System.out.println("{");
                System.out.println("\t" + ethernet);
                Packet packet = ethernet.getPacket();
                System.out.println("\t" + packet);
                while((packet = packet.getPacket()) != null) {
                    System.out.println("\t" + packet);
                }
                System.out.println("}");
            }
        };
        if (PacketHelper.loop(pcap, -1, callback, null) != 0) {
            String err = Jxnet.PcapGetErr(pcap);
            Jxnet.PcapClose(pcap);
            throw new Exception(err);
        }
*/
    }

}
