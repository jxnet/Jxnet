package com.ardikars.jxnet.packet;

public abstract class Packet {
	
	protected byte[] rawPacket;
	
	public abstract Packet getParent();
	
	public abstract Packet getChild();
	
}
