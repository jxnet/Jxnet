package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RadioFrequencyMonitorModeTest {

    @Test
    public void rfmon() {
        RadioFrequencyMonitorMode monitorMode = RadioFrequencyMonitorMode.valueOf("RFMON");
        assert monitorMode == RadioFrequencyMonitorMode.RFMON;
        assert monitorMode.getValue() == 1;
    }

    @Test
    public void nonRfmon() {
        RadioFrequencyMonitorMode monitorMode = RadioFrequencyMonitorMode.valueOf("NON_RFMON");
        assert monitorMode == RadioFrequencyMonitorMode.NON_RFMON;
        assert monitorMode.getValue() == 0;
    }

}
