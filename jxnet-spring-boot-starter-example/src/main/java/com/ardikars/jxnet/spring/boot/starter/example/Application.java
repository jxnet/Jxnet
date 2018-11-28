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
import com.ardikars.jxnet.DataLinkType;
import com.ardikars.jxnet.PcapAddr;
import com.ardikars.jxnet.PcapIf;
import com.ardikars.jxnet.PcapPktHdr;
import com.ardikars.jxnet.SockAddr;
import com.ardikars.jxnet.spring.boot.autoconfigure.jxpacket.DefaultJxpacketHandler;
import com.ardikars.jxpacket.common.Packet;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application implements CommandLineRunner  {

    public static final int MAX_PACKET = -1;

    public static final int WAIT_TIME_FOR_THREAD_TERMINATION = 10000;

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class.getName());

    @Autowired
    private Context context;

    @Autowired
    private PcapIf pcapIf;

    @Autowired
    private MacAddress macAddress;

    @Autowired
    private Handler handler;

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Network Interface : " + pcapIf.getName());
        LOGGER.info("Addresses         : ");
        for (PcapAddr addr : pcapIf.getAddresses()) {
            if (addr.getAddr().getSaFamily() == SockAddr.Family.AF_INET) {
                LOGGER.info("\tAddress       : " + Inet4Address.valueOf(addr.getAddr().getData()));
                LOGGER.info("\tNetwork       : " + Inet4Address.valueOf(addr.getNetmask().getData()));
            }
        }
        LOGGER.info("\tMAC Address   : " + macAddress);
        final ExecutorService pool = Executors.newCachedThreadPool();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                pool.shutdownNow();
            }
        });
        context.pcapLoop(MAX_PACKET, handler, "Jxnet!", pool);
		pool.shutdown();
		pool.awaitTermination(WAIT_TIME_FOR_THREAD_TERMINATION, TimeUnit.MICROSECONDS);
    }

    @Component
    public static class Handler extends DefaultJxpacketHandler<String> {

        public Handler(DataLinkType dataLinkType) {
            super(dataLinkType);
        }

        @Override
        public void next(String argument, PcapPktHdr header, Packet packet) {
            Iterator<Packet> iterator = packet.iterator();
            while (iterator.hasNext()) {
                LOGGER.info(iterator.next().toString());
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
