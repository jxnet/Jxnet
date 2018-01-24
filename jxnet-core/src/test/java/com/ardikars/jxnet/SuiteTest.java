package com.ardikars.jxnet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JxnetTest.class,
        CoreTest.class,
        MacAddress.class,
        FreakTest.class
})
public class SuiteTest {

    @Test
    public void LibraryTest() {
    }

}
