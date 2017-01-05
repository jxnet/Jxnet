
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet.packet.protocol.transport;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.protocol.datalink.Ethernet;

import java.nio.ByteBuffer;

public class TCP extends Packet {
	
	public static int TCP_HEADER_LENGTH = 20;
	
	private short sourcePort;
	private short destinationPort;
	private int sequence;
	private int acknowledge;
	private byte dataOffset; // 4 bit + 3 bit
	private short flags; // 9 bit
	private short windowSize;
	private short checksum;
	private short urgentPointer;
	private byte[] options;
	
	private byte[] data;
	
	public int getSourcePort() {
		return (sourcePort & 0xffff);
	}
	
	public void setSourcePort(short sourcePort) {
		this.sourcePort = sourcePort;
	}
	
	public int getDestinationPort() {
		return (int) destinationPort;
	}
	
	public void setDestinationPort(short destinationPort) {
		this.destinationPort = destinationPort;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public int getAcknowledge() {
		return acknowledge;
	}
	
	public void setAcknowledge(int acknowledge) {
		this.acknowledge = acknowledge;
	}
	
	public byte getDataOffset() {
		return (byte) (dataOffset & 0xf);
	}
	
	public void setDataOffset(byte dataOffset) {
		this.dataOffset = (byte) (dataOffset & 0xf);
	}
	
	public short getFlags() {
		return (short) (flags & 0x1ff);
	}
	
	public void setFlags(short flags) {
		this.flags = (short) (flags & 0x1ff);
	}
	
	public short getWindowSize() {
		return windowSize;
	}
	
	public void setWindowSize(short windowSize) {
		this.windowSize = windowSize;
	}
	
	public short getChecksum() {
		return checksum;
	}
	
	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}
	
	public short getUrgentPointer() {
		return urgentPointer;
	}
	
	public void setUrgentPointer(short urgentPointer) {
		this.urgentPointer = urgentPointer;
	}
	
	public byte[] getOptions() {
		return options;
	}
	
	public void setOptions(byte[] options) {
		this.options = options;
	}
	
	public static TCP wrap(byte[] bytes) {
		return TCP.wrap(bytes, 0, bytes.length);
	}
	
	public static TCP wrap(byte[] bytes, int offset, int length) {
		TCP tcp = new TCP();
		ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
		tcp.sourcePort = buffer.getShort();
		tcp.destinationPort = buffer.getShort();
		tcp.sequence = buffer.getInt();
		tcp.acknowledge = buffer.getInt();
		tcp.flags = buffer.getShort();
		tcp.dataOffset = ((byte) (tcp.flags >> 12 & 0xf));
		tcp.flags = (short) (tcp.flags & 0x1ff);
		tcp.windowSize = buffer.getShort();
		tcp.checksum = buffer.getShort();
		tcp.urgentPointer = buffer.getShort();
		if (tcp.dataOffset > 5) {
			int optionLength = (tcp.dataOffset << 2) - TCP_HEADER_LENGTH;
			if (buffer.limit() < buffer.position() + optionLength) {
				optionLength = buffer.limit() - buffer.position();
			}
			try {
				tcp.options = new byte[optionLength];
				buffer.get(tcp.options, 0, optionLength);
				tcp.data = new byte[(bytes.length - (TCP_HEADER_LENGTH + optionLength))];
				System.arraycopy(bytes, (TCP_HEADER_LENGTH + optionLength), tcp.data,
						0,
						(bytes.length - (TCP_HEADER_LENGTH + optionLength)));
			} catch (IndexOutOfBoundsException e) {
				tcp.options = null;
				tcp.data = new byte[(bytes.length - TCP_HEADER_LENGTH)];
				System.arraycopy(bytes, (TCP_HEADER_LENGTH), tcp.data, 0,
						(bytes.length - TCP_HEADER_LENGTH));
			}
		} else {
			tcp.data = new byte[(bytes.length - TCP_HEADER_LENGTH)];
			System.arraycopy(bytes, (TCP_HEADER_LENGTH), tcp.data, 0,
					(bytes.length - TCP_HEADER_LENGTH));
		}
		tcp.rawPacket = bytes;
		return tcp;
	}
	
	public byte[] toBytes() {
		byte[] data = new byte[TCP_HEADER_LENGTH + ((options == null) ? 0 : options.length)];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		buffer.putShort(sourcePort);
		buffer.putShort(destinationPort);
		buffer.putInt(sequence);
		buffer.putInt(acknowledge);
		buffer.putShort((short) ((flags & 0x1ff) | (dataOffset & 0xf) << 12));
		buffer.putShort(windowSize);
		buffer.putShort(checksum);
		buffer.putShort(urgentPointer);
		if (options != null)
			buffer.put(options);
		return data;
	}
	
	@Override
	public Packet getParent() {
		Ethernet ethernet = Ethernet.wrap(rawPacket);
		return ethernet.getChild();
	}
	
	@Override
	public Packet getChild() {
		return null;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("[")
				.append("Source port: " + (sourcePort & 0xffff))
				.append(", Destination port: " + (destinationPort & 0xffff))
				.append(", Sequence number: " + sequence)
				.append(", Acknowledgment number: " + acknowledge)
				.append(", Data offset: " + dataOffset)
				.append(", Flags: " + flags)
				.append(", Windows size: " + windowSize)
				.append(", Checksum: " + checksum)
				.append(", Urgent pointer: " + urgentPointer)
				.append(", Options: " + options)
				.append("]").toString();
	}
	
}
