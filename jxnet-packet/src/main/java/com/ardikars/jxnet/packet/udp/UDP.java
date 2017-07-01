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

package com.ardikars.jxnet.packet.udp;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.UnknownPacket;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class UDP extends Packet {

    public static final int UDP_HEADER_LENGTH = 8;

    private short sourcePort;
    private short destinationPort;
    private short length;
    private short checksum;

    /**
     * Get source port.
     * @return source port.
     */
    public short getSourcePort() {
        return (short) (this.sourcePort & 0xffff);
    }

    /**
     * Set source port.
     * @param sourcePort source port.
     * @return UDP object.
     */
    public UDP setSourcePort(final short sourcePort) {
        this.sourcePort = (short) (sourcePort & 0xffff);
        return this;
    }

    /**
     * Get destination port.
     * @return destination port.
     */
    public short getDestinationPort() {
        return (short) (this.destinationPort & 0xffff);
    }

    /**
     * Set destination port.
     * @param destinationPort destination port.
     * @return UDP object.
     */
    public UDP setDestinationPort(final short destinationPort) {
        this.destinationPort = (short) (destinationPort & 0xffff);
        return this;
    }

    /**
     * Get length.
     * @return length.
     */
    public short getLength() {
        return (short) (this.length & 0xffff);
    }

    /**
     * Set length.
     * @param length length.
     * @return UDP object.
     */
    public UDP setLength(final short length) {
        this.length = (short) (this.length & 0xffff);
        return this;
    }

    /**
     * Get checksum.
     * @return checksum.
     */
    public short getChecksum() {
        return (short) (this.checksum & 0xffff);
    }

    /**
     * Set checksum.
     * @param checksum checksum.
     * @return UDP object.
     */
    public UDP setChecksum(short checksum) {
        this.checksum = (short) (checksum & 0xffff);
        return this;
    }

    @Deprecated
    public byte[] getPayload() {
        return this.nextPacket;
    }

    @Deprecated
    public UDP setPayload(byte[] payload) {
        this.nextPacket = payload;
        return this;
    }

    /**
     * Crete new UDP object.
     * @param bytes bytes.
     * @return UDP object.
     */
    public static UDP newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    /**
     * Crete new UDP object.
     * @param bytes bytes.
     * @param offset offset.
     * @param length length.
     * @return UDP object.
     */
    public static UDP newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        UDP udp = new UDP();
        udp.setSourcePort(buffer.getShort());
        udp.setDestinationPort(buffer.getShort());
        udp.setLength(buffer.getShort());
        udp.setChecksum(buffer.getShort());
        udp.nextPacket = new byte[buffer.limit() - UDP_HEADER_LENGTH];
        buffer.get(udp.nextPacket);
        return udp;
    }

    /**
     * Set packet.
     * @param packet nextPacket.
     * @return UDP instance.
     */
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

    /**
     * Get packet.
     * @return packet.
     */
    @Override
    public Packet getPacket() {
        if (this.nextPacket == null || this.nextPacket.length == 0) return null;
        return UnknownPacket.newInstance(this.nextPacket);
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[UDP_HEADER_LENGTH + ((this.nextPacket == null) ? 0 : this.nextPacket.length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putShort(this.getSourcePort());
        buffer.putShort(this.getDestinationPort());
        this.length = (short) (UDP_HEADER_LENGTH + ((this.nextPacket == null) ? 0 : this.nextPacket.length));
        buffer.putShort(this.getLength());
        buffer.putShort(this.getChecksum());
        if (this.nextPacket != null)
            buffer.put(this.nextPacket);
        return data;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Source Port: " + this.getSourcePort())
                .append(", Destination Port: " + this.getDestinationPort())
                .append(", Length: " + this.getLength())
                .append(", Checksum: " + this.getChecksum())
                .toString();
    }

}
