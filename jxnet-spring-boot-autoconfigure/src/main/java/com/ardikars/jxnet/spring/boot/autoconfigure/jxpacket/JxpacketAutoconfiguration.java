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

import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.CONTEXT_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.DATALINK_TYPE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.EXECUTOR_SERVICE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXPACKET_AUTO_CONFIGURATION_BEAN_NAME;

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.memory.Memories;
import com.ardikars.common.memory.Memory;
import com.ardikars.common.tuple.Pair;
import com.ardikars.common.tuple.Tuple;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.context.Context;
import com.ardikars.jxnet.spring.boot.autoconfigure.memory.MemoryConfigurationProperties;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.UnknownPacket;
import com.ardikars.jxpacket.common.layer.DataLinkLayer;
import com.ardikars.jxpacket.common.layer.NetworkLayer;
import com.ardikars.jxpacket.common.layer.TransportLayer;
import com.ardikars.jxpacket.core.arp.Arp;
import com.ardikars.jxpacket.core.ethernet.Ethernet;
import com.ardikars.jxpacket.core.ethernet.Vlan;
import com.ardikars.jxpacket.core.icmp.Icmp4;
import com.ardikars.jxpacket.core.icmp.Icmp6;
import com.ardikars.jxpacket.core.ip.Ip4;
import com.ardikars.jxpacket.core.ip.Ip6;
import com.ardikars.jxpacket.core.ip.ip6.Authentication;
import com.ardikars.jxpacket.core.ip.ip6.DestinationOptions;
import com.ardikars.jxpacket.core.ip.ip6.Fragment;
import com.ardikars.jxpacket.core.ip.ip6.HopByHopOptions;
import com.ardikars.jxpacket.core.ip.ip6.Routing;
import com.ardikars.jxpacket.core.tcp.Tcp;
import com.ardikars.jxpacket.core.udp.Udp;
import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Jxpacket auto configuration for spring boot application.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.8
 */
@Configuration(JXPACKET_AUTO_CONFIGURATION_BEAN_NAME)
@ConditionalOnClass(Packet.class)
@AutoConfigureOrder
@EnableConfigurationProperties(JxpacketConfigurationProperties.class)
public class JxpacketAutoconfiguration implements JxpacketContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxpacketAutoconfiguration.class);

    private final Boolean autoRegister;
    private final Boolean checkBounds;

    private final Context context;
    private final ExecutorService executorService;
    private final int rawDataLinkType;
    private final int pcapMinorVersion;
    private final int pcapMajorVersion;

    /**
     * Jxpacket autoconfiguraton constractor.
     *
     * @param context application context.
     * @param executorService thread pool.
     * @param dataLinkType datalink type.
     * @param jxpacketProperties jxpacket configuration properties.
     * @param memoryProperties memory configuration properties.
     */
    public JxpacketAutoconfiguration(@Qualifier(CONTEXT_BEAN_NAME) Context context,
                                     @Qualifier(EXECUTOR_SERVICE_BEAN_NAME) ExecutorService executorService,
                                     @Qualifier(DATALINK_TYPE_BEAN_NAME) DataLinkType dataLinkType,
                                     JxpacketConfigurationProperties jxpacketProperties,
                                     MemoryConfigurationProperties memoryProperties) {
        this.autoRegister = jxpacketProperties.getAutoRegister();
        this.checkBounds = memoryProperties.getCheckBounds();
        register();
        this.context = context;
        this.executorService = executorService;
        this.rawDataLinkType = dataLinkType != null ? dataLinkType.getValue() : 1;
        this.pcapMinorVersion = this.context.pcapMinorVersion();
        this.pcapMajorVersion = this.context.pcapMajorVersion();
    }

    private void register() {
        if (this.autoRegister) {
            LOGGER.debug("Auto registering default packet.");
            DataLinkLayer.register(DataLinkLayer.EN10MB, new Ethernet.Builder());
            NetworkLayer.register(NetworkLayer.ARP, new Arp.Builder());
            NetworkLayer.register(NetworkLayer.IPV4, new Ip4.Builder());
            NetworkLayer.register(NetworkLayer.IPV6, new Ip6.Builder());
            NetworkLayer.register(NetworkLayer.DOT1Q_VLAN_TAGGED_FRAMES, new Vlan.Builder());
            NetworkLayer.register(NetworkLayer.IEEE_802_1_AD, new Vlan.Builder());
            TransportLayer.register(TransportLayer.TCP, new Tcp.Builder());
            TransportLayer.register(TransportLayer.UDP, new Udp.Builder());
            TransportLayer.register(TransportLayer.ICMP, new Icmp4.Builder());
            TransportLayer.register(TransportLayer.IPV6, new Ip6.Builder());
            TransportLayer.register(TransportLayer.IPV6_ICMP, new Icmp6.Builder());
            TransportLayer.register(TransportLayer.IPV6_AH, new Authentication.Builder());
            TransportLayer.register(TransportLayer.IPV6_DSTOPT, new DestinationOptions.Builder());
            TransportLayer.register(TransportLayer.IPV6_ROUTING, new Routing.Builder());
            TransportLayer.register(TransportLayer.IPV6_FRAGMENT, new Fragment.Builder());
            TransportLayer.register(TransportLayer.IPV6_HOPOPT, new HopByHopOptions.Builder());
        } else {
            LOGGER.warn("No packet has been registered.");
        }
    }

    @Override
    public Future<Pair<PcapPktHdr, Packet>> nextPacket() {
        return executorService.submit(new Callable<Pair<PcapPktHdr, Packet>>() {
            @Override
            public Pair<PcapPktHdr, Packet> call() throws Exception {
                PcapPktHdr pktHdr = new PcapPktHdr();
                ByteBuffer bytes = null;
                while (bytes == null) {
                    bytes = context.pcapNext(pktHdr);
                }
                Memory buffer = Memories.wrap(bytes, checkBounds);
                Packet packet;
                if (rawDataLinkType == 1) {
                    packet = Ethernet.newPacket(buffer);
                } else {
                    packet = UnknownPacket.newPacket(buffer);
                }
                return Tuple.of(pktHdr, packet);
            }
        });
    }

    @Override
    public int getPcapMinorVersion() {
        return pcapMinorVersion;
    }

    @Override
    public int getPcapMajorVersion() {
        return pcapMajorVersion;
    }

}
