package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.exception.JxnetException;

import static com.ardikars.jxnet.Jxnet.*;
import static com.ardikars.jxnet.Jxnet.PcapGetErr;

public class PcapActivate {

    @org.junit.Test
    public void run() {

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = PcapCreate(AllTests.deviceName, errbuf);
        if (pcap == null) {
            throw new JxnetException(PcapGetErr(pcap));
        }
        if (PcapSetSnaplen(pcap, AllTests.snaplen) != 0) {
            throw new JxnetException(PcapGetErr(pcap));
        }
        if (PcapSetPromisc(pcap, AllTests.promisc) !=0 ) {
            throw new JxnetException(PcapGetErr(pcap));
        }
        if (PcapSetTimeout(pcap, AllTests.to_ms) !=0 ) {
            throw new JxnetException(PcapGetErr(pcap));
        }
        if (PcapSetImmediateMode(pcap, AllTests.immediate) !=0 ) {
            throw new JxnetException(PcapGetErr(pcap));
        }
        if (PcapCanSetRfMon(pcap) == 1) {
            if (PcapSetRfMon(pcap, 1) != 0) {
                throw new JxnetException(PcapGetErr(pcap));
            }
        } else {
            if (PcapSetRfMon(pcap, 0) != 0) {
                throw new JxnetException(PcapGetErr(pcap));
            }
        }
        if (Jxnet.PcapActivate(pcap) != 0 ) {
            throw new JxnetException(PcapGetErr(pcap));
        }

        AllTests.nextPacket(pcap);

        PcapClose(pcap);

    }

}
