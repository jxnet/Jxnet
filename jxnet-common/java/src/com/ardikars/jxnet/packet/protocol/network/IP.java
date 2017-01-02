package com.ardikars.jxnet.packet.protocol.network;

import com.ardikars.jxnet.packet.Packet;

public abstract class IP extends Packet {
	
	public abstract void setVersion(byte version);
	
	public abstract byte getVersion();
	
}
