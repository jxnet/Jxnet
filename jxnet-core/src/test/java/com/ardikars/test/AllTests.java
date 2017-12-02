package com.ardikars.test;

import static com.ardikars.jxnet.Jxnet.*;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.util.Platforms;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RunWith(Suite.class)
@SuiteClasses({ PcapActivateTest.class, PcapSetFilterTest.class, PcapFindAllDevsTest.class,
		PcapLookupDevTest.class, PcapLookupNetTest.class, GenericTest.class, ErrorTest.class,
		PcapNextExTest.class, PcapOpenDeadTest.class, PcapOpenLiveTest.class,
		PcapOpenOfflineTest.class, PcapBreakLoopTest.class, BlockingTest.class,
		PcapDatalinkTest.class, PcapDispatchTest.class, PreconditionsTest.class,
		MacAddrTest.class, PcapDumpTest.class} )
public class AllTests {

	private static StringBuilder errbuf = new StringBuilder();

	public static String rawData = " 6c3b 6b04 e427 0002 3b10 1c09 8864 1100 " +
			" 0026 002e 0021 4500 002c a432 0000 2206 " +
			" 28f3 b5bc d092 2451 2107 163d 01bd 00af " +
			" 9c22 0000 0000 6002 ffff 1818 0000 0204 " +
			" 0550";

	public static final String deviceName = lookupDev();
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
		System.out.println("Open " + deviceName + ".");
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
		if (Platforms.isLinux()) {
			if (PcapSetImmediateMode(pcap, immediate) != 0) {
				String err = PcapGetErr(pcap);
				PcapClose(pcap);
				throw new JxnetException(err);
			}
		}
		if (Jxnet.PcapActivate(pcap) != 0 ) {
			String err = PcapGetErr(pcap);
			PcapClose(pcap);
			throw new JxnetException(err);
		}
		return pcap;
	}

	public static PcapHandler<String> handler = new PcapHandler<String>() {
		@Override
		public void nextPacket(String user, PcapPktHdr h, ByteBuffer bytes) {
			System.out.println("User   : " + user);
			System.out.println("Header : " + h);
			System.out.println("Packet : " + bytes);
			System.out.println("=======");
		}
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

	public static String lookupDev() {
		StringBuilder errbuf = new StringBuilder();
		List<PcapIf> alldevsp = new ArrayList<PcapIf>();
		PcapFindAllDevs(alldevsp, errbuf);
		String devName = null;
		for (PcapIf dev : alldevsp) {
			System.out.println("================================================\n\n");
			System.out.println("Name                  = " + dev.getName());
			System.out.println("Description           = " + dev.getDescription());
			System.out.println("Flags                 = " + dev.getFlags());

			for (PcapAddr addr : dev.getAddresses()) {
				if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
					System.out.println("------------------------------------------------");
					System.out.println("IPv4");
					System.out.println("Addr                   = " + addr.getAddr().toString());
					System.out.println("Netmask                = " + addr.getNetmask().toString());
					System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
					System.out.println("DstAddr                = " + addr.getDstAddr().toString());
					System.out.println("------------------------------------------------");
					if (addr.getAddr().getData() != null) {
						Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
						if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
							devName = dev.getName();
						}
					}
				} else if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET6) {
					System.out.println("------------------------------------------------");
					System.out.println("IPv6");
					System.out.println("Addr                   = " + addr.getAddr().toString());
					System.out.println("Netmask                = " + addr.getNetmask().toString());
					System.out.println("BroadAddr              = " + addr.getBroadAddr().toString());
					System.out.println("DstAddr                = " + addr.getDstAddr().toString());
					System.out.println("------------------------------------------------");
				}
			}
			//System.out.println(dev.getAddresses());

			Assert.assertNotEquals(null, dev.getName());
			System.out.println("================================================\n\n");
		}
		return devName;
	}

	static {
		Inet4Address addr = Inet4Address.ZERO;
		Inet4Address mask = Inet4Address.ZERO;
		if (PcapLookupNet(deviceName, addr, mask, errbuf) != 0) {
			System.err.println(errbuf);
		} else {
			netmask = mask.toInt();
		}
	}
}
