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

package com.ardikars.jxnet.packet.arp;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Inet4Address;
import com.ardikars.jxnet.MacAddress;
import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.packet.ethernet.EthernetType;
import com.ardikars.jxnet.util.Builder;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ARP extends Packet implements Builder<ARP> {

    public static final int ARP_HEADER_LENGTH = 28;

    private DataLinkType hardwareType;
    private EthernetType protocolType;
    private byte hardwareAddressLength;
    private byte protocolAddressLength;
    private ARPOperationCode operationCode;
    private MacAddress senderHardwareAddress;
    private Inet4Address senderProtocolAddress;
    private MacAddress targetHardwareAddress;
    private Inet4Address targetProtocolAddress;

    public ARP() {
        this.setHardwareType(DataLinkType.UNKNOWN);
        this.setProtocolType(EthernetType.ARP);
        this.setHardwareAddressLength((byte) 6);
        this.setProtocolAddressLength((byte) 4);
        this.setOperationCode(ARPOperationCode.UNKNOWN);
        this.setSenderHardwareAddress(MacAddress.ZERO);
        this.setSenderProtocolAddress(Inet4Address.LOCALHOST);
        this.setTargetHardwareAddress(MacAddress.ZERO);
        this.setTargetProtocolAddress(Inet4Address.LOCALHOST);
    }

    public DataLinkType getHardwareType() {
        return this.hardwareType;
    }

    public ARP setHardwareType(final DataLinkType hardwareType) {
        this.hardwareType = hardwareType;
        return this;
    }

    public EthernetType getProtocolType() {
        return this.protocolType;
    }

    public ARP setProtocolType(final EthernetType protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public byte getHardwareAddressLength() {
        return (byte) (this.hardwareAddressLength & 0xff);
    }

    public ARP setHardwareAddressLength(final byte hardwareAddressLength) {
        this.hardwareAddressLength = (byte) (hardwareAddressLength & 0xff);
        return this;
    }

    public byte getProtocolAddressLength() {
        return (byte) (this.protocolAddressLength & 0xff);
    }

    public ARP setProtocolAddressLength(final byte protocolAddressLength) {
        this.protocolAddressLength = (byte) (protocolAddressLength & 0xff);
        return this;
    }

    public ARPOperationCode getOperationCode() {
        return this.operationCode;
    }

    public ARP setOperationCode(final ARPOperationCode operationCode) {
        this.operationCode = operationCode;
        return this;
    }

    public MacAddress getSenderHardwareAddress() {
        return this.senderHardwareAddress;
    }

    public ARP setSenderHardwareAddress(final MacAddress senderHardwareAddress) {
        this.senderHardwareAddress = senderHardwareAddress;
        return this;
    }

    public Inet4Address getSenderProtocolAddress() {
        return this.senderProtocolAddress;
    }

    public ARP setSenderProtocolAddress(final Inet4Address senderProtocolAddress) {
        this.senderProtocolAddress = senderProtocolAddress;
        return this;
    }

    public MacAddress getTargetHardwareAddress() {
        return this.targetHardwareAddress;
    }

    public ARP setTargetHardwareAddress(final MacAddress targetHardwareAddress) {
        this.targetHardwareAddress = targetHardwareAddress;
        return this;
    }

    public Inet4Address getTargetProtocolAddress() {
        return this.targetProtocolAddress;
    }

    public ARP setTargetProtocolAddress(final Inet4Address targetProtocolAddress) {
        this.targetProtocolAddress = targetProtocolAddress;
        return this;
    }

    public static ARP newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static ARP newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer bb = ByteBuffer.wrap(bytes, offset, length);
        ARP arp = new ARP();
        arp.setHardwareType(DataLinkType.valueOf(bb.getShort()));
        arp.setProtocolType(EthernetType.getInstance(bb.getShort()));
        arp.setHardwareAddressLength(bb.get());
        arp.setProtocolAddressLength(bb.get());
        arp.setOperationCode(ARPOperationCode.getInstance(bb.getShort()));

        byte[] hwAddrBuf = new byte[arp.getHardwareAddressLength() & 0xff];
        bb.get(hwAddrBuf);
        arp.setSenderHardwareAddress(MacAddress.valueOf(hwAddrBuf));

        byte[] addrBuf = new byte[arp.getProtocolAddressLength() & 0xff];
        bb.get(addrBuf);
        arp.setSenderProtocolAddress(Inet4Address.valueOf(addrBuf));

        hwAddrBuf = new byte[arp.getHardwareAddressLength()];
        bb.get(hwAddrBuf);
        arp.setTargetHardwareAddress(MacAddress.valueOf(hwAddrBuf));

        addrBuf = new byte[arp.getProtocolAddressLength()];
        bb.get(addrBuf);
        arp.setTargetProtocolAddress(Inet4Address.valueOf(addrBuf));
        return arp;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return null;
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[ARP_HEADER_LENGTH];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.putShort(this.getHardwareType().getValue());
        buffer.putShort(this.getProtocolType().getValue());
        buffer.put(this.getHardwareAddressLength());
        buffer.put(this.getProtocolAddressLength());
        buffer.putShort(this.getOperationCode().getValue());
        buffer.put(this.getSenderHardwareAddress().toBytes());
        buffer.put(this.getSenderProtocolAddress().toBytes());
        buffer.put(this.getTargetHardwareAddress().toBytes());
        buffer.put(this.getTargetProtocolAddress().toBytes());
        return data;
    }

    @Override
    public ARP build() {
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[Hardware Type: ").append(this.getHardwareType().getDescription());
        sb.append(", Protocol Type: ").append(this.getProtocolType().getName());
        sb.append(", Hardware Address Length: ").append(this.getHardwareAddressLength());
        sb.append(", Protocol Address Length: ").append(this.getProtocolAddressLength());
        sb.append(", Operation Code: ").append(this.getOperationCode().getName());
        sb.append(", SHA: ").append(this.getSenderHardwareAddress().toString());
        sb.append(", SPA: ").append(this.getSenderProtocolAddress().toString());
        sb.append(", THA: ").append(this.getTargetHardwareAddress().toString());
        sb.append(", TPA: ").append(this.getTargetProtocolAddress().toString()).append("]");
        return sb.toString();
    }

}
