/*
package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.JxnetException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.packet.ethernet.Ethernet;

import java.nio.ByteBuffer;
import java.util.Map;

public abstract class PacketProcessor<T, V extends Packet> implements Encoder<byte[], Packet>, Decoder<V, byte[]> {

    private Pcap pcap;
    private T userArgument;
    private PcapPktHdr pcapPktHdr;

    protected void initialize(T userArgument, Pcap pcap, PcapPktHdr pktHdr) {
        this.pcap = pcap;
        this.pcapPktHdr = pktHdr;
        this.userArgument = userArgument;
    }

    public abstract void nextPacket(T arg, PcapPktHdr pcapPktHdr, V packet);

    public void exceptionCaught(Exception e) {
        e.printStackTrace();
    }

    public void sendPacket(Packet packet) {
        byte[] data = encode(packet);
        ByteBuffer buffer = ByteBuffer.allocateDirect(data.length);
        buffer.put(data);
        if (!this.pcap.isClosed()) {
            if (Jxnet.PcapSendPacket(this.pcap, buffer, buffer.capacity()) != Jxnet.OK) {
                exceptionCaught(new JxnetException(Jxnet.PcapGetErr(this.pcap)));
            }
        } else {
            exceptionCaught(new PcapCloseException());
        }
    }

    public void stop() {
        Jxnet.PcapBreakLoop(this.pcap);
    }

    public PcapStat status() {
        PcapStat stat = new PcapStat();
        Jxnet.PcapStats(this.pcap, stat);
        return stat;
    }

    @Override
    public byte[] encode(Packet data) {
        if (data == null) {
            return null;
        } else {
            ByteBuffer buffer = data.buffer();
            byte[] bytes = new byte[buffer.capacity()];
            buffer.get(bytes, 0, bytes.length);
            return bytes;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V decode(byte[] data) {
        // added more bugs
        Class clazz = null;
        try {
            clazz = Class.forName(Ethernet.class.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Map<Class, Packet> packets = PacketListener.parseMap(pcap.getDataLinkType(), data);
        Packet packet = packets.get(clazz);
        if (packet == null) {
            packet = UnknownPacket.newInstance(data);
            nextPacket(userArgument, pcapPktHdr, (V) packet);
        } else {
            nextPacket(userArgument, pcapPktHdr, (V) packet);
        }
        return (V) packet;
    }

}
*/
