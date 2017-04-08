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

package com.ardikars.jxnet.packet.ip;

import com.ardikars.jxnet.util.NamedNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class IPProtocolType extends NamedNumber<Byte, IPProtocolType> {

    public static final IPProtocolType ICMP = new IPProtocolType((byte) 1, "Internet Control Message Protocol Version 4");

    public static final IPProtocolType IPV6_ICMP = new IPProtocolType((byte) 58, "Internet Control Message Protocol Version 6");

    public static final IPProtocolType IGMP = new IPProtocolType((byte) 2, "Internet Group Management Protocol");

    public static final IPProtocolType TCP = new IPProtocolType((byte) 6, "Transmission Control Protocol");

    public static final IPProtocolType UDP = new IPProtocolType((byte) 17, "User Datagram Protocol");

    public static final IPProtocolType UNKNOWN = new IPProtocolType((byte) -1, "Unknown");

    protected IPProtocolType(Byte value, String name) {
        super(value, name);
    }

    private static Map<Byte, IPProtocolType> registry = new HashMap<Byte, IPProtocolType>();

    static {
        registry.put(ICMP.getValue(), ICMP);
        registry.put(ICMP.getValue(), IPV6_ICMP);
        registry.put(IGMP.getValue(), IGMP);
        registry.put(TCP.getValue(), TCP);
        registry.put(UDP.getValue(), UDP);
    }

    public static IPProtocolType register(IPProtocolType protocol) {
        return registry.put(protocol.getValue(), protocol);
    }

    public static IPProtocolType getInstance(Byte value) {
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
