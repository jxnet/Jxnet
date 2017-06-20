package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;

public abstract class PacketDecoder<T extends Packet> {

    private Pcap pcap;
    private T packet;
    private PcapPktHdr pktHdr;

    protected void read(Pcap pcap, PcapPktHdr pktHdr, T packet) {
        this.pcap = pcap;
        this.pktHdr = pktHdr;
        this.packet = packet;
    }

    public Pcap getPcap() {
        return this.pcap;
    }

    public T getPacket() {
        return this.packet;
    }

    public PcapPktHdr getPktHdr() {
        return this.pktHdr;
    }

}
