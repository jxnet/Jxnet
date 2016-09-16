
/*
 * Author	: Ardika Rommy Sanjaya
 * Website	: http://ardikars.com
 * Contact	: contact@ardikars.com
 * License	: Lesser GNU Public License Version 3
 */

package com.ardikars.jxnet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.ardikars.jxnet.exception.JxnetException;

public final class Jxnet {

	private static Logger logger = Logger.getLogger(Jxnet.class.getName());
	
	public static final int OK = 0;
	
	private static native int PcapFindAllDevs(List<PcapIf> alldevsp, StringBuilder errbuf);
	
	private static native Pcap PcapOpenLive(String source, int snaplen, int promisc, int to_ms, StringBuilder errbuf);
	
	private static native <T> int PcapLoop(Pcap pcap, int cnt, PcapHandler<T> callback, T user);
	
	private static native <T> int PcapDispatch(Pcap pcap, int cnt, PcapHandler<T> callback, T user);
	
	private static native PcapDumper PcapDumpOpen(Pcap pcap, String fname);
	
	private static native void PcapDump(PcapDumper pcap_dumper, PcapPktHdr h, ByteBuffer sp);
	
	private static native Pcap PcapOpenOffline(String fname, StringBuilder errbuf);
	
	private static native int PcapCompile(Pcap pcap, BpfProgram fp, String str, int optimize, int netmask);
	
	private static native int PcapSetFilter(Pcap pcap, BpfProgram fp);
	
	private static native int PcapSendPacket(Pcap pcap, ByteBuffer buf, int size);
	
	private static native ByteBuffer PcapNext(Pcap pcap, PcapPktHdr h);
	
	private static native int PcapNextEx(Pcap pcap, PcapPktHdr pkt_header, ByteBuffer pkt_data);
	
	private static native void PcapClose(Pcap pcap);
	
	private static native int PcapDumpFlush(PcapDumper pcap_dumper);
	
	private static native void PcapDumpClose(PcapDumper pcap_dumper);
	
	private static native int PcapDatalink(Pcap pcap);
	
	private static native int PcapSetDatalink(Pcap pcap, int dtl);
	
	private static native void PcapBreakLoop(Pcap pcap); //
	
	private static native String PcapLookupDev(StringBuilder errbuf); //
	
	private static native String PcapGetErr(Pcap pcap);
	
	private static native String PcapLibVersion();
	
	private static native int PcapIsSwapped(Pcap pcap);
	
	private static native int PcapSnapshot(Pcap pcap);
	
	private static native String PcapStrError(int error);
	
	private static native int PcapMajorVersion(Pcap pcap);
	
	private static native int PcapMinorVersion(Pcap pcap);
	
	private static native String PcapDatalinkValToName(int dtl);
	
	private static native String PcapDatalinkValToDescription(int dtl);
	
	private static native int PcapDatalinkNameToVal(String name);
	
	private static native int PcapSetNonBlock(Pcap pcap, int nonblock, StringBuilder errbuf);
	
	private static native int PcapGetNonBlock(Pcap pcap, StringBuilder errbuf);
	
	private static native Pcap PcapOpenDead(int linktype, int snaplen);
	
	private static native long PcapDumpFTell(PcapDumper pcap_dumper); //
	
	private static native void PcapFreeCode(BpfProgram bpf_program);
	
	private static native File PcapFile(Pcap pcap);
	
	private static native File PcapDumpFile(PcapDumper pcap_dumper);

	private static native PcapDumper PcapDumpFOpen(Pcap pcap, File f);
	
	private static native int PcapStats(Pcap pcap, PcapStat pcap_stat);
	
	private static native int Socket(int af, int type, int protocol);
	
	private static native int SendTo(int socket, ByteBuffer buf, int len, int flags, SockAddr to, int toLen);
	
	private static native int SendTO(int socket, ByteBuffer buf, int len, int flags, int sa_family, byte[] sa_data, int toLen);
	
	private static native int PcapLookupNet(String device, InetAddress netp, InetAddress maskp, StringBuilder errbuf);
	
	private static native int PcapCompileNoPcap(int snaplen_arg, int linktype_arg, BpfProgram program, String buf, int optimize, int mask);
	
	private static native void PcapPError(Pcap pcap, String prefix);
	
	public static int pcapFindAllDevs(List<PcapIf> alldevsp, StringBuilder errbuf) {
		int r = PcapFindAllDevs(alldevsp, errbuf);
		if (r == OK) {
			logger.info("OK");
		} else {
			logger.warning(errbuf.toString() + " ("+r+")");
		}
		return r;
	}
	
	public static void pcapFreeAllDevs(List<PcapIf> alldevsp) {
		if(!alldevsp.isEmpty()) {
			alldevsp.clear();
			logger.info("OK");
		} else {
			logger.info("is already freed");
		}
	}
	
	public static Pcap pcapOpenLive(String source, int snaplen, int promisc, int to_ms, StringBuilder errbuf) {
		Pcap pcap = PcapOpenLive(source, snaplen, promisc, to_ms, errbuf);
		if(pcap != null) {
			logger.info("OK");
		} else {
			logger.warning(errbuf.toString() + " (null)");
		}
		return pcap;
	}
	
