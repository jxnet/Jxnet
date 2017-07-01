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

package com.ardikars.jxnet.packet.ethernet;

import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.UnknownPacket;
import com.ardikars.jxnet.packet.arp.ARP;
import com.ardikars.jxnet.packet.ip.IPv4;
import com.ardikars.jxnet.packet.ip.IPv6;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class Ethernet extends Packet {

    public static final int ETHERNET_HEADER_LENGTH = 14;
    public static final int VLAN_HEADER_LENGTH = 4;

    private MacAddress destinationMacAddress;
    private MacAddress sourceMacAddress;
    private byte priorityCodePoint;
    private byte canonicalFormatIndicator;
    private short vlanIdentifier;
    private ProtocolType ethernetType;

    /**
     * If needed
     */
    private boolean padding;

    //private int checksum; //CRC32

    public Ethernet() {
        this.setDestinationMacAddress(MacAddress.ZERO);
        this.setSourceMacAddress(MacAddress.ZERO);
        this.setPriorityCodePoint((byte) 0);
        this.setCanonicalFormatIndicator((byte) 0);
        this.setVlanIdentifier((short) 0xffff);
        this.setEthernetType(ProtocolType.UNKNOWN);
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

    public ProtocolType getEthernetType() {
        return this.ethernetType;
    }

    public Ethernet setEthernetType(final ProtocolType ethernetType) {
        this.ethernetType = ethernetType;
        return this;
    }

    @Deprecated
    public byte[] getPayload() {
        return this.nextPacket;
    }

    @Deprecated
    public Ethernet setPayload(final byte[] payload) {
        this.nextPacket = payload;
        return this;
    }

    public Ethernet setPadding(final boolean padding) {
        this.padding = padding;
        return this;
    }

    public static Ethernet newInstance(final byte[] bytes) {
        return Ethernet.newInstance(bytes, 0, bytes.length);
    }

    public static Ethernet newInstance(final byte[] bytes, final int offset, final int length) {
        Ethernet ethernet = new Ethernet();
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        byte[] hwAddrBuf = new byte[MacAddress.MAC_ADDRESS_LENGTH];
        buffer.get(hwAddrBuf);
        ethernet.setDestinationMacAddress(MacAddress.valueOf(hwAddrBuf));
        hwAddrBuf = new byte[MacAddress.MAC_ADDRESS_LENGTH];
        buffer.get(hwAddrBuf);
        ethernet.setSourceMacAddress(MacAddress.valueOf(hwAddrBuf));
        ProtocolType ethernetType = ProtocolType.getInstance(buffer.getShort());
        if (ethernetType == ProtocolType.DOT1Q_VLAN_TAGGED_FRAMES) {
            short tci = buffer.getShort();
            ethernet.setPriorityCodePoint((byte) (tci >> 13 & 0x07));
            ethernet.setCanonicalFormatIndicator((byte) (tci >> 14 & 0x01));
            ethernet.setVlanIdentifier((short) (tci & 0x0fff));
            ethernetType = ProtocolType.getInstance(buffer.getShort());
        } else {
            ethernet.setVlanIdentifier((short) 0xffff);
        }
        ethernet.setEthernetType(ethernetType);
        if (ethernet.getVlanIdentifier() != (short) 0xffff) {
            ethernet.nextPacket = new byte[(buffer.limit() - (ETHERNET_HEADER_LENGTH + VLAN_HEADER_LENGTH))];
            buffer.get(ethernet.nextPacket);
        } else {
            ethernet.nextPacket = new byte[(buffer.limit() - ETHERNET_HEADER_LENGTH)];
            buffer.get(ethernet.nextPacket);
        }
        return ethernet;
    }

    @Override
    public Packet setPacket(final Packet packet) {
        if (packet == null) {
            return this;
        }
        switch (packet.getClass().getName()) {
            case "com.ardikars.jxnet.packet.ip.IPv4":
                IPv4 ipv4 = (IPv4) packet;
                this.setEthernetType(ProtocolType.IPV4);
                this.nextPacket = ipv4.toBytes();
                return this;
            case "com.ardikars.jxnet.packet.ip.IPv6":
                IPv6 ipv6 = (IPv6) packet;
                this.setEthernetType(ProtocolType.IPV6);
                this.nextPacket = ipv6.toBytes();
                return this;
            case "com.ardikars.jxnet.packet.arp.ARP":
                ARP arp = (ARP) packet;
                this.setEthernetType(ProtocolType.ARP);
                this.nextPacket = arp.toBytes();
                return this;
            default:
                this.nextPacket = packet.toBytes();
                return this;
        }
    }

    @Override
    public Packet getPacket() {
        return this.getEthernetType().decode(this.nextPacket);
    }

    @Override
    public byte[] toBytes() {
        int headerLength = ETHERNET_HEADER_LENGTH +
                ((this.getVlanIdentifier() != (short) 0xffff) ? VLAN_HEADER_LENGTH : 0) +
                ((this.nextPacket == null) ? 0 : this.nextPacket.length);
        if ((this.padding == true) && (headerLength < 60)) {
            headerLength = 60;
        }
        byte[] data = new byte[headerLength];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put(this.getDestinationMacAddress().toBytes());
        buffer.put(this.getSourceMacAddress().toBytes());
        if (this.getVlanIdentifier() != (short) 0xffff) {
            buffer.putShort(ProtocolType.DOT1Q_VLAN_TAGGED_FRAMES.getValue());
            buffer.putShort((short) (((this.getPriorityCodePoint() << 13) & 0x07)
                    | ((this.getCanonicalFormatIndicator() << 14) & 0x01) | (this.getVlanIdentifier() & 0x0fff)));
        }
        buffer.putShort((short) (this.getEthernetType().getValue() & 0xffff));
        if (this.nextPacket != null) {
            buffer.put(this.nextPacket);
        }
        return data;
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
