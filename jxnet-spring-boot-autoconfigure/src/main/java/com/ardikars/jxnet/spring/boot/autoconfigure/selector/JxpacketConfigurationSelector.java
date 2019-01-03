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

package com.ardikars.jxnet.spring.boot.autoconfigure.selector;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.jxnet.spring.boot.autoconfigure.annotation.EnablePacket;
import com.ardikars.jxnet.spring.boot.autoconfigure.constant.PacketHandlerType;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Configuration selector.
 * @see EnablePacket
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 */
public class JxpacketConfigurationSelector implements ImportSelector {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketConfigurationSelector.class);

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(importingClassMetadata
                        .getAnnotationAttributes(EnablePacket.class.getName(), false));
        if (attributes == null) {
            throw new IllegalStateException();
        }
        PacketHandlerType type = attributes.getEnum("packetHandlerType");
        switch (type) {
            case NETTY_BUFFER:
                LOGGER.debug("Applying netty buffer handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.netty.NettyBufferHandlerConfiguration"};
            case NETTY_BUFFER_ASYNC:
                LOGGER.debug("Applying netty buffer async handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.netty.NettyBufferAsyncHandlerConfiguration"};
            case NIO_BUFFER:
                LOGGER.debug("Applying nio buffer handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.nio.NioBufferHandlerConfiguration"};
            case NIO_BUFFER_ASYNC:
                LOGGER.debug("Applying nio buffer async handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.nio.NioBufferAsyncHandlerConfiguration"};
            case JXPACKET_ASYNC:
                LOGGER.debug("Applying jxpacket async handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.JxpacketAsyncHandlerConfiguration"};
            default:
                LOGGER.debug("Applying jxpacket handler configuration.");
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.JxpacketHandlerConfiguration"};
        }
    }

}
