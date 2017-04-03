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

package com.ardikars.jxnet.packet.icmp;

import com.ardikars.jxnet.util.NamedTwoKeyMap;
import com.ardikars.jxnet.util.TwoKeyMap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public abstract class ICMPTypeAndCode extends NamedTwoKeyMap<Byte, Byte, ICMPTypeAndCode> {

    public static Map<TwoKeyMap<Byte, Byte>, ICMPTypeAndCode> registry =
            new HashMap<TwoKeyMap<Byte, Byte>, ICMPTypeAndCode>();

    public ICMPTypeAndCode(Byte firstKey, Byte secondKey, String name) {
        super(firstKey, secondKey, name);
    }

    public static ICMPTypeAndCode getInstance(Byte type, Byte code) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance(type, code);
        return registry.get(key);
    }

    public byte getType() {
        return super.getKey().getFirstKey();
    }
    public byte getCode() {
        return super.getKey().getSecondKey();
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Type: " + this.getType())
                .append(", Code: " + this.getCode())
                .append("]").toString();
    }

    static {
        try {
            Class.forName("com.ardikars.jxnet.packet.icmp.ICMPv4DestinationUnreachable");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
