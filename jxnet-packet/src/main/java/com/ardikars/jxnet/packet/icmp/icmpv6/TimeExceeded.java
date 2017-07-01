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
public class TimeExceeded extends ICMPTypeAndCode {

    public static TimeExceeded HOP_LIMIT_EXCEEDED_IN_TRANSIT =
            new TimeExceeded((byte) 0, "Hop limit exceeded in transit");

    public static TimeExceeded FRAGMENT_REASSEMBLY_TIME_EXCEEDE =
            new TimeExceeded((byte) 1, "Fragment reassembly time exceeded");

    protected TimeExceeded(Byte code, String name) {
        super((byte) 3, code, name);
    }

    public static TimeExceeded register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 3, code);
        TimeExceeded timeExceeded =
                new TimeExceeded(key.getSecondKey(), name);
        return (TimeExceeded) ICMPTypeAndCode.registry.put(key, timeExceeded);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(HOP_LIMIT_EXCEEDED_IN_TRANSIT.getKey(), HOP_LIMIT_EXCEEDED_IN_TRANSIT);
        ICMPTypeAndCode.registry.put(FRAGMENT_REASSEMBLY_TIME_EXCEEDE.getKey(), FRAGMENT_REASSEMBLY_TIME_EXCEEDE);
    }

}
