package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.PacketProcessor;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ip.IPv4;

public class PacketCapture {


    public static void main(String[] args) throws Exception {
        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenLive("wlan0", 65535, 1, 2000,  errbuf);
        PacketProcessor<String, IPv4> callback = new PacketProcessor<String, IPv4>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pcapPktHdr, IPv4 packet) {
                if (packet != null) {
                    System.out.println(getPacketNumber() + " : " + packet);
                }
            }
        };
        PacketHelper.loop(pcap, 50, callback, "hah");
        Jxnet.PcapClose(pcap);

    }

}
