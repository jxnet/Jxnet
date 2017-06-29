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
public class ICMPv4RedirectMessage extends ICMPTypeAndCode {

    public static final ICMPv4RedirectMessage REDIRECT_DATAGRAM_FOR_NETWORK =
            new ICMPv4RedirectMessage((byte) 0, "Redirect datagram for the network");

    protected ICMPv4RedirectMessage(Byte code, String name) {
        super((byte) 5, code, name);
    }

    public static ICMPv4RedirectMessage register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 5, code);
        ICMPv4RedirectMessage redirectMessage = new ICMPv4RedirectMessage(key.getSecondKey(), name);
        return (ICMPv4RedirectMessage) ICMPTypeAndCode.registry.put(key, redirectMessage);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(REDIRECT_DATAGRAM_FOR_NETWORK.getKey(), REDIRECT_DATAGRAM_FOR_NETWORK);
    }

}
