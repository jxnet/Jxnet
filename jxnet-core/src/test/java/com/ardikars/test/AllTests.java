package com.ardikars.test;

import com.ardikars.jxnet.util.AddrUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PcapDumpOpen.class, PcapFindAllDevs.class,
		PcapLookupDev.class, PcapLookupNet.class, PcapLoop.class,
		PcapNext.class, PcapOpenDead.class, PcapOpenLive.class,
		PcapOpenOffline.class, PcapBreakLoop.class,
		PcapDatalink.class, PcapDispatch.class, Preconditions.class, Buffer.class })
public class AllTests {
	private static StringBuilder errbuf = new StringBuilder();
	public static final String deviceName = AddrUtils.LookupDev(errbuf);
	public static final int snaplen = 1500;
	public static final int promisc = 1;
	public static final int to_ms = 2000;
}
