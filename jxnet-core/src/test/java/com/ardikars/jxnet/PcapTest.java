package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapTest {

    private File file;
    private StringBuilder errbuf;
    private Pcap.Builder builder;

    private String source;

    @Before
    public void init() throws IOException {
        file = File.createTempFile("cap", "pcap");
        errbuf = new StringBuilder(255);
        source = findSource();
        builder = new Pcap.Builder()
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
                .fileName(file.getAbsolutePath() + "/" + file.getName())
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
