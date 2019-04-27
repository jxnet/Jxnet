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

package com.ardikars.jxnet.spring.boot.autoconfigure.memory;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.jxpacket.common.Packet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConditionalOnClass(Packet.class)
@ConfigurationProperties(prefix = "jxnet.memory")
public class MemoryConfigurationProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemoryConfigurationProperties.class);

    private Boolean checkBounds;

    /**
     * Initialize properties.
     */
    public MemoryConfigurationProperties() {
        if (checkBounds == null) {
            this.checkBounds = false;
        }
        LOGGER.debug("Memory checkBounds: {}", checkBounds);
    }

    public Boolean getCheckBounds() {
        return checkBounds;
    }

    public void setCheckBounds(Boolean checkBounds) {
        this.checkBounds = checkBounds;
    }

}
