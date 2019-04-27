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
import com.ardikars.common.memory.Memories;
import com.ardikars.common.memory.Memory;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.context.Context;
import com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName;
import com.ardikars.jxnet.spring.boot.autoconfigure.memory.MemoryConfigurationProperties;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
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

    @Autowired
    private MemoryConfigurationProperties memoryProperties;

    /**
     * Decode buffer.
     * @param bytes direct byte buffer.
     * @return returns {@link Packet}.
     */
    public Packet decode(ByteBuffer bytes) {
        return processPacket(Memories.wrap(bytes, memoryProperties.getCheckBounds()));
    }

    /**
     * Decode buffer.
     * @param address memory address.
     * @param length length.
     * @return returns {@link Packet}.
     */
    public Packet decodeRawBuffer(long address, int length) {
        return processPacket(Memories.wrap(address, length, memoryProperties.getCheckBounds()));
    }

    private Packet processPacket(Memory buf) {
        Packet packet;
        buf.writerIndex(buf.capacity());
        if (dataLinkType.getValue() == 1) {
            packet = Ethernet.newPacket(buf);
        } else {
            packet = UnknownPacket.newPacket(buf);
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
