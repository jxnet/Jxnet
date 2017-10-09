package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ethernet.Ethernet;

import java.nio.ByteBuffer;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {

        ByteBuffer buffer = ByteBuffer.allocateDirect(1100);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.putInt(1);
        buffer.position(0);
        buffer.rewind();
        System.out.println("Position   : " + buffer.position());
        System.out.println("Limit      : " + buffer.limit());
        System.out.println("Capacity   : " + buffer.capacity());
    }

}
