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

    public static final byte FIXED_HEADER_LENGTH = 12; // bytes

    private IPProtocolType nextHeader;
    private byte payloadLength;
    private int securityParameterIndex;
    private int sequenceNumber;
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

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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

    public static Authentication newInstance(final ByteBuffer buffer) {
        Authentication authentication = new Authentication();
        authentication.setNextHeader(IPProtocolType.getInstance(buffer.get()));
        authentication.setPayloadLength(buffer.get());
        buffer.getShort(); //reserved
        authentication.setSecurityParameterIndex(buffer.getInt());
        int icvLength = ((authentication.getPayloadLength() + 2) * 8) - 12;
        authentication.setSequenceNumber(buffer.getInt());
        authentication.integrityCheckValue = new byte[icvLength];
        buffer.get(authentication.integrityCheckValue, 0, icvLength);
        if (authentication.payload != null) {
            authentication.payload = new byte[buffer.limit() - icvLength + 12];
            buffer.get(authentication.payload, 0, authentication.payload.length);
        }
        return authentication;
    }

    public static Authentication newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static Authentication newInstance(final byte[] bytes, final int offset, final int length) {
        return newInstance(ByteBuffer.wrap(bytes, offset, length));
    }

    @Override
    public Packet setPacket(Packet packet) {
        ByteBuffer buffer = packet.buffer();
        this.payload = new byte[buffer.capacity()];
        buffer.get(this.payload);
        return this;
    }

    @Override
    public Packet getPacket() {
        return this.nextHeader.decode(ByteBuffer.wrap(this.getPayload()));
    }

    @Override
    public byte[] bytes() {
        byte[] payloadData = null;
        if (this.getPayload() != null) {
            payloadData = this.getPayload();
        }
        int headerLength = FIXED_HEADER_LENGTH
                + ((this.getIntegrityCheckValue() != null) ? this.getIntegrityCheckValue().length : 0);
        int payloadLength = 0;
        if (payloadData != null) {
            payloadLength = payloadData.length;
        }
        final byte[] data = new byte[headerLength + payloadLength];
        final ByteBuffer bb = ByteBuffer.wrap(data);
        bb.put(this.getNextHeader().getValue());
        bb.put(this.getPayloadLength());
        bb.putShort((short) 0);
        bb.putInt(this.getSequenceNumber());
        bb.putInt(this.getSecurityParameterIndex());
        if (this.getIntegrityCheckValue() != null) {
            bb.put(this.getIntegrityCheckValue(), 0, this.getIntegrityCheckValue().length);
        }
        if (payloadData != null) {
            bb.put(payloadData);
        }
        return data;
    }

    @Override
    public ByteBuffer buffer() {
        ByteBuffer buffer = ByteBuffer
                .allocateDirect(FIXED_HEADER_LENGTH +
                + ((this.getIntegrityCheckValue() != null) ? this.getIntegrityCheckValue().length : 0)
                + ((this.getPayload() != null) ? this.getPayload().length : 0));
        buffer.put(this.getNextHeader().getValue());
        buffer.put(this.getPayloadLength());
        buffer.putShort((short) 0);
        buffer.putInt(this.getSequenceNumber());
        buffer.putInt(this.getSecurityParameterIndex());
        if (this.getIntegrityCheckValue() != null) {
            buffer.put(this.getIntegrityCheckValue(), 0, this.getIntegrityCheckValue().length);
        }
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        return buffer;
    }

    @Override
    public String toString() {
        return new StringBuilder("[")
                .append("Next Header: ")
                .append(this.getNextHeader())
                .append(", Payload Length: ")
                .append(this.getPayloadLength())
                .append(", Sequence: ")
                .append(this.getSequenceNumber())
                .append(", SPI: ")
                .append(this.getSecurityParameterIndex())
                .append(", ICV: ")
                .append(Arrays.toString(this.getIntegrityCheckValue()))
                .append("]").toString();
    }

}
