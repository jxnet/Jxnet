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

/**
 * List of linktype available at @link https://www.tcpdump.org/linktypes.html.
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public final class DataLinkLayer extends NamedNumber<Short, DataLinkLayer> implements Packet.Factory {

    /**
     * Ethernet (10Mb, 100Mb, 1000Mb, and up): 1
     */
    public static final DataLinkLayer EN10MB = new DataLinkLayer((short) 1, "Ethernet");

    /**
     * Linux cooked-mode capture (SLL): 113
     */
    public static final DataLinkLayer LINUX_SLL = new DataLinkLayer((short) 113, "Linux SLL");

    private static final Map<DataLinkLayer, Short> registry =
            new HashMap<>();

    private static final Map<Short, Packet.Builder> builder =
            new HashMap<>();

    public DataLinkLayer(Short value, String name) {
        super(value, name);
    }

    @Override
    public Packet newInstance(ByteBuf buffer) {
        return builder.get(this.getValue()).build(buffer);
    }

    /**
     * @param value value.
     * @return returns {@link DataLinkLayer} object.
     */
    public static DataLinkLayer valueOf(short value) {
        for (Map.Entry<DataLinkLayer, Short> entry : registry.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
        }
        return new DataLinkLayer((short) -1, "Unknown");
    }

    /**
     *
     * @param dataLinkLayer data link type.
     */
    public static void register(DataLinkLayer dataLinkLayer) {
        synchronized (registry) {
            registry.put(dataLinkLayer, dataLinkLayer.getValue());
        }
    }

    /**
     *
     * @param dataLinkLayer data link type.
     * @param packetBuilder packet builder.
     */
    public static void register(DataLinkLayer dataLinkLayer, Packet.Builder packetBuilder) {
        synchronized (builder) {
            builder.put(dataLinkLayer.getValue(), packetBuilder);
        }
    }

    static {
        registry.put(EN10MB, EN10MB.getValue());
        registry.put(LINUX_SLL, LINUX_SLL.getValue());
    }

}
