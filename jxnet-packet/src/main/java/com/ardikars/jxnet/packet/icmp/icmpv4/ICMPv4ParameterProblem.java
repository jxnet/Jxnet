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
public class ICMPv4ParameterProblem extends ICMPTypeAndCode {

    public static final ICMPv4ParameterProblem POINTER_INDICATES_THE_ERROR =
            new ICMPv4ParameterProblem((byte) 0, "Pointer indicates the error");

    public static final ICMPv4ParameterProblem MISSING_REQUIRED_OPTION =
            new ICMPv4ParameterProblem((byte) 1, "Missing a required option");

    public static final ICMPv4ParameterProblem BAD_LENGTH =
            new ICMPv4ParameterProblem((byte) 2, "Bad length");

    protected ICMPv4ParameterProblem(Byte code, String name) {
        super((byte) 12, code, name);
    }

    public static ICMPv4ParameterProblem register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 12, code);
        ICMPv4ParameterProblem parameterProblem =
                new ICMPv4ParameterProblem(key.getSecondKey(), name);
        return (ICMPv4ParameterProblem) ICMPTypeAndCode.registry.put(key, parameterProblem);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(POINTER_INDICATES_THE_ERROR.getKey(), POINTER_INDICATES_THE_ERROR);
        ICMPTypeAndCode.registry.put(MISSING_REQUIRED_OPTION.getKey(), MISSING_REQUIRED_OPTION);
        ICMPTypeAndCode.registry.put(BAD_LENGTH.getKey(), BAD_LENGTH);
    }

}
