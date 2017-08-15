package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.PcapPktHdr;

import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public interface PacketListener<T> {

    /**
     * Next available packet.
     * @param arg user argument.
     * @param pktHdr PcapPktHdr.
     * @param packets packets.
     */
    void nextPacket(T arg, PcapPktHdr pktHdr, List<Packet> packets);

}
