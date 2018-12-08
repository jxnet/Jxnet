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
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.CONTEXT_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.DATALINK_TYPE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.ERRBUF_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.EXECUTOR_SERVICE_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.JXNET_AUTO_CONFIGURATION_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.MAC_ADDRESS_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.NETMASK_BEAN_NAME;
import static com.ardikars.jxnet.spring.boot.autoconfigure.constant.JxnetObjectName.PCAP_IF_BEAN_NAME;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.util.Platforms;
import com.ardikars.jxnet.Application;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.Jxnet;
import com.ardikars.jxnet.Pcap;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapCode;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.exception.DeviceNotFoundException;
import com.ardikars.jxnet.exception.NativeException;
import com.ardikars.jxnet.exception.PlatformNotSupportedException;
import com.ardikars.jxnet.exception.UnknownNetmaskException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring autoconfiguration.
 *
 * @author <a href="mailto:contact@ardikars.com">Ardika Rommy Sanjaya</a>
 * @since 1.4.0
 */
@Configuration(JXNET_AUTO_CONFIGURATION_BEAN_NAME)
@ConditionalOnClass({Jxnet.class, Context.class})
@AutoConfigureOrder
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

    public JxnetAutoConfiguration(JxnetConfigurationProperties properties) {
        this.properties = properties;
    }

    /**
     * Pcap if (source).
     * @param errbuf error buffer.
     * @return pcap_if.
     * @throws DeviceNotFoundException device not found exception.
     */
    @ConditionalOnClass(value = {PcapIf.class, PcapAddr.class, SockAddr.class, DeviceNotFoundException.class})
    @Bean(PCAP_IF_BEAN_NAME)
    public PcapIf pcapIf(@Qualifier(ERRBUF_BEAN_NAME) StringBuilder errbuf) throws DeviceNotFoundException {
        List<PcapIf> alldevsp = new ArrayList<>();
        if (PcapFindAllDevs(alldevsp, errbuf) != OK) {
            throw new NativeException(errbuf.toString());
        }
        String source = properties.getSource();
        if (source == null || source.isEmpty()) {
            for (PcapIf dev : alldevsp) {
                for (PcapAddr addr : dev.getAddresses()) {
                    if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET && addr.getAddr().getData() != null) {
                        Inet4Address d = Inet4Address.valueOf(addr.getAddr().getData());
                        if (!d.equals(Inet4Address.LOCALHOST) && !d.equals(Inet4Address.ZERO)) {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("Device: {}.", dev);
                            }
                            return dev;
                        }
                    }
                }
            }
        } else {
            for (PcapIf dev : alldevsp) {
                if (dev.getName().equals(source)) {
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Device: {}.", dev);
                    }
                    return dev;
                }
            }
        }
        for (PcapIf dev : alldevsp) {
            if (dev.isLoopback()) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn("No device connected to the network. Auto selected {}.", dev.getName());
                }
                return dev;
            }
        }
        throw new DeviceNotFoundException();
    }

    /**
     * Default netmask.
     * @param pcapIf default {@link PcapIf} object.
     * @return returns default netmask specified by {@link PcapIf} object.
     */
    @ConditionalOnClass({Inet4Address.class, PcapIf.class})
    @ConditionalOnBean({PcapIf.class})
    @Bean(NETMASK_BEAN_NAME)
    public Inet4Address netmask(@Qualifier(PCAP_IF_BEAN_NAME) PcapIf pcapIf) {
        Iterator<PcapAddr> iterator = pcapIf.getAddresses().iterator();
        while (iterator.hasNext()) {
            PcapAddr pcapAddr = iterator.next();
            if (pcapAddr.getNetmask() != null
                    && pcapAddr.getNetmask().getSaFamily() == SockAddr.Family.AF_INET
                    && pcapAddr.getNetmask().getData() != null) {
                Inet4Address netmask = Inet4Address.valueOf(pcapAddr.getNetmask().getData());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Default netmask: {}.", netmask);
                }
                return netmask;
            }
        }
        throw new UnknownNetmaskException();
    }

    /**
     * Default mac address specified by pcapIf.
     * @param pcapIf pcapIf.
     * @return returns mac address.
     * @throws PlatformNotSupportedException platform not supported exception.
     * @throws DeviceNotFoundException device not found exception.
     * @throws SocketException socket exception.
     */
    @ConditionalOnClass({MacAddress.class, PcapIf.class, PcapAddr.class, SockAddr.class,
            DeviceNotFoundException.class, PlatformNotSupportedException.class})
    @Bean(MAC_ADDRESS_BEAN_NAME)
    public MacAddress macAddress(@Qualifier(PCAP_IF_BEAN_NAME) PcapIf pcapIf)
            throws PlatformNotSupportedException, DeviceNotFoundException, SocketException {
        MacAddress macAddress;
        if (pcapIf.isLoopback()) {
            macAddress = MacAddress.ZERO;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("No MAC address for loopback interface, use default address: {}.", macAddress);
            }
            return macAddress;
        }
        if (Platforms.isWindows()) {
            byte[] hardwareAddress = Jxnet.FindHardwareAddress(pcapIf.getName());
            if (hardwareAddress != null && hardwareAddress.length == MacAddress.MAC_ADDRESS_LENGTH) {
                macAddress = MacAddress.valueOf(hardwareAddress);
            } else {
                throw new DeviceNotFoundException();
            }
        } else {
            macAddress = MacAddress.fromNicName(pcapIf.getName());
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Mac address: {}.", macAddress);
        }
        return macAddress;
    }

    /**
     * A handle link type.
     * @param context application context.
     * @return returns {@link com.ardikars.jxpacket.common.layer.DataLinkLayer}.
     */
    @Bean(DATALINK_TYPE_BEAN_NAME)
    public DataLinkType dataLinkType(@Qualifier(CONTEXT_BEAN_NAME) Context context) {
        DataLinkType dataLinkType = context.pcapDataLink();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Datalink type: {}.", dataLinkType);
        }
        return dataLinkType;
    }

    /**
     * Error buffer.
     * @return error buffer.
     */
    @Bean(ERRBUF_BEAN_NAME)
    public StringBuilder errbuf() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Create error buffer with size: {}.", PCAP_ERRBUF_SIZE);
        }
        return new StringBuilder(PCAP_ERRBUF_SIZE);
    }

    /**
     * Thread pool.
     * @return returns {@link ExecutorService} object.
     */
    @Bean(EXECUTOR_SERVICE_BEAN_NAME)
    public ExecutorService executorService() {
        if (this.properties.getNumberOfThread() == 0) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Use cached thread pool.");
            }
            return Executors.newCachedThreadPool();
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Use {} fixed thread pool.", this.properties.getNumberOfThread());
        }
        return Executors.newFixedThreadPool(this.properties.getNumberOfThread());
    }

    /**
     * Jxnet application context.
     * @param pcapIf pcap if.
     * @param errbuf error buffer.
     * @param netmask netmask.
     * @return returns application context.
     */
    @ConditionalOnClass({Pcap.class})
    @ConditionalOnBean({PcapIf.class, Inet4Address.class})
    @Bean(CONTEXT_BEAN_NAME)
    public Context context(@Qualifier(PCAP_IF_BEAN_NAME) PcapIf pcapIf,
                           @Qualifier(NETMASK_BEAN_NAME) Inet4Address netmask,
                           @Qualifier(ERRBUF_BEAN_NAME) StringBuilder errbuf) {
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
        Context context =  Application.getApplicationContext();
        if (properties.getFilter() != null) {
            if (context.pcapCompile(properties.getFilter(),
                    properties.getBpfCompileMode(),
                    netmask.toInt()) == PcapCode.PCAP_OK) {
                if (context.pcapSetFilter() != PcapCode.PCAP_OK) {
                    if (LOGGER.isWarnEnabled()) {
                        LOGGER.warn(context.pcapGetErr());
                    }
                } else {
                    LOGGER.debug("Filter \'{}\' has been applied.", this.properties.getFilter());
                }
            } else {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(context.pcapGetErr());
                }
            }
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("No filter has been applied.");
            }
        }
        return context;
    }

}
