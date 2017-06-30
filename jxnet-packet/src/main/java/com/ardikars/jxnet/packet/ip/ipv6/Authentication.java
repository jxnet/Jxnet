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
import com.ardikars.jxnet.packet.ip.IPProtocolType;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Authentication extends Packet {

    private IPProtocolType nextHeader;
    private byte payloadLength;
    private int securityParameterIndex;
    private byte[] integrityCheckValue;

    private byte[] payload;

    public IPProtocolType getNextHeader() {
        return this.nextHeader;
    }

    public Authentication setNextHeader(final IPProtocolType nextHeader) {
        this.nextHeader = nextHeader;
        return this;
    }

    public byte getPayloadLength() {
        return this.payloadLength;
    }

    public Authentication setPayloadLength(final byte payloadLength) {
        this.payloadLength = payloadLength;
        return this;
    }

    public int getSecurityParameterIndex() {
        return this.securityParameterIndex;
    }

    public Authentication setSecurityParameterIndex(final int securityParameterIndex) {
        this.securityParameterIndex = securityParameterIndex;
        return this;
    }

    public byte[] getIntegrityCheckValue() {
        return this.integrityCheckValue;
    }

    public Authentication setIntegrityCheckValue(final byte[] integrityCheckValue) {
        this.integrityCheckValue = integrityCheckValue;
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public Authentication setPayload(final byte[] payload) {
        this.payload = payload;
        return this;
    }

    public static Authentication newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static Authentication newInstance(final byte[] bytes, final int offset, final int length) {
        final ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        Authentication authentication = new Authentication();
        authentication.setNextHeader(IPProtocolType.getInstance(buffer.get()));
        authentication.setPayloadLength(buffer.get());
        buffer.getShort(); //reserved
        authentication.setSecurityParameterIndex(buffer.getInt());
        int icvLength = ((authentication.getPayloadLength() + 2) * 8) - 12;
        authentication.integrityCheckValue = new byte[icvLength];
        buffer.get(authentication.integrityCheckValue, 0, icvLength);
        if (authentication.payload != null) {
            authentication.payload = new byte[buffer.limit() - icvLength + 12];
            buffer.get(authentication.payload, 0, authentication.payload.length);
        }
        return authentication;
    }

    @Override
    public Packet setPacket(Packet packet) {
        this.payload = packet.toBytes();
        return this;
    }

    @Override
    public Packet getPacket() {
        return this.nextHeader.decode(this.getPayload());
    }

    @Override
    public byte[] toBytes() {
        return new byte[0];
    }

    @Override
    public Packet build() {
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder("[")
                .append("Next Header: ")
                .append(this.getNextHeader())
                .append(", Payload Length: ")
                .append(this.getPayloadLength())
                .append(", SPI: ")
                .append(this.getSecurityParameterIndex())
                .append(", ICV: ")
                .append(Arrays.toString(this.getIntegrityCheckValue()))
                .append("]").toString();
    }

}
