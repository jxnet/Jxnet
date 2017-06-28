package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.packet.ethernet.Ethernet;
import com.ardikars.jxnet.packet.radiotap.RadioTap;
import com.ardikars.jxnet.packet.sll.SLL;
import com.ardikars.jxnet.util.Decoder;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractPacketListener<T, V extends Packet> implements Decoder<V, byte[]> {

    private Pcap pcap;
    private PcapPktHdr pcapPktHdr;
    private T arg;

    public abstract void nextPacket(T arg, PcapPktHdr pcapPktHdr, V packet);

    public void send(Packet packet) {
        Jxnet.PcapSendPacket(getPcap(), packet.toBytes());
    }

    public Pcap getPcap() {
        return pcap;
    }

    public void setPcap(Pcap pcap) {
        this.pcap = pcap;
    }

    public PcapPktHdr getPcapPktHdr() {
        return pcapPktHdr;
    }

    public void setPcapPktHdr(PcapPktHdr pcapPktHdr) {
        this.pcapPktHdr = pcapPktHdr;
    }

    public T getArg() {
        return arg;
    }

    public void setArg(T arg) {
        this.arg = arg;
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
                        nextPacket(getArg(), getPcapPktHdr(), (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            case IEEE802_11_RADIO:
                packet = RadioTap.newInstance(data);
                while (packet != null) {
                    if (packet.getClass() == clazz) {
                        nextPacket(getArg(), getPcapPktHdr(), (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            case LINUX_SLL:
                packet = SLL.newInstance(data);
                while (packet != null) {
                    if (packet.getClass() == clazz) {
                        nextPacket(getArg(), getPcapPktHdr(), (V) packet);
                        return (V) packet;
                    }
                    packet = packet.getPacket();
                }
            default:
                nextPacket(getArg(), getPcapPktHdr(), null);
                return null;
        }
    }

}
