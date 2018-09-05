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

package com.ardikars.jxnet.example;

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.util.Hexs;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.ImmediateMode;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.PcapTimestampPrecision;
import com.ardikars.jxnet.PcapTimestampType;
import com.ardikars.jxnet.PromiscuousMode;
import com.ardikars.jxnet.RadioFrequencyMonitorMode;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.exception.DeviceNotFoundException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {

    public static final Logger LOGGER = Logger.getLogger(Application.class.getName());

    public static final String source = null;
    public static final int SNAPLEN = 65535;
    public static final PromiscuousMode PROMISCUOUS = PromiscuousMode.PRIMISCUOUS;
    public static final int TIMEOUT = 2000;
    public static final ImmediateMode IMMEDIATE = ImmediateMode.IMMEDIATE;
    public static final PcapTimestampType TIMESTAMP_TYPE = PcapTimestampType.HOST;
    public static final PcapTimestampPrecision TIMESTAMP_PRECISION = PcapTimestampPrecision.MICRO;
    public static final RadioFrequencyMonitorMode RFMON = RadioFrequencyMonitorMode.NON_RFMON;
    public static final boolean nonBlock = false;
    public static final DataLinkType LINK_TYPE = DataLinkType.EN10MB;
    public static final String FILE_NAME = "../gradle/resources/pcap/icmp.pcap";

    public static final Pcap.PcapType PCAP_TYPE = Pcap.PcapType.LIVE;

    public static final int MAX_PACKET = -1;

	public static final int WAIT_TIME_FOR_THREAD_TERMINATION = 10000;

    /**
     * Main method.
     * @param args args.
     * @throws InterruptedException interrupted exception.
     */
    public static void main(String[] args) throws InterruptedException {
        StringBuilder errbuf = new StringBuilder();
        try {
            PcapIf pcapIf = pcapIf(errbuf);
            Pcap.Builder builder = new Pcap.Builder()
                    .source(pcapIf.getName())
                    .snaplen(SNAPLEN)
                    .promiscuousMode(PROMISCUOUS)
                    .timeout(TIMEOUT)
                    .immediateMode(IMMEDIATE)
                    .timestampType(TIMESTAMP_TYPE)
                    .timestampPrecision(TIMESTAMP_PRECISION)
                    .rfmon(RFMON)
                    .enableNonBlock(nonBlock)
                    .dataLinkType(LINK_TYPE)
                    .fileName(FILE_NAME)
                    .errbuf(errbuf)
                    .pcapType(PCAP_TYPE);
            com.ardikars.jxnet.Application.run("application", "Application", "", builder);
            Context context = com.ardikars.jxnet.Application.getApplicationContext();
            final ExecutorService pool = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    pool.shutdownNow();
                }
            });
            context.pcapLoop(MAX_PACKET, new PcapHandler<String>() {
                @Override
                public void nextPacket(String user, PcapPktHdr pktHdr, ByteBuffer buffer) {
                    byte[] bytes = new byte[buffer.capacity()];
                    buffer.get(bytes, 0, bytes.length);
                    String hexDump = Hexs.toPrettyHexDump(bytes);
                    LOGGER.info("User argument : " + user);
                    LOGGER.info("Packet header : " + pktHdr);
                    LOGGER.info("Packet buffer : \n" + hexDump);
                }
            }, "Jxnet!", pool);
			pool.shutdown();
			pool.awaitTermination(WAIT_TIME_FOR_THREAD_TERMINATION, TimeUnit.MICROSECONDS);
        } catch (DeviceNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }

    }

    /**
     * Get default pcap interface.
     * @param errbuf error buffer.
     * @return returns PcapIf.
     * @throws DeviceNotFoundException device not found exception.
     */
    public static PcapIf pcapIf(StringBuilder errbuf) throws DeviceNotFoundException {
        String source = Application.source;
        List<PcapIf> alldevsp = new ArrayList<>();
        if (PcapFindAllDevs(alldevsp, errbuf) != OK && LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.warning("Error: {}" + errbuf.toString());
        }
        if (source == null || source.isEmpty()) {
            for (PcapIf dev : alldevsp) {
                for (PcapAddr addr : dev.getAddresses()) {
                    if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET && addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            return dev;
                        }
                    }
                }
            }
        } else {
            for (PcapIf dev : alldevsp) {
                if (dev.getName().equals(source)) {
                    return dev;
                }
            }
        }
        throw new DeviceNotFoundException("No device connected to the network.");
    }

}
