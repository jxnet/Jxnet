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

package com.ardikars.jxnet.packet.ip.ipv6;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.UnknownPacket;
import com.ardikars.jxnet.packet.icmp.ICMPv6;
import com.ardikars.jxnet.packet.ip.IPProtocolType;
import com.ardikars.jxnet.packet.ip.IPv6;
import com.ardikars.jxnet.packet.tcp.TCP;
import com.ardikars.jxnet.packet.udp.UDP;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Routing extends IPv6ExtensionHeader {

    public static final int FIXED_ROUTING_HEADER_LENGTH = 4;
    public static final int FIXED_ROUTING_DATA_LENGTH = 4;

    private IPProtocolType nextHeader;
    private byte extensionLength;
    private byte routingType;
    private byte segmentLeft;

    private byte[] routingData;

    public IPProtocolType getNextHeader() {
        return this.nextHeader;
    }

    public Routing setNextHeader(final IPProtocolType nextHeader) {
        this.nextHeader = nextHeader;
        return this;
    }

    public byte getExtensionLength() {
        return this.extensionLength;
    }

    public Routing setExtensionLength(final byte extensionLength) {
        this.extensionLength = extensionLength;
        return this;
    }

    public byte getRoutingType() {
        return this.routingType;
    }

    public Routing setRoutingType(final byte routingType) {
        this.routingType = routingType;
        return this;
    }

    public byte getSegmentLeft() {
        return this.segmentLeft;
    }

    public Routing setSegmentLeft(final byte segmentLeft) {
        this.segmentLeft = segmentLeft;
        return this;
    }

    public byte[] getRoutingData() {
        return this.routingData;
    }

    public Routing setRoutingData(final byte[] routingData) {
        this.routingData = routingData;
        return this;
    }

    public ByteBuffer getPayload() {
        return this.nextPacket;
    }

    public Routing setPayload(final byte[] payload) {
        this.nextPacket = ByteBuffer.wrap(payload);
        return this;
    }

    public Routing setPayload(final ByteBuffer payload) {
        this.nextPacket = payload;
        return this;
    }

    public static Routing newInstance(final ByteBuffer buffer) {
        Routing routing = new Routing();
        routing.setNextHeader(IPProtocolType.getInstance(buffer.get()));
        routing.setExtensionLength(buffer.get());
        routing.setRoutingType(buffer.get());
        routing.setSegmentLeft(buffer.get());
        int dataLength = Routing.FIXED_ROUTING_DATA_LENGTH + 8 * routing.getExtensionLength();
        routing.routingData = new byte[dataLength];
        buffer.get(routing.routingData, 0, routing.routingData.length);
        routing.nextPacket = buffer.slice();
        return routing;
    }

    public static Routing newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static Routing newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    @Override
    public Packet setPacket(Packet packet) {
        return this;
    }

    @Override
    public Packet getPacket() {
        if (this.getPayload() == null || this.getPayload().capacity() == 0) return null;
        switch (this.getNextHeader().getValue()) {
            case 6: return TCP.newInstance(this.getPayload());
            case 17: return UDP.newInstance(this.getPayload());
            case 41: return IPv6.newInstance(this.getPayload());
            case 43: return Routing.newInstance(this.getPayload());
            case 44: return Fragment.newInstance(this.getPayload());
            case 58: return ICMPv6.newInstance(this.getPayload());
            default: return UnknownPacket.newInstance(this.getPayload());
        }
    }

    @Override
    public byte[] bytes() {
        byte[] data = new byte[Routing.FIXED_ROUTING_HEADER_LENGTH
                + Routing.FIXED_ROUTING_DATA_LENGTH + (8 * this.getExtensionLength())
                + (this.getPayload() == null ? 0 : this.getPayload().capacity())];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getNextHeader().getValue());
        buffer.put(this.getExtensionLength());
        buffer.put(this.getRoutingType());
        buffer.put(this.getSegmentLeft());
        buffer.put(this.getRoutingData());
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        ByteBuffer buffer = ByteBuffer
                .allocateDirect(Routing.FIXED_ROUTING_HEADER_LENGTH
                        + Routing.FIXED_ROUTING_DATA_LENGTH + (8 * this.getExtensionLength())
                        + (this.getPayload() == null ? 0 : this.getPayload().capacity()));
        buffer.put(this.getNextHeader().getValue());
        buffer.put(this.getExtensionLength());
        buffer.put(this.getRoutingType());
        buffer.put(this.getSegmentLeft());
        buffer.put(this.getRoutingData());
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        return buffer;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[")
                .append("Next Header: ")
                .append(this.getNextHeader())
                .append(", Extension Length: ")
                .append(this.getExtensionLength())
                .append(", Routing Type: ")
                .append(this.getRoutingType())
                .append(", Segment Left: ")
                .append(this.getSegmentLeft())
                .append(", Routing Data: ")
                .append(Arrays.toString(this.getRoutingData()))
                .append("]").toString();
    }

}
