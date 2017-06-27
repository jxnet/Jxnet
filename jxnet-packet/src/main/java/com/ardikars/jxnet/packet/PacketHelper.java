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
import com.ardikars.jxnet.packet.radiotap.RadioTap;
import com.ardikars.jxnet.packet.sll.SLL;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.util.ByteUtils;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.ardikars.jxnet.Jxnet.*;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class PacketHelper {

    public static <T> int loop(Pcap pcap, int count, PacketHandler<T> handler, T arg) {
        DataLinkType datalinkType = DataLinkType.valueOf((short)PcapDataLink(pcap));
        PcapHandler<PacketHandler<T>> callback = (tPacketHandler, pcapPktHdr, buffer) -> {
            if (pcapPktHdr == null || buffer == null) return;
            tPacketHandler.nextPacket(arg, pcapPktHdr, parsePacket(datalinkType, ByteUtils.toByteArray(buffer)));
        };
        return PcapLoop(pcap, count, callback, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Packet> int loop(Pcap pcap, int count, PacketDecoder<T> decoder) {
        try {
            final String className = ((ParameterizedType) decoder.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            final Class<T> clazz = (Class<T>) Class.forName(className);
            PacketHandler<PacketDecoder<T>> callback = (coder, pktHdr, packets) -> {
                if (clazz != null) {
                    Packet packet = packets.get(clazz);
                    if (packet != null) {
                        coder.read(pcap, pktHdr, (T) packet);
                    }
                }
            };
            return PacketHelper.loop(pcap, count, callback, decoder);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static Map<Class, Packet> next(Pcap pcap, PcapPktHdr pcapPktHdr) {
        DataLinkType datalinkType = DataLinkType.valueOf((short) PcapDataLink(pcap));
        ByteBuffer buffer = PcapNext(pcap, pcapPktHdr);
        if (buffer == null) return null;
        return parsePacket(datalinkType, ByteUtils.toByteArray(buffer));
    }

    public static int nextEx(Pcap pcap, PcapPktHdr pktHdr, HashMap<Class, Packet> packets) {
        packets.clear();
        DataLinkType datalinkType = DataLinkType.valueOf((short)PcapDataLink(pcap));
        ByteBuffer buffer = ByteBuffer.allocateDirect(3600);
        int ret = PcapNextEx(pcap, pktHdr, buffer);
        if (pktHdr == null || buffer == null) return -1;
        Map<Class, Packet> pkts = parsePacket(datalinkType, ByteUtils.toByteArray(buffer));
        packets.putAll(pkts);
        return ret;
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
