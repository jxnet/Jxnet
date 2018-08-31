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

package com.ardikars.jxnet.packet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Abstraction for protocol codec.
 * @author Ardika Rommy Sanjaya
 * @since 1.5.0
 */
public abstract class AbstractPacket implements Packet {

    @Override
    public <T extends Packet> boolean contains(Class<T> clazz) {
        return !get(clazz).isEmpty();
    }

    @Override
    public <T extends Packet> List<T> get(Class<T> clazz) {
        List<Packet> packets = new ArrayList<>();
        Iterator<Packet> iterator = this.iterator();
        while (iterator.hasNext()) {
            Packet packet = iterator.next();
            if (clazz.isInstance(packet)) {
                packets.add(packet);
            }
        }
        return (List<T>) packets;
    }

    @Override
    public Iterator<Packet> iterator() {
        return new PacketIterator(this);
    }

}
