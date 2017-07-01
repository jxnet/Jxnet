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
import java.util.Arrays;

import static com.ardikars.jxnet.Validate.CheckNotNull;
import static com.ardikars.jxnet.Validate.CheckBounds;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class BufferUtils {

    /**
     * ByteBuffer to DirectByteBuffer.
     * @param byteBuffer ByteBuffer.
     * @return DirectByteBuffer.
     */
    public static ByteBuffer toDirectByteBuffer(final ByteBuffer byteBuffer) {
        CheckNotNull(byteBuffer);
        if (byteBuffer.isDirect())
            return byteBuffer;
        ByteBuffer buffer = ByteBuffer.
                allocateDirect(byteBuffer.capacity());
        return buffer.put(byteBuffer);
    }

    /**
     * Byte to DirectByteBuffer.
     * @param value byte.
     * @return DirectByteBuffer.
     */
    public static ByteBuffer toDirectByteBuffer(final byte value) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1);
        buffer.put(value);
        return buffer;
    }

    /**
     * Byte array to DirectByteBuffer.
     * @param bytes byte array.
     * @return DirectByteBuffer.
     */
    public static ByteBuffer toDirectByteBuffer(final byte[] bytes) {
        CheckNotNull(bytes);
        return toDirectByteBuffer(bytes, 0, bytes.length);
    }

    /**
     * Byte array to DirectByteBuffer.
     * @param bytes byte array.
     * @param offset offset.
     * @param length length.
     * @return DirectByteBuffer.
     */
    public static ByteBuffer toDirectByteBuffer(final byte[] bytes, final int offset, final int length) {
        CheckBounds(bytes, offset, length);
        ByteBuffer buffer = ByteBuffer.allocateDirect(length);
        buffer.put(Arrays.copyOfRange(bytes, offset, length));
        return buffer;
    }

}
