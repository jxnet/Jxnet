package com.ardikars.jxnet.packet.arp;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ProtocolType;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.util.HexUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by root on 16/08/17.
 */
public class Ethernet_ARP_Test {

    private static int index = 0;

    private String pcap_source_file = getClass().getResource("/sample-capture/eth_arp_pcap.pcapng").getPath();

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap = null;
    private Logger logger = LoggerFactory.getLogger(Ethernet_ARP_Test.class);

    String[] hexStream = new String[] {
            "14cc20ccb9ecb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a801fe",
            "b827eb9a9c5f14cc20ccb9ec0806000108000604000214cc20ccb9ecc0a801feb827eb9a9c5fc0a801964445045f7375620b5f676f6f676c65636173",
            "ffffffffffffb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a80197",
            "b827eb9a9c5f14dda95e8bbc0806000108000604000214dda95e8bbcc0a80197b827eb9a9c5fc0a80196e25dfb605018f926679a0000150303001a00",
            "ffffffffffff14dda95e8bbc0806000108000604000114dda95e8bbcc0a80197000000000000c0a80196e00000fc0001000000000000204648464145",
            "14dda95e8bbcb827eb9a9c5f08060001080006040002b827eb9a9c5fc0a8019614dda95e8bbcc0a80197",
            "ffffffffffffb827eb9a9c5f08060001080006040001b827eb9a9c5fc0a80196000000000000c0a80164",
            "b827eb9a9c5fe8de27452f1a08060001080006040002e8de27452f1ac0a80164b827eb9a9c5fc0a8019698f601c9f0100913a4e600000101080a007f"
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
                if (!HexUtils.toHexString(eth.bytes()).equals(hexStream[index])) {
                    logger.info(index + ": " + HexUtils.toHexString(eth.bytes()));
                } else {
                    Ethernet ethernet = (Ethernet) new Ethernet()
                            .setDestinationMacAddress(eth.getDestinationMacAddress())
                            .setSourceMacAddress(eth.getSourceMacAddress())
                            .setEthernetType(eth.getEthernetType())
                            .setPacket(eth.getPacket());
                    if (Arrays.equals(eth.bytes(), ethernet.bytes())) {
                        logger.info("Valid Ethernet.");
                    }
                    if (eth.getPacket() instanceof ARP) {
                        ARP arp = (ARP) eth.getPacket();
                        ARP arp1 = (ARP) new ARP()
                                .setHardwareType(DataLinkType.valueOf(arp.getHardwareType().getValue()))
                                .setProtocolType(ProtocolType.getInstance(arp.getProtocolType().getValue()))
                                .setHardwareAddressLength(arp.getHardwareAddressLength())
                                .setProtocolAddressLength(arp.getHardwareAddressLength())
                                .setOperationCode(arp.getOperationCode())
                                .setSenderHardwareAddress(arp.getSenderHardwareAddress())
                                .setSenderProtocolAddress(arp.getSenderProtocolAddress())
                                .setTargetHardwareAddress(arp.getTargetHardwareAddress())
                                .setTargetProtocolAddress(arp.getTargetProtocolAddress())
                                .setPacket(arp.getPacket());
                        logger.debug(Arrays.toString(arp.bytes()));
                        logger.debug(Arrays.toString(arp1.bytes()));
                        System.out.println("----");
                        if (Arrays.equals(arp.bytes(), arp1.bytes())) {
                            logger.info("Valid ARP.");
                        }
                    }
                }
                index++;
            }
        };
        PacketListener.loop(pcap, -1, callback, "");
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
