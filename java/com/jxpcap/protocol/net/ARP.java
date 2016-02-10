package com.jxpcap.protocol.net;

import java.nio.ByteBuffer;

public class ARP {

	public static final int ARP_LEN				= 28;
	
	public static final short ARPHRD_ETHER		= 1;	/* ethernet hardware format */
	public static final short ARPHRD_FRELAY		= 15;	/* frame relay hardware format */
	public static final short ARPOP_REQUEST		= 1;	/* request to resolve address */
	public static final short ARPOP_REPLY		= 2;	/* response to previous request */
	public static final short ARPOP_REVREQUEST	= 3;	/* request protocol address given hardware */
	public static final short ARPOP_REVREPLY	= 4;	/* response giving protocol address */
	public static final short ARPOP_INVREQUEST	= 8;	/* request to identify peer */
	public static final short ARPOP_INVREPLY	= 9;	/* response identifying peer */

	public static final byte	LEN_4			= 4;
	public static final byte	LEN_6			= 6;
	
	private short hardware_type;
	private short protocol_type;
	private byte hardware_length;
	private byte protocol_length;
	private short operation;
	private byte[] sender_hardware_address;
	private byte[] sender_protocol_address;
	private byte[] target_hardware_address;
	private byte[] target_protocol_address;
	
	public void setPacket(final short hardware_type, final short protocol_type, final byte hardware_length, final byte protocol_length, final short operation,
			final byte[] sender_hardware_address, final byte[] sender_protocol_address, final byte[] target_hardware_address, final byte[] target_protocol_address) {
		this.hardware_type			= hardware_type;
		this.protocol_type				= protocol_type;
		this.hardware_length			= hardware_length;
		this.protocol_length			= protocol_length;
		this.operation					= operation;
		this.sender_hardware_address	= sender_hardware_address;
		this.sender_protocol_address	= sender_protocol_address;
		this.target_hardware_address	= target_hardware_address;
		this.target_protocol_address	= target_protocol_address;
	}
	
	public ByteBuffer getPacket() {
		ByteBuffer buf = ByteBuffer.allocateDirect(ARP.ARP_LEN);
		buf.putShort(hardware_type);
		buf.putShort(protocol_type);
		buf.put(hardware_length);
		buf.put(protocol_length);
		buf.putShort(operation);
		buf.put(sender_hardware_address);
		buf.put(sender_protocol_address);
		buf.put(target_hardware_address);
		buf.put(target_protocol_address);
		return buf;
	}
}