package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.PcapCloseException;
import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;

public abstract class PacketHandler<T> {
	
	protected T user;
	
	protected Pcap handler;
	
	protected void recievedPacket(T user, PcapPktHdr pktHdr, Packet packet) {
		if (pktHdr == null || packet == null) return;
	}
	
	protected void sendPacket(Packet packet) throws PcapCloseException {
		if (handler == null) {
			throw new PcapCloseException("Packet handler is closed.");
		}
		byte[] rawPacket = packet.getRawPacket();
		ByteBuffer buffer = ByteBuffer.allocateDirect(rawPacket.length);
		buffer.put(rawPacket);
		Jxnet.pcapSendPacket(handler, buffer, rawPacket.length);
	}
}
