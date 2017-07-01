/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.packet.icmp;

import com.ardikars.jxnet.packet.Packet;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
abstract class ICMP extends Packet {

    public static int ICMP_HEADER_LENGTH = 4;

    private ICMPTypeAndCode typeAndCode;
    private short checksum;

    public ICMPTypeAndCode getTypeAndCode() {
        return this.typeAndCode;
    }

    public ICMP setTypeAndCode(final ICMPTypeAndCode typeAndCode) {
        this.typeAndCode = typeAndCode;
        return this;
    }

    public short getChecksum() {
        return this.checksum;
    }

    public ICMP setChecksum(final short checksum) {
        this.checksum = checksum;
        return this;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getTypeAndCode().toString()).toString();
    }

}
