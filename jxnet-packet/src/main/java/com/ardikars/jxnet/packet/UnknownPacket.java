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

package com.ardikars.jxnet.packet;

import com.ardikars.jxnet.util.HexUtils;
import com.ardikars.jxnet.util.Validate;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class UnknownPacket extends Packet {

    private ByteBuffer data;

    public static UnknownPacket newInstance(final ByteBuffer buffer) {
        UnknownPacket unknownPacket = new UnknownPacket();
        unknownPacket.data = buffer;
        return unknownPacket;
    }

    public static UnknownPacket newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static UnknownPacket newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    public UnknownPacket setData(final ByteBuffer buffer) {
        this.data = buffer;
        return this;
    }

    public UnknownPacket setData(final byte[] data) {
        this.data = ByteBuffer.wrap(data);
        return this;
    }

    @Override
    public Packet setPacket(final Packet packet) {
        return this;
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] bytes() {
        ByteBuffer buffer = buffer().duplicate();
        byte[] data = new byte[buffer.capacity()];
        buffer.get(data, 0, data.length);
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        return this.data;
    }

    @Override
    public String toString() {
//        return "[Unknown: 0x" + HexUtils.toHexString(this.getData()) + "]";
        return (this.data != null) ? this.data.toString() : "";
    }

}
