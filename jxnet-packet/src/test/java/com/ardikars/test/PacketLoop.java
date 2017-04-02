package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;

import java.util.Map;

public class PacketLoop {

    public static void main(String[] args) {

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("/home/pi/Downloads/pcapfiles/arp_pcap.pcapng.cap", errbuf);

        PacketHandler<String> callback = (arg, pktHdr, packets) -> {
            for (Map.Entry value : packets.entrySet()) {
                System.out.println(value);
            }
            System.out.println("===========================================================");
        };

        PacketHelper.loop(pcap, -1, callback, null);

        Jxnet.PcapClose(pcap);
    }
}
