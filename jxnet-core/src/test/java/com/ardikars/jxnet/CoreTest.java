package com.ardikars.jxnet;

import static com.ardikars.jxnet.Core.LookupNetworkInterface;
import static com.ardikars.jxnet.Core.PcapCompile;
import static com.ardikars.jxnet.Core.PcapCompileNoPcap;
import static com.ardikars.jxnet.Core.PcapCreate;
import static com.ardikars.jxnet.Core.PcapDatalink;
import static com.ardikars.jxnet.Core.PcapFreeAllDevs;
import static com.ardikars.jxnet.Core.PcapFreeDataLinks0;
import static com.ardikars.jxnet.Core.PcapFreeTStampTypes0;
import static com.ardikars.jxnet.Core.PcapGetTStampPrecision0;
import static com.ardikars.jxnet.Core.PcapListDataLinks0;
import static com.ardikars.jxnet.Core.PcapListTStampTypes0;
import static com.ardikars.jxnet.Core.PcapOpenDead;
import static com.ardikars.jxnet.Core.PcapOpenLive;
import static com.ardikars.jxnet.Core.PcapSetDatalink;
import static com.ardikars.jxnet.Core.PcapSetImmediateMode;
import static com.ardikars.jxnet.Core.PcapSetPromisc;
import static com.ardikars.jxnet.Core.PcapSetTStampType;
import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapActivate;
import static com.ardikars.jxnet.Jxnet.PcapClose;
import static com.ardikars.jxnet.Jxnet.PcapDumpClose;
import static com.ardikars.jxnet.Jxnet.PcapDumpOpen;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;
import static com.ardikars.jxnet.Jxnet.PcapFreeCode;
import static com.ardikars.jxnet.Jxnet.PcapLookupNet;
import static com.ardikars.jxnet.Jxnet.PcapLoop;
import static com.ardikars.jxnet.Jxnet.PcapSetFilter;
import static com.ardikars.jxnet.Jxnet.PcapSetSnaplen;
import static com.ardikars.jxnet.Jxnet.PcapSetTimeout;
import static com.ardikars.jxnet.Jxnet.PcapStrError;

