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
public class ParameterProblem extends ICMPTypeAndCode {

    public static final ParameterProblem ERRORNEOUS_HEADER_FIELD_ENCOUTERED =
            new ParameterProblem((byte) 0, "Erroneous header field encountered");

    public static final ParameterProblem UNRECOGNIZED_NEXT_HEADER_TYPE_ENCOUNTERED =
            new ParameterProblem((byte) 1, "Unrecognized Next Header type encountered");

    public static final ParameterProblem UNRECOGNIZED_IPV6_OPTION_ENCOUNTERED =
            new ParameterProblem((byte) 2, "Unrecognized IPv6 option encountered");

    protected ParameterProblem(Byte code, String name) {
        super((byte) 4, code, name);
    }

    public static ParameterProblem register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 4, code);
        ParameterProblem parameterProblem =
                new ParameterProblem(key.getSecondKey(), name);
        return (ParameterProblem) ICMPTypeAndCode.registry.put(key, parameterProblem);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(ERRORNEOUS_HEADER_FIELD_ENCOUTERED.getKey(), ERRORNEOUS_HEADER_FIELD_ENCOUTERED);
        ICMPTypeAndCode.registry.put(UNRECOGNIZED_NEXT_HEADER_TYPE_ENCOUNTERED.getKey(), UNRECOGNIZED_NEXT_HEADER_TYPE_ENCOUNTERED);
        ICMPTypeAndCode.registry.put(UNRECOGNIZED_IPV6_OPTION_ENCOUNTERED.getKey(), UNRECOGNIZED_IPV6_OPTION_ENCOUNTERED);
    }

}
