package com.ardikars.test;

import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.AbstractPacketListener;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.tcp.TCP;

public class Test {

    public static void main(String[] args) {

        AbstractPacketListener<String, TCP> callback = new AbstractPacketListener<String, TCP>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pcapPktHdr, TCP packet) {

            }
        };

    }

}
