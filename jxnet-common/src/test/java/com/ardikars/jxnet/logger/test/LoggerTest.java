package com.ardikars.jxnet.logger.test;

import com.ardikars.jxnet.logger.DefaultPrinter;
import com.ardikars.jxnet.logger.Logger;
import com.ardikars.jxnet.logger.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class LoggerTest {

    Logger logger = Logger.getLogger(LoggerTest.class, new DefaultPrinter());

    @Test
    public void info() {
        Logger.setLevel(Logger.Level.INFO); // set level to printer
        Logger.Level.INFO.setColor(Printer.Color.PURPLE); // set color to info level, default white
        logger.info("Hello world");
    }

    @Test
    public void warn() {
        Logger.setLevel(Logger.Level.WARN); // set level to printer
        logger.warn("Hello world");
    }

    @Test
    public void debug() {
        Logger.setLevel(Logger.Level.DEBUG); // set level to printer
        logger.debug("Hello world");
    }

    @Test
    public void error() {
        Logger.setLevel(Logger.Level.ERROR); // set level to printer
        logger.error("Hello world");
    }

}
