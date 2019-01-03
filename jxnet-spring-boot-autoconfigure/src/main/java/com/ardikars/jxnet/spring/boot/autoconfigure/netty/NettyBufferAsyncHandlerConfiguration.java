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

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.NETTY_BUFFER_ASYCN_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.HandlerConfigurer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Async netty buffer.
 *
 * @param <T> type.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.5.3
 */
@ConditionalOnClass({ByteBuf.class})
@Configuration(NETTY_BUFFER_ASYCN_HANDLER_CONFIGURATION_BEAN_NAME)
public class NettyBufferAsyncHandlerConfiguration<T> extends HandlerConfigurer<T, Future<Pair<PcapPktHdr, ByteBuf>>> implements PcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyBufferAsyncHandlerConfiguration.class);

    @Override
    public void nextPacket(final T user, final PcapPktHdr h, final ByteBuffer bytes) {
        Future<Pair<PcapPktHdr, ByteBuf>> packet = executorService.submit(new Callable<Pair<PcapPktHdr, ByteBuf>>() {
            @Override
            public Pair<PcapPktHdr, ByteBuf> call() throws Exception {
                ByteBuf buf = Unpooled.wrappedBuffer(bytes);
                return Tuple.of(h, buf);
            }
        });
        try {
            getHandler().next(user, packet);
        } catch (ExecutionException e) {
            LOGGER.warn(e);
        } catch (InterruptedException e) {
            LOGGER.warn(e);
        }
    }

}
