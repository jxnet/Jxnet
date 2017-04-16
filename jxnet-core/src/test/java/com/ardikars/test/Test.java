package com.ardikars.test;

import com.ardikars.jxnet.MacAddress;
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
        System.out.println(AddrUtils.getHardwareAddress("\\Device\\NPF_{5FA73811-1A2B-47DD-BA55-D2809F378B97}"));
    }

    static {
        System.loadLibrary("jxnet");
    }
}
