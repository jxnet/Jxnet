/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
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

package com.ardikars.jxnet.packet.util;

import com.ardikars.jxnet.packet.Packet;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A {@link Packet} iterator implementation.
 */
public class PacketIterator implements Iterator<Packet> {

    private Packet next;

    public PacketIterator(final Packet packet) {
        this.next = packet;
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Packet next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Packet previous = next;
        next = next.getPayload();
        return previous;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

}
