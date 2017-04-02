package com.ardikars.test;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.packet.ip.IPv6;
import com.ardikars.jxnet.packet.tcp.TCP;
import com.ardikars.jxnet.util.FormatUtils;

import javax.crypto.Mac;
import java.nio.ByteBuffer;

public class ChecksumTest {

    private static String HEX_STREAM = "" +
            "4c5e0c78508f40f02fa46abe08004500003497d8400040064fe3ac1001e59df00723d76201bb9439b553e802b0c6801000e5816c00000101080a000b228e5c026753";

    public static void main(String[] args) {
        Ethernet ethernet = new Ethernet()
                .setDestinationMacAddress(MacAddress.DUMMY)
                .setSourceMacAddress(MacAddress.valueOf("AA:AA:AA:BB:BB:BB"));

        IPv6 iPv6 = new IPv6()
                .setDestinationAddress(Inet6Address.LOCALHOST)
                .setSourceAddress(Inet6Address.LOCALHOST);

        IPv4 iPv4 = new IPv4();
        iPv4.setIdentification((short) 15326);
        iPv4.setFlags((byte) 0x02);
        iPv4.setTtl((byte) 64);
        iPv4.setSourceAddress(Inet4Address.LOCALHOST);
        iPv4.setDestinationAddress(Inet4Address.LOCALHOST);

        TCP tcp = new TCP()
                .setSourcePort((short) 55138)
                .setDestinationPort((short) 443)
                .setSequence(1)
                .setAcknowledge(24)
                .setDataOffset((byte) 0)
                .setFlags((short) 20480)
                .setWindowSize((short) 229)
                .setUrgentPointer((short) 0)
                .setOptions(FormatUtils.toBytes("0101080a000b228e5c026753"));
        System.out.println(tcp);

        iPv4.setPacket(tcp);
        ethernet.setPacket(iPv4);

        byte[] bytes = ethernet.toBytes();
        ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
        buffer.put(bytes);

        Pcap pcap = Jxnet.PcapOpenDead(1, 1500);
        PcapDumper dumper = Jxnet.PcapDumpOpen(pcap, "/tmp/test.pcap");
        PcapPktHdr pktHdr = new PcapPktHdr(buffer.capacity(), buffer.capacity(), 0, 0);
        Jxnet.PcapDump(dumper, pktHdr, buffer);
        Jxnet.PcapClose(pcap);


    }

}
