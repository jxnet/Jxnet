package com.ardikars.test;

import com.ardikars.jxnet.Inet6Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Inet6AddressTest {

    @Test
    public void string() {
        System.out.println(Inet6Address.ZERO);
        System.out.println(Inet6Address.valueOf("::1"));
    }

}
