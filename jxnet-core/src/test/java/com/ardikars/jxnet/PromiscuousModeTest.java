package com.ardikars.jxnet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PromiscuousModeTest {

    @Test
    public void promiscuous() {
        PromiscuousMode mode = PromiscuousMode.valueOf("PROMISCUOUS");
        assert mode == PromiscuousMode.PROMISCUOUS;
        assert mode.getValue() == 1;
    }

    @Test
    public void nonPromiscuous() {
        PromiscuousMode mode = PromiscuousMode.valueOf("NON_PROMISCUOUS");
        assert mode == PromiscuousMode.NON_PROMISCUOUS;
        assert mode.getValue() == 0;
    }

}
