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

package com.ardikars.jxnet;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Validate {

    /**
     * Validate argument.
     * @param expression expression.
     */
    public static void CheckArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Validate argument.
     * @param expression expression.
     * @param errorMessage error message.
     */
    public static void CheckArgument(boolean expression, String errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    /**
     * Check not null.
     * @param reference reference.
     * @param <T> type.
     * @return reference.
     */
    public static <T> T CheckNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Check not null.
     * @param reference reference.
     * @param errorMessage error message.
     * @param <T> type.
     * @return reference.
     */
    public static <T> T CheckNotNull(T reference, String errorMessage) {
        if (reference == null) {
            throw new NullPointerException(errorMessage);
        }
        return reference;
    }

    /**
     * Validate bounds.
     * @param array array.
     * @param offset offset.
     * @param length length.
     */
    public static void CheckBounds(byte[] array, int offset, int length) {
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
    public static void CheckBounds(char[] array, int offset, int length) {
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
    public static void CheckBounds(short[] array, int offset, int length) {
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
    public static void CheckBounds(int[] array, int offset, int length) {
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
    public static void CheckBounds(float[] array, int offset, int length) {
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
    public static void CheckBounds(long[] array, int offset, int length) {
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
    public static void CheckBounds(double[] array, int offset, int length) {
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
    public static void CheckBounds(Object[] array, int offset, int length) {
        CheckNotNull(array, "array is null.");
        CheckArgument(array.length > 0, "array is empty.");
        CheckArgument(length > 0, "length is zero.");
        if (offset < 0 || length < 0 || length > array.length || offset > array.length) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

}
