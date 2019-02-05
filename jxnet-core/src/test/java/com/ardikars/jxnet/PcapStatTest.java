package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapStatTest {

    private PcapStat stat = new PcapStat(1l, 2l, 3l);
    private PcapStat stat1 = new PcapStat(2l, 3l, 4l);
    private PcapStat stat2 = new PcapStat(1l, 1l, 4l);
    private PcapStat stat3 = new PcapStat(1l, 2l, 3l);

    @Test
    public void stat() {
        assert stat.equals(stat);
        assert stat.equals(null) == false;
        assert stat.equals(stat1) == false;
        assert stat.equals(stat2) == false;
        assert stat.equals(stat3);
        System.out.println(stat.hashCode());
        try {
            PcapStat s = stat.clone();
            assert s.equals(stat);
        } catch (CloneNotSupportedException e) {
            assert false;
        }
    }

}
