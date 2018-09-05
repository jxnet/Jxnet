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

package com.ardikars.jxnet.packet.layer;

import com.ardikars.common.util.NamedNumber;
import com.ardikars.jxnet.packet.Packet;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

public final class TransportLayer extends NamedNumber<Byte, TransportLayer> implements Packet.Factory {

    public static final TransportLayer ICMP = new TransportLayer((byte) 1, "Internet Control Message Protocol Version 4");

    public static final TransportLayer IPV6 = new TransportLayer((byte) 41, "IPv6 HeaderAbstract.");

    public static final TransportLayer IPV6_ICMP = new TransportLayer((byte) 58, "Internet Control Message Protocol Version 6");

    public static final TransportLayer IPV6_ROUTING = new TransportLayer((byte) 43, "Routing HeaderAbstract for IPv6.");

    public static final TransportLayer IPV6_FRAGMENT = new TransportLayer((byte) 44, "Fragment HeaderAbstract for IPv6.");

    public static final TransportLayer IPV6_HOPOPT = new TransportLayer((byte) 0, "IPv6 Hop by Hop NeighborDiscoveryOptions.");

    public static final TransportLayer IPV6_DSTOPT = new TransportLayer((byte) 60, "IPv6 Destination NeighborDiscoveryOptions.");

    public static final TransportLayer IPV6_ESP = new TransportLayer((byte) 50, "IPv6 ESP.");

    public static final TransportLayer IPV6_AH = new TransportLayer((byte) 51, "IPv6 Authentication HeaderAbstract.");

    public static final TransportLayer IGMP = new TransportLayer((byte) 2, "Internet Group Management Protocol");

    public static final TransportLayer TCP = new TransportLayer((byte) 6, "Transmission Control Protocol");

    public static final TransportLayer UDP = new TransportLayer((byte) 17, "User Datagram Protocol");

    public static final TransportLayer UNKNOWN = new TransportLayer((byte) -1, "Unknown");

    private static Map<Byte, TransportLayer> registry = new HashMap<>();

    private static Map<Byte, Packet.Builder> builder = new HashMap<>();

    protected TransportLayer(Byte value, String name) {
        super(value, name);
    }

    @Override
    public Packet newInstance(ByteBuf buffer) {
        return builder.get(this.getValue()).build(buffer);
    }

    /**
     *
     * @param value value.
     * @return returns {@link TransportLayer} object.
     */
    public static TransportLayer valueOf(final Byte value) {
        TransportLayer transportLayer = registry.get(value);
        if (transportLayer == null) {
            return UNKNOWN;
        } else {
            return transportLayer;
        }
    }

    /**
     *
     * @param type type
     */
    public static void register(final TransportLayer type) {
        registry.put(type.getValue(), type);
    }

    /**
     *
     * @param type type.
     * @param packetBuilder packet builder.
     */
    public static void register(TransportLayer type, Packet.Builder packetBuilder) {
        builder.put(type.getValue(), packetBuilder);
    }

    static {
        registry.put(ICMP.getValue(), ICMP);
        registry.put(IPV6.getValue(), IPV6);
        registry.put(IPV6_ICMP.getValue(), IPV6_ICMP);
        registry.put(IPV6_ROUTING.getValue(), IPV6_ROUTING);
        registry.put(IPV6_FRAGMENT.getValue(), IPV6_FRAGMENT);
        registry.put(IPV6_HOPOPT.getValue(), IPV6_HOPOPT);
        registry.put(IPV6_DSTOPT.getValue(), IPV6_DSTOPT);
        registry.put(IPV6_ESP.getValue(), IPV6_ESP);
        registry.put(IPV6_AH.getValue(), IPV6_AH);
        registry.put(IGMP.getValue(), IGMP);
        registry.put(TCP.getValue(), TCP);
        registry.put(UDP.getValue(), UDP);
    }

}
