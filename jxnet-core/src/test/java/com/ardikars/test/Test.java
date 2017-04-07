package com.ardikars.test;

import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapDirection;

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
        Inet4Address addr = Inet4Address.valueOf("128.42.5.4");
        Inet4Address mask = Inet4Address.valueOf("255.255.248.0");
        System.out.println(Inet4Address.valueOf(addr.toInt() & mask.toInt()));
    }
}
