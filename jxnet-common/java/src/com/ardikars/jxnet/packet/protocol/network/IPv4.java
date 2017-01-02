package com.ardikars.jxnet.packet.protocol.network;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class IPv4 extends IP {
	
	public static final int IPV4_HEADER_LENGTH = 20;
	
	private byte version;
	private byte headerLength;
	private byte diffServ;
	private short totalLength;
	private short identification;
	private byte flag;
	private short fragmentOffset;
	private byte ttl;
	private byte protocol;
	private short checksum;
	private Inet4Address sourceAddress;
	private Inet4Address destinationAddress;
	private byte[] option;
	
	private byte[] data;
	
	public static Packet instance() {
		return new IPv4();
	}
	
	@Override
	public byte getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(byte version) {
		this.version = version;
	}
	
	public byte getHeaderLength() {
		return headerLength;
	}
	
	public void setHeaderLength(byte headerLength) {
		this.headerLength = headerLength;
	}
	
	public byte getDiffServ() {
		return diffServ;
	}
	
	public void setDiffServ(byte diffServ) {
		this.diffServ = diffServ;
	}
	
	public short getTotalLength() {
		return totalLength;
	}
	
	public void setTotalLength(short totalLength) {
		this.totalLength = totalLength;
	}
	
	public short getIdentification() {
		return identification;
	}
	
	public void setIdentification(short identification) {
		this.identification = identification;
	}
	
	public byte getFlag() {
		return flag;
	}
	
	public void setFlag(byte flag) {
		this.flag = flag;
	}
	
	public short getFragmentOffset() {
		return fragmentOffset;
	}
	
	public void setFragmentOffset(short fragmentOffset) {
		this.fragmentOffset = fragmentOffset;
	}
	
	public byte getTtl() {
		return ttl;
	}
	
	public void setTtl(byte ttl) {
		this.ttl = ttl;
	}
	
	public byte getProtocol() {
		return protocol;
	}
	
	public void setProtocol(byte protocol) {
		this.protocol = protocol;
	}
	
	public short getChecksum() {
		return checksum;
	}
	
	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}
	
	public Inet4Address getSourceAddress() {
		return sourceAddress;
	}
	
	public void setSourceAddress(Inet4Address sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	public Inet4Address getDestinationAddress() {
		return destinationAddress;
	}
	
	public void setDestinationAddress(Inet4Address destinationAddress) {
		this.destinationAddress = destinationAddress;
	}
	
	public byte[] getOption() {
		return option;
	}
	
	public void setOption(byte[] option) {
		this.option = option;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public static IPv4 wrap(byte[] bytes) {
		return IPv4.wrap(bytes, 0, bytes.length);
	}
	
	public static IPv4 wrap(byte[] bytes, int offset, int length) {
		IPv4 ipv4 = new IPv4();
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
		byte version = buffer.get();
		ipv4.setVersion((byte) (version >> 4 & 0xf));
		byte headerLength = (byte) (version & 0xf);
		ipv4.setHeaderLength(headerLength);
		ipv4.setDiffServ(buffer.get());
		ipv4.setTotalLength(buffer.getShort());
		ipv4.setIdentification(buffer.getShort());
		short sscratch = buffer.getShort();
		ipv4.setFlag((byte) (sscratch >> 13 & 0x7));
		ipv4.setFragmentOffset((short) (sscratch & 0x1fff));
		ipv4.setTtl(buffer.get());
		ipv4.setProtocol(buffer.get());
		ipv4.setChecksum(buffer.getShort());
		byte[] IPv4Buffer = new byte[4];
		buffer.get(IPv4Buffer);
		ipv4.setSourceAddress(Inet4Address.valueOf(IPv4Buffer));
		buffer.get(IPv4Buffer);
		ipv4.setDestinationAddress(Inet4Address.valueOf(IPv4Buffer));
		if (headerLength > 5) {
			int optionLength = (headerLength - 5) * 4;
			byte[] option = new byte[optionLength];
			buffer.get(option);
			ipv4.setOption(option);
			ipv4.setData(Arrays.copyOfRange(bytes, (IPV4_HEADER_LENGTH + optionLength), bytes.length));
		} else {
			ipv4.setData(Arrays.copyOfRange(bytes, IPV4_HEADER_LENGTH, bytes.length));
		}
		ipv4.rawPacket = bytes;
		return ipv4;
	}
	
	@Override
	public Packet getParent() {
		Ethernet ethernet = Ethernet.wrap(rawPacket);
		return ethernet;
	}
	
	@Override
	public Packet getChild() {
		return null;
	}
	
}
