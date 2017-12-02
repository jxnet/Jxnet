package com.ardikars.test;

import com.ardikars.jxnet.*;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.*;

// 9
public class PcapSetFilterTest {

    @Test
    public void run() throws PcapCloseException {
        Pcap handler = AllTests.openHandle(); // Exception already thrown
        BpfProgram fp = AllTests.
                filter(handler, AllTests.filter,
                        AllTests.optimize, AllTests.netmask);
        Jxnet.PcapCompile(handler, fp, "icmp", 1, 0xffffffff);
        Jxnet.PcapSetFilter(handler, fp);
        Jxnet.PcapFreeCode(fp);
        PcapClose(handler);

    }

}