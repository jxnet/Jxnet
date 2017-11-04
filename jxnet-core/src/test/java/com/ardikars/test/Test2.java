package com.ardikars.test;

import com.ardikars.jxnet.util.Validate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class Test2 {

    @Test
    public void test() {
        byte startByte = 1;
        byte endByte = 10;
        byte myBytes = 4;
        Validate.between(startByte, endByte, myBytes);
    }

    @Test
    public void bounds() {
    }


}
