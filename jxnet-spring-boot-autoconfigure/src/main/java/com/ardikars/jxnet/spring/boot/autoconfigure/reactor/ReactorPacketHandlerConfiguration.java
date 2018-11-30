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

package com.ardikars.jxnet.spring.boot.autoconfigure.reactor;

import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.ReactorPacketHandler;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * Reactor packet handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@ConditionalOnClass({Packet.class, ByteBuf.class, Mono.class})
@Configuration("com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.reactorPacketHandler")
public class ReactorPacketHandlerConfiguration<T> implements PcapHandler<T> {

    private static final Log LOG = LogFactory.getLog(ReactorPacketHandlerConfiguration.class.getName());

    private final int rawDataLinkType;
    private final ReactorPacketHandler<T> packetHandler;
    private final ExecutorService executorService;

    /**
     *
     * @param executorService thread pool.
     * @param dataLinkType datalink type.
     * @param packetHandler callback function.
     */
    public ReactorPacketHandlerConfiguration(@Qualifier("com.ardikars.jxnet.spring.boot.autoconfigure.executorService")
                                            ExecutorService executorService,
                                             DataLinkType dataLinkType,
                                             ReactorPacketHandler<T> packetHandler) {
        this.rawDataLinkType = dataLinkType != null ? dataLinkType.getValue() : 1;
        this.packetHandler = packetHandler;
        this.executorService = executorService;
    }

    @Override
    public void nextPacket(final T user, final PcapPktHdr h, final ByteBuffer bytes) {
        Future<Mono<Packet>> packet = executorService.submit(new Callable<Mono<Packet>>() {
            @Override
            public Mono<Packet> call() throws Exception {
                ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(bytes.capacity());
                buffer.setBytes(0, bytes);
                Packet packet;
                if (rawDataLinkType == 1) {
                    packet = Ethernet.newPacket(buffer);
                } else {
                    packet = UnknownPacket.newPacket(buffer);
                }
                return Mono.justOrEmpty(packet);
            }
        });
        try {
            packetHandler.next(user, h, packet.get());
        } catch (ExecutionException | InterruptedException e) {
            LOG.warn(e.getMessage());
        }
    }

}
