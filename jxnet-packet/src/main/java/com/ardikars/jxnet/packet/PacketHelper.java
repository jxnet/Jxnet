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

package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.annotation.Type;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.radiotap.RadioTap;
import com.ardikars.jxnet.packet.sll.SLL;
import com.ardikars.jxnet.util.ByteUtils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.ardikars.jxnet.Jxnet.*;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
@Deprecated
public class PacketHelper {

    public static <T> int loop(Pcap pcap, int count, PacketHandler<T> handler, T arg) {
        DataLinkType datalinkType = pcap.getDataLinkType();
        PcapHandler<PacketHandler<T>> callback = (tPacketHandler, pcapPktHdr, buffer) -> {
            if (pcapPktHdr == null || buffer == null) return;
            tPacketHandler.nextPacket(arg, pcapPktHdr, parsePacket(datalinkType, ByteUtils.toByteArray(buffer)));
        };
        return PcapLoop(pcap, count, callback, handler);
    }

    public static Map<Class, Packet> next(Pcap pcap, PcapPktHdr pcapPktHdr) {
        DataLinkType datalinkType = pcap.getDataLinkType();
        ByteBuffer buffer = PcapNext(pcap, pcapPktHdr);
        if (buffer == null) return null;
        return parsePacket(datalinkType, ByteUtils.toByteArray(buffer));
    }

    public static int nextEx(Pcap pcap, PcapPktHdr pktHdr, HashMap<Class, Packet> packets) {
        packets.clear();
        DataLinkType datalinkType = pcap.getDataLinkType();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pcap.getSnapshotLength());
        int ret = PcapNextEx(pcap, pktHdr, buffer);
        if (pktHdr == null || buffer == null) return -1;
        Map<Class, Packet> pkts = parsePacket(datalinkType, ByteUtils.toByteArray(buffer));
        packets.putAll(pkts);
        return ret;
    }

    private static Packet parsePacket(DataLinkType dataLinkType, byte[] bytes, Type type) {
        Packet packet = null;
        Packet result = null;
        int packetNumber = 1;
        int typeNumber = 0;
        if (type != null) {
            typeNumber = type.number();
        }
        switch (dataLinkType) {
            case EN10MB:
                packet = Ethernet.newInstance(bytes);
                if (type == null) {
                    return packet;
                }
                while (packet != null) {
                    if (packet.getClass() == type.value()) {
                        if (typeNumber == 0) {
                            result = packet;
                        } else {
                            if (packetNumber == typeNumber) {
                                return packet;
                            } else {
                                packetNumber = packetNumber + 1;
                            }
                        }
                    }
                    packet = packet.getPacket();
                }
                return result;
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(bytes);
                if (type == null) {
                    return packet;
                }
                while (packet != null) {
                    if (packet.getClass() == type.value()) {
                        if (typeNumber == 0) {
                            result = packet;
                        } else {
                            if (packetNumber == typeNumber) {
                                return packet;
                            } else {
                                packetNumber = packetNumber + 1;
                            }
                        }
                    }
                    packet = packet.getPacket();
                }
                return result;
            case LINUX_SLL:
                packet = SLL.newInstance(bytes);
                if (type == null) {
                    return packet;
                }
                while (packet != null) {
                    if (packet.getClass() == type.value()) {
                        if (typeNumber == 0) {
                            result = packet;
                        } else {
                            if (packetNumber == typeNumber) {
                                return packet;
                            } else {
                                packetNumber = packetNumber + 1;
                            }
                        }
                    }
                    packet = packet.getPacket();
                }
                return result;
            default:
                return UnknownPacket.newInstance(bytes);
        }
    }

    private static Map<Class, Packet> parsePacket(DataLinkType datalinkType, byte[] bytes) {
        Map<Class, Packet> pkts = new HashMap<Class, Packet>();
        Packet packet = null;
        switch (datalinkType) {
            case EN10MB:
                packet = Ethernet.newInstance(bytes);
                pkts.put(packet.getClass(), packet);
                break;
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(bytes);
                pkts.put(packet.getClass(), packet);
                break;
            case LINUX_SLL:
                packet = SLL.newInstance(bytes);
                pkts.put(packet.getClass(), packet);
                break;
            default:
                packet = UnknownPacket.newInstance(bytes);
        }
        while ((packet = packet.getPacket()) != null) {
            pkts.put(packet.getClass(), packet);
        }
        return pkts;
    }

}
