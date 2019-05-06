package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapTest {

    private static final Logger LOGGER = Logger.getLogger(PcapTest.class.getName());

    private StringBuilder errbuf;
    private Pcap.Builder builder;
    private String pcapPath;

    private String source;

    @Before
    public void init() throws IOException {
        errbuf = new StringBuilder(255);
        source = findSource();
        pcapPath = "../gradle/resources/pcap/icmp.pcap";
        LOGGER.info("Pcap path: " + pcapPath);
        builder = Pcap.builder()
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
                .errbuf(errbuf);
    }

    @Test
    public void pcapLive() throws IOException {
        if (source != null) {
            builder.source(source);
            Pcap pcap = Pcap.live(builder);
            assert !pcap.isClosed();
            assert !pcap.isDead();
            assert pcap.address() != 0L;
            pcap.close();
            assert pcap.isClosed();
            assert pcap.address() == 0L;
        }
    }

    @Test
    public void pcapDead() throws IOException {
        Pcap pcap = Pcap.dead(builder);
        assert pcap.isDead();
        assert !pcap.isClosed();
        assert pcap.address() != 0L;
        pcap.close();
        assert pcap.isClosed();
        assert pcap.address() == 0L;
    }

    @Test
    public void pcapOffline() throws IOException {
        Pcap pcap = Pcap.offline(builder);
        assert !pcap.isClosed();
        pcap.close();
        assert pcap.isClosed();
        assert pcap.address() == 0L;
    }

    @Test
    public void pcapBuilder() throws IOException {
        assert builder.toString() != null;
        Pcap dead = builder.pcapType(Pcap.PcapType.DEAD)
                .build();
        assert !dead.isClosed();
        Pcap offline = builder.pcapType(Pcap.PcapType.OFFLINE)
                .build();
        if (source != null) {
            Pcap live = builder
                    .source(source)
                    .pcapType(Pcap.PcapType.LIVE)
                    .build();
            assert !live.isClosed();
            live.close();
            assert live.isClosed();
            assert live.toString() != null;
        }
        assert !offline.isClosed();
        dead.close();
        assert dead.toString() != null;
        assert dead.isClosed();
        offline.close();
        assert offline.isClosed();
        assert offline.toString() != null;
    }

    @Test
    public void equealsAndHashCodeTest() throws CloneNotSupportedException, IOException {
        Pcap pcap = Pcap.dead(builder);
        Pcap cln = pcap.clone();
        assert pcap.equals(pcap);
        assert !pcap.equals(null);
        assert pcap.equals(cln);
        assert cln.hashCode() == pcap.hashCode();
        pcap.close();
    }

    private String findSource() {
        List<PcapIf> alldevsp = new ArrayList<PcapIf>();
        if (PcapFindAllDevs(alldevsp, errbuf) != OK) {
            return null;
        }
        String source = null;
        for (PcapIf dev : alldevsp) {
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    if (addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            source = dev.getName();
                        }
                    }
                }
            }
        }
        return source;
    }

}
