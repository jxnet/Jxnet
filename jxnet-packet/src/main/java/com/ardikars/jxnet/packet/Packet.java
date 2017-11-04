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

import java.nio.ByteBuffer;
import java.util.Iterator;
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

    @Override
    public void remove() {
        // do nothing
    }

}
