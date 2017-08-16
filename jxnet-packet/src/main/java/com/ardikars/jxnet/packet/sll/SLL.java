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

package com.ardikars.jxnet.packet.sll;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ProtocolType;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class SLL extends Packet {

    public static final int SLL_HEADER_LENGTH = 16;

    private short packetType;
    private short hardwareAddressType;
    private short hardwareAddressLength;
    private byte[] address;
    private ProtocolType protocol;

    public SLL() {
        this.address = new byte[8];
    }

    public short getPacketType() {
        return (short) (this.packetType & 0xffff);
    }

    public SLL setPacketType(final short packetType) {
        this.packetType = (short) (packetType & 0xffff);
        return this;
    }

    public short getHardwareAddressType() {
        return (short) (this.hardwareAddressType & 0xffff);
    }

    public SLL setHardwareAddressType(final short hardwareAddressType) {
        this.hardwareAddressType = (short) (hardwareAddressType & 0xffff);
        return this;
    }

    public short getHardwareAddressLength() {
        return (short) (this.hardwareAddressLength & 0xffff);
    }

    public SLL setHardwareAddressLength(final short hardwareAddressLength) {
        this.hardwareAddressLength = (short) (hardwareAddressLength & 0xffff);
        return this;
    }

    public byte[] getAddress() {
        return this.address;
    }

    public SLL setAddress(final byte[] address) {
        this.address = address;
        return this;
    }

    public ProtocolType getProtocol() {
        return this.protocol;
    }

    public SLL setProtocol(final ProtocolType protocol) {
        this.protocol = protocol;
        return this;
    }

    @Deprecated
    public byte[] getPayload() {
        return this.nextPacket;
    }

    @Deprecated
    public SLL setPayload(final byte[] payload) {
        this.nextPacket = payload;
        return this;
    }

    public static SLL newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static SLL newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        SLL sll = new SLL();
        sll.setPacketType(buffer.getShort());
        sll.setHardwareAddressType(buffer.getShort());
        sll.setHardwareAddressLength(buffer.getShort());
        sll.address = new byte[8];
        buffer.get(sll.address);
        sll.setProtocol(ProtocolType.getInstance(buffer.getShort()));
        sll.nextPacket = new byte[buffer.limit() - SLL_HEADER_LENGTH];
        buffer.get(sll.nextPacket);
        return sll;
    }

    @Override
    public Packet setPacket(final Packet packet) {
        if (packet == null) {
            return this;
        }
        switch (packet.getClass().getName()) {
            default:
                this.nextPacket = packet.toBytes();
                return this;
        }
    }

    @Override
    public Packet getPacket() {
        return this.getProtocol().decode(this.nextPacket);
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[SLL_HEADER_LENGTH + ((this.nextPacket != null) ? 0 : this.nextPacket.length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putShort(this.getPacketType());
        buffer.putShort(this.getHardwareAddressType());
        buffer.putShort(this.getHardwareAddressLength());
        buffer.put(this.getAddress());
        buffer.putShort((short) (this.getProtocol().getValue() & 0xffff));
        if (this.nextPacket != null) {
            buffer.put(this.nextPacket);
        }
        return data;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Packet Type: ").append(this.getPacketType())
                .append(", Hardware Address Type: " + this.getHardwareAddressType())
                .append(", Hardware Address Length: " + this.getHardwareAddressLength())
                .append(", Address: " + Arrays.toString(this.getAddress()))
                .append(", Protocol: " + this.getProtocol().toString())
                .append("]").toString();
    }

}
