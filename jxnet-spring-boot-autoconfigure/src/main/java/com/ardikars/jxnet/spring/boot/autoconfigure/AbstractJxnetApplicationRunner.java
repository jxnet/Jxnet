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

import com.ardikars.common.logging.Logger;
import com.ardikars.common.logging.LoggerFactory;
import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.common.util.management.Jvm;
import com.ardikars.common.util.management.OperatingSystem;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapCode;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.context.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

/**
 * Abstract jxnet spring boot runner.
 * @param <T> type.
 * @since 1.5.3
 */
@Order(Integer.MIN_VALUE)
public abstract class AbstractJxnetApplicationRunner<T> implements CommandLineRunner {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractJxnetApplicationRunner.class);

    @Autowired
    protected Context context;

    @Autowired(required = false)
    protected PcapIf pcapIf;

    @Autowired(required = false)
    protected MacAddress macAddress;

    @Autowired(required = false)
    protected Jvm jvm;

    @Autowired(required = false)
    protected PcapHandler<T> pcapHandler;

    protected PcapCode loop(int count, T args) {
        if (pcapHandler != null) {
            return context.pcapLoop(count, pcapHandler, args);
        }
        return PcapCode.PCAP_ERROR;
    }

    protected void showNetworkInfo() {
        LOGGER.info("{}----------------------- {} -----------------------{}", "+", "Network Information", "+");
        LOGGER.info("Network Interface      : {}", pcapIf.getName());
        LOGGER.info("MAC Address            : {}", macAddress);
        LOGGER.info("Addresses");
        for (PcapAddr addr : pcapIf.getAddresses()) {
            if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                LOGGER.info("\tAddress            : {}", Inet4Address.valueOf(addr.getAddr().getData()));
                LOGGER.info("\tNetwork            : {}", Inet4Address.valueOf(addr.getNetmask().getData()));
            }
        }
        LOGGER.info("{}-------------------------------------------------------------------{}", "+", "+");

    }

    protected void showSystemInfo() {
        if (jvm != null && jvm.getOperatingSystem() != null) {
            OperatingSystem os = jvm.getOperatingSystem();
            if (os.isAccessible()) {
                long mbDivider = 20;
                long physicalMemoryInBytes = os.getTotalPhysicalMemorySize();
                long physicalMemoryInMegaBytes = physicalMemoryInBytes >> mbDivider;
                long swapSpaceInBytes = os.getTotalSwapSpaceSize();
                long swapSpaceInMegaBytes = swapSpaceInBytes >> mbDivider;
                LOGGER.info("{}----------------------- {} -----------------------{}", "+", "System Information ", "+");
                LOGGER.info("Operating system       : {}  {}  {}", os.getName(), os.getArch(), os.getVersion());
                if (physicalMemoryInBytes > 0 && physicalMemoryInMegaBytes > 0 && swapSpaceInBytes > 0 && swapSpaceInMegaBytes > 0) {
                    LOGGER.info("Total physical memory  : {} bytes ({} MB)", physicalMemoryInBytes, physicalMemoryInMegaBytes);
                    LOGGER.info("Total swap space       : {} bytes ({} MB)", swapSpaceInBytes, swapSpaceInMegaBytes);
                }
            }
            if (jvm.hasJvm()) {
                LOGGER.info("Available processors   : {} cores", jvm.getAvailableProcessors());
            }
            LOGGER.info("{}-------------------------------------------------------------------{}", "+", "+");
        }
    }

}
