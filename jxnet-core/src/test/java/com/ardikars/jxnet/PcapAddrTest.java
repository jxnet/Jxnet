package com.ardikars.jxnet;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.jxnet.exception.OperationNotSupportedException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapAddrTest extends BaseTest {

    private SockAddr sockAddr1 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
            Inet4Address.valueOf("192.168.1.1").getAddress());
    private SockAddr sockAddr2 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
            Inet4Address.valueOf("192.168.1.2").getAddress());
    private SockAddr sockAddr3 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
            Inet4Address.valueOf("192.168.1.3").getAddress());
    private SockAddr sockAddr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
            Inet4Address.valueOf("192.168.1.4").getAddress());

    @Override
    public void registerTest() {
        assert true;
    }

    @Test
    @Override
    public void equalsAndHashCodeTest() throws CloneNotSupportedException {
        PcapAddr pcapAddr = new PcapAddr(sockAddr1, sockAddr2, sockAddr3, sockAddr4);
        PcapAddr pcapAddr0 = new PcapAddr(sockAddr1, sockAddr2, sockAddr3, sockAddr4);
        PcapAddr pcapAddr1 = new PcapAddr(sockAddr2, sockAddr2, sockAddr3, sockAddr4);
        PcapAddr pcapAddr2 = new PcapAddr(sockAddr1, sockAddr1, sockAddr3, sockAddr4);
        PcapAddr pcapAddr3 = new PcapAddr(sockAddr1, sockAddr2, sockAddr1, sockAddr4);
        PcapAddr pcapAddr4 = new PcapAddr(sockAddr1, sockAddr2, sockAddr3, sockAddr1);
        assert pcapAddr0.equals(pcapAddr0);
        assert !pcapAddr0.equals(null);
        assert !pcapAddr0.equals(pcapAddr1);
        assert !pcapAddr0.equals(pcapAddr2);
        assert !pcapAddr0.equals(pcapAddr3);
        assert !pcapAddr0.equals(pcapAddr4);
        assert pcapAddr.equals(pcapAddr0);
        assert pcapAddr.equals(pcapAddr.clone());
        assert pcapAddr.hashCode() == pcapAddr0.hashCode();
        assert pcapAddr.hashCode() == pcapAddr.clone().hashCode();
    }

    @Override
    public void toStringTest() {
        PcapAddr pcapAddr = new PcapAddr(sockAddr1, sockAddr2, sockAddr3, sockAddr4);
        assert !pcapAddr.toString().equals("");
    }

    @Test
    @Override
    public void getterTest() {
        SockAddr addr1 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.valueOf("192.168.1.1").getAddress());
        SockAddr addr2 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.valueOf("192.168.1.2").getAddress());
        SockAddr addr3 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.valueOf("192.168.1.3").getAddress());
        SockAddr addr4 = new SockAddr(SockAddr.Family.AF_INET.getValue(),
                Inet4Address.valueOf("192.168.1.4").getAddress());
        PcapAddr pcapAddr = new PcapAddr(sockAddr1, sockAddr2, sockAddr3, sockAddr4);
        assert pcapAddr.getAddr().equals(addr1);
        assert pcapAddr.getNetmask().equals(addr2);
        assert pcapAddr.getBroadAddr().equals(addr3);
        assert pcapAddr.getDstAddr().equals(addr4);
    }

    @Test(expected = OperationNotSupportedException.class)
    @Override
    public void newInstanceTest() {
        PcapAddr.newInstance();
    }

}
