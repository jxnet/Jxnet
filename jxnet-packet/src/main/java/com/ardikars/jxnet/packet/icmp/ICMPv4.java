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

package com.ardikars.jxnet.packet.icmp;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.util.Builder;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ICMPv4 extends Packet implements ICMP {

    public static int ICMP_HEADER_LENGTH = 4;

    private ICMPTypeAndCode typeAndCode;
    private short checksum;

    /**
     * ICMPv4 payload
     */
    private byte[] payload;

    public ICMPTypeAndCode getTypeAndCode() {
        return this.typeAndCode;
    }

    public ICMPv4 setTypeAndCode(final ICMPTypeAndCode typeAndCode) {
        this.typeAndCode = typeAndCode;
        return this;
    }

    public short getChecksum() {
        return (short) (this.checksum & 0xffff);
    }

    public ICMPv4 setChecksum(final short checksum) {
        this.checksum = (short) (checksum & 0xffff);
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public ICMPv4 setPayload(final byte[] payload) {
        this.payload = payload;
        return this;
    }

    public static ICMPv4 newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static ICMPv4 newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        ICMPv4 icmp = new ICMPv4();
        icmp.setTypeAndCode(ICMPTypeAndCode.getInstance(buffer.get(), buffer.get()));
        icmp.setChecksum(buffer.getShort());
        icmp.payload = new byte[buffer.limit() - ICMP_HEADER_LENGTH];
        buffer.get(icmp.payload);
        return icmp;
    }

    @Override
    public Packet setPacket(final Packet packet) {
        return this.setPayload(packet.toBytes());
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[ICMP_HEADER_LENGTH + ((this.getPayload() == null) ? 0 : this.getPayload().length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getTypeAndCode().getType());
        buffer.put(this.getTypeAndCode().getCode());
        buffer.putShort(this.getChecksum());
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        if (this.getChecksum() == 0) {
            buffer.rewind();
            int accumulation = 0;
            for (int i = 0; i < data.length / 2; ++i) {
                accumulation += 0xffff & buffer.getShort();
            }
            // pad to an even number of shorts
            if (data.length % 2 > 0) {
                accumulation += (buffer.get() & 0xff) << 8;
            }

            accumulation = (accumulation >> 16 & 0xffff)
                    + (accumulation & 0xffff);
            this.checksum = (short) (~accumulation & 0xffff);
            buffer.putShort(2, this.checksum);
        }
        return data;
    }

    @Override
    public Packet build() {
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getTypeAndCode().toString()).toString();
    }

}
