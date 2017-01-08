package com.ardikars.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({  PcapDumpOpen.class, PcapFindAllDevs.class,
		PcapLookupDev.class, PcapLookupNet.class, PcapLoop.class,
		PcapNext.class, PcapOpenDead.class, PcapOpenLive.class,
		PcapOpenOffline.class })
public class AllTests
{
	public static final String deviceName = "eth0";
	public static final int snaplen = 1500;
	public static final int promisc = 1;
	public static final int to_ms = 2000;
}
