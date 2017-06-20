package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.PacketDecoder;
import com.ardikars.jxnet.packet.tcp.TCP;


public class TCPDecoder extends PacketDecoder<TCP> {


    @Override
    public Pcap getPcap() {
        return super.getPcap();
    }
}
