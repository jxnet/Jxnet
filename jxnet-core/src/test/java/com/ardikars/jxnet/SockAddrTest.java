package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SockAddrTest {

    @Test
    public void register() {
        SockAddr.Family.register(SockAddr.Family.AF_INET);
        SockAddr.Family.register(SockAddr.Family.AF_INET6);
        SockAddr sockAddr = new SockAddr();
        System.out.println(sockAddr);
    }

}
