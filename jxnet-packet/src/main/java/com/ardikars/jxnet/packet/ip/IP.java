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

package com.ardikars.jxnet.packet.ip;

import com.ardikars.jxnet.packet.Packet;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
abstract class IP extends Packet {

    protected byte version;

    public byte getVersion() {
        return this.version;
    }

    public IP setVersion(final byte version) {
        this.version = version;
        return this;
    }

}
