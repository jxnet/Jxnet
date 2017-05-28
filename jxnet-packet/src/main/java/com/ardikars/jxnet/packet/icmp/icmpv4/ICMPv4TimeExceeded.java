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

package com.ardikars.jxnet.packet.icmp.icmpv4;

import com.ardikars.jxnet.packet.icmp.ICMPTypeAndCode;
import com.ardikars.jxnet.util.TwoKeyMap;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ICMPv4TimeExceeded extends ICMPTypeAndCode {

    public static final ICMPv4TimeExceeded TTL_EXPIRED_IN_TRANSIT =
            new ICMPv4TimeExceeded((byte) 0, "TTL expired in transit");

    public static final ICMPv4TimeExceeded FRAGMENT_REASSEMBLY_TIME_EXEEDED =
            new ICMPv4TimeExceeded((byte) 1, "Fragment reassembly time exceeded");

    protected ICMPv4TimeExceeded(Byte code, String name) {
        super((byte) 11, code, name);
    }

    public static ICMPv4TimeExceeded register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 11, code);
        ICMPv4TimeExceeded timeExceeded =
                new ICMPv4TimeExceeded(key.getSecondKey(), name);
        return (ICMPv4TimeExceeded) ICMPTypeAndCode.registry.put(key, timeExceeded);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(TTL_EXPIRED_IN_TRANSIT.getKey(), TTL_EXPIRED_IN_TRANSIT);
        ICMPTypeAndCode.registry.put(FRAGMENT_REASSEMBLY_TIME_EXEEDED.getKey(), FRAGMENT_REASSEMBLY_TIME_EXEEDED);
    }

}
