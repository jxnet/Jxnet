package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.JxnetException;
import com.ardikars.jxnet.util.AddrUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.nio.ByteBuffer;

@RunWith(Suite.class)
@SuiteClasses({ PcapActivate.class, PcapSetFilter.class, PcapDump.class, PcapFindAllDevs.class,
		PcapLookupDev.class, PcapLookupNet.class, Generic.class, Error.class,
		PcapNextEx.class, PcapOpenDead.class, PcapOpenLive.class,
		PcapOpenOffline.class, PcapBreakLoop.class, Blocking.class,
		PcapDatalink.class, PcapDispatch.class, Preconditions.class })
public class AllTests {

	private static StringBuilder errbuf = new StringBuilder();

	public static final String deviceName = AddrUtils.LookupDev(errbuf);
	public static final int snaplen = 1500;
	public static final int promisc = 1;
	public static final int to_ms = 500;

	public static final int immediate = 1;

	public static final int maxIteration = 5;

	public static final String filter = "icmp";

	public static final int optimize = 1;

	public static int netmask = 0xffffff;

	public static Pcap openHandle() throws JxnetException {
		StringBuilder errbuf = new StringBuilder();
		Pcap pcap = PcapCreate(deviceName, errbuf);
		if (pcap == null) {
			throw new JxnetException(PcapGetErr(pcap));
		}
		if (PcapSetSnaplen(pcap, snaplen) != 0) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		if (PcapSetPromisc(pcap, promisc) !=0 ) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		if (PcapSetTimeout(pcap, to_ms) !=0 ) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		if (PcapSetImmediateMode(pcap, immediate) !=0 ) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		if (Jxnet.PcapActivate(pcap) != 0 ) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		return pcap;
	}

	public static PcapHandler<String> handler = (user, h, bytes) -> {
		System.out.println("User   : " + user);
		System.out.println("Header : " + h);
		System.out.println("Packet : " + bytes);
		System.out.println("=======");
	};

	public static void nextPacket(Pcap pcap) {
		PcapPktHdr h = new PcapPktHdr();
		for (int i=0; i<maxIteration; i++) {
			ByteBuffer bytes = PcapNext(pcap, h);
			if (bytes != null) {
				System.out.println("Header : " + h);
				System.out.println("Packet : " + bytes);
				System.out.println("=======");
			} else {
				System.out.println("Timeout.");
			}
		}
	}

	public static BpfProgram filter(Pcap pcap, String filter, int optimize, int netmask) {
		BpfProgram fp = new BpfProgram();
		PcapCompile(pcap, fp, filter, optimize, netmask);
		PcapSetFilter(pcap, fp);
		return fp;
	}

	static {
		Inet4Address addr = new Inet4Address();
		Inet4Address mask = new Inet4Address();
		if (PcapLookupNet(deviceName, addr, mask, errbuf) != 0) {
			System.err.println(errbuf);
		} else {
			netmask = mask.toInt();
		}
	}
}
