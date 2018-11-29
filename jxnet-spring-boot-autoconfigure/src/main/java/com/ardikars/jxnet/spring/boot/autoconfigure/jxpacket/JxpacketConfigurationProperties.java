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

import com.ardikars.jxpacket.common.Packet;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jxnet 2018/11/29
 * @author <a href="mailto:contact@ardikars.com">Langkuy</a>
 */
@ConditionalOnClass(Packet.class)
@ConfigurationProperties(prefix = "jxnet.jxpacket")
public class JxpacketConfigurationProperties {

    private Boolean autoRegister;

    private Integer numberOfThread;

    /**
     * Initialize properties.
     */
    @PostConstruct
    public void initialize() {
        if (autoRegister == null) {
            this.autoRegister = false;
        }
        if (numberOfThread == null) {
            numberOfThread = 0;
        }
    }

    public Boolean getAutoRegister() {
        return autoRegister;
    }

    public void setAutoRegister(Boolean autoRegister) {
        this.autoRegister = autoRegister;
    }

    public Integer getNumberOfThread() {
        return numberOfThread;
    }

    public void setNumberOfThread(Integer numberOfThread) {
        this.numberOfThread = numberOfThread;
    }

}
