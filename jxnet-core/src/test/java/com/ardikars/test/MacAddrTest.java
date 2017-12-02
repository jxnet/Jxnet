package com.ardikars.test;

import com.ardikars.jxnet.MacAddress;

public class MacAddrTest {

    @org.junit.Test
    public void run() {
        System.loadLibrary("jxnet");
        MacAddress address = MacAddress.fromNicName("wlp2s0");
        System.out.println(address);
        System.out.println(MacAddress.isValidAddress("de:ad:be:ef:c0-fe"));
    }
}