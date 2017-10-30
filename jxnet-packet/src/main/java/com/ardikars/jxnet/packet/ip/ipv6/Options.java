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
import com.ardikars.jxnet.util.HexUtils;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Options extends IPv6ExtensionHeader {

    public static int FIXED_OPTIONS_LENGTH = 6;
    public static int LENGTH_UNIT = 8;

    private byte nextHeader;
    private byte extensionLength;
    private byte[] options;

    protected byte type;

    public byte getNextHeader() {
        return this.nextHeader;
    }

    public Options setNextHeader(final byte nextHeader) {
        this.nextHeader = nextHeader;
        return this;
    }

    public byte getExtensionLength() {
        return this.extensionLength;
    }

    public Options setExtensionLength(final byte extensionLength) {
        this.extensionLength = extensionLength;
        return this;
    }

    public byte[] getOptions() {
        return this.options;
    }

    public Options setOptions(final byte[] options) {
        this.options = options;
        return this;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return null;
    }

    @Override
    public Packet getPacket() {
        if (this.nextPacket != null) {
            this.nextPacket.rewind();
        }
        switch (nextHeader) {
            case 58:
                return ICMPv6.newInstance(this.nextPacket);
            default:
                return UnknownPacket.newInstance(this.nextPacket);
        }
    }

    @Override
    public byte[] bytes() {
        return new byte[0];
    }

    @Override
    public ByteBuffer buffer() {
        return ByteBuffer.wrap(new byte[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append("[Next Header: ").append(this.getNextHeader())
                .append(", Extension Length: ").append(this.getExtensionLength())
                .append(", Options: ").append(this.getOptions())
                .append("]");
        return sb.toString();
    }

}
