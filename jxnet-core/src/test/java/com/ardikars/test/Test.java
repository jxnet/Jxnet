package com.ardikars.test;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDirection;
import com.ardikars.jxnet.util.AddrUtils;

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

    public static void main(String[] args) {
        System.out.println(AddrUtils.getGatewayAddress("eth0"));
    }
}
