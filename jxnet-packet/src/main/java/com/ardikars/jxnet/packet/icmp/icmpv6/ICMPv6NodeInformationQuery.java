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
public class ICMPv6NodeInformationQuery extends ICMPTypeAndCode {

    public static final ICMPv6NodeInformationQuery DATA_FIELD_CONTAINS_IPV6_ADDRESS =
            new ICMPv6NodeInformationQuery((byte) 0, "Data field contains an IPv6 address");

    public static final ICMPv6NodeInformationQuery DATA_FIELD_CONTAIONS_NAME =
            new ICMPv6NodeInformationQuery((byte) 1, "Data field contains a name which is the Subject of this Query");

    public static final ICMPv6NodeInformationQuery DATA_FIELD_CONTAINS_IPV4_ADDRESS =
            new ICMPv6NodeInformationQuery((byte) 2, "Data field contains an IPv4 address");

    protected ICMPv6NodeInformationQuery(Byte code, String name) {
        super((byte) 139, code, name);
    }

    public static ICMPv6NodeInformationQuery register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 139, code);
        ICMPv6NodeInformationQuery nodeInformationQuery =
                new ICMPv6NodeInformationQuery(key.getSecondKey(), name);
        return (ICMPv6NodeInformationQuery) ICMPTypeAndCode.registry.put(key, nodeInformationQuery);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
