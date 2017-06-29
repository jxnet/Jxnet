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
import com.ardikars.jxnet.TwoKeyMap;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ICMPv4EchoRequest extends ICMPTypeAndCode {

    public static final ICMPv4EchoRequest ECHO_REQUEST =
            new ICMPv4EchoRequest((byte) 0, "Echo request (used to ping)");

    protected ICMPv4EchoRequest(Byte code, String name) {
        super((byte) 8, code, name);
    }

    public static ICMPv4EchoRequest register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 8, code);
        ICMPv4EchoRequest icmPv4EchoRequest =
                new ICMPv4EchoRequest(key.getSecondKey(), name);
        return (ICMPv4EchoRequest) ICMPTypeAndCode.registry.put(key, icmPv4EchoRequest);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(ECHO_REQUEST.getKey(), ECHO_REQUEST);
    }

}
