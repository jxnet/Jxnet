package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.JxnetException;
import org.junit.*;

public class Error {

    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();

        Pcap pcap = Jxnet.PcapCreate(AllTests.deviceName, errbuf);

        // Make error message
        if (Jxnet.PcapCanSetRfMon(pcap) == 0) {
            Jxnet.PcapSetRfMon(pcap, 1);
        }

        int res = Jxnet.PcapActivate(pcap);
        System.out.println(Jxnet.PcapStrError(res));
        if (res != 0) {
            //Jxnet.PcapPError(pcap, ""); // it's tested
        }
        Jxnet.PcapClose(pcap);

    }

}
