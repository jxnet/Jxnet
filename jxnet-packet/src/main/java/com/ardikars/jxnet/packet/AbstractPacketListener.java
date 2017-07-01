package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.JxnetException;
import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.radiotap.RadioTap;
import com.ardikars.jxnet.packet.sll.SLL;

import java.lang.reflect.ParameterizedType;
import java.nio.ByteBuffer;

public abstract class AbstractPacketListener<T, V extends Packet> implements Encoder<byte[], Packet>, Decoder<V, byte[]> {

    private int packetNumber;
    private Pcap pcap;
    private T userArgument;
    private PcapPktHdr pcapPktHdr;

    protected void initialize(int packetNumber, T userArgument, Pcap pcap, PcapPktHdr pktHdr) {
        this.packetNumber = packetNumber;
        this.pcap = pcap;
        this.pcapPktHdr = pktHdr;
        this.userArgument = userArgument;
    }

    public abstract void nextPacket(T arg, PcapPktHdr pcapPktHdr, V packet);

    public int getPacketNumber() {
        return this.packetNumber;
    }

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
            return data.toBytes();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public V decode(byte[] data) {
        Packet packet;
        String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        switch (pcap.getDataLinkType()) {
            case EN10MB:
                packet = Ethernet.newInstance(data);
                while (packet != null) {
                    if (packet.getClass() == clazz) {
                        nextPacket(this.userArgument, this.pcapPktHdr, (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(data);
                while (packet != null) {
                    if (packet.getClass() == clazz) {
                        nextPacket(this.userArgument, this.pcapPktHdr, (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            case LINUX_SLL:
                packet = SLL.newInstance(data);
                while (packet != null) {
                    if (packet.getClass() == clazz) {
                        nextPacket(this.userArgument, this.pcapPktHdr, (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            default:
                nextPacket(this.userArgument, this.pcapPktHdr, null);
                return null;
        }
    }

}
