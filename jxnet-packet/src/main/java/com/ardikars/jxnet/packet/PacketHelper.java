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

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.radiotap.RadioTap;
import com.ardikars.jxnet.packet.sll.SLL;
import com.ardikars.jxnet.util.ByteUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class PacketHelper {

    public static <T> int loop(Pcap pcap, int count, PacketListener<T> packetListener, T arg) {
        PcapHandler<PacketListener<T>> handler = (user, h, bytes) -> {
            List<Packet> packets = parseList(pcap.getDataLinkType(), ByteUtils.toByteArray(bytes));
            user.nextPacket(arg, h, packets);
        };
        return Jxnet.PcapLoop(pcap, count, handler, packetListener);
    }

    public static <T> int loop(Pcap pcap, int count, PacketHandler<T> packetHandler, T arg) {
        PcapHandler<PacketHandler<T>> handler = (user, h, bytes) -> {
            Map<Class, Packet> packets = parseMap(pcap.getDataLinkType(), ByteUtils.toByteArray(bytes));
            user.nextPacket(arg, h, packets);
        };
        return Jxnet.PcapLoop(pcap, count, handler, packetHandler);
    }

    public static <T, V extends Packet> int loop(Pcap pcap, int count, PacketProcessor<T, V> handler, T arg) {
        PcapHandler<PacketProcessor<T, V>> callback = (user, h, bytes) -> {
            user.initialize(arg, pcap, h);
            user.decode(ByteUtils.toByteArray(bytes));
        };
        return Jxnet.PcapLoop(pcap, count, callback, handler);
    }

    public static List<Packet> nextList(Pcap pcap, PcapPktHdr pcapPktHdr) {
        ByteBuffer buffer = Jxnet.PcapNext(pcap, pcapPktHdr);
        return parseList(pcap.getDataLinkType(), ByteUtils.toByteArray(buffer));
    }

    public static Map<Class, Packet> nextMap(Pcap pcap, PcapPktHdr pcapPktHdr) {
        ByteBuffer buffer = Jxnet.PcapNext(pcap, pcapPktHdr);
        return parseMap(pcap.getDataLinkType(), ByteUtils.toByteArray(buffer));
    }

    public static int nextExList(Pcap pcap, PcapPktHdr pktHdr, List<Packet> packets) {
        packets.clear();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pcap.getSnapshotLength());
        int ret = Jxnet.PcapNextEx(pcap, pktHdr, buffer);
        List<Packet> pkts = parseList(pcap.getDataLinkType(), ByteUtils.toByteArray(buffer));
        packets.addAll(pkts);
        return ret;
    }

    public static int nextExMap(Pcap pcap, PcapPktHdr pktHdr, HashMap<Class, Packet> packets) {
        packets.clear();
        ByteBuffer buffer = ByteBuffer.allocateDirect(pcap.getSnapshotLength());
        int ret = Jxnet.PcapNextEx(pcap, pktHdr, buffer);
        Map<Class, Packet> pkts = parseMap(pcap.getDataLinkType(), ByteUtils.toByteArray(buffer));
        packets.putAll(pkts);
        return ret;
    }

    public static Map<Class, Packet> parseMap(DataLinkType linkType, byte[] bytes) {
        Map<Class, Packet> packets = new HashMap<Class, Packet>();
        if (bytes == null) {
            packets.clear();
            return packets;
        }
        Packet packet = null;
        switch (linkType) {
            case EN10MB:
                packet = Ethernet.newInstance(bytes);
                packets.put(packet.getClass(), packet);
                break;
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(bytes);
                packets.put(packet.getClass(), packet);
                break;
            case LINUX_SLL:
                packet = SLL.newInstance(bytes);
                packets.put(packet.getClass(), packet);
                break;
            default:
                packet = UnknownPacket.newInstance(bytes);
        }
        while ((packet = packet.getPacket()) != null) {
            packets.put(packet.getClass(), packet);
        }
        return packets;
    }

    public static List<Packet> parseList(DataLinkType linkType, byte[] bytes) {
        List<Packet> packets = new ArrayList<>();
        if (bytes == null) {
            packets.clear();
            return packets;
        }
        Packet packet = null;
        switch (linkType) {
            case EN10MB:
                packet = Ethernet.newInstance(bytes);
                packets.add(packet);
                break;
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(bytes);
                packets.add(packet);
                break;
            case LINUX_SLL:
                packet = SLL.newInstance(bytes);
                packets.add(packet);
                break;
            default:
                packet = UnknownPacket.newInstance(bytes);
        }
        while ((packet = packet.getPacket()) != null) {
            packets.add(packet);
        }
        return packets;
    }

    public static <T> int loop(Pcap pcap, int count, PacketListener<T> packetListener, T arg, Executor executor) {
        PcapHandler<PacketListener> handler = (user, h, bytes) -> {
            executor.execute(() -> {
                List<Packet> packets = parseList(pcap.getDataLinkType(), ByteUtils.toByteArray(bytes));
                user.nextPacket(arg, h, packets);
            });
        };
        return Jxnet.PcapLoop(pcap, count, handler, packetListener);
    }

    public static <T> int loop(Pcap pcap, int count, PacketHandler<T> packetHandler, T arg, Executor executor) {
        PcapHandler<PacketHandler> handler = (user, h, bytes) -> {
            executor.execute(() -> {
                Map<Class, Packet> packets = parseMap(pcap.getDataLinkType(), ByteUtils.toByteArray(bytes));
                user.nextPacket(arg, h, packets);
            });
        };
        return Jxnet.PcapLoop(pcap, count, handler, packetHandler);
    }

    public static <T, V extends Packet> int loop(Pcap pcap, int count, PacketProcessor<T, V> handler, T arg, Executor executor) {
        PcapHandler<PacketProcessor<T, V>> callback = (user, h, bytes) -> {
            executor.execute(() -> {
                user.initialize(arg, pcap, h);
                user.decode(ByteUtils.toByteArray(bytes));
            });
        };
        return Jxnet.PcapLoop(pcap, count, callback, handler);
    }

}
