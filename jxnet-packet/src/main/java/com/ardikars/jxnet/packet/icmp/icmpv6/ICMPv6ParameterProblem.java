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
public class ICMPv6ParameterProblem extends ICMPTypeAndCode {

    public static final ICMPv6ParameterProblem ERRORNEOUS_HEADER_FIELD_ENCOUTERED =
            new ICMPv6ParameterProblem((byte) 0, "Erroneous header field encountered");

    public static final ICMPv6ParameterProblem UNRECOGNIZED_NEXT_HEADER_TYPE_ENCOUNTERED =
            new ICMPv6ParameterProblem((byte) 1, "Unrecognized Next Header type encountered");

    public static final ICMPv6ParameterProblem UNRECOGNIZED_IPV6_OPTION_ENCOUNTERED =
            new ICMPv6ParameterProblem((byte) 2, "Unrecognized IPv6 option encountered");

    protected ICMPv6ParameterProblem(Byte code, String name) {
        super((byte) 4, code, name);
    }

    public static ICMPv6ParameterProblem register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 4, code);
        ICMPv6ParameterProblem parameterProblem =
                new ICMPv6ParameterProblem(key.getSecondKey(), name);
        return (ICMPv6ParameterProblem) ICMPTypeAndCode.registry.put(key, parameterProblem);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
