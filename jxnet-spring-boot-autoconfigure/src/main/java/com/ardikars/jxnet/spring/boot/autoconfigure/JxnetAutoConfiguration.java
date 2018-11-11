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

import static com.ardikars.jxnet.Jxnet.OK;
import static com.ardikars.jxnet.Jxnet.PCAP_ERRBUF_SIZE;
import static com.ardikars.jxnet.Jxnet.PcapFindAllDevs;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.util.Platforms;
import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Spring autoconfiguration.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.0
 */
@Configuration("com.ardikars.jxnet.autoConfiguration")
@ConditionalOnClass({Jxnet.class, Context.class})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties(JxnetConfigurationProperties.class)
public class JxnetAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(JxnetAutoConfiguration.class.getName());

    @Value("${spring.application.name:}")
    private String applicationName;

    @Value("${spring.application.displayName:}")
    private String applicationDisplayName;

    @Value("${spring.application.version:0.0.0}")
    private String applicationVersion;

    private final JxnetConfigurationProperties properties;

    @Autowired
    public JxnetAutoConfiguration(JxnetConfigurationProperties properties) {
        this.properties = properties;
    }

    /**
     * Jxnet application context.
     * @param pcapIf pcap if.
     * @param errbuf error buffer.
     * @return returns application context.
     */
    @Bean("com.ardikars.jxnet.contex")
    public Context context(PcapIf pcapIf, StringBuilder errbuf) {
        String source = pcapIf.getName();
        Pcap.Builder builder = new Pcap.Builder()
                .source(source)
                .snaplen(properties.getSnapshot())
                .promiscuousMode(properties.getPromiscuous())
                .timeout(properties.getTimeout())
                .immediateMode(properties.getImmediate())
                .timestampType(properties.getTimestampType())
                .direction(properties.getDirection())
                .timestampPrecision(properties.getTimestampPrecision())
                .rfmon(properties.getRfmon())
                .enableNonBlock(!properties.getBlocking())
                .dataLinkType(properties.getDatalink())
                .fileName(properties.getFile())
                .errbuf(errbuf);
        switch (properties.getPcapType()) {
            case DEAD:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Opening pcap dead handler : {}", builder);
                }
                builder.pcapType(Pcap.PcapType.DEAD);
                break;
            case OFFLINE:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Opening pcap offline hadler : {}", builder);
                }
                builder.pcapType(Pcap.PcapType.OFFLINE);
                break;
            default:
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Opening pcap live hadler : {}", builder);
                }
                builder.pcapType(Pcap.PcapType.LIVE);
                break;
        }
        Application.run(applicationName, applicationDisplayName, applicationVersion, builder);
        return Application.getApplicationContext();
    }

    /**
     * Pcap if (source).
     * @param errbuf error buffer.
     * @return pcap_if.
     * @throws DeviceNotFoundException device not found exception.
     */
    @Bean("com.ardikars.jxnet.pcapIf")
    public PcapIf pcapIf(StringBuilder errbuf) throws DeviceNotFoundException {
        String source = properties.getSource();
        List<PcapIf> alldevsp = new ArrayList<>();
        if (PcapFindAllDevs(alldevsp, errbuf) != OK && LOGGER.isDebugEnabled()) {
            LOGGER.debug("Error: {}", errbuf.toString());
        }
        if (source == null || source.isEmpty()) {
            for (PcapIf dev : alldevsp) {
                for (PcapAddr addr : dev.getAddresses()) {
                    if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET && addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            return dev;
                        }
                    }
                }
            }
        } else {
            for (PcapIf dev : alldevsp) {
                if (dev.getName().equals(source)) {
                    return dev;
                }
            }
        }
        throw new DeviceNotFoundException("No device connected to the network.");
    }

    /**
     * Default mac address specified by pcapIf.
     * @param pcapIf pcapIf.
     * @return returns mac address.
     * @throws PlatformNotSupportedException platform not supported exception.
     * @throws DeviceNotFoundException device not found exception.
     * @throws SocketException socket exception.
     */
    @Bean("com.ardikars.jxnet.macAddress")
    public MacAddress macAddress(PcapIf pcapIf) throws PlatformNotSupportedException, DeviceNotFoundException, SocketException {
        if (pcapIf.isLoopback()) {
            return MacAddress.ZERO;
        }
        if (Platforms.isWindows()) {
            byte[] hardwareAddress = Jxnet.FindHardwareAddress(pcapIf.getName());
            if (hardwareAddress != null && hardwareAddress.length == MacAddress.MAC_ADDRESS_LENGTH) {
                return MacAddress.valueOf(hardwareAddress);
            } else {
                throw new DeviceNotFoundException();
            }
        } else {
            return MacAddress.fromNicName(pcapIf.getName());
        }
    }

    /**
     * Error buffer.
     * @return error buffer.
     */
    @Bean("com.ardikars.jxnet.errbuf")
    public StringBuilder errbuf() {
        return new StringBuilder(PCAP_ERRBUF_SIZE);
    }

}
