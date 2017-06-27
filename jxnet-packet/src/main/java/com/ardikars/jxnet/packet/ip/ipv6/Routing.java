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

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class Routing extends IPv6ExtensionHeader {

    private byte nextHeader;
    private byte extensionLength;
    private byte routingType;
    private byte segmentLeft;

    private byte[] routingData;

    public byte getNextHeader() {
        return this.nextHeader;
    }

    public Routing setNextHeader(final byte nextHeader) {
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

}
