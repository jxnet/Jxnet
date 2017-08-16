package com.ardikars.jxnet.packet.tcp;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.logger.DefaultPrinter;
import com.ardikars.jxnet.logger.Logger;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.util.HexUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class Ethernet_IPv4_TCP_Test {

    private static int index = 0;

    private String pcap_source_file = getClass().getResource("/sample-capture/eth_ipv4_tcp.pcapng").getPath();

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap = null;
    private Logger logger = Logger.getLogger(Ethernet_IPv4_TCP_Test.class, new DefaultPrinter());

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

    @Before
    public void OpenHandle() {
        pcap = Jxnet.PcapOpenOffline(pcap_source_file, errbuf);
        logger.info("Open handle.");
        Assert.assertNotEquals(errbuf.toString(), null, pcap);
    }

    @Test
    public void validate() {
        PacketListener.List<String> callback = (arg, pktHdr, packets) -> {
            Ethernet eth = (Ethernet) packets.get(0);
            if (eth != null) {
                if (!HexUtils.toHexString(eth.toBytes()).equals(hexStream[index])) {
                    logger.info(index+": "+HexUtils.toHexString(eth.toBytes()));
                } else {
                    Ethernet ethernet = (Ethernet) new Ethernet()
                            .setDestinationMacAddress(eth.getDestinationMacAddress())
                            .setSourceMacAddress(eth.getSourceMacAddress())
                            .setEthernetType(eth.getEthernetType())
                            .setPacket(eth.getPacket());
                    if (Arrays.equals(eth.toBytes(), ethernet.toBytes())) {
                        logger.info("Valid Ethernet.");
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
                            logger.info("Valid IPv4.");
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
                                logger.info("Valid TCP.");
                            }
                        }
                    }

                }
                index++;
            }
        };
        PacketListener.loop(pcap, -1, callback, "");
    }

    @Test
    public void foreach() {
        PacketListener.List<String> listCallback = (arg, pktHdr, packets) -> {
            logger.info("==========================================");
            packets.stream().forEach(packet -> logger.info(packet.toString()));
            logger.info("-------------------------------------------");
            Packet packet = packets.get(0);
            packet.forEachRemaining(packet1 -> logger.info(packet1.toString()));
            logger.info("-------------------------------------------");
            Packet packet2 = packets.get(0);
            while (packet2.hasNext()) { // false
                logger.info(packet2.next().toString());
            }
            logger.info("==========================================");
        };
        PacketListener.Map<String> mapCallback = (arg, pktHdr, packets) -> {
            logger.info("==========================================");
            packets.forEach((aClass, packet) -> {
                logger.info(aClass + ": "+ packet.toString());
            });
            logger.info("-------------------------------------------");
            Packet packet = packets.get(Ethernet.class);
            packet.forEachRemaining(packet1 -> logger.info(packet1.toString()));
            logger.info("-------------------------------------------");
            Packet packet2 = packets.get(Ethernet.class);
            while (packet2.hasNext()) { // false
                logger.info(packet2.next().toString());
            }
            logger.info("==========================================");
        };

        PacketListener.loop(pcap, -1, listCallback, "");
        PacketListener.loop(pcap, -1, mapCallback, "");
    }

    @After
    public void close() {
        if (!pcap.isClosed()) {
            logger.info("Close handle.");
            Jxnet.PcapClose(pcap);
        } else {
            logger.info("Handle already closed.");
        }
    }

}
