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

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXPACKET_ASYNC_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.spring.boot.autoconfigure.HandlerConfigurer;
import com.ardikars.jxpacket.common.Packet;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutionException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@ConditionalOnClass({Packet.class, ByteBuf.class})
@Configuration(JXPACKET_ASYNC_HANDLER_CONFIGURATION_BEAN_NAME)
public class JxpacketAsyncHandlerConfiguration<T> extends HandlerConfigurer<T, Pair<PcapPktHdr, Packet>> implements PcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketHandlerConfiguration.class);

    @Override
    public void nextPacket(final T user, final PcapPktHdr h, final ByteBuffer bytes) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    getHandler().next(user, Tuple.of(h, decode(bytes)));
                } catch (ExecutionException e) {
                    LOGGER.warn(e);
                } catch (InterruptedException e) {
                    LOGGER.warn(e);
                }
            }
        });
    }


}
