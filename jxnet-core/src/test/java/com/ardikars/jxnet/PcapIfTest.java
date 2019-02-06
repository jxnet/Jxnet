package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.OperationNotSupportedException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapIfTest extends BaseTest {

    @Test
    @Override
    public void registerTest() {
        assert true;
    }

    @Test
    @Override
    public void equalsAndHashCodeTest() throws CloneNotSupportedException {
        PcapIf pcapIf0 = new PcapIf("en0", "Ethernet 0", new ArrayList<PcapAddr>(), 10);
        PcapIf pcapIf1 = new PcapIf("en0", "Ethernet 0", new ArrayList<PcapAddr>(), 2);
        PcapIf pcapIf2 = new PcapIf("en1", "Ethernet 0", new ArrayList<PcapAddr>(), 10);
        PcapIf pcapIf3 = new PcapIf("en0", "Ethernet 1", new ArrayList<PcapAddr>(), 10);
        PcapIf pcapIf4 = new PcapIf("en0", "Ethernet 0", new ArrayList<PcapAddr>(), 10);
        assert pcapIf0.equals(pcapIf0);
        assert !pcapIf0.equals(null);
        assert !pcapIf0.equals(pcapIf1);
        assert !pcapIf0.equals(pcapIf2);
        assert !pcapIf0.equals(pcapIf3);
        assert pcapIf0.equals(pcapIf4);
        assert pcapIf0.equals(pcapIf0.clone());
        assert pcapIf0.hashCode() == pcapIf4.hashCode();
        assert pcapIf0.hashCode() == pcapIf0.clone().hashCode();
    }

    @Test
    @Override
    public void toStringTest() {
        PcapIf pcapIf = new PcapIf("en0", "Ethernet 0", new ArrayList<PcapAddr>(), 10);
        assert !pcapIf.toString().equals("");
    }

    @Test
    @Override
    public void getterTest() {
        PcapIf pcapIf = new PcapIf("en0", "Ethernet 0", new ArrayList<PcapAddr>(), 10);
        assert pcapIf.getAddresses() != null;
        assert pcapIf.getAddresses().isEmpty();
        assert pcapIf.getDescription().equals("Ethernet 0");
        assert pcapIf.getName().equals("en0");
        assert pcapIf.getFlags() == 10;
    }

    @Test(expected = OperationNotSupportedException.class)
    @Override
    public void newInstanceTest() {
        PcapIf.newInstance();
    }

    @Test(expected = OperationNotSupportedException.class)
    public void newInstance() {
        PcapIf.newInstance();
    }

}
