
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.packet;

public abstract class Packet {
	
	protected byte[] rawPacket;
	
	public abstract Packet getParent();
	
	public abstract Packet getChild();
	
	protected byte[] getRawPacket() {
		return rawPacket;
	}
	
}
