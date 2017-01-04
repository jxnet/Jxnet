package com.ardikars.jxnet.packet.protocol.network;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;
import com.ardikars.jxnet.packet.protocol.transport.TCP;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class IPv4 extends IP {
	
	public static final int IPV4_HEADER_LENGTH = 20;
	
	private byte version;
	private byte headerLength;
	private byte diffServ;
	private byte expCon;
	private short totalLength;
	private short identification;
	private byte flags;
	private short fragmentOffset;
	private byte ttl;
	private Protocol protocol;
	private short checksum;
	private Inet4Address sourceAddress;
	private Inet4Address destinationAddress;
	private byte[] options;
	
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
	
	public byte getExpCon() {
		return expCon;
	}
	
	public void setExpCon(byte expCon) {
		this.expCon = expCon;
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
	
	public byte getFlags() {
		return flags;
	}
	
	public void setFlags(byte flags) {
		this.flags = flags;
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
	
	public Protocol getProtocol() {
		return protocol;
	}
	
	public void setProtocol(Protocol protocol) {
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
	
	public byte[] getOptions() {
		return options;
	}
	
	public void setOptions(byte[] options) {
		this.options = options;
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
		ipv4.version = buffer.get();
		ipv4.headerLength = (byte) (ipv4.version & 0xf);
		ipv4.version = (byte) ((ipv4.version >> 4) & 0xf);
		byte tmp = buffer.get();
		ipv4.diffServ = (byte) ((tmp >> 2) & 0x3f);
		ipv4.expCon = (byte) (tmp & 0x3);
		ipv4.totalLength = buffer.getShort();
		ipv4.identification = buffer.getShort();
		short sscratch = buffer.getShort();
		ipv4.flags = (byte) (sscratch >> 13 & 0x7);
		ipv4.fragmentOffset = (short) (sscratch & 0x1fff);
		ipv4.ttl = buffer.get();
		ipv4.protocol = Protocol.getProtocol(buffer.get());
		ipv4.checksum = buffer.getShort();
		byte[] IPv4Buffer = new byte[4];
		buffer.get(IPv4Buffer);
		ipv4.sourceAddress = Inet4Address.valueOf(IPv4Buffer);
		
		byte[] IPv4Buffer2 = new byte[4];
		buffer.get(IPv4Buffer2);
		ipv4.destinationAddress = Inet4Address.valueOf(IPv4Buffer2);
		if (ipv4.headerLength > 5) {
			int optionsLength = (ipv4.headerLength - 5) * 4;
			ipv4.options = new byte[optionsLength];
			buffer.get(ipv4.options);
			ipv4.data = new byte[(bytes.length - (optionsLength + IPV4_HEADER_LENGTH))];
			System.arraycopy(bytes, (IPV4_HEADER_LENGTH + optionsLength), ipv4.data,
					0, (bytes.length - (optionsLength + IPV4_HEADER_LENGTH)));
		} else {
			ipv4.data = new byte[(bytes.length - (IPV4_HEADER_LENGTH))];
			System.arraycopy(bytes, (IPV4_HEADER_LENGTH), ipv4.data,
					0, (bytes.length - IPV4_HEADER_LENGTH));
		}
		ipv4.rawPacket = bytes;
		return ipv4;
	}
	
	public byte[] toBytes() {
		byte[] data = new byte[IPV4_HEADER_LENGTH +
				((this.data == null) ? 0 : this.data.length) +
				((headerLength > 5) ? options.length * 5: 0)];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.put((byte) ((version & 0xf) << 4 | headerLength & 0xf));
		buffer.put((byte) (((diffServ << 2) & 0x3f) | expCon & 0x3));
		buffer.putShort(totalLength);
		buffer.putShort(identification);
		buffer.putShort((short) ((flags & 0x7) << 13 | fragmentOffset & 0x1fff));
		buffer.put(ttl);
		buffer.put(protocol.getType());
		buffer.putShort(checksum);
		buffer.put(sourceAddress.toBytes());
		buffer.put(destinationAddress.toBytes());
		if (options != null) {
			buffer.put(options);
		}
		
		if (checksum == 0) {
			buffer.rewind();
			int accumulation = 0;
			for (int i = 0; i < headerLength * 2; ++i) {
				accumulation += 0xffff & buffer.getShort();
			}
			accumulation = (accumulation >> 16 & 0xffff)
					+ (accumulation & 0xffff);
			checksum = (short) (~accumulation & 0xffff);
			buffer.putShort(10, checksum);
		}
		return data;
	}
	
	@Override
	public Packet getParent() {
		Ethernet ethernet = Ethernet.wrap(rawPacket);
		return ethernet;
	}
	
	@Override
	public Packet getChild() {
		if (protocol == Protocol.TCP) {
			return TCP.wrap(data);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("[")
				.append("Version: " + version)
				.append(", Internet Header Length: " + headerLength)
				.append(", Differentiated Services Code Point: " + diffServ)
				.append(", Explicit Congestion Notification: " + expCon)
				.append(", Total Length: " + totalLength)
				.append(", Identification: " + identification)
				.append(", Flags: " + flags)
				.append(", Fragment Offset: " + fragmentOffset)
				.append(", Time To Live: " + ttl)
				.append(", Protocol: " + protocol)
				.append(", Header Checksum: " + checksum)
				.append(", Source Address: " + sourceAddress)
				.append(", Destination Address: " + destinationAddress)
				.append(", Options: " + options)
				.append("]").toString();
	}
}
