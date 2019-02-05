package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SockAddrTest {

    private SockAddr sockAddr = new SockAddr((short) 2, Inet4Address.LOCALHOST.getAddress());

    @Test
    public void register() {
        SockAddr.Family.register(SockAddr.Family.AF_INET);
        SockAddr.Family.register(SockAddr.Family.AF_INET6);
        System.out.println(sockAddr);
        System.out.println(sockAddr.getData().length);
        System.out.println(sockAddr.hashCode());
    }

    @Test
    public void equals() {
        SockAddr addr = new SockAddr((short) 2, Inet4Address.LOCALHOST.getAddress());
        assert sockAddr.equals(sockAddr);
        assert addr.equals(sockAddr);
        assert sockAddr.equals(null) == false;
        SockAddr addr2 = new SockAddr((short) 4, Inet4Address.LOCALHOST.getAddress());
        assert sockAddr.equals(addr2) == false;
    }

    @Test
    public void getData() {
        SockAddr addr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(), null);
        SockAddr addr6 = new SockAddr(SockAddr.Family.AF_INET.getValue(), null);
        assert addr4.getData() != null;
        assert addr6.getData() != null;
    }

}
