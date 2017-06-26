/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.ardikars.jxnet.util;

import static com.ardikars.jxnet.util.Preconditions.CheckArgument;
import static com.ardikars.jxnet.util.Preconditions.CheckNotNull;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ArrayUtils {

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(byte[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(char[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(short[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(int[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(float[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(long[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(double[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void validateBounds(Object[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static byte[] reverse(byte[] value) {
        CheckNotNull(value);
        byte[] array = new byte[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static char[] reverse(char[] value) {
        CheckNotNull(value);
        char[] array = new char[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static short[] reverse(short[] value) {
        CheckNotNull(value);
        short[] array = new short[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static int[] reverse(int[] value) {
        CheckNotNull(value);
        int[] array = new int[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static float[] reverse(float[] value) {
        CheckNotNull(value);
        float[] array = new float[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static long[] reverse(long[] value) {
        CheckNotNull(value);
        long[] array = new long[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static double[] reverse(double[] value) {
        CheckNotNull(value);
        double[] array = new double[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static Object[] reverse(Object[] value) {
        CheckNotNull(value);
        Object[] array = new Object[value.length];
        for (int i=0; i<value.length; i++) {
            array[i] = value[value.length - i -1];
        }
        return array;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static byte[] concatenate(byte[] arr1, byte[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        byte[] result = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static char[] concatenate(char[] arr1, char[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        char[] result = new char[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static short[] concatenate(short[] arr1, short[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        short[] result = new short[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static int[] concatenate(int[] arr1, int[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        int[] result = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static float[] concatenate(float[] arr1, float[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        float[] result = new float[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static long[] concatenate(long[] arr1, long[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        long[] result = new long[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static double[] concatenate(double[] arr1, double[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        double[] result = new double[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Concatenate array.
     * @param arr1 array 1.
     * @param arr2 array 2.
     * @return array 1 + array 2.
     */
    public static Object[] concatenate(Object[] arr1, Object[] arr2) {
        CheckNotNull(arr1);
        CheckNotNull(arr2);
        Object[] result = new Object[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

}
