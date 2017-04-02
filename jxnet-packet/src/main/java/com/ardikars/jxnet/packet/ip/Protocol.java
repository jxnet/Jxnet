/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.packet.ip;

import com.ardikars.jxnet.util.NamedNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class Protocol extends NamedNumber<Byte, Protocol> {

    public static final Protocol ICMP = new Protocol((byte) 1, "Internet Control Message Protocol");

    public static final Protocol IGMP = new Protocol((byte) 2, "Internet Group Management Protocol");

    public static final Protocol TCP = new Protocol((byte) 6, "Transmission Control Protocol");

    public static final Protocol UDP = new Protocol((byte) 11, "User Datagram Protocol");

    public static final Protocol UNKNOWN = new Protocol((byte) -1, "Unknown");

    protected Protocol(Byte value, String name) {
        super(value, name);
    }

    private static Map<Byte, Protocol> registry = new HashMap<Byte, Protocol>();

    static {
        registry.put(ICMP.getValue(), ICMP);
        registry.put(IGMP.getValue(), IGMP);
        registry.put(TCP.getValue(), TCP);
        registry.put(UDP.getValue(), UDP);
    }

    public static Protocol register(Protocol protocol) {
        return registry.put(protocol.getValue(), protocol);
    }

    public static Protocol getInstance(Byte value) {
        if (registry.containsKey(value)) {
            return registry.get(value);
        } else {
            return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
