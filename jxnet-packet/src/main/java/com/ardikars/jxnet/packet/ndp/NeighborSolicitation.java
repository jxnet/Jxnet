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

package com.ardikars.jxnet.packet.ndp;

import com.ardikars.jxnet.Inet6Address;
import com.ardikars.jxnet.packet.Packet;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class NeighborSolicitation extends Packet {

    private Inet6Address targetAddress;

    private NeighborDiscoveryOptions options;

    public Inet6Address getTargetAddress() {
        return this.targetAddress;
    }

    public NeighborSolicitation setTargetAddress(final Inet6Address targetAddress) {
        this.targetAddress = targetAddress;
        return this;
    }

    public List<NeighborDiscoveryOptions.Option> getOptions() {
        return this.options.getOptions();
    }

    public NeighborSolicitation setOptions(final NeighborDiscoveryOptions options) {
        this.options = options;
        return this;
    }

    public NeighborSolicitation addOption(final NeighborDiscoveryOptions.OptionType type, final byte[] data) {
        if (this.options != null) {
            this.options.addOptions(type, data);
        } else {
            this.options = new NeighborDiscoveryOptions();
        }
        return this;
    }

    public static NeighborSolicitation newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static NeighborSolicitation newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        NeighborSolicitation neighborSolicitation = new NeighborSolicitation();
        byte[] ipv6AddrBuffer = new byte[Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(ipv6AddrBuffer, 0, Inet6Address.IPV6_ADDRESS_LENGTH);
        neighborSolicitation.setTargetAddress(Inet6Address.valueOf(ipv6AddrBuffer));
        byte[] optionsBuffer = new byte[buffer.limit() - Inet6Address.IPV6_ADDRESS_LENGTH];
        buffer.get(optionsBuffer, 0, optionsBuffer.length);
        neighborSolicitation.setOptions(NeighborDiscoveryOptions.newInstance(optionsBuffer));
        return neighborSolicitation;
    }

    @Override
    public byte[] toBytes() {
        byte[] opBuf = this.options.toBytes();
        byte[] data = new byte[Inet6Address.IPV6_ADDRESS_LENGTH + opBuf.length];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getTargetAddress().toBytes());
        buffer.put(opBuf);
        return data;
    }

}
