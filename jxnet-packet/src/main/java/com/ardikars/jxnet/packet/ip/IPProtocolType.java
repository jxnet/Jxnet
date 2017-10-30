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

import com.ardikars.jxnet.Decoder;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.icmp.ICMPv4;
import com.ardikars.jxnet.packet.icmp.ICMPv6;
import com.ardikars.jxnet.packet.ip.ipv6.*;
import com.ardikars.jxnet.NamedNumber;
import com.ardikars.jxnet.packet.ndp.*;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class IPProtocolType extends NamedNumber<Byte, IPProtocolType> implements Decoder<Packet, ByteBuffer> {

    public static final IPProtocolType ICMP = new IPProtocolType((byte) 1, "Internet Control Message Protocol Version 4");

    public static final IPProtocolType IPV6 = new IPProtocolType((byte) 41, "IPv6 Header.");

    public static final IPProtocolType IPV6_ICMP = new IPProtocolType((byte) 58, "Internet Control Message Protocol Version 6");

    public static final IPProtocolType IPV6_ROUTING = new IPProtocolType((byte) 43, "Routing Header for IPv6.");

    public static final IPProtocolType IPV6_FRAGMENT = new IPProtocolType((byte) 44, "Fragment Header for IPv6.");

    public static final IPProtocolType IPV6_HOPOPT = new IPProtocolType((byte) 0, "IPv6 Hop by Hop NeighborDiscoveryOptions.");

    public static final IPProtocolType IPV6_DSTOPT = new IPProtocolType((byte) 60, "IPv6 Destination NeighborDiscoveryOptions.");

    public static final IPProtocolType IPV6_ESP = new IPProtocolType((byte) 50, "IPv6 ESP.");

    public static final IPProtocolType IPV6_AS = new IPProtocolType((byte) 51, "IPv6 Authentication Header.");

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
        registry.put(IPV6.getValue(), IPV6);
        registry.put(IPV6_ICMP.getValue(), IPV6_ICMP);
        registry.put(IPV6_ROUTING.getValue(), IPV6_ROUTING);
        registry.put(IPV6_FRAGMENT.getValue(), IPV6_FRAGMENT);
        registry.put(IPV6_HOPOPT.getValue(), IPV6_HOPOPT);
        registry.put(IPV6_DSTOPT.getValue(), IPV6_DSTOPT);
        registry.put(IPV6_ESP.getValue(), IPV6_ESP);
        registry.put(IPV6_AS.getValue(), IPV6_AS);
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

    /**
     * Decode payload.
     * @param buffer ByteBuffer.
     * @return packet.
     */
    @Override
    public Packet decode(final ByteBuffer buffer) {
        if (buffer == null) {
            return null;
        }
        buffer.rewind();
        if (buffer == null || buffer.capacity() == 0) {
            return null;
        }
        int value = super.getValue();
        switch (value) {
            case 1:
                return ICMPv4.newInstance(buffer);
            case 41:
                return IPv6.newInstance(buffer);
            case 58:
                return ICMPv6.newInstance(buffer);
            case 43:
                return Routing.newInstance(buffer);
            case 44:
                return Fragment.newInstance(buffer);
            case 0:
                return HopByHopOptions.newInstance(buffer);
            case 60:
                return null;
            case 50:
                return EncapsulatingSecurityPayload.newInstance(buffer);
            case 51:
                return null;
            case 2:
                return null;
            case 6:
                return com.ardikars.jxnet.packet.tcp.TCP.newInstance(buffer);
            case 17:
                return com.ardikars.jxnet.packet.udp.UDP.newInstance(buffer);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
