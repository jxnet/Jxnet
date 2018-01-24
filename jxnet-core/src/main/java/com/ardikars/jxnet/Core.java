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
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.4
 */
class Core {

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
    public static Pcap PcapOpenLive(PcapIf source, int snaplen, PromiscuousMode promisc,
                                    int timeout, StringBuilder errbuf) {
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
     * The returned handle must be activated with PcapActivate() before packets can be captured with it;
     * options for the capture, such as promiscuous mode, can be set on the handle before activating it.
     * @param source network device.
     * @param errbuf errof buffer.
     * @return returns a pcap instance on success and NULL on failure. If NULL is returned, errbuf is filled in with an
     * appropriate error message.
     * @since 1.1.5
     */
    public static Pcap PcapCreate(PcapIf source, StringBuilder errbuf) {
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
    public static int PcapSetPromisc(Pcap pcap, PromiscuousMode promiscuousMode) {
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
    public static int PcapSetImmediateMode(Pcap pcap, ImmediateMode immediateMode) {
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
    public static int PcapCompile(Pcap pcap, BpfProgram fp, String filter,
                                  BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
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
     * @param snaplen_arg snapshot length.
     * @param linkType datalink type.
     * @param program bpf.
     * @param buf filter.
     * @param optimize optimize.
     * @param netmask netmask.
     * @return -1 on error.
     * @since 1.1.5
     */
    public static int PcapCompileNoPcap(int snaplen_arg, DataLinkType linkType, BpfProgram program,
                                  String buf, BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
        Validate.between(0, 65536, snaplen_arg);
        Validate.nullPointer(linkType);
        Validate.nullPointer(program);
        buf = Validate.nullPointer(buf, "");
        Validate.nullPointer(optimize);
        netmask = Validate.nullPointer(netmask, Inet4Address.valueOf("255.255.255.0"));
        return Jxnet.PcapCompileNoPcap(snaplen_arg, linkType.getValue(), program, buf,
                optimize.getValue(), netmask.toInt());
    }

    /**
     * Return the link layer of an adapter.
     * @param pcap pcap instance.
     * @return link layer of an adapter.
     * @since 1.1.5
     */
    public static DataLinkType PcapDatalink(Pcap pcap) {
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
    public static int PcapSetDatalink(Pcap pcap, DataLinkType linkType) {
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
    public static Pcap PcapOpenDead(DataLinkType linkType, int snaplen) {
        Validate.nullPointer(linkType);
        Validate.between(0, 65536, snaplen);
        return Jxnet.PcapOpenDead(linkType.getValue(), snaplen);
    }

    /**
     * Removes all of the elements.
     * @param pcapIf PcapIf object.
     * @since 1.1.5
     */
    public static void PcapFreeAllDevs(List<PcapIf> pcapIf) {
        Validate.nullPointer(pcapIf);
        if (!pcapIf.isEmpty()) {
            pcapIf.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param dtl_list datalinks.
     */
    public static void PcapFreeDataLinks(List<Integer> dtl_list) {
        Validate.nullPointer(dtl_list);
        if (!dtl_list.isEmpty()) {
            dtl_list.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param tstamp_type_list time stamp types.
     */
    public static void PcapFreeTStampTypes(List<Integer> tstamp_type_list) {
        Validate.nullPointer(tstamp_type_list);
        if (!tstamp_type_list.isEmpty()) {
            tstamp_type_list.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param dtl_list datalinks.
     */
    public static void PcapFreeDataLinks0(List<DataLinkType> dtl_list) {
        Validate.nullPointer(dtl_list);
        if (!dtl_list.isEmpty()) {
            dtl_list.clear();
        }
    }

    /**
     * Removes all of the elements.
     * @param tstamp_type_list time stamp types.
     */
    public static void PcapFreeTStampTypes0(List<TimeStampType> tstamp_type_list) {
        Validate.nullPointer(tstamp_type_list);
        if (!tstamp_type_list.isEmpty()) {
            tstamp_type_list.clear();
        }
    }

    /**
     * Return the first connected device to the network.
     * @param errbuf error buffer.
     * @return PcapIf object.
     * @since 1.1.5
     */
    public static PcapIf LookupNetworkInterface(StringBuilder errbuf) {
        Validate.nullPointer(errbuf);
        List<PcapIf> pcapIfs = new ArrayList<>();
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            return null;
        }
        for (PcapIf pcapIf : pcapIfs) {
            for (PcapAddr pcapAddr : pcapIf.getAddresses()) {
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
                        errbuf.append(e.getMessage() + "\n");
                    }
                    if (!address.equals(Inet4Address.ZERO) && !address.equals(Inet4Address.LOCALHOST)
                            && !netmask.equals(Inet4Address.ZERO) && !bcastaddr.equals(Inet4Address.ZERO)) {
                        return pcapIf;
                    }
                }
            }
        }
        errbuf.append("Check your network connection.\n");
        return null;
    }

    /**
     * Select network interface.
     * @param errbuf errbuf.
     * @return PcapIf.
     * @since 1.1.5
     */
    public static PcapIf SelectNetowrkInterface(StringBuilder errbuf) {
        Validate.nullPointer(errbuf);
        List<PcapIf> pcapIfs = new ArrayList<PcapIf>();
        if (Jxnet.PcapFindAllDevs(pcapIfs, errbuf) != Jxnet.OK) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i=0;
        for (PcapIf pcapIf : pcapIfs) {
            sb.append("NO[" + ++i +"]\t=> ");
            sb.append("NAME: " + pcapIf.getName() + " (" + pcapIf.getDescription() + " )\n");
            for (PcapAddr pcapAddr : pcapIf.getAddresses()) {
                sb.append("\t\tADDRESS: " + pcapAddr.getAddr().toString() + "\n");
            }
        }
        System.out.println(sb.toString());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Select a device number, or enter 'q' to quit -> ");
            String input;
            try {
                input = reader.readLine();
                i = Integer.parseInt(input);
            } catch (IOException e) {
                errbuf.append(e.toString());
                return null;
            }
            return pcapIfs.get(i-1);
        }
    }

    /**
     * Get list of datalinks.
     * @param pcap pcap instance.
     * @param dataLinkTypes datalinks.
     * @return list of datalinks.
     */
    public static int PcapListDataLinks0(Pcap pcap, List<DataLinkType> dataLinkTypes) {
        List<Integer> datalinks = new ArrayList<Integer>();
        int result = Jxnet.PcapListDataLinks(pcap, datalinks);
        for (Integer datalink : datalinks) {
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
    public static int PcapListTStampTypes0(Pcap pcap, List<TimeStampType> timeStampTypes) {
        List<Integer> tsTypes = new ArrayList<Integer>();
        int result = Jxnet.PcapListTStampTypes(pcap, tsTypes);
        for (Integer tsType : tsTypes) {
            timeStampTypes.add(TimeStampType.valueOf(tsType.intValue()));
        }
        return result;
    }

    /**
     * Get the time stamp precision returned in captures.
     * @param pcap pcap instance.
     * @return the precision of the time stamp returned in packet captures on the pcap descriptor.
     */
    public static TimeStampPrecision PcapGetTStampPrecision0(Pcap pcap) {
        int timeStampPrecision = Jxnet.PcapGetTStampPrecision(pcap);
        return TimeStampPrecision.valueOf(timeStampPrecision);
    }

    /**
     * Set the time stamp type returned in captures.
     * @param pcap pcap.
     * @param timeStampPrecision time stamp type.
     * @return 0 on success if specified time type precision is expected to be supported
     * by operating system.
     */
    public static int PcapSetTStampType(Pcap pcap, TimeStampPrecision timeStampPrecision) {
        return Jxnet.PcapSetTStampPrecision(pcap, timeStampPrecision.getValue());
    }

}
