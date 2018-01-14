package com.ardikars.jxnet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.logging.Logger;

import static com.ardikars.jxnet.Jxnet.*;

@RunWith(JUnit4.class)
public class Tests {

    private Logger logger = Logger.getLogger(Tests.class.getName());

    private Pcap pcap;
    private int snaplen = 65535;
    private DataLinkType linkType = DataLinkType.LINUX_SLL;

    private int resultCode;

    @Before
    public void create() {
        pcap = PcapOpenDead(linkType, snaplen);
        if (pcap == null) {
            logger.warning("create:PcapOpenDead(): ");
            return;
        }
    }

    @Test
    public void Test01_PcapGetErr() {
        System.out.println("Pcap Error: " + PcapGetErr(pcap));
    }

    @Test
    public void Test02_PcapDatalink() {
        System.out.println("Data Link Type: " + PcapDataLink(pcap));
    }

    @Test
    public void Test03_PcapSnaplen() {
        System.out.println("Snapshot: " + PcapSnapshot(pcap));
    }

    @Test
    public void Test04_PcapSetDirection() {
        if ((resultCode = PcapSetDirection(pcap, PcapDirection.PCAP_D_IN)) != OK) {
            logger.warning("PcapSetDirection:PcapSetDirection() " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test25_PcapCanSetRfMonAndPcapSetRfMon() {
        if (PcapCanSetRfMon(pcap) == 1) {
            if ((resultCode = PcapSetRfMon(pcap, 1)) != OK) {
                logger.warning("PcapCanSetRfMonAndPcapSetRfMon:PcapCanSetRfMon(): " + PcapStrError(resultCode));
                return;
            }
        }
    }

    @Test
    public void destroy() {
        PcapClose(pcap);
    }

}
