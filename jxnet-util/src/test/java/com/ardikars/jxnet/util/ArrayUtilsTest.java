package com.ardikars.jxnet.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class ArrayUtilsTest {

    private Logger logger = LoggerFactory.getLogger(ArrayUtilsTest.class);

    @Test
    public void reverseByte() {
        byte[] data = new byte[] { 1, 2, 3, 4};
        byte[] reversed = new byte[] { 4, 3, 2, 1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseByte");
        }
    }

    @Test
    public void reverseChar() {
        char[] data = new char[] {'a', 'b', 'c', 'd'};
        char[] reversed = new char[] {'d', 'c', 'b', 'a'};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseChar");
        }
    }

    @Test
    public void reverseShort() {
        short[] data = new short[] {1, 2, 3, 4};
        short[] reversed = new short[] {4, 3, 2, 1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseShort");
        }
    }

    @Test
    public void reverseInt() {
        int[] data = new int[] {1, 2, 3, 4};
        int[] reversed = new int[] {4, 3, 2, 1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseInt");
        }
    }

    @Test
    public void reverseFloat() {
        float[] data = new float[] {(float) 1.1, (float) 2.2, (float) 3.3, (float) 4.4};
        float[] reversed = new float[] {(float) 4.4, (float) 3.3, (float) 2.2, (float) 1.1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseFloat");
        }
    }

    @Test
    public void reverseLong() {
        long[] data = new long[] {1, 2, 3, 4};
        long[] reversed = new long[] {4, 3, 2, 1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseLong");
        }
    }

    @Test
    public void reverseDouble() {
        double[] data = new double[] {(double) 1.1, (double) 2.2, (double) 3.3, (double) 4.4};
        double[] reversed = new double[] {(double) 4.4, (double) 3.3, (double) 2.2, (double) 1.1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseDouble");
        }
    }

    @Test
    public void reverseObject() {
        Object[] data = new Object[] {1, "2", (float) 3.3, (double) 4.4};
        Object[] reversed = new Object[] {(double) 4.4, (float) 3.3, "2", 1};
        if (!Arrays.equals(ArrayUtils.reverse(data), reversed)) {
            logger.error("reverseObject");
        }
    }

    @Test
    public void concatenateByte() {
        byte[] a = new byte[] {1, 2};
        byte[] b = new byte[] {3, 4};
        byte[] c = new byte[] {5, 6};
        byte[] result = new byte[] {1, 2, 3, 4, 5, 6};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b, c), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateChar() {
        char[] a = new char[] {'1', '2'};
        char[] b = new char[] {'3', '4'};
        char[] result = new char[] {'1', '2', '3', '4'};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateShort() {
        short[] a = new short[] {1, 2};
        short[] b = new short[] {3, 4};
        short[] result = new short[] {1, 2, 3, 4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateInt() {
        int[] a = new int[] {1, 2};
        int[] b = new int[] {3, 4};
        int[] result = new int[] {1, 2, 3, 4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateFloat() {
        float[] a = new float[] {(float) 1.1, (float) 2.2};
        float[] b = new float[] {(float) 3.3, (float) 4.4};
        float[] result = new float[] {(float) 1.1, (float) 2.2, (float) 3.3, (float) 4.4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateLong() {
        long[] a = new long[] {1, 2};
        long[] b = new long[] {3, 4};
        long[] result = new long[] {1, 2, 3, 4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateDouble() {
        double[] a = new double[] {(double) 1.1, (double) 2.2};
        double[] b = new double[] {(double) 3.3, (double) 4.4};
        double[] result = new double[] {(double) 1.1, (double) 2.2, (double) 3.3, (double) 4.4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void concatenateObject() {
        Object[] a = new Object[] {1, "2"};
        Object[] b = new Object[] {(float) 3.3, (double) 4.4};
        Object[] result = new Object[] {1, "2", (float) 3.3, (double) 4.4};
        if (!Arrays.equals(ArrayUtils.concatenate(a, b), result)) {
            logger.error("concatenateByte");
        }
    }

    @Test
    public void copyOfRangeByte() {
        byte[] data = new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        byte[] result = new byte[] {3, 4, 5, 6, 7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeByte");
        }
    }

    @Test
    public void copyOfRangeChar() {
        char[] data = new char[] {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] result = new char[] {'3', '4', '5', '6', '7'};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeChar");
        }
    }

    @Test
    public void copyOfRangeShort() {
        short[] data = new short[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        short[] result = new short[] {3, 4, 5, 6, 7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeShort");
        }
    }

    @Test
    public void copyOfRangeInt() {
        int[] data = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] result = new int[] {3, 4, 5, 6, 7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeInt");
        }
    }

    @Test
    public void copyOfRangeFloat() {
        float[] data = new float[] {(float) 1.1, (float) 2.2, (float) 3.3 , (float) 4.4,
                (float) 5.5, (float) 6.6, (float) 7.7, (float) 8.8, (float) 9.9};
        float[] result = new float[] {(float) 3.3, (float) 4.4, (float) 5.5, (float) 6.6, (float) 7.7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeFloat");
        }
    }

    @Test
    public void copyOfRangeLong() {
        long[] data = new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        long[] result = new long[] {3, 4, 5, 6, 7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeLong");
        }
    }

    @Test
    public void copyOfRangeDouble() {
        double[] data = new double[] {(double) 1.1, (double) 2.2, (double) 3.3 , (double) 4.4,
                (double) 5.5, (double) 6.6, (double) 7.7, (double) 8.8, (double) 9.9};
        double[] result = new double[] {(double) 3.3, (double) 4.4, (double) 5.5, (double) 6.6, (double) 7.7};
        if (!Arrays.equals(ArrayUtils.copyOfRange(data, 2, 5), result)) {
            logger.error("copyOfRangeDouble");
        }
    }

}
