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
public class ICMPv6MobilePrefixSolicitation extends ICMPTypeAndCode {

    public static final ICMPv6MobilePrefixSolicitation MOBILE_PREFIX_SOLICITATION =
            new ICMPv6MobilePrefixSolicitation((byte) 0, "Mobile Prefix Solicitation");

    protected ICMPv6MobilePrefixSolicitation(Byte code, String name) {
        super((byte) 146, code, name);
    }

    public static ICMPv6MobilePrefixSolicitation register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 146, code);
        ICMPv6MobilePrefixSolicitation mobilePrefixSolicitation =
                new ICMPv6MobilePrefixSolicitation(key.getSecondKey(), name);
        return (ICMPv6MobilePrefixSolicitation) ICMPTypeAndCode.registry.put(key, mobilePrefixSolicitation);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
