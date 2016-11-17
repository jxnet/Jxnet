package com.ardikars.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PcapDumpOpen.class, PcapFindAllDevs.class, PcapLookupDev.class, PcapLookupNet.class })
public class AllTests {

}
