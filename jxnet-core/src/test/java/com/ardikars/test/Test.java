package com.ardikars.test;

import com.ardikars.jxnet.Jxnet;

/**
 * Jxnet Untested function
 *
 * PcapFile
 * PcapDumpFile
 * PcapDumpFOpen
 *
 * PcapCompileNoPcap
 */



public class Test {

    @org.junit.Test
    public void run() {
        StringBuilder errbuf = new StringBuilder();
        System.out.println(Jxnet.PcapLookupDev(errbuf));
    }

}
