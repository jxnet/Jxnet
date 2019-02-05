package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImmediateModeTest {

    @Test
    public void immediate() {
        ImmediateMode immediateMode = ImmediateMode.valueOf("IMMEDIATE");
        assert immediateMode == ImmediateMode.IMMEDIATE;
        assert immediateMode.getValue() == 1;
    }

    @Test
    public void nonImmediate() {
        ImmediateMode immediateMode = ImmediateMode.valueOf("NON_IMMEDIATE");
        assert immediateMode == ImmediateMode.NON_IMMEDIATE;
        assert immediateMode.getValue() == 0;
    }

}
