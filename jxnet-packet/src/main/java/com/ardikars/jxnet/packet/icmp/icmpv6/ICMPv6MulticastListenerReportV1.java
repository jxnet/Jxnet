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
public class ICMPv6MulticastListenerReportV1 extends ICMPTypeAndCode {

    public static final ICMPv6MulticastListenerReportV1 MULTICAST_LISTENER_REPORT =
            new ICMPv6MulticastListenerReportV1((byte) 0, "Multicast listener report");

    protected ICMPv6MulticastListenerReportV1(Byte code, String name) {
        super((byte) 131, code, name);
    }

    public static ICMPv6MulticastListenerReportV1 register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 131, code);
        ICMPv6MulticastListenerReportV1 multicastListenerReport =
                new ICMPv6MulticastListenerReportV1(key.getSecondKey(), name);
        return (ICMPv6MulticastListenerReportV1) ICMPTypeAndCode.registry.put(key, multicastListenerReport);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
