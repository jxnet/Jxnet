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

package com.ardikars.jxnet.spring.boot.autoconfigure;

import com.ardikars.common.annotation.Incubating;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.context.Context;
import com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @param <T> type.
 * @param <V> packet type.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
@Incubating
public class HandlerConfigurer<T, V> {

    @Autowired
    @Qualifier(JxnetObjectName.EXECUTOR_SERVICE_BEAN_NAME)
    protected ExecutorService executorService;

    @Autowired
    @Qualifier(JxnetObjectName.CONTEXT_BEAN_NAME)
    protected Context context;

    @Autowired
    @Qualifier(JxnetObjectName.DATALINK_TYPE_BEAN_NAME)
    protected DataLinkType dataLinkType;

    @Autowired
    private Handler<T, V> handler;

    /**
     * Decode buffer.
     * @param bytes direct byte buffer.
     * @return returns {@link Packet}.
     */
    public Packet decode(ByteBuffer bytes) {
        ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
        Packet packet;
        if (dataLinkType.getValue() == 1) {
            packet = Ethernet.newPacket(buffer);
        } else {
            packet = UnknownPacket.newPacket(buffer);
        }
        return packet;
    }

    /**
     * Get handler.
     * @return returns {@link Handler} implementation.
     */
    public Handler<T, V> getHandler() {
        return handler;
    }

}
