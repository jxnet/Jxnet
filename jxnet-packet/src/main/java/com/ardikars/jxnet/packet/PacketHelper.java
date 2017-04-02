package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.util.FormatUtils;
import com.ardikars.jxnet.packet.ethernet.Ethernet;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static com.ardikars.jxnet.Jxnet.*;

public class PacketHelper {

    public static <T> int loop(Pcap pcap, int count, PacketHandler<T> handler, T arg) {
        DataLinkType datalinkType = DataLinkType.valueOf((short)PcapDataLink(pcap));
        PcapHandler<PacketHandler<T>> callback = (tPacketHandler, pcapPktHdr, buffer) -> {
            if (pcapPktHdr == null || buffer == null) return;
            tPacketHandler.nextPacket(arg, pcapPktHdr, parsePacket(datalinkType, FormatUtils.toBytes(buffer)));
        };
        return PcapLoop(pcap, count, callback, handler);
    }

    public static Map<Class, Packet> next(Pcap pcap, PcapPktHdr pcapPktHdr) {
        DataLinkType datalinkType = DataLinkType.valueOf((short) PcapDataLink(pcap));
        ByteBuffer buffer = PcapNext(pcap, pcapPktHdr);
        if (buffer == null) return null;
        return parsePacket(datalinkType, FormatUtils.toBytes(buffer));
    }

    public static int nextEx(Pcap pcap, PcapPktHdr pktHdr, Map<Class, Packet> packets) {
        packets.clear();
        DataLinkType datalinkType = DataLinkType.valueOf((short)PcapDataLink(pcap));
        ByteBuffer buffer = ByteBuffer.allocateDirect(3600);
        int ret = PcapNextEx(pcap, pktHdr, buffer);
        if (pktHdr == null || buffer == null) return -1;
        Map pkts = parsePacket(datalinkType, FormatUtils.toBytes(buffer));
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
            case IEEE802:
                break;
        }
        while ((packet = packet.getPacket()) != null) {
            pkts.put(packet.getClass(), packet);
        }
        return pkts;
    }

}
