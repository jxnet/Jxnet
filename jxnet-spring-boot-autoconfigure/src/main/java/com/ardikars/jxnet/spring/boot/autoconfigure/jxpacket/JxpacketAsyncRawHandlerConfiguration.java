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

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXPACKET_ASYNC_RAW_HANDLER_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.memory.Memory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.RawPcapHandler;
import com.ardikars.jxnet.spring.boot.autoconfigure.HandlerConfigurer;
import com.ardikars.jxpacket.common.Packet;

import java.util.concurrent.ExecutionException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * Jxpacket handler.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@ConditionalOnClass({Packet.class, Memory.class})
@Configuration(JXPACKET_ASYNC_RAW_HANDLER_CONFIGURATION_BEAN_NAME)
public class JxpacketAsyncRawHandlerConfiguration<T> extends HandlerConfigurer<T, Pair<PcapPktHdr, Packet>> implements RawPcapHandler<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketAsyncRawHandlerConfiguration.class);

    @Override
    public void nextPacket(final T user, final int capLen, final int len, final int tvSec, final long tvUsec, final long memoryAddress) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    getHandler().next(user, Tuple.of(PcapPktHdr.newInstance(capLen, len, tvSec, tvUsec), decodeRawBuffer(memoryAddress, len)));
                } catch (ExecutionException e) {
                    LOGGER.warn(e);
                } catch (InterruptedException e) {
                    LOGGER.warn(e);
                }
            }
        });
    }

}
