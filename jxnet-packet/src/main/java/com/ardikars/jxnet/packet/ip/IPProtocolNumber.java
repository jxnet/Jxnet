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
public class IPProtocolNumber extends NamedNumber<Byte, IPProtocolNumber> {

    public static final IPProtocolNumber ICMP = new IPProtocolNumber((byte) 1, "Internet Control Message Protocol");

    public static final IPProtocolNumber IGMP = new IPProtocolNumber((byte) 2, "Internet Group Management Protocol");

    public static final IPProtocolNumber TCP = new IPProtocolNumber((byte) 6, "Transmission Control Protocol");

    public static final IPProtocolNumber UDP = new IPProtocolNumber((byte) 17, "User Datagram Protocol");

    public static final IPProtocolNumber UNKNOWN = new IPProtocolNumber((byte) -1, "Unknown");

    protected IPProtocolNumber(Byte value, String name) {
        super(value, name);
    }

    private static Map<Byte, IPProtocolNumber> registry = new HashMap<Byte, IPProtocolNumber>();

    static {
        registry.put(ICMP.getValue(), ICMP);
        registry.put(IGMP.getValue(), IGMP);
        registry.put(TCP.getValue(), TCP);
        registry.put(UDP.getValue(), UDP);
    }

    public static IPProtocolNumber register(IPProtocolNumber protocol) {
        return registry.put(protocol.getValue(), protocol);
    }

    public static IPProtocolNumber getInstance(Byte value) {
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
