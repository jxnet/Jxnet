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

package com.ardikars.jxnet.spring.boot.autoconfigure.netty;

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.EXECUTOR_SERVICE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.NETTY_BUFFER_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.NettyBufferHandler;
import com.ardikars.jxpacket.common.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Netty buffer handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@ConditionalOnClass({Packet.class, ByteBuf.class})
@Configuration(NETTY_BUFFER_HANDLER_CONFIGURATION_BEAN_NAME)
public class NettyBufferHandlerConfiguration<T> implements PcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyBufferHandlerConfiguration.class);

    private final NettyBufferHandler<T> packetHandler;
    private final ExecutorService executorService;

    /**
     *
     * @param executorService thread pool.
     * @param packetHandler callback function.
     */
    public NettyBufferHandlerConfiguration(@Qualifier(EXECUTOR_SERVICE_BEAN_NAME) ExecutorService executorService,
                                           NettyBufferHandler<T> packetHandler) {
        this.packetHandler = packetHandler;
        this.executorService = executorService;
    }

    @Override
    public void nextPacket(final T user, final PcapPktHdr h, final ByteBuffer bytes) {
        Future<Pair<PcapPktHdr, ByteBuf>> packet = executorService.submit(new Callable<Pair<PcapPktHdr, ByteBuf>>() {
            @Override
            public Pair<PcapPktHdr, ByteBuf> call() throws Exception {
                ByteBuf buffer = ByteBufAllocator.DEFAULT.directBuffer(bytes.capacity());
                buffer.setBytes(0, bytes);
                return Tuple.of(h, buffer);
            }
        });
        try {
            packetHandler.next(user, packet);
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

}
