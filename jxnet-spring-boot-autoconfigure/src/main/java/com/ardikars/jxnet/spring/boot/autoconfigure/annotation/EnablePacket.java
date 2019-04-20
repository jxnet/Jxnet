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

package com.ardikars.jxnet.spring.boot.autoconfigure.annotation;

import com.ardikars.jxnet.spring.boot.autoconfigure.constant.PacketHandlerType;
import com.ardikars.jxnet.spring.boot.autoconfigure.selector.JxpacketConfigurationSelector;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * Enable packet handler configuration.
 *
 * @see JxpacketConfigurationSelector
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(JxpacketConfigurationSelector.class)
public @interface EnablePacket {

    /**
     * Packet handler type.
     * @return a {@link PacketHandlerType} object.
     */
    PacketHandlerType packetHandlerType() default PacketHandlerType.JXPACKET_RAW;

}
