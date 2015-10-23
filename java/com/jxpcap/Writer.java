package com.jxpcap;

import com.jxpcap.packet.Packet;

public class Writer extends Jxpcap {
    
    
    private long jxpcap_dumper;
    
    private native String nativeOpenDumpFile(final long pcap, String filename);
    
    private native String nativeWritePacket(Packet packet);
    
    private Writer(Jxpcap pcap, String filename) {
        String result = nativeOpenDumpFile(pcap.jxpcap, filename);
    }
    
    public static Writer openDumpFile(Jxpcap pcap, String filename) {
        return new Writer(pcap, filename);
    }
    
    public String writePacket(Packet packet) {
        return nativeWritePacket(packet);
    }
}