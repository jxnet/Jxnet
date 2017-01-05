package com.ardikars.jxnet;

import com.ardikars.jxnet.packet.Packet;

public abstract class PacketHandler<T> {
	
	protected T user;
	
	protected void recievedPacket(T user, PcapPktHdr pktHdr, Packet packet) {
		if (pktHdr == null || packet == null) return;
	};
	
}
