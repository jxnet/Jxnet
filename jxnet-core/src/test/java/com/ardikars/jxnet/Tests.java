package com.ardikars.jxnet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Tests {

    @Test
    public void PcapLibVersion() {
        Assert.assertNotEquals(Jxnet.PcapLibVersion(), null);
    }

}
