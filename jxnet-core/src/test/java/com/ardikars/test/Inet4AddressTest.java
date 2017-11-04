package com.ardikars.test;

import com.ardikars.jxnet.Inet4Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Inet4AddressTest {

    @Test
    public void string() {
        String str = "12.3.3.1";
        System.out.println(Inet4Address.valueOf(str));
    }

}
