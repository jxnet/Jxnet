package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketHandler;
import com.ardikars.jxnet.packet.PacketHelper;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.packet.udp.UDP;
import com.ardikars.jxnet.util.HexUtils;
import org.junit.Test;

import java.util.Arrays;

public class Ethernet_IPv4_UDP {

    private static int index = 0;

    @Test
    public void run() {
        String[] hexStream = new String[] {
                "14cc20ccb9ecb827eb9a9c5f08004500003aa160400040119200c0a80196b4839090edc700350026078a31fe0100000100000000000008617264696b61727303636f6d0000010001",
                "b827eb9a9c5f14cc20ccb9ec08004500004a01944000fc1175bcb4839090c0a801960035edc70036f7ec31fe8100000100010000000008617264696b61727303636f6d0000010001c00c00010001000037af0004dea5ffc4",
                "14cc20ccb9ecb827eb9a9c5f08004500004aa1614000401191efc0a80196b4839090dbb800350036079adc33010000010000000000000331393603323535033136350332323207696e2d61646472046172706100000c0001",
                "b827eb9a9c5f14cc20ccb9ec0800450000aa085d4000fc116e93b4839090c0a801960035dbb80096b674dc33818000010001000200010331393603323535033136350332323207696e2d61646472046172706100000c0001c00c000c000100015180001a0a7769726f6b657274656e096964776562686f737403636f6d00c01000020001000151800011036e73320476656c6f036e657402696400c01000020001000151800006036e7331c0640000291000000080000000",
                "14cc20ccb9ecb827eb9a9c5f08004500003aa22340004011913dc0a80196b48390909a5500350026078adaa40100000100000000000008617264696b61727303636f6d0000010001",
                "b827eb9a9c5f14cc20ccb9ec08004500004a95654000fc11e1eab4839090c0a8019600359a550036a2bcdaa48100000100010000000008617264696b61727303636f6d0000010001c00c00010001000037ab0004dea5ffc4",
                "14cc20ccb9ecb827eb9a9c5f08004500004aa229400040119127c0a80196b4839090abcc00350036079afa11010000010000000000000331393603323535033136350332323207696e2d61646472046172706100000c0001",
                "b827eb9a9c5f14cc20ccb9ec08004500009f996b4000fc11dd8fb4839090c0a801960035abcc008b5ccbfa11818000010001000200000331393603323535033136350332323207696e2d61646472046172706100000c0001c00c000c00010001517c001a0a7769726f6b657274656e096964776562686f737403636f6d00c010000200010001517c0011036e73320476656c6f036e657402696400c010000200010001517c0006036e7331c064"
        };

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = Jxnet.PcapOpenOffline("../sample-capture/eth_ipv4_udp_dns.pcapng", errbuf);
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
                        if (ip.getPacket() instanceof UDP) {
                            UDP udp = (UDP) ip.getPacket();
                            UDP u = new UDP();
                            u.setSourcePort(udp.getSourcePort());
                            u.setDestinationPort(udp.getDestinationPort());
                            u.setLength(udp.getLength());
                            u.setChecksum(udp.getChecksum());
                            u.setPacket(udp.getPacket());
                            if (Arrays.equals(udp.toBytes(), u.toBytes())) {
                                System.out.println("Valid udp.");
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
