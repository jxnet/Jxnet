package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import org.junit.Test;

// 12
public class PcapCloseTest {

    @Test
    public void run() {
        Pcap pcap = AllTests.openHandle();
        Jxnet.PcapClose(pcap);
    }

}
