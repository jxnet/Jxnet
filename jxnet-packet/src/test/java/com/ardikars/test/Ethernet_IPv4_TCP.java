package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.packet.tcp.TCP;
import com.ardikars.jxnet.util.HexUtils;
import org.junit.Test;

import java.util.Arrays;

public class Ethernet_IPv4_TCP {

    private static int index = 0;

    @Test
    public void run() {

        String[] hexStream = new String[] {
                "14cc20ccb9ecb827eb9a9c5f08004500003c8303400040061710c0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020eca70000000001030307",
                "14cc20ccb9ecb827eb9a9c5f08004500003c830440004006170fc0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020ed0b0000000001030307",
                "14cc20ccb9ecb827eb9a9c5f08004500003c830540004006170ec0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020edd30000000001030307",
                "14cc20ccb9ecb827eb9a9c5f08004500003c830640004006170dc0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020ef640000000001030307",
                "14cc20ccb9ecb827eb9a9c5f08004500003c830740004006170cc0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020f2860000000001030307",
                "b827eb9a9c5f14cc20ccb9ec080045000028e3ca00002d0691337223e470c0a80196a8ad0017244e23b9000000005002b3b5f28e0000000000006650",
                "14cc20ccb9ecb827eb9a9c5f0800450000281ab040004006074ec0a801967223e4700017a8ad00000000244e23ba50140000a6310000",
                "b827eb9a9c5f14cc20ccb9ec0800450000284de64000f006bc9b34088a07c0a8019601bb38f3dd6fa3f10000000050040000738300000000000023b9",
                "14cc20ccb9ecb827eb9a9c5f08004500003c830840004006170bc0a80196dea5ffc4e7661f9069206fa400000000a0027210a0d70000020405b40402080a0020f8c80000000001030307"
        };

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("../sample-capture/eth_ipv4_tcp.pcapng", errbuf);
        if (pcap == null) {
            System.err.println(errbuf.toString());
            return;
        }

        PacketHandler<String> callback = (arg, pktHdr, packets) -> {
            Ethernet eth = (Ethernet) packets.get(Ethernet.class);
            if (eth != null) {
                if (!HexUtils.toHexString(eth.toBytes()).equals(hexStream[index])) {
                    System.out.println(index+": "+HexUtils.toHexString(eth.toBytes()));
                } else {
                    Ethernet ethernet = (Ethernet) new Ethernet()
                            .setDestinationMacAddress(eth.getDestinationMacAddress())
                            .setSourceMacAddress(eth.getSourceMacAddress())
                            .setEthernetType(eth.getEthernetType())
                            .setPacket(eth.getPacket());
                    if (Arrays.equals(eth.toBytes(), ethernet.toBytes())) {
                        System.out.println("Valid ethernet.");
                    }

                    if (eth.getPacket() instanceof IPv4) {
                        IPv4 ipv4 = (IPv4) eth.getPacket();
                        IPv4 ip = new IPv4();
                        ip.setVersion(ipv4.getVersion());
                        ip.setHeaderLength(ipv4.getHeaderLength());
                        ip.setDiffServ(ipv4.getDiffServ());
                        ip.setExpCon(ipv4.getExpCon());
                        ip.setTotalLength(ipv4.getTotalLength());
                        ip.setIdentification(ipv4.getIdentification());
                        ip.setFlags(ipv4.getFlags());
                        ip.setFragmentOffset(ipv4.getFragmentOffset());
                        ip.setTtl(ipv4.getTtl());
                        ip.setProtocol(ipv4.getProtocol());
                        ip.setChecksum(ipv4.getChecksum());
                        ip.setSourceAddress(ipv4.getSourceAddress());
                        ip.setDestinationAddress(ipv4.getDestinationAddress());
                        ip.setOptions(ipv4.getOptions());
                        ip.setPacket(ipv4.getPacket());
                        if (Arrays.equals(ipv4.toBytes(), ip.toBytes())) {
                            System.out.println("Valid ipv4.");
                        }

                        if (ip.getPacket() instanceof TCP) {
                            TCP tcp = (TCP) ip.getPacket();
                            TCP t = new TCP();
                            t.setSourcePort(tcp.getSourcePort());
                            t.setDestinationPort(tcp.getDestinationPort());
                            t.setSequence(tcp.getSequence());
                            t.setAcknowledge(tcp.getAcknowledge());
                            t.setDataOffset(tcp.getDataOffset());
                            t.setFlags(tcp.getFlags());
                            t.setWindowSize(tcp.getWindowSize());
                            t.setChecksum(tcp.getChecksum());
                            t.setUrgentPointer(tcp.getUrgentPointer());
                            t.setOptions(tcp.getOptions());
                            t.setPacket(tcp.getPacket());
                            if (Arrays.equals(tcp.toBytes(), t.toBytes())) {
                                System.out.println("Valid tcp.");
                            }
                        }
                    }

                }
                index++;
            }
        };

        PacketHelper.loop(pcap, -1, callback, null);

        Jxnet.PcapClose(pcap);
    }

}
