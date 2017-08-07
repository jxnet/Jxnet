package com.ardikars.jxnet.logger.test;

import com.ardikars.jxnet.logger.DefaultPrinter;
import com.ardikars.jxnet.logger.Logger;
import com.ardikars.jxnet.logger.Printer;
import org.junit.Test;

/**
 * Created by root on 8/7/17.
 */
public class LoggerTest {

    Logger logger = Logger.getLogger(LoggerTest.class, new DefaultPrinter());

    @Test
    public void test() {
        Logger.setLevel(Logger.Level.WARN);
        logger.warn("Hello world");
    }

}
