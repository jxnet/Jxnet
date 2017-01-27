
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.util.Loader;

import java.nio.ByteBuffer;
import java.util.List;

public final class Jxnet {

	public static final int OK = 0;
	
	private static boolean isLoaded = false;
	
	private Jxnet() {
		//prevent to create jxnet instance
	}

	public static native int PcapFindAllDevs(List<PcapIf> alldevsp, StringBuilder errbuf);

	public static native Pcap PcapOpenLive(String source, int snaplen, int promisc, int to_ms, StringBuilder errbuf);

	public static native <T> int PcapLoop(Pcap pcap, int cnt, PcapHandler<T> callback, T user);

	public static native <T> int PcapDispatch(Pcap pcap, int cnt, PcapHandler<T> callback, T user);

	public static native PcapDumper PcapDumpOpen(Pcap pcap, String fname);

	public static native void PcapDump(PcapDumper pcap_dumper, PcapPktHdr h, ByteBuffer sp);

	public static native Pcap PcapOpenOffline(String fname, StringBuilder errbuf);

	public static native int PcapCompile(Pcap pcap, BpfProgram fp, String str, int optimize, int netmask);

	public static native int PcapSetFilter(Pcap pcap, BpfProgram fp);

	public static native int PcapSendPacket(Pcap pcap, ByteBuffer buf, int size);

	public static native ByteBuffer PcapNext(Pcap pcap, PcapPktHdr h);

	public static native int PcapNextEx(Pcap pcap, PcapPktHdr pkt_header, ByteBuffer pkt_data);

	public static native void PcapClose(Pcap pcap);

	public static native int PcapDumpFlush(PcapDumper pcap_dumper);

	public static native void PcapDumpClose(PcapDumper pcap_dumper);

	public static native int PcapDatalink(Pcap pcap);

	public static native int PcapSetDatalink(Pcap pcap, int dtl);

	public static native void PcapBreakLoop(Pcap pcap); //

	public static native String PcapLookupDev(StringBuilder errbuf); //

	public static native String PcapGetErr(Pcap pcap);

	public static native String PcapLibVersion();

	public static native int PcapIsSwapped(Pcap pcap);

	public static native int PcapSnapshot(Pcap pcap);

	public static native String PcapStrError(int error);

	public static native int PcapMajorVersion(Pcap pcap);

	public static native int PcapMinorVersion(Pcap pcap);

	public static native String PcapDatalinkValToName(int dtl);

	public static native String PcapDatalinkValToDescription(int dtl);

	public static native int PcapDatalinkNameToVal(String name);

	public static native int PcapSetNonBlock(Pcap pcap, int nonblock, StringBuilder errbuf);

	public static native int PcapGetNonBlock(Pcap pcap, StringBuilder errbuf);

	public static native Pcap PcapOpenDead(int linktype, int snaplen);

	public static native long PcapDumpFTell(PcapDumper pcap_dumper); //

	public static native void PcapFreeCode(BpfProgram bpf_program);

	public static native File PcapFile(Pcap pcap);

	public static native File PcapDumpFile(PcapDumper pcap_dumper);

	public static native PcapDumper PcapDumpFOpen(Pcap pcap, File f);

	public static native int PcapStats(Pcap pcap, PcapStat pcap_stat);

	public static native int PcapLookupNet(String device, InetAddress netp, InetAddress maskp, StringBuilder errbuf);

	public static native int PcapCompileNoPcap(int snaplen_arg, int linktype_arg, BpfProgram program, String buf, int optimize, int mask);

	public static native void PcapPError(Pcap pcap, String prefix);

	public static native ARP ARPOpen();

	public static native <T> int ARPLoop(ARP arp, ARPHandler<T> callback,T arg);

	public static native int ARPAdd(ARP arp, ARPEntry arpEntry);

	public static native int ARPDelete(ARP arp, byte[] arp_pa);

	public static native int ARPGet(ARP arp, ARPEntry arpEntry);

	public static native ARP ARPClose(ARP arp);

	//public static native int Socket(int af, int type, int protocol);
	//private static native int SendTo(int socket, ByteBuffer buf, int len, int flags, SockAddr to, int toLen);
	//private static native int SendTO(int socket, ByteBuffer buf, int len, int flags, int sa_family, byte[] sa_data, int toLen);

	static {
		if (!Jxnet.isLoaded) {
			try {
				Loader.loadLibrary();
				Jxnet.isLoaded = true;
			} catch (Exception e) {
				System.err.println(e.getMessage());
				Jxnet.isLoaded = false;
			}
		}
	}

}