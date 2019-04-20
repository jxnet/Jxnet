/**
 * Copyright (C) 2015-2018 Jxnet
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

import com.ardikars.common.memory.Memories;
import com.ardikars.common.memory.Memory;
import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.common.util.CommonConsumer;
import com.ardikars.common.util.Hexs;
import com.ardikars.common.util.Platforms;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.ImmediateMode;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.PcapTimestampPrecision;
import com.ardikars.jxnet.PcapTimestampType;
import com.ardikars.jxnet.PromiscuousMode;
import com.ardikars.jxnet.RadioFrequencyMonitorMode;
import com.ardikars.jxnet.RawPcapHandler;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.context.Context;
import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;
import com.ardikars.jxpacket.common.AbstractPacket;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.common.layer.DataLinkLayer;
import com.ardikars.jxpacket.common.layer.NetworkLayer;
import com.ardikars.jxpacket.common.layer.TransportLayer;
import com.ardikars.jxpacket.common.util.PacketIterator;
import com.ardikars.jxpacket.core.arp.Arp;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import com.ardikars.jxpacket.core.ethernet.Vlan;
import com.ardikars.jxpacket.core.icmp.Icmp4;
import com.ardikars.jxpacket.core.icmp.Icmp6;
import com.ardikars.jxpacket.core.ip.Ip4;
import com.ardikars.jxpacket.core.ip.Ip6;
import com.ardikars.jxpacket.core.ip.ip6.Authentication;
import com.ardikars.jxpacket.core.ip.ip6.DestinationOptions;
import com.ardikars.jxpacket.core.ip.ip6.Fragment;
import com.ardikars.jxpacket.core.ip.ip6.HopByHopOptions;
import com.ardikars.jxpacket.core.ip.ip6.Routing;
import com.ardikars.jxpacket.core.tcp.Tcp;
import com.ardikars.jxpacket.core.udp.Udp;

import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
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
    public static final PromiscuousMode PROMISCUOUS = PromiscuousMode.PROMISCUOUS;
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

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

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
            com.ardikars.jxnet.context.Application.run("application", "Application", "", builder);
            final Context context = com.ardikars.jxnet.context.Application.getApplicationContext();
            LOGGER.info("Network Interface : " + pcapIf.getName());
            LOGGER.info("Addresses         : ");
            for (PcapAddr addr : pcapIf.getAddresses()) {
                if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    LOGGER.info("\tAddress       : " + Inet4Address.valueOf(addr.getAddr().getData()));
                    LOGGER.info("\tNetwork       : " + Inet4Address.valueOf(addr.getNetmask().getData()));
                }
            }
            if (Platforms.isWindows()) {
                try {
                    byte[] hardwareAddress = Jxnet.FindHardwareAddress(pcapIf.getName());
                    LOGGER.info("\tMAC Address   : " + MacAddress.valueOf(hardwareAddress));
                } catch (PlatformNotSupportedException e) {
                    LOGGER.warning(e.getMessage());
                } catch (DeviceNotFoundException e) {
                    LOGGER.warning(e.getMessage());
                }
            } else {
                try {
                    LOGGER.info("\tMAC Address   : " + MacAddress.fromNicName(pcapIf.getName()));
                } catch (SocketException e) {
                    LOGGER.warning(e.getMessage());
                }
            }
            final ExecutorService pool = Executors.newCachedThreadPool();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    pool.shutdownNow();
                }
            });

            final int linktype = context.pcapDataLink().getValue();

            context.pcapLoop(MAX_PACKET, new RawPcapHandler<String>() {

                @Override
                public void nextPacket(String user, int capLen, int len, int tvSec, long tvUsec, long memoryAddress) {
                    Memory buffer = Memories.wrap(memoryAddress, len);
                    buffer.writerIndex(buffer.capacity());
                    Packet packet;
                    if (linktype == 1) {
                        packet = Ethernet.newPacket(buffer);
                    } else {
                        packet = UnknownPacket.newPacket(buffer);
                    }
                    print(Tuple.of(PcapPktHdr.newInstance(capLen, len, tvSec, tvUsec), packet));
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
        List<PcapIf> alldevsp = new ArrayList<PcapIf>();
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

    private static void print(Pair<PcapPktHdr, Packet> packet) {
        Iterator<Packet> iterator = packet.getRight().iterator();
        LOGGER.info("Pcap packet header : " + packet.getLeft());
        LOGGER.info("Packet header      : ");
        while (iterator.hasNext()) {
            LOGGER.info(iterator.next().toString());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    static {
        DataLinkLayer.register(DataLinkLayer.EN10MB, new Ethernet.Builder());
        NetworkLayer.register(NetworkLayer.ARP, new Arp.Builder());
        NetworkLayer.register(NetworkLayer.IPV4, new Ip4.Builder());
        NetworkLayer.register(NetworkLayer.IPV6, new Ip6.Builder());
        NetworkLayer.register(NetworkLayer.DOT1Q_VLAN_TAGGED_FRAMES, new Vlan.Builder());
        NetworkLayer.register(NetworkLayer.IEEE_802_1_AD, new Vlan.Builder());
        TransportLayer.register(TransportLayer.TCP, new Tcp.Builder());
        TransportLayer.register(TransportLayer.UDP, new Udp.Builder());
        TransportLayer.register(TransportLayer.ICMP, new Icmp4.Builder());
        TransportLayer.register(TransportLayer.IPV6, new Ip6.Builder());
        TransportLayer.register(TransportLayer.IPV6_ICMP, new Icmp6.Builder());
        TransportLayer.register(TransportLayer.IPV6_AH, new Authentication.Builder());
        TransportLayer.register(TransportLayer.IPV6_DSTOPT, new DestinationOptions.Builder());
        TransportLayer.register(TransportLayer.IPV6_ROUTING, new Routing.Builder());
        TransportLayer.register(TransportLayer.IPV6_FRAGMENT, new Fragment.Builder());
        TransportLayer.register(TransportLayer.IPV6_HOPOPT, new HopByHopOptions.Builder());
    }

}
