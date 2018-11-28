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

package com.ardikars.jxnet.spring.boot.starter.example;

import com.ardikars.common.net.Inet4Address;
import com.ardikars.common.net.MacAddress;
import com.ardikars.jxnet.Context;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapHandler;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.spring.boot.autoconfigure.PacketHandler;
import com.ardikars.jxpacket.common.Packet;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements PacketHandler<String>, CommandLineRunner  {

    public static final int MAX_PACKET = -1; // infinite loop

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    private static final String PRETTY_FOOTER = ""
            + "+-----------------------------------------------------------------------------------------------------+";

    private Context context;
    private PcapIf pcapIf;
    private MacAddress macAddress;

    @Autowired
    private PcapHandler<String> pcapHandler;

    public Application(Context context, PcapIf pcapIf, MacAddress macAddress) {
        this.context = context;
        this.pcapIf = pcapIf;
        this.macAddress = macAddress;
    }

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Network Interface : {}", pcapIf.getName());
        LOGGER.info("MAC Address       : {}", macAddress);
        LOGGER.info("Addresses         : ");
        for (PcapAddr addr : pcapIf.getAddresses()) {
            if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                LOGGER.info("\tAddress       : {}", Inet4Address.valueOf(addr.getAddr().getData()));
                LOGGER.info("\tNetwork       : {}", Inet4Address.valueOf(addr.getNetmask().getData()));
            }
        }
        context.pcapLoop(MAX_PACKET, pcapHandler, "Jxnet!");
    }

    @Override
    public void next(String argument, PcapPktHdr header, Packet packet) {
        Iterator<Packet> iterator = packet.iterator();
        while (iterator.hasNext()) {
            LOGGER.info(iterator.next().toString());
        }
        LOGGER.info(PRETTY_FOOTER);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
