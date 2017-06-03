/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
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
     * @return pcap object.
     */
    public static Pcap PcapOpenLive(PcapIf source, short snaplen, Pcap.PromiscuousMode promisc,
                                    int timeout, StringBuilder errbuf) {
        return Jxnet.PcapOpenLive(source.getName(), snaplen, promisc.getValue(), timeout, errbuf);
    }

    /**
     * Compile a packet filter, converting an high level filtering expression
     * (see Filtering expression syntax) in a program that can be interpreted
     * by the kernel-level filtering engine.
     * @param pcap pcap object.
     * @param fp compiled bfp.
     * @param filter filter.
     * @param optimize optimize.
     * @param netmask netmask.
     * @return -1 on error, 0 otherwise.
     */
    public static int PcapCompile(Pcap pcap, BpfProgram fp, String filter,
                                  BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
        return Jxnet.PcapCompile(pcap, fp, filter, optimize.getValue(), netmask.toInt());
    }

    /**
     * Compile a packet filter without the need of opening an adapter.
     * This function converts an high level filtering expression (see Filtering expression syntax)
     * in a program that can be interpreted by the kernel-level filtering engine.
     * @param snaplen_arp snapshot length.
     * @param linkType datalink type.
     * @param program bpf.
     * @param buf filter.
     * @param optimize optimize.
     * @param netmask netmask.
     * @return -1 on error.
     */
    public static int PcapCompileNoPcap(short snaplen_arp, DataLinkType linkType, BpfProgram program,
                                  String buf, BpfProgram.BpfCompileMode optimize, Inet4Address netmask) {
        return Jxnet.PcapCompileNoPcap(snaplen_arp, linkType.getValue(), program, buf,
                optimize.getValue(), netmask.toInt());
    }

    /**
     * Return the link layer of an adapter.
     * @param pcap pcap object.
     * @return link layer of an adapter.
     */
    public static DataLinkType PcapDatalink(Pcap pcap) {
        return DataLinkType.valueOf((short) Jxnet.PcapDataLink(pcap));
    }

    /**
     * Set the current data link type of the pcap descriptor to the type
     * specified by dlt. -1 is returned on failure.
     * @param pcap pcap object.
     * @param linkType datalink type.
     * @return -1 on error, 0 otherwise.
     */
    public static int PcapSetDatalink(Pcap pcap, DataLinkType linkType) {
        return Jxnet.PcapSetDataLink(pcap, linkType.getValue());
    }

    /**
     * Create a pcap_t structure without starting a capture.
     * @param linkType datalink type.
     * @param snaplen snapshot length.
     * @return pcap object.
     */
    public static Pcap PcapOpenDead(DataLinkType linkType, short snaplen) {
        return Jxnet.PcapOpenDead(linkType.getValue(), snaplen);
    }

}
