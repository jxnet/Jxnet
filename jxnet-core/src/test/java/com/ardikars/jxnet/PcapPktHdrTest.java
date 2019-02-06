package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapPktHdrTest extends BaseTest {

    @Test
    @Override
    public void registerTest() {
        assert true;
    }

    @Test
    @Override
    public void equalsAndHashCodeTest() throws CloneNotSupportedException {
        PcapPktHdr pktHdr0 = new PcapPktHdr(50, 60, 1000, 2000L);
        PcapPktHdr pktHdr1 = new PcapPktHdr(51, 60, 1000, 2000L);
        PcapPktHdr pktHdr2 = new PcapPktHdr(50, 61, 1000, 2000L);
        PcapPktHdr pktHdr3 = new PcapPktHdr(50, 60, 1001, 2000L);
        PcapPktHdr pktHdr4 = new PcapPktHdr(50, 60, 1000, 2001L);
        PcapPktHdr pktHdr5 = new PcapPktHdr(50, 60, 1000, 2000L);
        assert pktHdr0.equals(pktHdr0);
        assert !pktHdr0.equals(null);
        assert !pktHdr0.equals(pktHdr1);
        assert !pktHdr0.equals(pktHdr2);
        assert !pktHdr0.equals(pktHdr2);
        assert !pktHdr0.equals(pktHdr3);
        assert !pktHdr0.equals(pktHdr4);
        assert pktHdr0.equals(pktHdr5);
        assert pktHdr0.equals(pktHdr0.clone());
        assert pktHdr0.hashCode() == pktHdr5.hashCode();
        assert pktHdr0.hashCode() == pktHdr0.clone().hashCode();
    }

    @Test
    @Override
    public void toStringTest() {
        PcapPktHdr pktHdr = new PcapPktHdr(50, 60, 1000, 2000L);
        assert !pktHdr.toString().equals("");
    }

    @Test
    @Override
    public void getterTest() {
        PcapPktHdr pktHdr = new PcapPktHdr(50, 60, 1000, 2000L);
        assert pktHdr.getCapLen() == 50;
        assert pktHdr.getLen() == 60;
        assert pktHdr.getTvSec() == 1000;
        assert pktHdr.getTvUsec() == 2000L;
    }

    @Test
    @Override
    public void newInstanceTest() {
        PcapPktHdr pktHdr = new PcapPktHdr(50, 60, 1000, 2000L);
        assert pktHdr != null;
    }

    @Test
    public void copyTest() {
        PcapPktHdr pktHdr = new PcapPktHdr(50, 60, 1000, 2000L);
        assert pktHdr.equals(pktHdr.copy());
    }

}