	public static <T> int pcapLoop(Pcap pcap, int cnt, PcapHandler<T> callback, T user) {
		int r = PcapLoop(pcap, cnt, callback, user);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapLopp(Pcap, int, PcapHandler<T>, T): Failed ("+r+")");
		}
		return r;
	}
	
	public static <T> int pcapDispatch(Pcap pcap, int cnt, PcapHandler<T> callback, T user) {
		int r = PcapDispatch(pcap, cnt, callback, user);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapDispatch(Pcap, int, PcapHandler<T>, T): Failed (+r+)" );
		}
		return r;
	}
	
	public static PcapDumper pcapDumpOpen(Pcap pcap, String fname) {
		PcapDumper pcap_dumper = PcapDumpOpen(pcap, fname);
		if(pcap_dumper != null) {
			logger.info("OK");
		} else {
			logger.warning("PcapDumpOpen(Pcap, String): Failed (null)");
		}
		return pcap_dumper;
	}
	
	public static void pcapDump(PcapDumper pcap_dumper, PcapPktHdr h, ByteBuffer sp) {
		PcapDump(pcap_dumper, h, sp);
	}
	
	public static Pcap pcapOpenOffline(String fname, StringBuilder errbuf) {
		Pcap pcap = PcapOpenOffline(fname, errbuf);
		if(pcap != null) {
			logger.info("OK");
		} else {
			logger.warning(errbuf.toString());
		}
		return pcap;
	}
	
	public static int pcapCompile(Pcap pcap, BpfProgram fp, String str, int optimize, Inet4Address netmask) {
		return PcapCompile(pcap, fp, str, optimize, netmask.toInt());
	}
	
	public static int pcapSetFilter(Pcap pcap, BpfProgram fp) {
		int r = PcapSetFilter(pcap, fp);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapSetFilter(Pcap, BpfProgram): Failed ("+r+")");
		}
		return r;
	}
	
	public static int pcapSendPacket(Pcap pcap, ByteBuffer buf, int size) {
		int r = PcapSendPacket(pcap, buf, size);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapSendPacket(Pcap, ByteBuffer, int): Failed ("+r+")");
		}
		return r;
	}
	
	public static long pcapDumpFTell(PcapDumper pcap_dumper) {
		return PcapDumpFTell(pcap_dumper);
	}
	
	public static ByteBuffer pcapNext(Pcap pcap, PcapPktHdr h) {
		return PcapNext(pcap, h);
	}
	
	public static int pcapNextEx(Pcap pcap, PcapPktHdr pkt_header, ByteBuffer pkt_data) {
		if(pkt_data.capacity() >= 65535) {
			return PcapNextEx(pcap, pkt_header, pkt_data);
		}
		try {
			throw new Exception("Please allocate ByteBuffer to maximum packet size (65535)");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static void pcapClose(Pcap pcap) {
		PcapClose(pcap);
	}
	
	public static int pcapDumpFlush(PcapDumper pcap_dumper) {
		int r = PcapDumpFlush(pcap_dumper);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("PcapDumpFlush(PcapDumper): Failed ("+r+")");
		}
		return r;
	}
	
	public static void pcapDumpClose(PcapDumper pcap_dumper) {
		PcapDumpClose(pcap_dumper);
	}
	
	public static int pcapDatalink(Pcap pcap) {
		int r = PcapDatalink(pcap);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapDataLink(Pcap): Failed ("+r+")");
		}
		return r;
	}
	
	public static int pcapSetDatalink(Pcap pcap, int dtl) {
		int r = PcapSetDatalink(pcap, dtl);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapSetDatalink(Pcap, int): Failed ("+r+")");
		}
		return r;
	}
	
	public static void pcapBreakLoop(Pcap pcap) {
		PcapBreakLoop(pcap);
	}
	
	public static String pcapLookupDev(StringBuilder errbuf) {
		return PcapLookupDev(errbuf);
	}
	
	public static String pcapGetErr(Pcap pcap) {
		return PcapGetErr(pcap);
	}
	
	public static String pcapLibVersion() {
		return PcapLibVersion();
	}
	
	public static int pcapIsSwapped(Pcap pcap) {
		int r = PcapIsSwapped(pcap);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapIsSwapped(Pcap): Failed ("+r+")");
		}
		return r;
	}
	
	public static int pcapSnapshot(Pcap pcap) {
		int r = PcapSnapshot(pcap);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapSnapshot(Pcap): Failed ("+r+")");
		}
		return r;
	}
	
	public static String pcapStrError(int error) {
		return PcapStrError(error);
	}
	
	public static int pcapMajorVersion(Pcap pcap) {
		int r = PcapMajorVersion(pcap);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapMajorVersion(Pcap): Failed ("+r+")");
		}
		return r;
	}
	
	public static int pcapMinorVersion(Pcap pcap) {
		int r = PcapMinorVersion(pcap);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapMinorVersion(Pcap): Failed ("+r+")");
		}
		return r;
	}
	
	public static String pcapDatalinkValToName(int dtl) {
		return PcapDatalinkValToName(dtl);
	}
	
	public static String pcapDatalinkValToDescription(int dtl) {
		return PcapDatalinkValToDescription(dtl);
	}
	
	public static int pcapDatalinkNameToVal(String name) {
		int r = PcapDatalinkNameToVal(name);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapDatalinkNameToVal(String): Failed ("+r+")");
		}
		return r;
	}
	
	public static int pcapSetNonBlock(Pcap pcap, int nonblock, StringBuilder errbuf) {
		int r = PcapSetNonBlock(pcap, nonblock, errbuf);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapSetNonBlock(Pcap, int, StringBuilder)");
		}
		return r;
	}
	
	public static int pcapGetNonBlock(Pcap pcap, StringBuilder errbuf) {
		int r = PcapGetNonBlock(pcap, errbuf);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapGetNonBlock(Pcap, StringBuilder): Failed ("+r+")");
		}
		return r;
	}
	
	public static Pcap pcapOpenDead(int linktype, int snaplen) {
		Pcap pcap = PcapOpenDead(linktype, snaplen);
		if(pcap != null) {
			logger.info("OK");
		} else {
			logger.warning("pcapOpenDead(int, int): Failed (null)");
		}
		return pcap;
	}
	
	public static void pcapFreeCode(BpfProgram bpf_program) {
		PcapFreeCode(bpf_program);
	}
	
	public static File pcapFile(Pcap pcap) {
		File file = PcapFile(pcap);
		if(file != null) {
			logger.info("OK");
		} else {
			logger.warning("pcapFile(Pcap): Failed (mull)");
		}
		return file;
	}
	
	public static File pcapDumpFile(PcapDumper pcap_dumper) {
		File file = PcapDumpFile(pcap_dumper);
		if(file != null) {
			logger.info("OK");
		} else {
			logger.warning("pcapDumpFile(PcapDumper): Filed (null)");
		}
		return file;
	}
	
	public static PcapDumper pcapDumpFOpen(Pcap pcap, File f) {
		PcapDumper pcap_dumper = PcapDumpFOpen(pcap, f);
		if(pcap_dumper != null) {
			logger.info("OK");
		} else {
			logger.warning("pcapDumperFOpen(Pcap, File): Filed (null)");
		}
		return pcap_dumper;
	}
	
	public static int pcapStats(Pcap pcap, PcapStat pcap_stat) {
		int r = PcapStats(pcap, pcap_stat);
		if(r == OK) {
			logger.info("OK");
		} else {
			logger.warning("pcapStat(Pcap, PcapStat: Failed ("+r+")");
		}
		return r;
	}
	
