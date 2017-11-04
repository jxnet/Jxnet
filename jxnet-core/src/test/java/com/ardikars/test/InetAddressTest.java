package com.ardikars.test;

import com.ardikars.jxnet.InetAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class InetAddressTest {

    @Test
    public void inet4Address() {
        System.out.println(InetAddress.isValidAddress("10.34.4.4"));
    }

    @Test
    public void inet6Address() {
        System.out.println(InetAddress.isValidAddress(":s:"));
    }

}
