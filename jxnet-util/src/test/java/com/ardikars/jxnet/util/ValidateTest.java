package com.ardikars.jxnet.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RunWith(JUnit4.class)
public class ValidateTest {

    @Test
    public void bounds() {
        int length = 5;
        byte[] arr1 = new byte[length];
        char[] arr2 = new char[length];
        short[] arr3 = new short[length];
        int[] arr4 = new int[length];
        float[] arr5 = new float[length];
        long[] arr6 = new long[length];
        double[] arr7 = new double[length];
        // Generic array
        String[] arr8 = new String[length];
        for (int i=0; i<length; i++) {
            Validate.bounds(arr1, i, length-1);
            Validate.bounds(arr2, i, length-1);
            Validate.bounds(arr3, i, length-1);
            Validate.bounds(arr4, i, length-1);
            Validate.bounds(arr5, i, length-1);
            Validate.bounds(arr6, i, length-1);
            Validate.bounds(arr7, i, length-1);
        }
    }

    @Test
    public void argument() {
        Validate.illegalArgument(true);
        Validate.illegalArgument(true, new IllegalArgumentException("Error message"));
        try {
            Validate.illegalArgument(false, new IllegalArgumentException("Error message"));
        } catch (IllegalArgumentException e) {
            System.out.println("Catched exception: " + e);
        }
        System.out.println(Validate.illegalArgument(false, "MyRef", "MyNewVal"));
    }

    @Test
    public void notNull() {
        String str1 = "";
        String str2 = null;
        Validate.nullPointer(str1);
        Validate.nullPointer(str1, new NullPointerException("Error message"));
        try {
            Validate.nullPointer(str2, new NullPointerException("Error message"));
        } catch (NullPointerException e) {
            System.out.println("Catched excaption: " + e);
        }
        System.out.println(Validate.nullPointer(null, "MyNewVal"));
    }

    @Test
    public void test() {
    }

}
