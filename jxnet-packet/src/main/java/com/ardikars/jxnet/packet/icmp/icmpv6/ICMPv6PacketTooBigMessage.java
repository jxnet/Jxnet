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
public class ICMPv6PacketTooBigMessage extends ICMPTypeAndCode {

    /**
     * A Packet Too Big MUST be sent by a router in response to a packet
     * that it cannot forward because the packet is larger than the MTU of
     * the outgoing link.  The information in this message is used as part
     * of the Path MTU Discovery process [PMTU].
     *
     * Originating a Packet Too Big Message makes an exception to one of the
     * rules as to when to originate an ICMPv6 error message.  Unlike other
     * messages, it is sent in response to a packet received with an IPv6
     * multicast destination address, or with a link-layer multicast or
     * link-layer broadcast address.
     */

    public static final ICMPv6PacketTooBigMessage PACKET_TOO_BIG_MESSAGE =
            new ICMPv6PacketTooBigMessage((byte) 0, "Packet too big message");

    protected ICMPv6PacketTooBigMessage(Byte code, String name) {
        super((byte) 2, code, name);
    }

    public static ICMPv6PacketTooBigMessage register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 2, code);
        ICMPv6PacketTooBigMessage packetTooBigMessage =
                new ICMPv6PacketTooBigMessage(key.getSecondKey(), name);
        return (ICMPv6PacketTooBigMessage) ICMPTypeAndCode.registry.put(key, packetTooBigMessage);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
