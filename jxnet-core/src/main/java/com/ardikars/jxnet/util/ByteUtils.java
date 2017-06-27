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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.ardikars.jxnet.util.Preconditions.CheckNotNull;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ByteUtils {

    /**
     * Byte to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final byte value) {
        return new byte[] { value };
    }

    /**
     * Short to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final short value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Short to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final short value, final ByteOrder bo) {
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            return new byte[] { (byte) ((value >> 0) & 0xff), (byte) ((value >> 8) & 0xff) };
        } else {
            return new byte[] { (byte) ((value >> 8) & 0xff), (byte) ((value >> 0) & 0xff) };
        }
    }

    /**
     * Short array to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final short[] value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Short array to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final short[] value, final ByteOrder bo) {
        return toByteArray(value, 0, value.length, bo);
    }

    /**
     * Short array to byte array.
     * @param value value.
     * @param offset offset.
     * @param length length.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final short[] value, final int offset, final int length, final ByteOrder bo) {
        ArrayUtils.validateBounds(value, offset, length);
        byte[] array = new byte[length << 1];
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            for (int i=offset; i<length; i++) {
                short x = value[i];
                int j = i << 1;
                array[j++] = (byte) ((x >> 0) & 0xff);
                array[j++] = (byte) ((x >> 8) & 0xff);
            }
            return array;
        } else {
            for (int i=offset; i<length; i++) {
                short x = value[i];
                int j = i << 1;
                array[j++] = (byte) ((x >> 8) & 0xff);
                array[j++] = (byte) ((x >> 0) & 0xff);
            }
            return array;
        }
    }

    /**
     * Int to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final int value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Int to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final int value, final ByteOrder bo) {
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            return new byte[] {
                    (byte) ((value >> 0) & 0xff),
                    (byte) ((value >> 8) & 0xff),
                    (byte) ((value >> 16 & 0xff)),
                    (byte) ((value >> 24 & 0xff))
            };
        } else {
            return new byte[] {
                    (byte) ((value >> 24) & 0xff),
                    (byte) ((value >> 16) & 0xff),
                    (byte) ((value >> 8) & 0xff),
                    (byte) ((value >> 0) & 0xff)
            };
        }
    }

    /**
     * Int array to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final int[] value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Int array to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final int[] value, final ByteOrder bo) {
        return toByteArray(value, 0, value.length, bo);
    }

    /**
     * Int array to byte array.
     * @param value value.
     * @param offset offset.
     * @param length length.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final int[] value, final int offset, final int length, final ByteOrder bo) {
        ArrayUtils.validateBounds(value, offset, length);
        byte[] array = new byte[length << 2];
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            for (int i = offset; i < length; i++) {
                int x = value[i];
                int j = i << 2;
                array[j++] = (byte) ((x >> 0) & 0xff);
                array[j++] = (byte) ((x >> 8) & 0xff);
                array[j++] = (byte) ((x >> 16) & 0xff);
                array[j++] = (byte) ((x >> 24) & 0xff);
            }
            return array;
        } else {
            for (int i = offset; i < length; i++) {
                int x = value[i];
                int j = i << 2;
                array[j++] = (byte) ((x >> 24) & 0xff);
                array[j++] = (byte) ((x >> 16) & 0xff);
                array[j++] = (byte) ((x >> 8) & 0xff);
                array[j++] = (byte) ((x >> 0) & 0xff);
            }
            return array;
        }
    }

    /**
     * Long to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final long value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Long to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final long value, final ByteOrder bo) {
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            return new byte[] {
                    (byte) ((value >> 0) & 0xff),
                    (byte) ((value >> 8) & 0xff),
                    (byte) ((value >> 16) & 0xff),
                    (byte) ((value >> 24) & 0xff),
                    (byte) ((value >> 32) & 0xff),
                    (byte) ((value >> 40) & 0xff),
                    (byte) ((value >> 48) & 0xff),
                    (byte) ((value >> 56) & 0xff)
            };
        } else {
            return new byte[] {
                    (byte) ((value >> 56) & 0xff),
                    (byte) ((value >> 48) & 0xff),
                    (byte) ((value >> 40) & 0xff),
                    (byte) ((value >> 32) & 0xff),
                    (byte) ((value >> 24) & 0xff),
                    (byte) ((value >> 16) & 0xff),
                    (byte) ((value >> 8) & 0xff),
                    (byte) ((value >> 0) & 0xff)
            };
        }
    }

    /**
     * Long array to byte array.
     * @param value value.
     * @return byte array.
     */
    public static byte[] toByteArray(final long[] value) {
        return toByteArray(value, ByteOrder.BIG_ENDIAN);
    }

    /**
     * Long array to byte array.
     * @param value value.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final long[] value, final ByteOrder bo) {
        return toByteArray(value, 0, value.length, bo);
    }

    /**
     * Long array to byte array.
     * @param value value.
     * @param offset offset.
     * @param length length.
     * @param bo byte order.
     * @return byte array.
     */
    public static byte[] toByteArray(final long[] value, final int offset, final int length, final ByteOrder bo) {
        ArrayUtils.validateBounds(value, offset, length);
        byte[] array = new byte[length << 3];
        if (bo.equals(ByteOrder.BIG_ENDIAN)) {
            for (int i = offset; i < length; i++) {
                long x = value[i];
                int j = i << 3;
                array[j++] = (byte) ((x >> 0) & 0xff);
                array[j++] = (byte) ((x >> 8) & 0xff);
                array[j++] = (byte) ((x >> 16) & 0xff);
                array[j++] = (byte) ((x >> 24) & 0xff);
                array[j++] = (byte) ((x >> 32) & 0xff);
                array[j++] = (byte) ((x >> 40) & 0xff);
                array[j++] = (byte) ((x >> 48) & 0xff);
                array[j++] = (byte) ((x >> 56) & 0xff);
            }
            return array;
        } else {
            for (int i = offset; i < length; i++) {
                long x = value[i];
                int j = i << 3;
                array[j++] = (byte) ((x >> 56) & 0xff);
                array[j++] = (byte) ((x >> 48) & 0xff);
                array[j++] = (byte) ((x >> 40) & 0xff);
                array[j++] = (byte) ((x >> 32) & 0xff);
                array[j++] = (byte) ((x >> 24) & 0xff);
                array[j++] = (byte) ((x >> 16) & 0xff);
                array[j++] = (byte) ((x >> 8) & 0xff);
                array[j++] = (byte) ((x >> 0) & 0xff);
            }
            return array;
        }
    }

    /**
     * ByteBuffer to byte array.
     * @param byteBuffer ByteBuffer.
     * @return byte array.
     */
    public static byte[] toByteArray(final ByteBuffer byteBuffer) {
        CheckNotNull(byteBuffer);
        if (!byteBuffer.hasRemaining()) {
            byteBuffer.flip();
        }
        byte[] buffer = new byte[byteBuffer.remaining()];
        byteBuffer.get(buffer);
        return buffer;
    }

}
