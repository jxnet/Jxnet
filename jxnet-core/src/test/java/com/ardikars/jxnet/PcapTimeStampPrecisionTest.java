package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PcapTimeStampPrecisionTest {

    @Test
    public void micro() {
        PcapTimestampPrecision precision = PcapTimestampPrecision.valueOf("MICRO");
        assert precision == PcapTimestampPrecision.MICRO;
        assert precision.getValue() == 0;
    }

    @Test
    public void nano() {
        PcapTimestampPrecision precision = PcapTimestampPrecision.valueOf("NANO");
        assert precision == PcapTimestampPrecision.NANO;
        assert precision.getValue() == 1;
    }

}
