package com.jxpcap;

import com.jxpcap.packet.Packet;

public class Captor extends Jxpcap {

    private native String nativeGetPacket(final long device, final Packet packet);
    public String getPacket(final Jxpcap jxpcap, Packet packet) {
        return nativeGetPacket(jxpcap.jxpcap, packet);
    }
}