//	public static int socket(int af, int type, int protocol) {
//		int socket = Socket(af, type, protocol);
//		if(socket != -1) {
//			logger.info("OK");
//		} else {
//			logger.warning("socket(int, int, int): Failed (null)");
//		}
//		return socket;
//	}
//	
//	public static int sendTo(int socket, ByteBuffer buf, int len, int flags, SockAddr to, int toLen) {
//		int r = SendTO(socket, buf, len, flags, to.getSaFamily(), to.getData(), toLen);
//		if(r == 0) {
//			logger.info("OK");
//		} else {
//			logger.warning("sendTo(int, byte[], int, flags, byte[], int): Failed (null)");
//		}
//		return r;
//	}
	
	public static int pcapLookupNet(String device, InetAddress netp, InetAddress maskp, StringBuilder errbuf) {
		int r = PcapLookupNet(device, netp, maskp, errbuf);
		if(r == 0) {
			logger.info("OK");
		} else {
			logger.warning("pcapLookupNet(String, InetAddress, InetAddress, StringBuilder): Failed (null)");
		}
		return r;
	}
	
	public static int pcapCompileNoPcap(int snaplen_arg, int linktype_arg, BpfProgram program, String buf, int optimize, Inet4Address mask) {
		int r = PcapCompileNoPcap(snaplen_arg, linktype_arg, program, buf, optimize, mask.toInt());
		if(r == 0) {
			logger.info("OK");
		} else {
			logger.warning("pcapCompileNoPcap(int, int, BpfProgram, String int int): Failed ("+r+")");
		}
		return r;
	}
	
	public static void main(String[] args) {
		JOptionPane.showMessageDialog(null, "Jxnet is a network library for java.");
	}
	
	static {
		try {
			LogManager.getLogManager()
				.readConfiguration(new FileInputStream("./settings/logging.properties"));
			logger.addHandler(new FileHandler());
		} catch (SecurityException e) {
			logger.setLevel(Level.OFF);
			logger.log(Level.WARNING, e.toString(), new JxnetException(e.toString()));
		} catch (FileNotFoundException e) {
			logger.setLevel(Level.OFF);
			logger.log(Level.WARNING, e.toString(), new JxnetException(e.toString()));
		} catch (IOException e) {
			logger.setLevel(Level.OFF);
			logger.log(Level.WARNING, e.toString(), new JxnetException(e.toString()));
		}
	}
	
}