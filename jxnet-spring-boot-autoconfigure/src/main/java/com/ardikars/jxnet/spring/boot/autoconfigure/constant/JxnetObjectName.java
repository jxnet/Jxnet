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

package com.ardikars.jxnet.spring.boot.autoconfigure.constant;

import com.ardikars.common.annotation.Incubating;

/**
 * Jxnet spring bean name.
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.5.0
 */
@Incubating
public final class JxnetObjectName {

    public static final String CONTEXT_BEAN_NAME = "com.ardikars.jxnet.contex";
    public static final String PCAP_IF_BEAN_NAME = "com.ardikars.jxnet.pcapIf";
    public static final String NETMASK_BEAN_NAME = "com.ardikars.jxnet.netmask";
    public static final String ERRBUF_BEAN_NAME = "com.ardikars.jxnet.errbuf";
    public static final String MAC_ADDRESS_BEAN_NAME = "com.ardikars.jxnet.macAddress";
    public static final String DATALINK_TYPE_BEAN_NAME = "com.ardikars.jxnet.dataLinkType";
    public static final String EXECUTOR_SERVICE_BEAN_NAME = "com.ardikars.jxnet.executorService";
    public static final String JXPACKET_AUTO_CONFIGURATION_BEAN_NAME = "com.ardikras.jxnet.jxpacketAutoconfiguration";
    public static final String JXNET_AUTO_CONFIGURATION_BEAN_NAME = "com.ardikras.jxnet.jxnetAutoConfiguration";
    public static final String JXPACKET_HANDLER_CONFIGURATION_BEAN_NAME = "com.ardikras.jxnet.jxpacketHandlerConfiguration";
    public static final String NETTY_BUFFER_HANDLER_CONFIGURATION_BEAN_NAME = "com.ardikras.jxnet.nettyBufferHandlerConfiguration";
    public static final String NIO_BUFFER_HANDLER_CONFIGURATION_BEAN_NAME = "com.ardikras.jxnet.nioBufferHandlerConfiguration";
    public static final String PCAP_BUILDER_BEAN_NAME = "com.ardikras.jxnet.pcapBuilder";

}
