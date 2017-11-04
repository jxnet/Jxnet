package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapPktHdr;
import org.junit.Test;

import java.nio.ByteBuffer;

import static com.ardikars.jxnet.Jxnet.*;

public class PcapNextExTest {

    @Test
    public void run() {

        Pcap handler = AllTests.openHandle(); // Exception already thrown
        for (int i = 0; i < AllTests.maxIteration; i++) {
            PcapPktHdr pkthdr = new PcapPktHdr();
            ByteBuffer buf = ByteBuffer.allocate(AllTests.snaplen);
            int res = PcapNextEx(handler, pkthdr, buf);
            if (res == 0) {
                System.out.println("Timeout.");
            } else if (res == 1) {
                System.out.println("Header  : " + pkthdr);
                System.out.println("Packet  : " + buf);
            } else if (res == -1) {
                String err = PcapGetErr(handler);
                PcapClose(handler);
                throw new JxnetException(err);
            }
        }
        PcapClose(handler);
    }

}
