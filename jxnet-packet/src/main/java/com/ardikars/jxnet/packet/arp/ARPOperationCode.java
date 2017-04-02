/**
 * Copyright (C) 2017  Ardika Rommy Sanjaya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet.packet.arp;

import com.ardikars.jxnet.util.NamedNumber;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public final class ARPOperationCode extends NamedNumber<Short, ARPOperationCode> {

    public static final ARPOperationCode ARP_REQUEST = new ARPOperationCode((short) 0x01, "ARP Request");

    public static final ARPOperationCode ARP_REPLY = new ARPOperationCode((short) 0x02, "ARP Reply");

    public static final ARPOperationCode UNKNOWN = new ARPOperationCode((short) -1, "Unknown");

    public ARPOperationCode(Short value, String name) {
        super(value, name);
    }

    private static final Map<Short, ARPOperationCode> registry
            = new HashMap<Short, ARPOperationCode>();

    static {
        registry.put(ARP_REQUEST.getValue(), ARP_REQUEST);
        registry.put(ARP_REPLY.getValue(), ARP_REPLY);
    }

    public static ARPOperationCode register(ARPOperationCode operationCode) {
        return registry.put(operationCode.getValue(), operationCode);
    }

    public static ARPOperationCode getInstance(Short value) {
        if (registry.containsKey(value)) {
            return registry.get(value);
        } else {
            return UNKNOWN;
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
