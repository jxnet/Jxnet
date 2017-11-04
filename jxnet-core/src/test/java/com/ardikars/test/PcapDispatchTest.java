package com.ardikars.test;

import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.PcapCloseException;
import org.junit.Test;

import static com.ardikars.jxnet.Jxnet.*;
import static com.ardikars.jxnet.Jxnet.PcapDispatch;
import static com.ardikars.jxnet.Jxnet.PcapOpenLive;

@SuppressWarnings("unchecked")
public class PcapDispatchTest {

    @Test
    public void run() throws PcapCloseException {

        Pcap handler = AllTests.openHandle(); // Exception already thrown

        if (PcapDispatch(handler, AllTests.maxIteration, AllTests.handler, null) != 0) {
            String err = PcapGetErr(handler);
            //Jxnet.PcapClose(handler);
            //throw new JxnetException(err);
        }
        PcapClose(handler);
    }

}
