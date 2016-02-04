package com.jxpcap.packet;

import java.nio.ByteBuffer;

public class Packet implements PacketData, PacketHeader {
    
	public Packet() {
		setPacket(0, 0, 0, 0, null);
	}
	
	public Packet(long time_in_seconds, long time_in_microseconds, int length, int captured_length, ByteBuffer packet_data) {
		setPacket(time_in_seconds, time_in_microseconds, length, captured_length, packet_data);
	}
	
	private long time_in_seconds;
    private long time_in_microseconds;
    private int length;
    private int captured_length;
      
    private ByteBuffer packet_data;

    @Override
    public ByteBuffer getPacketData() {
        return packet_data;
    }

    @Override
    public long getTimeInSeconds() {
        return time_in_seconds;
    }

    @Override
    public long getTimeInMicroseconds() {
        return time_in_microseconds;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public int getCapturedLength() {
        return captured_length;
    }
   
    private void setPacket(long time_in_seconds, long time_in_microseconds, int length, int captured_length, ByteBuffer packet_data) {
        this.time_in_seconds = time_in_seconds;
        this.time_in_microseconds = time_in_microseconds;
        this.length = length;
        this.captured_length = captured_length;
        this.packet_data = packet_data;
    }
}