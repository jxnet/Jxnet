package com.jxpcap;

import java.nio.ByteBuffer;

public class Sender extends Jxpcap {
    
    public static String OK = "OK.";
    
    private native String nativeSendPacket(final long jxpcap, final ByteBuffer packet, final int length);
    
    public String sendPacket(final Jxpcap jxpcap, final byte[] packet, final int length) {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(length);
        buffer.put(packet);
        return nativeSendPacket(jxpcap.jxpcap, buffer, length);
    }
    
}
