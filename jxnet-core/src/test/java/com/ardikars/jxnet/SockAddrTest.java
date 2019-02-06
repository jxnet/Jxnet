package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.Inet6Address;
import com.ardikars.jxnet.exception.OperationNotSupportedException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SockAddrTest extends BaseTest {

    @Test
    @Override
    public void registerTest() {
        SockAddr.Family.register(SockAddr.Family.AF_INET);
        SockAddr.Family.register(SockAddr.Family.AF_INET6);
        assert SockAddr.Family.valueOf(SockAddr.Family.AF_INET.getValue()) == SockAddr.Family.AF_INET;
        assert SockAddr.Family.valueOf(SockAddr.Family.AF_INET6.getValue()) == SockAddr.Family.AF_INET6;
    }

    @Test
    @Override
    public void equalsAndHashCodeTest() throws CloneNotSupportedException {
        SockAddr addr = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.LOCALHOST.getAddress());
        SockAddr addr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.LOCALHOST.getAddress());
        SockAddr addr4Zero = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.ZERO.getAddress());
        SockAddr addr6 = new SockAddr(SockAddr.Family.AF_INET6.getValue(),
                Inet4Address.LOCALHOST.getAddress());
        SockAddr addr6Zero = new SockAddr(SockAddr.Family.AF_INET6.getValue(),
                Inet4Address.ZERO.getAddress());
        assert addr4.equals(addr4);
        assert addr6.equals(addr6);
        assert !addr4.equals(null);
        assert !addr6.equals(null);
        assert !addr4.equals(addr6);
        assert !addr6.equals(addr4);
        assert !addr4.equals(addr4Zero);
        assert !addr6.equals(addr6Zero);
        assert addr.equals(addr.clone());
        assert addr.hashCode() == addr.hashCode();
        assert addr.hashCode() == addr.clone().hashCode();
    }

    @Test
    @Override
    public void toStringTest() {
        SockAddr addr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.LOCALHOST.getAddress());
        SockAddr addr6 = new SockAddr(SockAddr.Family.AF_INET6.getValue(),
                Inet6Address.LOCALHOST.getAddress());
        assert addr4.toString().equals(Inet4Address.LOCALHOST.toString());
        assert addr4.getSaFamily().toString() != "";
        assert addr6.toString().equals(Inet6Address.LOCALHOST.toString());
        assert addr6.getSaFamily().toString() != "";
    }

    @Test
    @Override
    public void getterTest() {
        SockAddr addr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(), null);
        SockAddr addr6 = new SockAddr(SockAddr.Family.AF_INET6.getValue(), null);
        assert addr4.getData().length == Inet4Address.IPV4_ADDRESS_LENGTH;
        assert addr4.getSaFamily() == SockAddr.Family.AF_INET;
        assert addr6.getData().length == Inet6Address.IPV6_ADDRESS_LENGTH;
        assert addr6.getSaFamily() == SockAddr.Family.AF_INET6;
    }

    @Test(expected = OperationNotSupportedException.class)
    @Override
    public void newInstanceTest() {
        SockAddr.newInstance();
    }

}
