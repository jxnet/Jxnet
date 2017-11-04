package com.ardikars.test;

import com.ardikars.jxnet.*;

import static com.ardikars.jxnet.Jxnet.*;

public class PcapSetFilterTest {

    @org.junit.Test
    public void run() {

        Pcap handler = AllTests.openHandle(); // Exception already thrown
        BpfProgram fp = AllTests.
                filter(handler, AllTests.filter,
                AllTests.optimize, AllTests.netmask);

        AllTests.nextPacket(handler);
        PcapClose(handler);
        PcapFreeCode(fp);
    }
}
