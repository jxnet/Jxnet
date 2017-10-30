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

import com.ardikars.jxnet.Builder;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public abstract class Packet implements Iterator<Packet> {

    private Packet next = this;

    protected ByteBuffer nextPacket;

    /**
     * Set payload.
     * @param packet paket.
     * @return packet.
     */
    public Packet setPacket(final Packet packet) {
        return this;
    }

    /**
     * Get payload.
     * @return packet.
     */
    public Packet getPacket() {
        return null;
    }

    public abstract byte[] bytes();

    /**
     * Return packet in DirectByteBuffer.
     * @return DirectByteBuffer.
     */
    public abstract ByteBuffer buffer();

    /**
     * Packet builder.
     * @return Instance of PacketBuilder.
     */
    public static PacketBuilder PacketBuilder() {
        return new PacketBuilder();
    }

    public static class PacketBuilder implements Builder<Packet> {

        /**
         * Packet list.
         */
        private List<Packet> packets = new ArrayList<Packet>();

        /**
         * Add payload.
         * @param packet paket.
         * @return PacketBuilder.
         */
        public PacketBuilder add(Packet packet) {
            packets.add(packet);
            return this;
        }

        /**
         * Remove packet.
         * @param packetNumber packet number.
         * @return PacketBuilder.
         */
        public PacketBuilder remove(int packetNumber) {
            this.packets.remove(packetNumber - 1);
            return this;
        }

        /**
         * Build packet with payloads.
         * @return packets.
         */
        @Override
        public Packet build() {
            for (int i=packets.size(); i>1; i--) {
                packets.get(i - 2).setPacket(packets.get(i-1));
            }
            return packets.get(0);
        }

    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Packet next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        Packet current = next;
        next = current.getPacket();
        return current;
    }

}
