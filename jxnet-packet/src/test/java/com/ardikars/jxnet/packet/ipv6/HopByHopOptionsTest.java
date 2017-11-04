package com.ardikars.jxnet.packet.ipv6;


import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.PacketListener;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class HopByHopOptionsTest {

    private static int index = 0;

    private String pcap_source_file = getClass().getResource("/sample-capture/capture.pcap").getPath();

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap = null;
    private Logger logger = LoggerFactory.getLogger(HopByHopOptionsTest.class);

    java.util.logging.Logger log = java.util.logging.Logger.getLogger("");
    @Before
    public void OpenHandle() {
        logger.debug("fdf", "sfdsf");
        pcap = Jxnet.PcapOpenOffline(pcap_source_file, errbuf);
        logger.info("Open handle.");
        Assert.assertNotEquals(errbuf.toString(), null, pcap);
    }

    @Test
    public void validate() {
        PacketListener.List<String> callback = new PacketListener.List<String>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pktHdr, List<Packet> packets) {
                Ethernet eth = (Ethernet) packets.get(0);
                if (eth != null) {

                    index++;
                }
            }
        };
        PacketListener.loop(pcap, -1, callback, "");
    }

    @Test
    public void foreach() {
        PacketListener.List<String> listCallback = new PacketListener.List<String>() {
            @Override
            public void nextPacket(String arg, PcapPktHdr pktHdr, List<Packet> packets) {
                Packet packet2 = packets.get(0);
                while (packet2.hasNext()) { // false
                    logger.info(packet2.next().toString());
                }
                logger.info("==========================================");
            }
        };
        PacketListener.loop(pcap, -1, listCallback, "");
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
