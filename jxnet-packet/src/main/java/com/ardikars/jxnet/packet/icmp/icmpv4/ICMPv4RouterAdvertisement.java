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
public class ICMPv4RouterAdvertisement extends ICMPTypeAndCode {

    public static final ICMPv4RouterAdvertisement ROUTER_ADVERTISEMENT =
            new ICMPv4RouterAdvertisement((byte) 0, "Router Advertisement");

    protected ICMPv4RouterAdvertisement(Byte code, String name) {
        super((byte) 9, code, name);
    }

    public static ICMPv4RouterAdvertisement register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 9, code);
        ICMPv4RouterAdvertisement routerAdvertisement =
                new ICMPv4RouterAdvertisement(key.getSecondKey(), name);
        return (ICMPv4RouterAdvertisement) ICMPTypeAndCode.registry.put(key, routerAdvertisement);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
