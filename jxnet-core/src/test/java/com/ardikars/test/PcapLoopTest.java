package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import org.junit.Test;


import static com.ardikars.jxnet.Jxnet.PcapClose;
import static com.ardikars.jxnet.Jxnet.PcapLoop;

// 3
public class PcapLoopTest {

    @Test
    public void run() {
        Pcap handle = AllTests.openHandle(); // Exception already thrown
        PcapLoop(handle, AllTests.maxIteration, AllTests.handler, "");
        PcapClose(handle);
    }

}
