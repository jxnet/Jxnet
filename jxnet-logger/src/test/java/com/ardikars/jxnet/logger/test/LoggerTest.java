package com.ardikars.jxnet.logger.test;

import com.ardikars.jxnet.logger.DefaultPrinter;
import com.ardikars.jxnet.logger.Logger;
import com.ardikars.jxnet.logger.Printer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.*;

@RunWith(JUnit4.class)
public class LoggerTest {

    Logger logger = Logger.getLogger(LoggerTest.class, new DefaultPrinter());

    @Test
    public void info() {
        Logger.setLevel(Logger.Level.INFO); // set level to printer
        Logger.Level.INFO.setColor(Printer.Color.PURPLE); // set color to info level, default white
        logger.info("Hello world");
        logger.info("Hello world", Logger.class);
    }

    @Test
    public void warn() {
        Logger.setLevel(Logger.Level.WARN); // set level to printer
        logger.warn("Hello world");
        logger.warn("Hello world", Logger.class);
    }

    @Test
    public void debug() {
        Logger.setLevel(Logger.Level.DEBUG); // set level to printer
        logger.debug("Hello world");
        logger.debug("Hello world", Logger.class);
    }

    @Test
    public void error() {
        Logger.setLevel(Logger.Level.ERROR); // set level to printer
        logger.error("Hello world");
        logger.error("Hello world", Logger.class);
    }

    @Test
    public void infoList() {
        List<TestClass> testClasses = Arrays.asList(
                new TestClass(1),
                new TestClass(2),
                new TestClass(3),
                new TestClass(4),
                new TestClass(5)
        );
        logger.info(testClasses);
        logger.info(testClasses, Logger.class);
    }

    @Test
    public void warnList() {
        List<TestClass> testClasses = Arrays.asList(
                new TestClass(1),
                new TestClass(2),
                new TestClass(3),
                new TestClass(4),
                new TestClass(5)
        );
        logger.warn(testClasses);
        logger.warn(testClasses, Logger.class);
    }

    @Test
    public void debugList() {
        List<TestClass> testClasses = Arrays.asList(
                new TestClass(1),
                new TestClass(2),
                new TestClass(3),
                new TestClass(4),
                new TestClass(5)
        );
        logger.debug(testClasses);
        logger.debug(testClasses, Logger.class);
    }

    @Test
    public void errorList() {
        List<TestClass> testClasses = Arrays.asList(
                new TestClass(1),
                new TestClass(2),
                new TestClass(3),
                new TestClass(4),
                new TestClass(5)
        );
        logger.error(testClasses);
        logger.error(testClasses, Logger.class);
    }

    @Test
    public void infoSet() {
        Set<TestClass> testClasses = new HashSet<>();
        testClasses.add(new TestClass(1));
        testClasses.add(new TestClass(2));
        testClasses.add(new TestClass(3));
        testClasses.add(new TestClass(4));
        testClasses.add(new TestClass(5));
        logger.info(testClasses);
        logger.info(testClasses, Logger.class);
    }

    @Test
    public void warnSet() {
        Set<TestClass> testClasses = new HashSet<>();
        testClasses.add(new TestClass(1));
        testClasses.add(new TestClass(2));
        testClasses.add(new TestClass(3));
        testClasses.add(new TestClass(4));
        testClasses.add(new TestClass(5));
        logger.warn(testClasses);
        logger.warn(testClasses, Logger.class);
    }

    @Test
    public void debugSet() {
        Set<TestClass> testClasses = new HashSet<>();
        testClasses.add(new TestClass(1));
        testClasses.add(new TestClass(2));
        testClasses.add(new TestClass(3));
        testClasses.add(new TestClass(4));
        testClasses.add(new TestClass(5));
        logger.debug(testClasses);
        logger.debug(testClasses, Logger.class);
    }

    @Test
    public void errorSet() {
        Set<TestClass> testClasses = new HashSet<>();
        testClasses.add(new TestClass(1));
        testClasses.add(new TestClass(2));
        testClasses.add(new TestClass(3));
        testClasses.add(new TestClass(4));
        testClasses.add(new TestClass(5));
        logger.error(testClasses);
        logger.error(testClasses, Logger.class);
    }

    @Test
    public void infoMap() {
        Map<Integer, TestClass> testClassMap = new HashMap<>();
        testClassMap.put(1, new TestClass(1));
        testClassMap.put(2, new TestClass(2));
        testClassMap.put(3, new TestClass(3));
        testClassMap.put(4, new TestClass(4));
        testClassMap.put(5, new TestClass(5));
        logger.info(testClassMap);
        logger.info(testClassMap, Logger.class);
    }

    @Test
    public void warnMap() {
        Map<Integer, TestClass> testClassMap = new HashMap<>();
        testClassMap.put(1, new TestClass(1));
        testClassMap.put(2, new TestClass(2));
        testClassMap.put(3, new TestClass(3));
        testClassMap.put(4, new TestClass(4));
        testClassMap.put(5, new TestClass(5));
        logger.warn(testClassMap);
        logger.warn(testClassMap, Logger.class);
    }

    @Test
    public void debugMap() {
        Map<Integer, TestClass> testClassMap = new HashMap<>();
        testClassMap.put(1, new TestClass(1));
        testClassMap.put(2, new TestClass(2));
        testClassMap.put(3, new TestClass(3));
        testClassMap.put(4, new TestClass(4));
        testClassMap.put(5, new TestClass(5));
        logger.debug(testClassMap);
        logger.debug(testClassMap, Logger.class);
    }

    @Test
    public void errorMap() {
        Map<Integer, TestClass> testClassMap = new HashMap<>();
        testClassMap.put(1, new TestClass(1));
        testClassMap.put(2, new TestClass(2));
        testClassMap.put(3, new TestClass(3));
        testClassMap.put(4, new TestClass(4));
        testClassMap.put(5, new TestClass(5));
        logger.error(testClassMap);
        logger.error(testClassMap, Logger.class);
    }

    @Test
    public void infoStatic() {
        Logger.info("Hello World", LoggerTest.class,
                (message, clazz, level) -> System.out.println(message));
    }

    class TestClass {

        private final int number;

        public TestClass(int number) {
            this.number = number;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestClass)) return false;

            TestClass testClass = (TestClass) o;

            return number == testClass.number;
        }

        @Override
        public int hashCode() {
            return number;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("TestClass{");
            sb.append("number=").append(number);
            sb.append('}');
            return sb.toString();
        }

    }

}
