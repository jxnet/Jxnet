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
public class ICMPv4RouterSolicitation extends ICMPTypeAndCode {

    public static final ICMPv4RouterSolicitation ROUTER_DISCOVERY_SELECTION_SOLICITATION =
            new ICMPv4RouterSolicitation((byte) 0, "Router discovery/selection/solicitation");

    protected ICMPv4RouterSolicitation(Byte code, String name) {
        super((byte) 10, code, name);
    }

    public static ICMPv4RouterSolicitation register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 10, code);
        ICMPv4RouterSolicitation routerSolicitation =
                new ICMPv4RouterSolicitation(key.getSecondKey(), name);
        return (ICMPv4RouterSolicitation) ICMPTypeAndCode.registry.put(key, routerSolicitation);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(ROUTER_DISCOVERY_SELECTION_SOLICITATION.getKey(), ROUTER_DISCOVERY_SELECTION_SOLICITATION);
    }

}
