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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ArrayUtils {

    /**
     * Reverse order.
     * @param value value.
     * @return array in reverse order.
     */
    public static byte[] reverse(byte[] value) {
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(value);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
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
        Validate.notNull(arr1);
        Validate.notNull(arr2);
        Object[] result = new Object[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, result, 0, arr1.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    /**
     * Copy array.
     * @param data byte array.
     * @param offset offset.
     * @param length length.
     * @return byte array.
     */
    public static byte[] copyOfRange(byte[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        byte[] bytes = new byte[length];
        System.arraycopy(data, offset, bytes, 0, length);
        return bytes;
    }

    /**
     * Copy array.
     * @param data char array.
     * @param offset offset.
     * @param length length.
     * @return char array.
     */
    public static char[] copyOfRange(char[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        char[] chars = new char[length];
        System.arraycopy(data, offset, chars, 0, length);
        return chars;
    }

    /**
     * Copy array.
     * @param data short array.
     * @param offset offset.
     * @param length length.
     * @return short array.
     */
    public static short[] copyOfRange(short[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        short[] shorts = new short[length];
        System.arraycopy(data, offset, shorts, 0, length);
        return shorts;
    }

    /**
     * Copy array.
     * @param data int array.
     * @param offset offset.
     * @param length length.
     * @return int array.
     */
    public static int[] copyOfRange(int[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        int[] ints = new int[length];
        System.arraycopy(data, offset, ints, 0, length);
        return ints;
    }

    /**
     * Copy array.
     * @param data float array.
     * @param offset offset.
     * @param length length.
     * @return float array.
     */
    public static float[] copyOfRange(float[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        float[] floats = new float[length];
        System.arraycopy(data, offset, floats, 0, length);
        return floats;
    }

    /**
     * Copy array.
     * @param data long array.
     * @param offset offset.
     * @param length length.
     * @return long array.
     */
    public static long[] copyOfRange(long[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        long[] longs = new long[length];
        System.arraycopy(data, offset, longs, 0, length);
        return longs;
    }

    /**
     * Copy array.
     * @param data double array.
     * @param offset offset.
     * @param length length.
     * @return double array.
     */
    public static double[] copyOfRange(double[] data, int offset, int length) {
        Validate.bounds(data, offset, length);
        double[] doubles = new double[length];
        System.arraycopy(data, offset, doubles, 0, length);
        return doubles;
    }

}
