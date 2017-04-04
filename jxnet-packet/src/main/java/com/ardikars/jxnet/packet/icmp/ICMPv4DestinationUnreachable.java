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

import com.ardikars.jxnet.util.TwoKeyMap;

/**
 * @author Ardika Rommy Sanjaya
 * @since 1.1.0
 */
public class ICMPv4DestinationUnreachable extends ICMPTypeAndCode {

    public static ICMPv4DestinationUnreachable DESTINATION_NETWORK_UNREACHABLE =
            new ICMPv4DestinationUnreachable((byte) 0, "Destination network unreachable");

    public static ICMPv4DestinationUnreachable DESTINATION_HOST_UNREACHABLE =
            new ICMPv4DestinationUnreachable((byte) 1, "Destination host unreachable");

    public static ICMPv4DestinationUnreachable DESTINATION_PROTOCOL_UNREACHABLE =
            new ICMPv4DestinationUnreachable((byte) 2, "Destination protocol unreachable");

    public static ICMPv4DestinationUnreachable DESTINATION_PORT_UNREACHABLE =
            new ICMPv4DestinationUnreachable((byte) 3, "Destination port unreachable");

    public static ICMPv4DestinationUnreachable FRAGMENTATION_REQUIRED =
            new ICMPv4DestinationUnreachable((byte) 4, "Fragmentation required, and DF flag set");

    public static ICMPv4DestinationUnreachable SOURCE_ROUTE_FAILED =
            new ICMPv4DestinationUnreachable((byte) 5, "Source route failed");

    public static ICMPv4DestinationUnreachable DESTINATION_NETWORK_UNKNOWN =
            new ICMPv4DestinationUnreachable((byte) 6, "Destination network unknown");

    public static ICMPv4DestinationUnreachable DESTINATION_HOST_UNKOWN =
            new ICMPv4DestinationUnreachable((byte) 7, "Destinatin host unknown");

    public static ICMPv4DestinationUnreachable SOURCE_HOST_ISOLATED =
            new ICMPv4DestinationUnreachable((byte) 8, "Destination host isolated");

    public static ICMPv4DestinationUnreachable NETWORK_ADMINISTRATIVELY_PROHIBITED =
            new ICMPv4DestinationUnreachable((byte) 9, "Network administratively prohibited");

    public static ICMPv4DestinationUnreachable HOST_ADMINISTRATIVELY_PROHIBITED =
            new ICMPv4DestinationUnreachable((byte) 10, "Host administratively prohibited");

    public static ICMPv4DestinationUnreachable NETWORK_UNREACHABLE_FOR_TOS =
            new ICMPv4DestinationUnreachable((byte) 11, "Network unreachable for ToS");

    public static ICMPv4DestinationUnreachable HOST_UNREACHABLE_FOR_TOS =
            new ICMPv4DestinationUnreachable((byte) 12, "Host unreachable for ToS");

    public static ICMPv4DestinationUnreachable COMMUNICATION_ADMINISTRATIVELY_PROHIBITED =
            new ICMPv4DestinationUnreachable((byte) 13, "Communication administratively prohibited");

    public static ICMPv4DestinationUnreachable HOST_PRECEDENCE_VIOLATION =
            new ICMPv4DestinationUnreachable((byte) 14, "Host Precedence Violation");

    public static ICMPv4DestinationUnreachable PRECEDENCE_CUTOFF_IN_EFFECT =
            new ICMPv4DestinationUnreachable((byte) 15, "Precedence cutoff in effect");

    protected ICMPv4DestinationUnreachable(Byte code, String name) {
        super((byte) 3, code, name);
    }

    public static ICMPv4DestinationUnreachable register(Byte code, String name) {
        TwoKeyMap<Byte, Byte> key = TwoKeyMap.newInstance((byte) 3, code);
        ICMPv4DestinationUnreachable destinationUnreachable =
                new ICMPv4DestinationUnreachable(key.getSecondKey(), name);
        return (ICMPv4DestinationUnreachable) ICMPTypeAndCode.registry.put(key, destinationUnreachable);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    static {
        ICMPTypeAndCode.registry.put(DESTINATION_NETWORK_UNREACHABLE.getKey(), DESTINATION_HOST_UNREACHABLE);
        ICMPTypeAndCode.registry.put(DESTINATION_HOST_UNREACHABLE.getKey(), DESTINATION_HOST_UNREACHABLE);
        ICMPTypeAndCode.registry.put(DESTINATION_PROTOCOL_UNREACHABLE.getKey(), DESTINATION_PROTOCOL_UNREACHABLE);
        ICMPTypeAndCode.registry.put(DESTINATION_PORT_UNREACHABLE.getKey(), DESTINATION_PORT_UNREACHABLE);
        ICMPTypeAndCode.registry.put(FRAGMENTATION_REQUIRED.getKey(), FRAGMENTATION_REQUIRED);
        ICMPTypeAndCode.registry.put(SOURCE_ROUTE_FAILED.getKey(), SOURCE_ROUTE_FAILED);
        ICMPTypeAndCode.registry.put(DESTINATION_NETWORK_UNKNOWN.getKey(), DESTINATION_NETWORK_UNKNOWN);
        ICMPTypeAndCode.registry.put(DESTINATION_HOST_UNKOWN.getKey(), DESTINATION_HOST_UNKOWN);
        ICMPTypeAndCode.registry.put(SOURCE_HOST_ISOLATED.getKey(), SOURCE_HOST_ISOLATED);
        ICMPTypeAndCode.registry.put(NETWORK_ADMINISTRATIVELY_PROHIBITED.getKey(), NETWORK_ADMINISTRATIVELY_PROHIBITED);
        ICMPTypeAndCode.registry.put(HOST_ADMINISTRATIVELY_PROHIBITED.getKey(), HOST_ADMINISTRATIVELY_PROHIBITED);
        ICMPTypeAndCode.registry.put(NETWORK_UNREACHABLE_FOR_TOS.getKey(), NETWORK_UNREACHABLE_FOR_TOS);
        ICMPTypeAndCode.registry.put(HOST_UNREACHABLE_FOR_TOS.getKey(), HOST_UNREACHABLE_FOR_TOS);
        ICMPTypeAndCode.registry.put(COMMUNICATION_ADMINISTRATIVELY_PROHIBITED.getKey(), COMMUNICATION_ADMINISTRATIVELY_PROHIBITED);
        ICMPTypeAndCode.registry.put(HOST_PRECEDENCE_VIOLATION.getKey(), HOST_PRECEDENCE_VIOLATION);
        ICMPTypeAndCode.registry.put(PRECEDENCE_CUTOFF_IN_EFFECT.getKey(), PRECEDENCE_CUTOFF_IN_EFFECT);
    }

}
