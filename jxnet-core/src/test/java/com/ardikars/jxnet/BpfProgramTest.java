package com.ardikars.jxnet;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BpfProgramTest {

    private static final Logger LOGGER = Logger.getLogger(BpfProgram.class.getName());

    private Pcap pcap;
    private BpfProgram.Builder builder;
    private String pcapPath;

    @Before
    public void init() {
        StringBuilder errbuf = new StringBuilder();
        pcapPath = "../gradle/resources/pcap/icmp.pcap";
        LOGGER.info("Pcap path: " + pcapPath);
        pcap = Pcap.builder()
                .snaplen(65535)
                .promiscuousMode(PromiscuousMode.PROMISCUOUS)
                .timeout(2000)
                .immediateMode(ImmediateMode.IMMEDIATE)
                .timestampType(PcapTimestampType.HOST)
                .direction(PcapDirection.PCAP_D_INOUT)
                .timestampPrecision(PcapTimestampPrecision.MICRO)
                .rfmon(RadioFrequencyMonitorMode.NON_RFMON)
                .enableNonBlock(false)
                .dataLinkType(DataLinkType.EN10MB)
                .fileName(pcapPath)
                .pcapType(Pcap.PcapType.OFFLINE)
                .errbuf(errbuf).build();
        builder = BpfProgram.builder()
                .filter("arp")
                .netmask(0xffffff00)
                .pcap(pcap);
    }

    @Test
    public void optimize() {
        assert !pcap.isClosed();
        assert pcap.toString() != null;
        assert builder.toString() != null;
        BpfProgram optimize = builder.bpfCompileMode(BpfProgram.BpfCompileMode.OPTIMIZE)
                .build();
        assert !optimize.isClosed();
        try {
            optimize.close();
        } catch (IOException e) {
            //
        }
        assert optimize.isClosed();
        assert optimize.toString() != null;
    }

    @After
    public void destroy()  {
        try {
            pcap.close();
        } catch (IOException e) {
            //
        }
    }

}
