/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

import static com.ardikars.jxnet.Core.LookupNetworkInterface;
import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapActivate;
import static com.ardikars.jxnet.Jxnet.PcapCheckActivated;
import static com.ardikars.jxnet.Jxnet.PcapClose;
import static com.ardikars.jxnet.Jxnet.PcapCompile;
import static com.ardikars.jxnet.Jxnet.PcapCompileNoPcap;
import static com.ardikars.jxnet.Jxnet.PcapCreate;
import static com.ardikars.jxnet.Jxnet.PcapDataLink;
import static com.ardikars.jxnet.Jxnet.PcapDumpClose;
import static com.ardikars.jxnet.Jxnet.PcapDumpOpen;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;
import static com.ardikars.jxnet.Jxnet.PcapFreeCode;
import static com.ardikars.jxnet.Jxnet.PcapGetTStampPrecision;
import static com.ardikars.jxnet.Jxnet.PcapLookupNet;
import static com.ardikars.jxnet.Jxnet.PcapLoop;
import static com.ardikars.jxnet.Jxnet.PcapOpenDead;
import static com.ardikars.jxnet.Jxnet.PcapOpenLive;
import static com.ardikars.jxnet.Jxnet.PcapSetDataLink;
import static com.ardikars.jxnet.Jxnet.PcapSetFilter;
import static com.ardikars.jxnet.Jxnet.PcapSetImmediateMode;
import static com.ardikars.jxnet.Jxnet.PcapSetPromisc;
import static com.ardikars.jxnet.Jxnet.PcapSetSnaplen;
import static com.ardikars.jxnet.Jxnet.PcapSetTStampType;
import static com.ardikars.jxnet.Jxnet.PcapSetTimeout;
import static com.ardikars.jxnet.Jxnet.PcapStrError;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.NativeException;
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
        Application.run("CoreTest", "0.0.1", LoaderTest.Initializer.class, new ApplicationContext());
        if ((resultCode = PcapFindAllDevs(alldevsp, errbuf)) != OK) {
            logger.warning("create:PcapFindAllDevs(): " + errbuf.toString());
        }
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
        pcap = PcapCreate(source.getName(), errbuf);
        if (pcap == null) {
            logger.warning("create:PcapCreate(): " + errbuf.toString());
            return;
        }
        if ((resultCode = PcapSetSnaplen(pcap, snaplen)) != OK) {
            logger.warning("create:PcapSetSnaplen(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        if ((resultCode = PcapSetPromisc(pcap, promisc.getValue())) != OK) {
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
            if ((resultCode = PcapSetImmediateMode(pcap, immediate.getValue())) != OK) {
                logger.warning("create:PcapSetImmediateMode(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
        }
        if ((resultCode = PcapCheckActivated(pcap)) == 0) {
            if ((resultCode = PcapActivate(pcap)) != OK) {
                logger.warning("create:PcapActivate(): " + PcapStrError(resultCode));
                PcapClose(pcap);
                return;
            }
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
        Pcap pcap = PcapOpenLive(source.getName(), snaplen, promisc.getValue(), timeout, errbuf);
        if (pcap == null) {
            logger.warning("PcapOpenLiveAndPcapClose:PcapOpenLive(): " + errbuf.toString());
        } else {
            PcapClose(pcap);
        }
    }

    @Test
    public void Test02_PcapCompileAndPcapSetFilter() {
        if ((resultCode = PcapCompile(pcap, bpfProgram, filter, optimize.getValue(), maskp.toInt())) != OK) {
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
        if ((resultCode = PcapCompileNoPcap(snaplen, DataLinkType.EN10MB.getValue(),
                bpfProgram, filter, optimize.getValue(), maskp.toInt())) != OK) {
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
        Pcap pcap = PcapOpenDead(DataLinkType.EN10MB.getValue(), snaplen);
        if (pcap == null) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapOpenDead()");
            return;
        }
        DataLinkType dataLinkType = DataLinkType.valueOf((short) PcapDataLink(pcap));
        System.out.println("Data Link Type (Before): " + dataLinkType);
        if ((resultCode = PcapSetDataLink(pcap, DataLinkType.LINUX_SLL.getValue())) != OK) {
            logger.warning("PcapSetDataLinkPcapDataLinkPcapOpenDeadAndPcapClose:PcapSetDataLink(): " + PcapStrError(resultCode));
            PcapClose(pcap);
            return;
        }
        dataLinkType = DataLinkType.valueOf((short) PcapDataLink(pcap));
        System.out.println("Data Link Type (After): " + dataLinkType);
        PcapClose(pcap);
    }

    @Test
    public void Test05_LookupNetworkInterface() {
        try {
            PcapIf networkInterface = LookupNetworkInterface();
            System.out.println(networkInterface);
        } catch (NativeException e) {
            logger.warning(e.getLocalizedMessage());
        } catch (DeviceNotFoundException e) {
            logger.warning(e.getMessage());
        }
    }

    @Test
    public void Test06_PcapGetTStampPrecisionAndPcapSetTStampPrecision() {
        TimeStampPrecision timestamp = TimeStampPrecision.valueOf(PcapGetTStampPrecision(pcap));
        System.out.println("Time stamp precision (before): " + timestamp);
        if ((resultCode = PcapSetTStampType(pcap, (timestamp == TimeStampPrecision.TIMESTAMP_MICRO)
                ? 1 : 0)) != OK) {
            logger.warning("Timestamp precision not supported by operation system.");
        }
        timestamp = TimeStampPrecision.valueOf(PcapGetTStampPrecision(pcap));
        System.out.println("Time stamp precision (after) : " + timestamp);
    }

    @Test
    public void Test07_PcapListDatalinks() {
//        List<DataLinkType> datalinks = new ArrayList<DataLinkType>();
//        if ((resultCode = PcapListDataLinks0(pcap, datalinks)) < 0) {
//            logger.warning("PcapListDataLinks:PcapListDataLinks(): " + PcapStrError(resultCode));
//            return;
//        }
//        System.out.print("DataLinks:");
//        for (DataLinkType datalink : datalinks) {
//            System.out.print(" " + datalink);
//        }
//        PcapFreeDataLinks0(datalinks);
    }

    @Test
    public void Test08_PcapListTStampTypes() {
//        List<TimeStampType> tsTypes = new ArrayList<TimeStampType>();
//        if ((resultCode = PcapListTStampTypes0(pcap, tsTypes)) < 0) {
//            logger.warning("PcapListTStampTypes:PcapListTStampTypes(): " + PcapStrError(resultCode));
//            return;
//        }
//        System.out.print("Time Stamp Types:");
//        for (TimeStampType tsType : tsTypes) {
//            System.out.print(" " + tsType);
//        }
//        PcapFreeTStampTypes0(tsTypes);
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
