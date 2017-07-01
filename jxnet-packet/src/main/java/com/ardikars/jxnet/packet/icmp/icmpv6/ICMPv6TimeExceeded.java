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

package com.ardikars.jxnet.packet.icmp.icmpv6;

import com.ardikars.jxnet.TwoKeyMap;
import com.ardikars.jxnet.packet.icmp.ICMPTypeAndCode;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ICMPv6TimeExceeded extends ICMPTypeAndCode {

    public static ICMPv6TimeExceeded HOP_LIMIT_EXCEEDED_IN_TRANSIT =
            new ICMPv6TimeExceeded((byte) 0, "Hop limit exceeded in transit");

    public static ICMPv6TimeExceeded FRAGMENT_REASSEMBLY_TIME_EXCEEDED =
            new ICMPv6TimeExceeded((byte) 1, "Fragment reassembly time exceeded");

    protected ICMPv6TimeExceeded(Byte code, String name) {
        super((byte) 3, code, name);
    }

    public static ICMPv6TimeExceeded register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 3, code);
        ICMPv6TimeExceeded timeExceeded =
                new ICMPv6TimeExceeded(key.getSecondKey(), name);
        return (ICMPv6TimeExceeded) ICMPTypeAndCode.registry.put(key, timeExceeded);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
