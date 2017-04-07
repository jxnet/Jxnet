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

package com.ardikars.jxnet.packet.icmp;

import com.ardikars.jxnet.util.TwoKeyMap;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ICMPv4Timestamp extends ICMPTypeAndCode {

    public static final ICMPv4Timestamp TIMESTAMP =
            new ICMPv4Timestamp((byte) 0, "Timestamp");

    protected ICMPv4Timestamp(Byte code, String name) {
        super((byte) 13, code, name);
    }

    public static ICMPv4Timestamp register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 13, code);
        ICMPv4Timestamp timestamp =
                new ICMPv4Timestamp(key.getSecondKey(), name);
        return (ICMPv4Timestamp) ICMPTypeAndCode.registry.put(key, timestamp);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(TIMESTAMP.getKey(), TIMESTAMP);
    }

}
