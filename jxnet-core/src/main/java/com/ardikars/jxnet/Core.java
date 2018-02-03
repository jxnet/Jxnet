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

import com.ardikars.jxnet.util.Validate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.4
 */
abstract class Core {

    protected Core() {

    }

    /**
     * Open a live capture from the network.
     * @param source interface name.
     * @param snaplen snaplen.
     * @param promisc promiscuous mode.
     * @param timeout timeout.
     * @param errbuf error buffer.
     * @return pcap instance.
     * @since 1.1.5
     */
    public static Pcap PcapOpenLive(final PcapIf source, final int snaplen, final PromiscuousMode promisc,
                                    final int timeout, final StringBuilder errbuf) {
        Validate.nullPointer(source);
        Validate.between(0, 65536, snaplen);
        Validate.nullPointer(promisc);
        Validate.illegalArgument(timeout > 0);
        Validate.nullPointer(errbuf);
        return Jxnet.PcapOpenLive(source.getName(), snaplen, promisc.getValue(), timeout, errbuf);
    }

    /**
     * Is used to create a packet capture handle to look at packets on the network.
     * Source is a string that specifies the network device to open;
     * on Linux systems with 2.2 or later kernels, a source argument of "any" or NULL
     * can be used to capture packets from all interfaces.
     *
     * <p>The returned handle must be activated with PcapActivate() before packets can be captured with it;
     * options for the capture, such as promiscuous mode, can be set on the handle before activating it.</p>
     * @param source network device.
     * @param errbuf errof buffer.
     * @return returns a pcap instance on success and NULL on failure. If NULL is returned, errbuf is filled in with an appropriate error message.
     * @since 1.1.5
     */
    public static Pcap PcapCreate(final PcapIf source, final StringBuilder errbuf) {
        Validate.nullPointer(source);
        Validate.nullPointer(errbuf);
        return Jxnet.PcapCreate(source.getName(), errbuf);
    }

    /**
     * Sets whether promiscuous mode should be set on a capture handle when the handle is activated.
     * If promisc is non-zero, promiscuous mode will be set, otherwise it will not be set.
     * @param pcap pcap instance.
     * @param promiscuousMode promisc mode.
     * @return 0 on success.
     * @since 1.1.5
     */
    public static int PcapSetPromisc(final Pcap pcap, final PromiscuousMode promiscuousMode) {
        Validate.nullPointer(pcap);
        Validate.nullPointer(promiscuousMode);
        return Jxnet.PcapSetPromisc(pcap, promiscuousMode.getValue());
    }

    /**
     * Sets whether immediate mode should be set on a capture handle when the handle is activated. If immediate_mode is non-zero,
     * immediate mode will be set, otherwise it will not be set.
     * @param pcap pcap instance.
     * @param immediateMode immediate_mode.
     * @return 0 on success.
     * @since 1.1.5
     */
    public static int PcapSetImmediateMode(final Pcap pcap, final ImmediateMode immediateMode) {
        Validate.nullPointer(pcap);
        Validate.nullPointer(immediateMode);
        return Jxnet.PcapSetImmediateMode(pcap, immediateMode.getValue());
    }

    /**
     * Compile a packet filter, converting an high level filtering expression
     * (see Filtering expression syntax) in a program that can be interpreted
     * by the kernel-level filtering engine.
     * @param pcap pcap instance.
     * @param fp compiled bfp.
     * @param filter filter.
     * @param optimize optimize.
     * @param netmask netmask.
     * @return -1 on error, 0 otherwise.
     * @since 1.1.5
     */
    public static int PcapCompile(final Pcap pcap, final BpfProgram fp, String filter,
                                  final BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
        Validate.nullPointer(pcap);
        Validate.nullPointer(fp);
        filter = Validate.nullPointer(filter, "");
        Validate.nullPointer(optimize);
        netmask = Validate.nullPointer(netmask, Inet4Address.valueOf("255.255.255.0"));
        return Jxnet.PcapCompile(pcap, fp, filter, optimize.getValue(), netmask.toInt());
    }

