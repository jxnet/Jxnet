package com.jxpcap.protocol.lan;

import java.nio.ByteBuffer;

public class Ethernet {

	/* http://www.scs.stanford.edu/histar/src/uinc/linux/if_ether.h */
	
	
	public static final short ETH_P_LOOP		= 0x0060;			/* Ethernet Loopback packet					*/
	public static final short ETH_P_PUP			= 0x0200;			/* Xerox PUP packet							*/
	public static final short ETH_P_PUPAT		= 0x0201;			/* Xerox PUP Addr Trans packet				*/
	public static final short ETH_P_IP			= 0x0800;			/* Internet Protocol packet					*/
	public static final short ETH_P_X25			= 0x0805;			/* CCITT X.25								*/
	public static final short ETH_P_ARP			= 0x0806;			/* Address Resolution packet				*/
	public static final short ETH_P_BPQ			= 0x08FF;			/* G8BPQ AX.25 Ethernet Packet	[ NOT AN OFFICIALLY REGISTERED ID ] */
	public static final short ETH_P_IEEEPUP		= 0x0a00;			/* Xerox IEEE802.3 PUP packet 				*/
	public static final short ETH_P_IEEEPUPAT	= 0x0a01;			/* Xerox IEEE802.3 PUP Addr Trans packet 	*/
	public static final short ETH_P_DEC			= 0x6000;			/* DEC Assigned proto           			*/
	public static final short ETH_P_DNA_DL		= 0x6001;			/* DEC DNA Dump/Load            			*/
	public static final short ETH_P_DNA_RC		= 0x6002;			/* DEC DNA Remote Console       			*/
	public static final short ETH_P_DNA_RT		= 0x6003;			/* DEC DNA Routing              			*/
	public static final short ETH_P_LAT			= 0x6004;			/* DEC LAT                      			*/
	public static final short ETH_P_DIAG		= 0x6005;			/* DEC Diagnostics              			*/
	public static final short ETH_P_CUST		= 0x6006;			/* DEC Customer use             			*/
	public static final short ETH_P_SCA			= 0x6007;			/* DEC Systems Comms Arch       			*/
	public static final short ETH_P_RARP		= (short) 0x8035;	/* Reverse Addr Res packet					*/
	public static final short ETH_P_ATALK		= (short) 0x809B;	/* Appletalk DDP							*/
	public static final short ETH_P_AARP		= (short) 0x80F3;	/* Appletalk AARP							*/
	public static final short ETH_P_8021Q		= (short) 0x8100;	/* 802.1Q VLAN Extended Header  			*/
	public static final short ETH_P_IPX			= (short) 0x8137;	/* IPX over DIX								*/
	public static final short ETH_P_IPV6		= (short) 0x86DD;	/* IPv6 over bluebook						*/
	public static final short ETH_P_WCCP		= (short) 0x883E;	/* Web-cache coordination protoco defined in draft-wilson-wrec-wccp-v2-00.txt */
	public static final short ETH_P_PPP_DISC	= (short) 0x8863;	/* PPPoE discovery messages     			*/
	public static final short ETH_P_PPP_SES		= (short) 0x8864;	/* PPPoE session messages					*/
	public static final short ETH_P_MPLS_UC		= (short) 0x8847;	/* MPLS Unicast traffic						*/
	public static final short ETH_P_MPLS_MC		= (short) 0x8848;	/* MPLS Multicast traffic					*/
	public static final short ETH_P_ATMMPOA		= (short) 0x884c;	/* MultiProtocol Over ATM					*/
	public static final short ETH_P_ATMFATE		= (short) 0x8884;	/* Frame-based ATM Transport 				*/
	
	
	/* http://www.scs.stanford.edu/histar/src/uinc/linux/if_ether.h */
	
	
	
	public short hardware_type;
	public byte[] dst;
	public byte[] src;
	
	public void setPacket(byte[] dst, byte[] src, short hardware_type) {
		this.dst = dst;
		this.src = src;
		this.hardware_type = hardware_type;
	}
	
	public ByteBuffer getPacket() {
		ByteBuffer buf = ByteBuffer.allocateDirect(14);
		buf.put(dst);
		buf.put(src);
		buf.putShort(hardware_type);
		return buf;
	}
}