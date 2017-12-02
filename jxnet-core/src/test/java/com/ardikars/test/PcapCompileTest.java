package com.ardikars.test;

import com.ardikars.jxnet.BpfProgram;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.PcapClose;

// 8
public class PcapCompileTest {

    @Test
    public void run() throws PcapCloseException {
        Pcap handler = AllTests.openHandle(); // Exception already thrown
        BpfProgram fp = AllTests.
                filter(handler, AllTests.filter,
                        AllTests.optimize, AllTests.netmask);
        Jxnet.PcapCompile(handler, fp, "icmp" , 1, 0xffffffff);
        Jxnet.PcapFreeCode(fp);
        PcapClose(handler);

    }

}
