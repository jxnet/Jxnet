package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Platforms;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import static com.ardikars.jxnet.Jxnet.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JxnetTest {

    private final Logger logger = Logger.getLogger(JxnetTest.class.getName());

    private int resultCode;

    private final String resourceDumpFile = "/tmp/dump.pcap";

    private StringBuilder errbuf = new StringBuilder();
    private Pcap pcap;
    private PcapDumper dumper;
    private BpfProgram bpfProgram;
    private ByteBuffer pkt;
    private PcapPktHdr pktHdr = new PcapPktHdr();

    private String source;
    private final int snaplen = 65335;
    private final int promisc = 1;
    private final int timeout = 2000;
    private final int immediate = 1;
    private final int optimize = 1;
    private final int bufferSize = 1500;
    private final String filter = "icmp";

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
                            source = dev.getName();
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
        if ((resultCode = PcapLookupNet(source, netp, maskp, errbuf)) != OK) {
            logger.warning("create:PcapLookupNet(): " + errbuf.toString());
            return;
        }
    }

    @Test
    public void Test01_PcapFindAllDevs() {
        if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
            logger.warning("PcapFindAllDevs:PcapFindAllDevs(): " + errbuf.toString());
        }
        for (PcapIf dev : alldevsp) {
            System.out.println("================================================\n\n");
            System.out.println("Name                  = " + dev.getName());
            System.out.println("Description           = " + dev.getDescription());
            System.out.println("Flags                 = " + dev.getFlags());
            for (PcapAddr addr : dev.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    System.out.println("------------------------------------------------");
                    System.out.println("IPv4");
                    System.out.println("Addr                   = " + addr.getAddr().toString());
                    System.out.println("Netmask                = " + addr.getNetmask().toString());
                    System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
                    System.out.println("DstAddr                = " + addr.getDstAddr().toString());
                    System.out.println("------------------------------------------------");
                } else if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET6) {
                    System.out.println("------------------------------------------------");
                    System.out.println("IPv6");
                    System.out.println("Addr                   = " + addr.getAddr().toString());
                    System.out.println("Netmask                = " + addr.getNetmask().toString());
                    System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
                    System.out.println("DstAddr                = " + addr.getDstAddr().toString());
                    System.out.println("------------------------------------------------");
                } else {
                    logger.info(addr.toString());
                }
            }
            System.out.println("================================================\n\n");
        }
    }

    @Test
    public void Test02_PcapOpenLiveAndPcapClose() {
        Pcap pcap = PcapOpenLive(source, snaplen, promisc, timeout, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenLiveAndPcapClose:PcapOpenLive(): "+ errbuf.toString());
        } else {
            PcapClose(pcap);
        }
    }

    @Test
    public void Test03_PcapLoopAndPcapStats() {
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
        PcapStat stat = new PcapStat();
        if ((resultCode = PcapStats(pcap, stat)) != OK) {
            logger.warning("PcapLoopAndPcapStats:PcapStats(): " + PcapStrError(resultCode));
            return;
        }
        System.out.println(stat);
    }

    @Test
    public void Test04_PcapDispatchAndPcapSetNonBlock() {
        if ((resultCode = PcapSetNonBlock(pcap, 1, errbuf)) != OK) {
            logger.warning("PcapDispatch:PcapSetNonBlock(): " + PcapStrError(resultCode));
        }
        if ((resultCode = PcapDispatch(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapDispatch:PcapDispatch(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test05_PcapDumpOpenAndPcapDumpClose() throws IOException {
        File file = File.createTempFile("dump", ".pcap");
        if (!file.delete()) {
            logger.warning("PcapDumpOpenAndClose:File.delete()");
            return;
        }
        PcapDumper dumper = PcapDumpOpen(pcap, file.getAbsolutePath());
        if (dumper == null) {
            logger.warning("PcapDumpOpenAndClose:PcapDumpOpen() ");
            return;
        }
        PcapDumpClose(dumper);
    }

    @Test
    public void Test06_PcapDumpPcapDumpFTellAndPcapDumpFlush() {
        if ((resultCode = PcapLoop(pcap, maxPkt, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
                if (h != null && bytes != null) {
                    System.out.println("Dumping packet into a file.");
                    System.out.println("File position: " + PcapDumpFTell(dumper));
                    PcapDump(dumper, h, bytes);
                    PcapDumpFlush(dumper);
                }
            }
        }, "This Is Dumper Argument")) != OK) {
            logger.warning("PcapDumpPcapDumpFTellAndPcapDumpFlush:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test07_PcapOpenOfflinePcapLoopAndPcapClose() {
        Pcap pcap = PcapOpenOffline(resourceDumpFile, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenOfflinePcapLoopAndPcapClose:PcapOpenOffline(): " + errbuf.toString());
            return;
        }
        PcapLoop(pcap, maxPkt, callback, "");
        PcapClose(pcap);
    }

    @Test
    public void Test08_PcapCompilePcapSetFilterAndPcapLoop() {
        if ((resultCode = PcapCompile(pcap, bpfProgram, filter, optimize, maskp.toInt())) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapCompile(): " + PcapStrError(resultCode));
            return;
        }
        if((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompilePcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test09_PcapSendPacket() throws Exception {
//        throw new Exception("Not implemented yet");
    }

    @Test
    public void Test10_PcapNext() {
        for (int i=0; i<maxPkt; i++) {
            pkt = PcapNext(pcap, pktHdr);
            if (pktHdr != null && pkt != null) {
                System.out.println("PacketHeader: " + pktHdr);
                System.out.println("PacketBuffer: " + pkt);
            }
        }
    }

    @Test
    public void Test11_PcapNextEx() {
        pkt = ByteBuffer.allocateDirect(bufferSize);
        for (int i=0; i<maxPkt; i++) {
            resultCode = PcapNextEx(pcap, pktHdr, pkt);
            logger.info("Result: " + resultCode);
            if (pktHdr != null && pkt != null) {
                System.out.println("PacketHeader: " + pktHdr);
                System.out.println("PacketBuffer: " + pkt);
            }
        }
    }

    @Test
    public void Test12_PcapDataLink() {
        System.out.println("Data Link Type: " + PcapDataLink(pcap));
    }

    @Test
    public void Test13_PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose() {
        Pcap pcap = PcapOpenDead(DataLinkType.EN10MB.getValue(), snaplen);
        if (pcap == null) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapOpenDead()");
            return;
        }
        System.out.println("Data Link Type (Before): " + PcapDataLink(pcap));
        if ((resultCode = PcapSetDataLink(pcap, DataLinkType.LINUX_SLL.getValue())) != OK) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapSetDataLink(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        System.out.println("Data Link Type (After): " + PcapDataLink(pcap));
        PcapClose(pcap);
    }

    @Test
    public void Test14_PcapBreakLoopAndPcapLoop() {
        cntPkt = 0;
        if ((resultCode = PcapLoop(pcap, maxPkt, new PcapHandler<String>() {
            @Override
            public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
                if (cntPkt == (maxPkt/2)) {
                    System.out.println("Break loop.");
                    PcapBreakLoop(pcap);
                }
                System.out.println("Argument    : " + user);
                System.out.println("PacketHeader: " + h);
                System.out.println("PacketBuffer: " + bytes);
                cntPkt++;
            }
        }, "This Is User Argument")) != OK) {
            logger.warning("PcapBreakLoopAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test15_PcapLookupDev() {
        String device = PcapLookupDev(errbuf);
        if (device == null) {
            logger.warning("PcapLookupDev:PcapLookupDev(): " + errbuf.toString());
            return;
        }
        System.out.println("Lookup device: " + device);
    }

    @Test
    public void Test16_PcapGetErr() {
        System.out.println("Pcap Error: " + PcapGetErr(pcap));
    }

    @Test
    public void Test17_PcapLibVersionPcapMajorVersionAndPcapMinorVersion() {
        System.out.println("Pcap Library Version: " + PcapLibVersion());
        System.out.println("Pcap Major Version: " + PcapMajorVersion(pcap));
        System.out.println("Pcap Minor Version: " + PcapMinorVersion(pcap));
    }

    @Test
    public void Test18_PcapIsSwapped() {
        System.out.println("Pcap is swapped: " + PcapIsSwapped(pcap));
    }

    @Test
    public void Test19_PcapSnapshot() {
        System.out.println("Snapshot: " + PcapSnapshot(pcap));
    }

    @Test
    public void Test20_PcapDataLinkValToNamePcapDataLinkValToDescriptionAndPcapDataLinkNameToVal() {
        String dataLinkName = PcapDataLinkValToName(DataLinkType.EN10MB.getValue());
        System.out.println("Data Link Name: " + dataLinkName);
        System.out.println("Data Link Description: " + PcapDataLinkValToDescription(DataLinkType.EN10MB.getValue()));
        System.out.println("Data Link Value: " + PcapDataLinkNameToVal(dataLinkName));
    }

    @Test
    public void Test21_PcapSetNonBlockPcapGetNonBlock() {
        int blocking = PcapGetNonBlock(pcap, errbuf);
        System.out.println("Blocking (Before) : " + blocking);
        System.out.println("Error Buffer      : " + errbuf.toString());
        if ((resultCode = PcapSetNonBlock(pcap, (blocking == 0) ? 1 : 0, errbuf)) != OK) {
            logger.warning("PcapSetNonBlockPcapGetNonBlock:PcapSetNonBlock(): " + errbuf.toString());
            return;
        }
        System.out.println("Blocking (After)  : " + PcapGetNonBlock(pcap, errbuf));
        System.out.println("Error Buffer      : " + errbuf.toString());
        if ((resultCode = PcapSetNonBlock(pcap, (blocking == 0) ? 0 : 1, errbuf)) != OK) {
            logger.warning("PcapSetNonBlockPcapGetNonBlock:PcapSetNonBlock(): " + errbuf.toString());
            return;
        }
        System.out.println("Blocking          : " + PcapGetNonBlock(pcap, errbuf));
        System.out.println("Error Buffer      : " + errbuf.toString());
    }

    @Test
    public void Test22_PcapLookupNet() {
        Inet4Address netp = Inet4Address.valueOf(0);
        Inet4Address maskp = Inet4Address.valueOf(0);
        if ((resultCode = PcapLookupNet(source, netp, maskp, errbuf)) != OK) {
            logger.warning("PcapLookupNet:PcapLookupNet(): " + errbuf.toString());
            return;
        }
        System.out.println("Network Address : " + netp);
        System.out.println("Network Mask    : " + maskp);
    }

    @Test
    public void Test22_PcapCompileNoPcapSetFilterAndPcapLoop() {
        if ((resultCode = PcapCompileNoPcap(snaplen, DataLinkType.EN10MB.getValue(),
                bpfProgram, filter, optimize, maskp.toInt())) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapCompileNoPcap(): " + errbuf.toString());
            return;
        }
        if((resultCode = PcapSetFilter(pcap, bpfProgram)) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapSetFilter(): " + PcapStrError(resultCode));
            return;
        }
        if ((resultCode = PcapLoop(pcap, maxPkt, callback, "This Is User Argument")) != OK) {
            logger.warning("PcapCompileNoPcapSetFilterAndPcapLoop:PcapLoop(): " + PcapStrError(resultCode));
            return;
        }
    }

    @Test
    public void Test23_PcapPError() {
        PcapPError(pcap, "Pcap Error");
    }

    @Test
    public void Test24_PcapSetBufferSize() throws Exception {
//        throw new Exception("Not implemented yet.");
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
    public void Test26_PcapSetDirection() {
        if ((resultCode = PcapSetDirection(pcap, PcapDirection.PCAP_D_IN)) != OK) {
            logger.warning("PcapSetDirection:PcapSetDirection() " + PcapStrError(resultCode));
            return;
        }
    }

    @After
    public void destroy() {
        PcapClose(pcap);
        PcapFreeCode(bpfProgram);
        PcapDumpClose(dumper);
    }

}
