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

package com.ardikars.jxnet.packet.ethernet;

import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.packet.ip.IPv6;
import com.ardikars.jxnet.util.Builder;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class Ethernet extends Packet implements Builder<Ethernet> {

    public static final int ETHERNET_HEADER_LENGTH = 14;
    public static final int VLAN_HEADER_LENGTH = 4;

    private MacAddress destinationMacAddress;
    private MacAddress sourceMacAddress;
    private byte priorityCodePoint;
    private byte canonicalFormatIndicator;
    private short vlanIdentifier;
    private EthernetType ethernetType;
    private byte[] padding;

    /**
     * Ethernet paylaod
     */
    private byte[] payload;

    //private int checksum; //CRC32

    public Ethernet() {
        this.setDestinationMacAddress(MacAddress.ZERO);
        this.setSourceMacAddress(MacAddress.ZERO);
        this.setPriorityCodePoint((byte) 0);
        this.setCanonicalFormatIndicator((byte) 0);
        this.setVlanIdentifier((short) 0xffff);
        this.setEthernetType(EthernetType.UNKNOWN);
        this.setPayload(null);
    }

    public MacAddress getDestinationMacAddress() {
        return this.destinationMacAddress;
    }

    public Ethernet setDestinationMacAddress(final MacAddress destinationMacAddress) {
        this.destinationMacAddress = destinationMacAddress;
        return this;
    }

    public MacAddress getSourceMacAddress() {
        return this.sourceMacAddress;
    }

    public Ethernet setSourceMacAddress(final MacAddress sourceMacAddress) {
        this.sourceMacAddress = sourceMacAddress;
        return this;
    }

    public byte getPriorityCodePoint() {
        return (byte) (this.priorityCodePoint & 0x07);
    }

    public Ethernet setPriorityCodePoint(final byte priorityCodePoint) {
        this.priorityCodePoint = (byte) (priorityCodePoint & 0x07);
        return this;
    }

    public byte getCanonicalFormatIndicator() {
        return (byte) (this.canonicalFormatIndicator & 0x01);
    }

    public Ethernet setCanonicalFormatIndicator(final byte canonicalFormatIndicator) {
        this.canonicalFormatIndicator = (byte) (canonicalFormatIndicator & 0x01);
        return this;
    }

    public short getVlanIdentifier() {
        return (short) (this.vlanIdentifier & 0xffff);
    }

    public Ethernet setVlanIdentifier(final short vlanIdentifier) {
        this.vlanIdentifier = (short) (vlanIdentifier & 0xffff);
        return this;
    }

    public EthernetType getEthernetType() {
        return this.ethernetType;
    }

    public Ethernet setEthernetType(final EthernetType ethernetType) {
        this.ethernetType = ethernetType;
        return this;
    }

    public boolean isPadded() {
        return !((this.payload.length + ETHERNET_HEADER_LENGTH +
                ((this.vlanIdentifier == 0xffff) ? 0 : VLAN_HEADER_LENGTH) +
                ((this.padding == null) ? 0 : padding.length))  < 60);
    }

    public byte[] getPadding() {
        return this.padding;
    }

    public Ethernet setPadding(final byte[] padding) {
        this.padding = padding;
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public Ethernet setPayload(final byte[] payload) {
        this.payload = payload;
        return this;
    }

    public static Ethernet newInstance(final byte[] bytes) {
        return Ethernet.newInstance(bytes, 0, bytes.length);
    }

    public static Ethernet newInstance(final byte[] bytes, final int offset, final int length) {
        Ethernet ethernet = new Ethernet();
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        byte[] MACBuffer = new byte[MacAddress.MAC_ADDRESS_LENGTH];
        buffer.get(MACBuffer);
        ethernet.setDestinationMacAddress(MacAddress.valueOf(MACBuffer));
        buffer.get(MACBuffer);
        ethernet.setSourceMacAddress(MacAddress.valueOf(MACBuffer));
        EthernetType ethernetType = EthernetType.getInstance(buffer.getShort());
        if (ethernetType == EthernetType.DOT1Q_VLAN_TAGGED_FRAMES) {
            short tci = buffer.getShort();
            ethernet.setPriorityCodePoint((byte) (tci >> 13 & 0x07));
            ethernet.setCanonicalFormatIndicator((byte) (tci >> 14 & 0x01));
            ethernet.setVlanIdentifier((short) (tci & 0x0fff));
            ethernetType = EthernetType.getInstance(buffer.getShort());
        } else {
            ethernet.setVlanIdentifier((short) 0xffff);
        }
        ethernet.setEthernetType(ethernetType);
        if (ethernet.getVlanIdentifier() != (short) 0xffff) {
            ethernet.payload = new byte[(buffer.limit() - (ETHERNET_HEADER_LENGTH + VLAN_HEADER_LENGTH))];
            buffer.get(ethernet.payload);
        } else {
            ethernet.payload = new byte[(buffer.limit() - ETHERNET_HEADER_LENGTH)];
            buffer.get(ethernet.payload);
        }
        return ethernet;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return setPayload(packet.toBytes());
    }

    @Override
    public Packet getPacket() {
        if (this.getEthernetType() == null) return null;
        switch (this.getEthernetType().getValue() & 0xffff) {
            case 0x0800: return IPv4.newInstance(this.getPayload());
            case 0x86dd: return IPv6.newInstance(this.getPayload());
        }
        return null;
    }

    @Override
    public byte[] toBytes() {
        int headerLength = ETHERNET_HEADER_LENGTH +
                ((this.getEthernetType() == EthernetType.DOT1Q_VLAN_TAGGED_FRAMES) ? VLAN_HEADER_LENGTH : 0) +
                ((this.getPayload() == null) ? 0 : this.getPayload().length) +
                ((this.getPadding() == null) ? 0 : this.getPadding().length);
        if (headerLength < 60) {
            headerLength = 60;
        }
        byte[] data = new byte[headerLength];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getDestinationMacAddress().toBytes());
        buffer.put(this.getSourceMacAddress().toBytes());
        if (this.getVlanIdentifier() != (short) 0xffff) {
            buffer.putShort(EthernetType.DOT1Q_VLAN_TAGGED_FRAMES.getValue());
            buffer.putShort((short) (((this.getPriorityCodePoint() << 13) & 0x07)
                    | ((this.getCanonicalFormatIndicator() << 14) & 0x01) | (this.getVlanIdentifier() & 0x0fff)));
        }
        buffer.putShort((short) (this.getEthernetType().getValue() & 0xffff));
        if (this.getPayload() != null) {
            buffer.put(this.getPayload());
        }
        if (headerLength < 60 && this.getPadding() != null) {
            buffer.put(this.getPadding());
        }
        return data;
    }

    @Override
    public Ethernet build() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
            .append("[").append("Destination: " + this.getDestinationMacAddress().toString())
            .append(", Source: " + this.getSourceMacAddress().toString());
            if ((this.getVlanIdentifier() != (short) 0xffff)) {
                sb.append(", Tag Control Information (Priority Code Point: " + this.getPriorityCodePoint())
                .append(", Canonical Format Indicator: " + this.getCanonicalFormatIndicator())
                .append(", Vlan Identifier: " + this.getVlanIdentifier())
                .append(")");
            }
            return sb.append(", Ethernet Type: " + this.getEthernetType().getName())
                    .append("]").toString();
    }

}
