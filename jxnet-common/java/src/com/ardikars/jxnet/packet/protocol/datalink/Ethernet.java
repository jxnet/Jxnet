package com.ardikars.jxnet.packet.protocol.datalink;

import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.network.IPv4;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Ethernet extends Packet {
	
	/**
	 * Ethernet Type Codes
	 */
	public enum EtherType {
		IPv4((short) 0x0800, null),
		VLAN((short) 0x8100, null)
		;
		
		private short type;
		private String description;
		
		private EtherType(short type, String description) {
			this.type = type;
			this.description = description;
		}
		
		public short getType() {
			return type;
		}
		
		public String getDescription() {
			return description;
		}
		
		public static EtherType getEtherType(short type) {
			for (EtherType t : values()) {
				if (t.getType() == type) {
					return t;
				}
			}
			return null;
		}
		
	}
	
	public static final int ETHERNET_HEADER_LENGTH = 14;
	
	private MacAddress destinationMacAddress;
	private MacAddress sourceMacAddress;
	private byte priorityCode;
	private short vlanID;
	private EtherType etherType;
	
	private byte[] data;
	
	public MacAddress getDestinationMacAddress() {
		return destinationMacAddress;
	}
	
	public void setDestinationMacAddress(MacAddress destinationMacAddress) {
		this.destinationMacAddress = destinationMacAddress;
	}
	
	public MacAddress getSourceMacAddress() {
		return sourceMacAddress;
	}
	
	public void setSourceMacAddress(MacAddress sourceMacAddress) {
		this.sourceMacAddress = sourceMacAddress;
	}
	
	public byte getPriorityCode() {
		return priorityCode;
	}
	
	public void setPriorityCode(byte priorityCode) {
		this.priorityCode = priorityCode;
	}
	
	public short getVlanID() {
		return vlanID;
	}
	
	public void setVlanID(short vlanID) {
		this.vlanID = vlanID;
	}
	
	public EtherType getEtherType() {
		return etherType;
	}
	
	public void setEtherType(EtherType etherType) {
		this.etherType = etherType;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public static Ethernet wrap(byte[] bytes) {
		return Ethernet.wrap(bytes, 0, bytes.length);
	}
	
	public static Ethernet wrap(byte[] bytes, int offset, int length) {
		Ethernet ethernet = new Ethernet();
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
		byte[] MACBuffer = new byte[6];
		buffer.get(MACBuffer);
		ethernet.setDestinationMacAddress(MacAddress.valueOf(MACBuffer));
		buffer.get(MACBuffer);
		ethernet.setSourceMacAddress(MacAddress.valueOf(MACBuffer));
		EtherType etherType = EtherType.getEtherType(buffer.getShort());
		if (etherType == EtherType.VLAN) {
			short tci = buffer.getShort();
			ethernet.setPriorityCode((byte) (tci >> 13 & 0x07));
			ethernet.setVlanID((short) (tci & 0x0fff));
			etherType = EtherType.getEtherType(buffer.getShort());
		} else {
			ethernet.setVlanID((short) 0xffff);
		}
		ethernet.setEtherType(etherType);
		if (etherType == EtherType.VLAN)
			ethernet.setData(Arrays.copyOfRange(bytes, (ETHERNET_HEADER_LENGTH + 4), bytes.length));
		else
			ethernet.setData(Arrays.copyOfRange(bytes, ETHERNET_HEADER_LENGTH, bytes.length));
		ethernet.rawPacket = bytes;
		return ethernet;
	}
	
	
	@Override
	public Packet getParent() {
		return this;
	}
	
	@Override
	public Packet getChild() {
		if (etherType == EtherType.IPv4){
			return IPv4.wrap(data);
		}
		return null;
	}
	
}
