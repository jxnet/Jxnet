package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDirection;
import com.ardikars.jxnet.PcapStat;
import com.ardikars.jxnet.exception.JxnetException;

import static com.ardikars.jxnet.Jxnet.*;
import static com.ardikars.jxnet.Jxnet.PcapGetErr;

public class PcapActivate {

    @org.junit.Test
    public void run() {

        StringBuilder errbuf = new StringBuilder();
        Pcap pcap = PcapCreate(AllTests.deviceName, errbuf);
        if (pcap == null) {
            throw new JxnetException("Filed create pcap handle.");
        }
        if (PcapSetSnaplen(pcap, AllTests.snaplen) != 0) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (PcapSetBufferSize(pcap, 10000) != 0) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (PcapSetPromisc(pcap, AllTests.promisc) !=0 ) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (PcapSetTimeout(pcap, AllTests.to_ms) !=0 ) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (PcapSetImmediateMode(pcap, AllTests.immediate) !=0 ) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (PcapCanSetRfMon(pcap) == 1) {
            if (PcapSetRfMon(pcap, 1) != 0) {
                String err = PcapGetErr(pcap);
                PcapClose(pcap);
                throw new JxnetException(err);
            }
        } else {
            if (PcapSetRfMon(pcap, 0) != 0) {
                String err = PcapGetErr(pcap);
                PcapClose(pcap);
                throw new JxnetException(err);
            }
        }
        if (Jxnet.PcapActivate(pcap) != 0 ) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }
        if (Jxnet.PcapSetDirection(pcap, PcapDirection.PCAP_D_IN) !=0 ) {
            String err = PcapGetErr(pcap);
            PcapClose(pcap);
            throw new JxnetException(err);
        }

        AllTests.nextPacket(pcap);

        PcapStat stat = new PcapStat();
        if (Jxnet.PcapStats(pcap, stat) != -1) {
            System.out.println(stat);
        } else {
            PcapClose(pcap);
            throw new JxnetException("PcapStat error");
        }
        PcapClose(pcap);

    }

}
