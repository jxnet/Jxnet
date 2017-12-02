package com.ardikars.test;

import com.ardikars.jxnet.util.Platforms;
import org.junit.Test;

public class PlatformTest {

    @Test
    public void test() {
        System.out.println(Platforms.getName());
        System.out.println(Platforms.getArchitecture());
    }

}
