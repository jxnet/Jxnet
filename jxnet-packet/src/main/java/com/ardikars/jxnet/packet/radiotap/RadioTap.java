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

package com.ardikars.jxnet.packet.radiotap;

import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class RadioTap extends Packet {

    public static final int RADIO_TAP_HEADER = 8;

    private byte version;
    private byte padding;
    private short length;
    private int present;

    public byte getVersion() {
        return (byte) (this.version & 0xff);
    }

    public RadioTap setVersion(final byte version) {
        this.version = (byte) (version & 0xff);
        return this;
    }

    public byte getPadding() {
        return (byte) (this.padding & 0xff);
    }

    public RadioTap setPadding(final byte padding) {
        this.padding = (byte) (padding & 0xff);
        return this;
    }

    public short getLength() {
        return (short) (this.length & 0xffff);
    }

    public RadioTap setLength(final short length) {
        this.length = (short) (length & 0xffff);
        return this;
    }

    public int getPresent() {
        return (byte) (this.present & 0xffffffffL);
    }

    public RadioTap setPresent(final int present) {
        this.present = (byte) (present & 0xffffffffL);
        return this;
    }

    public static RadioTap newInstance(final ByteBuffer buffer) {
        RadioTap radioTap = new RadioTap();
        radioTap.setVersion(buffer.get());
        radioTap.setPadding(buffer.get());
        radioTap.setLength(buffer.getShort());
        radioTap.setPresent(buffer.getInt());
        radioTap.nextPacket = buffer.slice();
        return radioTap;
    }

    public static RadioTap newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static RadioTap newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }


    @Override
    public Packet setPacket(final Packet packet) {
        if (packet == null) {
            return this;
        }
        switch (packet.getClass().getName()) {
            default:
                this.nextPacket = packet.buffer();
                return this;
        }
    }

    @Override
    public byte[] bytes() {
        if (this.nextPacket != null) {
            this.nextPacket.rewind();
        }
        byte[] data = new byte[RADIO_TAP_HEADER +
                ((this.nextPacket == null) ? 0 : this.nextPacket.capacity())];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getVersion());
        buffer.put(this.getPadding());
        buffer.putShort(this.getLength());
        buffer.putInt(this.getPresent());
        if (this.nextPacket!= null) {
            buffer.put(this.nextPacket);
        }
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        if (this.nextPacket != null) {
            this.nextPacket.rewind();
        }
        ByteBuffer buffer = ByteBuffer
                .allocateDirect(RADIO_TAP_HEADER +
                        ((this.nextPacket == null) ? 0 : this.nextPacket.capacity()));
        buffer.put(this.getVersion());
        buffer.put(this.getPadding());
        buffer.putShort(this.getLength());
        buffer.putInt(this.getPresent());
        if (this.nextPacket!= null) {
            buffer.put(this.nextPacket);
        }
        return buffer;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Version: " + this.getVersion())
                .append(", Padding: " + this.getPadding())
                .append(", Length: " + this.getLength())
                .append(", Present: " + this.getPresent())
                .append("]").toString();
    }

}
