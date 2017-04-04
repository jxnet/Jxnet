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
public class ICMPUnknownTypeAndCode extends ICMPTypeAndCode {

    public static ICMPUnknownTypeAndCode UNKNOWN =
            new ICMPUnknownTypeAndCode((byte) -1, "Unknown type or code");

    protected ICMPUnknownTypeAndCode(Byte code, String name) {
        super((byte) -1, code, name);
    }

    public static ICMPUnknownTypeAndCode register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) -1, code);
        ICMPUnknownTypeAndCode unknownTypeAndCode =
                new ICMPUnknownTypeAndCode(key.getSecondKey(), name);
        return (ICMPUnknownTypeAndCode) ICMPTypeAndCode.registry.put(key, unknownTypeAndCode);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(UNKNOWN.getKey(), UNKNOWN);
    }

}