    /**
     * Compile a packet filter without the need of opening an adapter.
     * This function converts an high level filtering expression (see Filtering expression syntax)
     * in a program that can be interpreted by the kernel-level filtering engine.
     * @param snaplenArg snapshot length.
     * @param linkType datalink type.
     * @param program bpf.
     * @param buf filter.
     * @param optimize optimize.
     * @param netmask netmask.
     * @return -1 on error.
     * @since 1.1.5
     */
    public static int PcapCompileNoPcap(final int snaplenArg, final DataLinkType linkType, final BpfProgram program,
                                  String buf, final BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
        Validate.between(0, 65536, snaplenArg);
        Validate.nullPointer(linkType);
        Validate.nullPointer(program);
        buf = Validate.nullPointer(buf, "");
        Validate.nullPointer(optimize);
        netmask = Validate.nullPointer(netmask, Inet4Address.valueOf("255.255.255.0"));
        return Jxnet.PcapCompileNoPcap(snaplenArg, linkType.getValue(), program, buf,
                optimize.getValue(), netmask.toInt());
    }

    /**
     * Return the link layer of an adapter.
     * @param pcap pcap instance.
     * @return link layer of an adapter.
     * @since 1.1.5
     */
    public static DataLinkType PcapDatalink(final Pcap pcap) {
        Validate.nullPointer(pcap);
        return DataLinkType.valueOf((short) Jxnet.PcapDataLink(pcap));
    }

    /**
     * Set the current data link type of the pcap descriptor to the type
     * specified by dlt. -1 is returned on failure.
     * @param pcap pcap instance.
     * @param linkType datalink type.
     * @return -1 on error, 0 otherwise.
     * @since 1.1.5
     */
    public static int PcapSetDatalink(final Pcap pcap, final DataLinkType linkType) {
        Validate.nullPointer(pcap);
        Validate.nullPointer(linkType);
        return Jxnet.PcapSetDataLink(pcap, linkType.getValue());
    }

    /**
     * Create a pcap instance without starting a capture.
     * @param linkType datalink type.
     * @param snaplen snapshot length.
     * @return pcap instance.
     * @since 1.1.5
     */
    public static Pcap PcapOpenDead(final DataLinkType linkType, final int snaplen) {
        Validate.nullPointer(linkType);
        Validate.between(0, 65536, snaplen);
        return Jxnet.PcapOpenDead(linkType.getValue(), snaplen);
    }

