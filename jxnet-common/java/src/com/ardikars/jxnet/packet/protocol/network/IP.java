
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.packet.protocol.network;

import com.ardikars.jxnet.packet.Packet;

public abstract class IP extends Packet {
	
	public enum Protocol {
		ICMP((byte) 0x1, "ICMP"),
		TCP((byte) 0x6, "TCP"),
		UDP((byte) 0x11, "UDP");
		
		private byte type;
		private String description;
		
		private Protocol(byte type, String description) {
			this.type = type;
			this.description = description;
		}
		
		public byte getType() {
			return type;
		}
		
		public String getDescription() {
			return description;
		}
		
		public static Protocol getProtocol(byte type) {
			for (Protocol t : values()) {
				if (t.getType() == type) {
					return t;
				}
			}
			return null;
		}
		
	}
	
	public abstract void setVersion(byte version);
	
	public abstract byte getVersion();
	
}
