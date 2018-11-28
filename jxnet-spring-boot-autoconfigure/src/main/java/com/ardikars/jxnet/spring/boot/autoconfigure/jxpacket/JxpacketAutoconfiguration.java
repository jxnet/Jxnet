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

import com.ardikars.jxnet.spring.boot.autoconfigure.JxnetConfigurationProperties;
import com.ardikars.jxpacket.common.Packet;
import com.ardikars.jxpacket.common.layer.DataLinkLayer;
import com.ardikars.jxpacket.common.layer.NetworkLayer;
import com.ardikars.jxpacket.common.layer.TransportLayer;
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
@Configuration("com.ardikras.jxnet.spring.boot.autoconfiguration.jxpacket.jxpacketAutoconfiguration")
@ConditionalOnClass(Packet.class)
@AutoConfigureOrder
@EnableConfigurationProperties(JxnetConfigurationProperties.class)
public class JxpacketAutoconfiguration {

    private final Boolean autoRegister;

    public JxpacketAutoconfiguration(JxnetConfigurationProperties properties) {
        this.autoRegister = properties.getJxpacketAutoRegister();
        register();
    }

    private void register() {
        if (this.autoRegister) {
            DataLinkLayer.register(DataLinkLayer.EN10MB, new Ethernet.Builder());
            NetworkLayer.register(NetworkLayer.IPV4, new Ip4.Builder());
            NetworkLayer.register(NetworkLayer.IPV6, new Ip6.Builder());
            NetworkLayer.register(NetworkLayer.DOT1Q_VLAN_TAGGED_FRAMES, new Vlan.Builder());
            NetworkLayer.register(NetworkLayer.IEEE_802_1_AD, new Vlan.Builder());
            TransportLayer.register(TransportLayer.TCP, new Tcp.Builder());
            TransportLayer.register(TransportLayer.UDP, new Udp.Builder());
            TransportLayer.register(TransportLayer.ICMP, new Icmp4.Builder());
            TransportLayer.register(TransportLayer.IPV6_ICMP, new Icmp6.Builder());
            TransportLayer.register(TransportLayer.IPV6_AH, new Authentication.Builder());
            TransportLayer.register(TransportLayer.IPV6_DSTOPT, new DestinationOptions.Builder());
            TransportLayer.register(TransportLayer.IPV6_ROUTING, new Routing.Builder());
            TransportLayer.register(TransportLayer.IPV6_FRAGMENT, new Fragment.Builder());
            TransportLayer.register(TransportLayer.IPV6_HOPOPT, new HopByHopOptions.Builder());
        }
    }

}
