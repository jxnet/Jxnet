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

package com.ardikars.jxnet;

import java.util.Arrays;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class Addr {

    private short addr_type;
    private short addr_bits;
    private byte[] data;

    private Addr(short addr_type, short addr_bits, byte[] data) {
        this.addr_type = addr_type;
        this.addr_bits = addr_bits;
        this.data = data;
    }

    public static Addr initialize(short addr_type, short addr_bits, byte[] data) {
        return new Addr(addr_type, addr_bits, data);
    }

    public void setAddrType(short addr_type) {
        this.addr_type = addr_type;
    }

    public void setAddrBits(short addr_bits) {
        this.addr_bits = addr_bits;
    }

    public short getAddrType() {
        return addr_type;
    }

    public short getAddrBits() {
        return addr_bits;
    }

    public byte[] getData() {
        return data;
    }

    public String getStringAddress() {
        switch (addr_type) {
            case 2:
                return Inet4Address.valueOf(data).toString();
            case 10:
                return Inet6Address.valueOf(data).toString();
            default:
                return MacAddress.valueOf(data).toString();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Addr Type: ")
                .append(addr_type)
                .append(", Addr Bits: ")
                .append(addr_bits)
                .append(", Data: ")
                .append(Arrays.toString(data))
                .append("]").toString();
    }

}