    /**
     * Removes all of the elements.
     * @param pcapIf PcapIf object.
     * @since 1.1.5
     */
    public static void PcapFreeAllDevs(final List<PcapIf> pcapIf) {
        Validate.nullPointer(pcapIf);
        if (!pcapIf.isEmpty()) {
            pcapIf.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param dtlList datalinks.
     */
    public static void PcapFreeDataLinks(final List<Integer> dtlList) {
        Validate.nullPointer(dtlList);
        if (!dtlList.isEmpty()) {
            dtlList.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param tstampTypeList time stamp types.
     */
    public static void PcapFreeTStampTypes(final List<Integer> tstampTypeList) {
        Validate.nullPointer(tstampTypeList);
        if (!tstampTypeList.isEmpty()) {
            tstampTypeList.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param dtlList datalinks.
     */
    public static void PcapFreeDataLinks0(final List<DataLinkType> dtlList) {
        Validate.nullPointer(dtlList);
        if (!dtlList.isEmpty()) {
            dtlList.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param tstampTypeList time stamp types.
     */
    public static void PcapFreeTStampTypes0(final List<TimeStampType> tstampTypeList) {
        Validate.nullPointer(tstampTypeList);
        if (!tstampTypeList.isEmpty()) {
            tstampTypeList.clear();
        }
    }

    /**
     * Return the first connected device to the network.
     * @param errbuf error buffer.
     * @return PcapIf object.
     * @since 1.1.5
     */
    public static PcapIf LookupNetworkInterface(final StringBuilder errbuf) {
        Validate.nullPointer(errbuf);
        final List<PcapIf> pcapIfs = new ArrayList<>();
        PcapIf result = null;
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            return result;
        }
        for (final PcapIf pcapIf : pcapIfs) {
            for (final PcapAddr pcapAddr : pcapIf.getAddresses()) {
                if (pcapAddr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                    Inet4Address address = Inet4Address.valueOf(0);
                    Inet4Address netmask = Inet4Address.valueOf(0);
                    Inet4Address bcastaddr = Inet4Address.valueOf(0);
                    try {
                        address = Inet4Address.valueOf(pcapAddr.getAddr().getData());
                        netmask = Inet4Address.valueOf(pcapAddr.getNetmask().getData());
                        bcastaddr = Inet4Address.valueOf(pcapAddr.getBroadAddr().getData());
                        //Inet4Address dstaddr = Inet4Address.valueOf(pcapAddr.getDstAddr().getData());;
                    } catch (Exception e) {
                        errbuf.append(e.getMessage() + '\n');
                    }
                    if (!address.equals(Inet4Address.ZERO) && !address.equals(Inet4Address.LOCALHOST)
                            && !netmask.equals(Inet4Address.ZERO) && !bcastaddr.equals(Inet4Address.ZERO)) {
                        result = pcapIf;
                        break;
                    }
                }
            }
        }
        errbuf.append("Check your network connection.\n");
        return result;
    }

    /**
     * Select network interface.
     * @param errbuf errbuf.
     * @return PcapIf.
     * @since 1.1.5
     */
    public static PcapIf SelectNetowrkInterface(final StringBuilder errbuf) {
        Validate.nullPointer(errbuf);
        final List<PcapIf> pcapIfs = new ArrayList<PcapIf>();
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            return null;
        }
        int index = 0;
        final StringBuilder sb = new StringBuilder(1000);
        for (final PcapIf pcapIf : pcapIfs) {
            sb.append("NO[").append(++index).append("]\t=> ");
            sb.append("NAME: ").append(pcapIf.getName()).append(" (").append(pcapIf.getDescription()).append(" )\n");
            for (final PcapAddr pcapAddr : pcapIf.getAddresses()) {
                sb.append("\t\tADDRESS: ").append(pcapAddr.getAddr().toString()).append('\n');
            }
        }
        System.out.println(sb.toString());
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));
        PcapIf pcapIf = null;
        index = 0;
        while (index == 0) {
            System.out.print("Select a device number, or enter 'q' to quit -> ");
            try {
                final String input = reader.readLine();
                index = Integer.parseInt(input);
                if (index > pcapIfs.size() || index <= 0) {
                    index = 0;
                } else {
                    pcapIf = pcapIfs.get(index - 1);
                    reader.close();
                }
            } catch (NumberFormatException e) {
                index = 0;
            } catch (IOException e) {
                index = -1;
                errbuf.append(e.getMessage());
                try {
                    reader.close();
                } catch (IOException e1) {
                    errbuf.append(e1.getMessage());
                }
            }
        }
        return pcapIf;
    }

    /**
     * Get list of datalinks.
     * @param pcap pcap instance.
     * @param dataLinkTypes datalinks.
     * @return list of datalinks.
     */
    public static int PcapListDataLinks0(final Pcap pcap, final List<DataLinkType> dataLinkTypes) {
        final List<Integer> datalinks = new ArrayList<Integer>();
        final int result = Jxnet.PcapListDataLinks(pcap, datalinks);
        for (final Integer datalink : datalinks) {
            dataLinkTypes.add(DataLinkType.valueOf(datalink.shortValue()));
        }
        return result;
    }

    /**
     * Get link of time stamp types.
     * @param pcap pcap instance.
     * @param timeStampTypes time stamp types.
     * @return time stamp types.
     */
    public static int PcapListTStampTypes0(final Pcap pcap, final List<TimeStampType> timeStampTypes) {
        final List<Integer> tsTypes = new ArrayList<Integer>();
        final int result = Jxnet.PcapListTStampTypes(pcap, tsTypes);
        for (final Integer tsType : tsTypes) {
            timeStampTypes.add(TimeStampType.valueOf(tsType.intValue()));
        }
        return result;
    }

    /**
     * Get the time stamp precision returned in captures.
     * @param pcap pcap instance.
     * @return the precision of the time stamp returned in packet captures on the pcap descriptor.
     */
    public static TimeStampPrecision PcapGetTStampPrecision0(final Pcap pcap) {
        final int timeStampPrecision = Jxnet.PcapGetTStampPrecision(pcap);
        return TimeStampPrecision.valueOf(timeStampPrecision);
    }

    /**
     * Set the time stamp type returned in captures.
     * @param pcap pcap.
     * @param timeStampPrecision time stamp type.
     * @return 0 on success if specified time type precision is expected to be supported by operating system.
     */
    public static int PcapSetTStampType(final Pcap pcap, final TimeStampPrecision timeStampPrecision) {
        return Jxnet.PcapSetTStampPrecision(pcap, timeStampPrecision.getValue());
    }

}
