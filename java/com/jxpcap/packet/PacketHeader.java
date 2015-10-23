package com.jxpcap.packet;

public interface PacketHeader {
    public long getTimeInSeconds();
    public long getTimeInMicroseconds();
    public int getLength();
    public int getCapturedLength();
}
