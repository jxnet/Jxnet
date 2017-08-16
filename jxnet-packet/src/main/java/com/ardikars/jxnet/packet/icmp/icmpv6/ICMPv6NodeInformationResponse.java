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
import com.ardikars.jxnet.packet.icmp.ICMPv6;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.5
 */
public class ICMPv6NodeInformationResponse extends ICMPTypeAndCode {

    public static final ICMPv6NodeInformationResponse SUCCESSFULL_REPLY =
            new ICMPv6NodeInformationResponse((byte) 0, "A successfull reply");

    public static final ICMPv6NodeInformationResponse RESPONDER_REFUSES_TO_SUPPLY_THE_ASWER =
            new ICMPv6NodeInformationResponse((byte) 1, "The Responder refuses to supply the answer");

    public static final ICMPv6NodeInformationResponse QTYPE_OF_THE_QUERY_IS_UNKNOWN_TO_THE_RESPONDER =
            new ICMPv6NodeInformationResponse((byte) 2, "The Qtype of the Query is unknown to the Responder");

    protected ICMPv6NodeInformationResponse(Byte code, String name) {
        super((byte) 140, code, name);
    }

    public static ICMPv6NodeInformationResponse register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 140, code);
        ICMPv6NodeInformationResponse informationResponse =
                new ICMPv6NodeInformationResponse(key.getSecondKey(), name);
        return (ICMPv6NodeInformationResponse) ICMPTypeAndCode.registry.put(key, informationResponse);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
