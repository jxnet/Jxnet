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
public class ICMPv4TimestampReply extends ICMPTypeAndCode {

    public static final ICMPv4TimestampReply TIMESTAMP_REPLY =
            new ICMPv4TimestampReply((byte) 0, "Timestamp reply");

    protected ICMPv4TimestampReply(Byte code, String name) {
        super((byte) 14, code, name);
    }

    public static ICMPv4TimestampReply register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 14, code);
        ICMPv4TimestampReply timestampReply =
                new ICMPv4TimestampReply(key.getSecondKey(), name);
        return (ICMPv4TimestampReply) ICMPTypeAndCode.registry.put(key, timestampReply);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(TIMESTAMP_REPLY.getKey(), TIMESTAMP_REPLY);
    }

}
