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

import com.ardikars.jxnet.spring.boot.autoconfigure.annotation.EnablePacket;
import com.ardikars.jxnet.spring.boot.autoconfigure.constant.PacketHandlerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jxnet 2018/11/30
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
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
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Applying netty buffer handler configuration.");
                }
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.netty.NettyBufferHandlerConfiguration"};
            case NIO_BUFFER:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Applying nio buffer handler configuration.");
                }
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.nio.NioBufferHandlerConfiguration"};
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Applying jxpacket handler configuration.");
                }
                return new String[] {"com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.JxpacketHandlerConfiguration"};
        }
    }

}
