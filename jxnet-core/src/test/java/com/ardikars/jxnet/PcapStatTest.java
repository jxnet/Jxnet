package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.OperationNotSupportedException;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapStatTest extends BaseTest {

    @Test
    @Override
    public void registerTest() {
        assert true;
    }

    @Test
    @Override
    public void equalsAndHashCodeTest() throws CloneNotSupportedException {
        PcapStat stat = new PcapStat(1L, 2L, 3L);
        PcapStat stat0 = new PcapStat(1L, 2L, 3L);
        PcapStat stat1 = new PcapStat(2L, 3L, 4L);
        PcapStat stat2 = new PcapStat(1L, 1L, 4L);
        PcapStat stat3 = new PcapStat(1L, 2L, 3L);
        assert stat0.equals(stat0);
        assert !stat0.equals(null);
        assert !stat0.equals(stat1);
        assert !stat0.equals(stat2);
        assert stat0.equals(stat3);
        assert stat.equals(stat0);
        assert stat.equals(stat.clone());
        assert stat.hashCode() == stat0.hashCode();
        assert stat.hashCode() == stat.clone().hashCode();
    }

    @Test
    @Override
    public void toStringTest() {
        PcapStat stat = new PcapStat(1L, 2L, 3L);
        assert !stat.toString().equals("");
    }

    @Test
    @Override
    public void getterTest() {
        PcapStat stat = new PcapStat(1L, 2L, 3L);
        assert stat.getPsRecv() == 1L;
        assert stat.getPsDrop() == 2L;
        assert stat.getPsIfdrop() == 3L;
    }

    @Test(expected = OperationNotSupportedException.class)
    @Override
    public void newInstanceTest() {
        PcapStat.newInstance();
    }

}
