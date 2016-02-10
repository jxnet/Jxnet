package com.jxpcap;

import java.nio.ByteBuffer;

public class Sender extends Jxpcap {
    
    public static String OK = "OK.";
    
    private native String nativeSendPacket(final long jxpcap, final ByteBuffer packet, final int length);
    
    private native String nativeSendPacket(final long jxpcap, final int packet, final int length);
    
    public String sendPacket(final Jxpcap jxpcap, final byte[] packet, final int length) {
        final ByteBuffer buffer = ByteBuffer.allocateDirect(length);
        buffer.put(packet);
        return nativeSendPacket(jxpcap.jxpcap, buffer, length);
    }
    
    public String sendPacket(final Jxpcap jxpcap, final int packet, final int length) {
    	return nativeSendPacket(jxpcap.jxpcap, packet, length);
    }
    
    public String sendPacket(final Jxpcap jxpcap, final ByteBuffer packet, final int length) {
    	if(packet.isDirect()) {
    		return nativeSendPacket(jxpcap.jxpcap, packet, length);
    	}
    	return null;
    }
}
