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

package com.ardikars.jxnet.packet.pppoe;

import com.ardikars.jxnet.packet.Packet;
import com.ardikars.jxnet.util.Builder;

import java.nio.ByteBuffer;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class PPPoED extends Packet implements Builder<Packet> {

    public static int PPPOED_DISCOVERY_HEADER_LENGTH = 6;

    private byte version;
    private byte type;
    private byte code;
    private short sessionID;
    private short length;

    /**
     * PPPoED payload
     */
    private byte[] payload;

    public byte getVersion() {
        return (byte) (this.version & 0xf);
    }

    public PPPoED setVersion(final byte version) {
        this.version = (byte) (version & 0xf);
        return this;
    }

    public byte getType() {
        return (byte) (this.type & 0xf);
    }

    public PPPoED setType(final byte type) {
        this.type = (byte) (type & 0xf);
        return this;
    }

    public byte getCode() {
        return (byte) (this.code & 0xff);
    }

    public PPPoED setCode(final byte code) {
        this.code = (byte) (code & 0xff);
        return this;
    }

    public short getSessionID() {
        return (short) (this.sessionID & 0xffff);
    }

    public PPPoED setSessionID(final short sessionID) {
        this.sessionID = (short) (sessionID & 0xffff);
        return this;
    }

    public short getLength() {
        return (byte) (this.length & 0xffff);
    }

    public PPPoED setLength(final short length) {
        this.length = (byte) (length & 0xffff);
        return this;
    }

    public byte[] getPayload() {
        return this.payload;
    }

    public void setPayload(final byte[] payload) {
        this.payload = payload;
    }

    public static PPPoED newInstance(final byte[] bytes) {
        return newInstance(bytes, 0, bytes.length);
    }

    public static PPPoED newInstance(final byte[] bytes, final int offset, final int length) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, offset, length);
        PPPoED pppoe = new PPPoED();
        pppoe.version = buffer.get();
        pppoe.setVersion((byte) (pppoe.version & 0xf));
        pppoe.setType((byte) ((pppoe.version >> 4) & 0xf));
        pppoe.setCode(buffer.get());
        pppoe.setSessionID(buffer.getShort());
        pppoe.setLength(buffer.getShort());
        pppoe.payload = new byte[buffer.limit() - PPPOED_DISCOVERY_HEADER_LENGTH];
        buffer.get(pppoe.payload);
        return pppoe;
    }

    @Override
    public Packet setPacket(Packet packet) {
        return this;
    }

    @Override
    public Packet getPacket() {
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] data = new byte[PPPOED_DISCOVERY_HEADER_LENGTH +
                ((this.getPayload() == null) ? 0 : this.getPayload().length)];
        ByteBuffer buffer = ByteBuffer.wrap(data);
        buffer.put((byte) (((this.getVersion() << 4) & 0xf) | (this.getType() & 0xf)));
        buffer.put(this.getCode());
        buffer.putShort(this.getSessionID());
        buffer.putShort(this.getLength());
        buffer.put(this.getPayload());
        return data;
    }

    @Override
    public Packet build() {
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Version: " + this.getVersion())
                .append(", Type: " + this.getType())
                .append(", Code: " + this.getCode())
                .append(", Session ID: " + this.getSessionID())
                .append(", Length: " + this.getLength())
                .toString();
    }

}