import com.ardikars.jxnet.util.Platforms;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoreTest {

    private final Logger logger = Logger.getLogger(JxnetTest.class.getName());

    private int resultCode;

    private final String resourceDumpFile = "/tmp/dump.pcap";

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap;
    private PcapDumper dumper;
    private BpfProgram bpfProgram;
    private ByteBuffer pkt;
    private PcapPktHdr pktHdr = new PcapPktHdr();

    private PcapIf source;
    private final int snaplen = 65335;
    private final PromiscuousMode promisc = PromiscuousMode.PRIMISCUOUS;
    private final int timeout = 2000;
    private final ImmediateMode immediate = ImmediateMode.IMMEDIATE;
    private final RadioFrequencyMonitorMode rfMon = RadioFrequencyMonitorMode.RFMON;
    private final BpfProgram.BpfCompileMode optimize = BpfProgram.BpfCompileMode.OPTIMIZE;
    private final int bufferSize = 1500;
    private final String filter = "icmp";

    private final PcapDirection direction = PcapDirection.PCAP_D_IN;

    private final int maxPkt = 5;
    private static int cntPkt = 0;

    private List<PcapIf> alldevsp = new ArrayList<PcapIf>();

    private Inet4Address netp = Inet4Address.valueOf(0);
    private Inet4Address maskp = Inet4Address.valueOf("255.255.255.0");

    private final PcapHandler<String> callback = new PcapHandler<String>() {
        @Override
        public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
            System.out.println("Argument    : " + user);
            System.out.println("PacketHeader: " + h);
            System.out.println("PacketBuffer: " + bytes);
        }
    };

    /**
     * Initialize
     * @throws Exception Exception.
     */
    @Before
    public void create() throws Exception {
        if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
            logger.warning("create:PcapFindAllDevs(): " + errbuf.toString());
        }
        for (PcapIf dev : alldevsp) {
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    if (addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            source = dev;
                        }
                    }
                }
            }
        }
        if (source == null) {
            throw new Exception("Failed to lookup device");
        } else {
            System.out.println("Source: " + source);
        }
        PcapFreeAllDevs(alldevsp);
        pcap = PcapCreate(source, errbuf);
        if (pcap == null) {
            logger.warning("create:PcapCreate(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapSetSnaplen(pcap, snaplen)) != OK) {
            logger.warning("create:PcapSetSnaplen(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if ((resultCode = PcapSetPromisc(pcap, promisc)) != OK) {
            logger.warning("create:PcapSetPromisc(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if ((resultCode = PcapSetTimeout(pcap, timeout)) != OK) {
            logger.warning("create:PcapSetTimeout(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if (!Platforms.isWindows()) {
            if ((resultCode = PcapSetImmediateMode(pcap, immediate)) != OK) {
                logger.warning("create:PcapSetImmediateMode(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
        }
        if ((resultCode = PcapActivate(pcap)) != OK) {
            logger.warning("create:PcapActivate(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }

        bpfProgram = new BpfProgram();

        File file = File.createTempFile("dump", ".pcap");
        if (!file.delete()) {
            logger.warning("create:File.delete()");
            return;
        }
        dumper = PcapDumpOpen(pcap, file.getAbsolutePath());
        if (dumper == null) {
            logger.warning("create:PcapDumpOpen() ");
            return;
        }
        if ((resultCode = PcapLookupNet(source.getName(), netp, maskp, errbuf)) != OK) {
            logger.warning("create:PcapLookupNet(): " + errbuf.toString());
            return;
        }
    }

    @Test
    public void Test01_PcapOpenLiveAndPcapClose() {
        Pcap pcap = PcapOpenLive(source, snaplen, promisc, timeout, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenLiveAndPcapClose:PcapOpenLive(): " + errbuf.toString());
        } else {
            PcapClose(pcap);
        }
    }

    @Test
    public void Test02_PcapCompileAndPcapSetFilter() {
        if ((resultCode = PcapCompile(pcap, bpfProgram, filter, optimize, maskp)) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapCompile(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test03_PcapCompileNoPcapSetFilterAndPcapLoop() {
        if ((resultCode = PcapCompileNoPcap(snaplen, DataLinkType.EN10MB,
                bpfProgram, filter, optimize, maskp)) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapCompileNoPcap(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test04_PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose() {
        Pcap pcap = PcapOpenDead(DataLinkType.EN10MB, snaplen);
        if (pcap == null) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapOpenDead()");
            return;
        }
        DataLinkType dataLinkType = PcapDatalink(pcap);
        System.out.println("Data Link Type (Before): " + dataLinkType);
        if ((resultCode = PcapSetDatalink(pcap, DataLinkType.LINUX_SLL)) != OK) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapSetDataLink(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        dataLinkType = PcapDatalink(pcap);
        System.out.println("Data Link Type (After): " + dataLinkType);
        PcapClose(pcap);
    }

    @Test
    public void Test05_LookupNetworkInterface() {
        PcapIf networkInterface = LookupNetworkInterface(errbuf);
        if (networkInterface == null) {
            logger.warning("LookupNetworkInterface:LookupNetworkInterface()");
            return;
        }
        System.out.println(networkInterface);
    }

    @Test
    public void Test06_PcapGetTStampPrecisionAndPcapSetTStampPrecision() {
        TimeStampPrecision timestamp = PcapGetTStampPrecision0(pcap);
        System.out.println("Time stamp precision (before): " + timestamp);
        if ((resultCode = PcapSetTStampType(pcap, (timestamp == TimeStampPrecision.TIMESTAMP_MICRO)
                ? TimeStampPrecision.TIMESTAMP_NANO : TimeStampPrecision.TIMESTAMP_MICRO)) != OK) {
            logger.warning("Timestamp precision not supported by operation system.");
        }
        timestamp = PcapGetTStampPrecision0(pcap);
        System.out.println("Time stamp precision (after) : " + timestamp);
    }

    @Test
    public void Test07_PcapListDatalinks() {
        List<DataLinkType> datalinks = new ArrayList<DataLinkType>();
        if ((resultCode = PcapListDataLinks0(pcap, datalinks)) < 0) {
            logger.warning("PcapListDataLinks:PcapListDataLinks(): " + PcapStrError(resultCode));
            return;
        }
        System.out.print("DataLinks:");
        for (DataLinkType datalink : datalinks) {
            System.out.print(" " + datalink);
        }
        PcapFreeDataLinks0(datalinks);
    }

    @Test
    public void Test08_PcapListTStampTypes() {
        List<TimeStampType> tsTypes = new ArrayList<TimeStampType>();
        if ((resultCode = PcapListTStampTypes0(pcap, tsTypes)) < 0) {
            logger.warning("PcapListTStampTypes:PcapListTStampTypes(): " + PcapStrError(resultCode));
            return;
        }
        System.out.print("Time Stamp Types:");
        for (TimeStampType tsType : tsTypes) {
            System.out.print(" " + tsType);
        }
        PcapFreeTStampTypes0(tsTypes);
    }

    /**
     * Destroy method.
     */
    @After
    public void destroy() {
        PcapClose(pcap);
        PcapFreeCode(bpfProgram);
        PcapDumpClose(dumper);
    }

}
