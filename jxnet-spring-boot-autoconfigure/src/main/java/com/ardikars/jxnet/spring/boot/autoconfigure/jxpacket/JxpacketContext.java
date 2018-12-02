/**
 * Copyright (C) 2015-2018 Jxnet
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

package com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket;

import com.ardikars.common.tuple.Pair;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxpacket.common.Packet;

import java.util.concurrent.Future;

/**
 * Jxpacket context.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
public interface JxpacketContext {

    /**
     * Next packet.
     * @return returns {@link Future}.
     */
    Future<Pair<PcapPktHdr, Packet>> nextPacket();

    /**
     * Get pcap minor version.
     * @return returns pcap minor version.
     */
    int getPcapMinorVersion();

    /**
     * Get pcap major version.
     * @return returns pcap major version.
     */
    int getPcapMajorVersion();

}
