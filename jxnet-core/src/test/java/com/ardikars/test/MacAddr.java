package com.ardikars.test;

import com.ardikars.jxnet.MacAddress;

public class MacAddr {

    @org.junit.Test
    public void run() {
        MacAddress address = MacAddress.fromNicName(AllTests.deviceName);
        System.out.println(address);
    }
}