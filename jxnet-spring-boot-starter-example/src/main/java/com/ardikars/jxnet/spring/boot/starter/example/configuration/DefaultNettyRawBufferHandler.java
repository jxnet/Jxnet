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

package com.ardikars.jxnet.spring.boot.starter.example.configuration;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.NettyRawBufferHandler;
import io.netty.buffer.ByteBuf;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//@EnablePacket(packetHandlerType = PacketHandlerType.NETTY_RAW_BUFFER)
//@Configuration
public class DefaultNettyRawBufferHandler implements NettyRawBufferHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNettyRawBufferHandler.class);

    private static final String PRETTY_FOOTER = "+---------------------------------------------------"
            + "--------------------------------------------------+";

    private void print(Pair<PcapPktHdr, ByteBuf> packet) {
        LOGGER.info("Pcap packet header : {}", packet.getLeft());
        LOGGER.info("Pcap packet buffer : {}", packet.getRight());
        LOGGER.info(PRETTY_FOOTER);
    }

    @Override
    public void next(String argument, Future<Pair<PcapPktHdr, ByteBuf>> packet) throws ExecutionException, InterruptedException {
        LOGGER.info("User argument      : {}", argument);
        print(packet.get());
    }

}
